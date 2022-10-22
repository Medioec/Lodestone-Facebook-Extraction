/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sit.onlinedataextractor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.sql.Timestamp;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.casemodule.NoCurrentCaseException;
import org.sleuthkit.autopsy.casemodule.services.FileManager;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.coreutils.MessageNotifyUtil;
import org.sleuthkit.autopsy.datamodel.ContentUtils;
import org.sleuthkit.autopsy.ingest.FileIngestModule;
import org.sleuthkit.autopsy.ingest.IngestJobContext;
import org.sleuthkit.autopsy.ingest.IngestMessage;
import org.sleuthkit.autopsy.ingest.IngestModule.ProcessResult;
import org.sleuthkit.autopsy.ingest.IngestModuleReferenceCounter;
import org.sleuthkit.autopsy.ingest.IngestMonitor;
import org.sleuthkit.autopsy.ingest.IngestServices;
import org.sleuthkit.autopsy.ingest.ModuleContentEvent;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.Account;
import org.sleuthkit.datamodel.AccountFileInstance;
import org.sleuthkit.datamodel.Blackboard;
import org.sleuthkit.datamodel.Blackboard.BlackboardException;
import org.sleuthkit.datamodel.BlackboardArtifact;
import org.sleuthkit.datamodel.BlackboardAttribute;
import org.sleuthkit.datamodel.BlackboardAttribute.ATTRIBUTE_TYPE;
import org.sleuthkit.datamodel.BlackboardAttribute.TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE;
import org.sleuthkit.datamodel.DerivedFile;
import org.sleuthkit.datamodel.ReadContentInputStream;
import org.sleuthkit.datamodel.Relationship;
import org.sleuthkit.datamodel.Score;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.datamodel.TskData;
import org.sleuthkit.datamodel.TskDataException;
import org.sleuthkit.datamodel.TskException;
import org.sleuthkit.datamodel.blackboardutils.CommunicationArtifactsHelper;
import org.sleuthkit.datamodel.blackboardutils.attributes.MessageAttachments;
import org.sleuthkit.datamodel.blackboardutils.attributes.MessageAttachments.FileAttachment;
import org.sleuthkit.datamodel.SleuthkitCase;

/**
 *
 * @author Eric
 */
public class FacebookFileIngestModule implements FileIngestModule{
    
    private static final Logger logger = Logger.getLogger("org.sit.onlinedataextractor.FacebookFileIngestModule");
    private Case currentCase;
    private Blackboard blackboard;
    private IngestJobContext context = null;
    private static final IngestModuleReferenceCounter refCounter = new IngestModuleReferenceCounter();

    @Override
    public ProcessResult process(AbstractFile af) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        blackboard = currentCase.getSleuthkitCase().getBlackboard();
        logger.log(Level.INFO, "after blackboard");
        
        //skip unalloc
        if ((af.getType().equals(TskData.TSK_DB_FILES_TYPE_ENUM.UNALLOC_BLOCKS))
                || (af.getType().equals(TskData.TSK_DB_FILES_TYPE_ENUM.SLACK))) {
            return ProcessResult.OK;
        }
        
        if ((af.isFile() == false)) {
            return ProcessResult.OK;
        }
        
        if (context.fileIngestIsCancelled()) {
            return ProcessResult.OK;
        }
        
        if (af.isFile() && "json".equals(af.getNameExtension())){
            String filename = af.getName();
            switch (filename) {
                case "comments.json":
                    processComments(af);
                    break;
                case "posts_and_comments.json":
                    processReactions(af);
                    break;
            }
        }
        
        return ProcessResult.OK;
    }

    @Override
    public void startUp(IngestJobContext context) throws IngestModuleException {
        this.context = context;
        refCounter.incrementAndGet(context.getJobId());
        logger.log(Level.INFO, "File Ingest Started");
        try{
            currentCase = Case.getCurrentCaseThrows();
        }
        catch(NoCurrentCaseException e){
            logger.log(Level.SEVERE, "Exception while getting open case.", e);
            throw new IngestModuleException("Error opening case", e);
        }
    }

    @Override
    public void shutDown() {
        FileIngestModule.super.shutDown(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    private void processComments(AbstractFile af){
        int intsize;
        long size = af.getSize();
        byte[] jsonBytes;
        if (size > Integer.MAX_VALUE){
            //do later
            jsonBytes = new byte[Integer.MAX_VALUE];
        }
        else {
            intsize = (int)size;
            jsonBytes = new byte[intsize];
        }
        try{
            af.read(jsonBytes, 0, size);
        }
        catch(TskCoreException e){
            logger.log(Level.SEVERE, "File read failure");
        }
        
        String jsonString = new String(jsonBytes, StandardCharsets.UTF_8);
        JsonParser jsonParser = new JsonParser();
        JsonObject json = (JsonObject)jsonParser.parse(jsonString);
        if(json.has("comments_v2")){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type commentDate;
            BlackboardAttribute.Type commentTitle;
            BlackboardAttribute.Type commentCommentString;
            BlackboardAttribute.Type commentAuthor;
            BlackboardAttribute.Type commentUri;
            BlackboardAttribute.Type commentUrl;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_COMMENT") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_COMMENT", "Facebook Comment");
                    commentDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    commentTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                    commentCommentString = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_COMMENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Comment");
                    commentAuthor = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_AUTHOR", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Author");
                    commentUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URI");
                    commentUrl = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_URL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URL");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_COMMENT");
                    commentDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_DATE");
                    commentTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_TITLE");
                    commentCommentString = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_COMMENT");
                    commentAuthor = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_AUTHOR");
                    commentUri = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_URI");
                    commentUrl = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_URL");
                }
            }
            catch (TskCoreException | TskDataException e){
                // TODO handling
                return;
            }
            
            JsonArray commentsV2 = json.getAsJsonArray("comments_v2");
            for (JsonElement comment:commentsV2){
                
                String date = "";
                String title = "";
                String commentString = "";
                String author = "";
                String uri = "";
                String url = "";
                
                if (comment.isJsonObject()){
                    JsonObject commentObj = (JsonObject)comment;
                    title = commentObj.get("title").getAsString();
                    date = new Date(new Timestamp(commentObj.get("timestamp").getAsLong()).getTime() * 1000).toString();
                    if (commentObj.has("data")){
                        JsonObject commentData = (JsonObject)commentObj.getAsJsonArray("data").get(0);
                        commentString = commentData.getAsJsonObject("comment").get("comment").getAsString();
                        author = commentData.getAsJsonObject("comment").get("author").getAsString();
                    }
                    if (commentObj.has("attachments")){
                        JsonArray attachments = commentObj.getAsJsonArray("attachments");
                        for (JsonElement data:attachments){
                            JsonArray attachArray = ((JsonObject)data).getAsJsonArray("data");
                            for (JsonElement obj:attachArray){
                                if (((JsonObject)obj).has("external_context")){
                                    url = ((JsonObject)obj).getAsJsonObject("external_context").get("url").getAsString();
                                }
                                else if (((JsonObject)obj).has("media")){
                                    uri = ((JsonObject)obj).getAsJsonObject("media").get("uri").getAsString();
                                }
                            }
                        }
                    }
                    
                    // add variables to attributes
                    Collection<BlackboardAttribute> attributelist = new ArrayList();
                    attributelist.add(new BlackboardAttribute(commentDate, FacebookIngestModuleFactory.getModuleName(), date));
                    attributelist.add(new BlackboardAttribute(commentTitle, FacebookIngestModuleFactory.getModuleName(), title));
                    attributelist.add(new BlackboardAttribute(commentCommentString, FacebookIngestModuleFactory.getModuleName(), commentString));
                    attributelist.add(new BlackboardAttribute(commentAuthor, FacebookIngestModuleFactory.getModuleName(), author));
                    attributelist.add(new BlackboardAttribute(commentUri, FacebookIngestModuleFactory.getModuleName(), uri));
                    attributelist.add(new BlackboardAttribute(commentUrl, FacebookIngestModuleFactory.getModuleName(), url));
                    
                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), "FacebookFileIngestModule");
                    }
                    catch (TskCoreException | BlackboardException e){
                        // handle exception
                        return;
                    }
                }
            }
        }
        else{
            logger.log(Level.INFO, "No comments_v2 found");
            return;
        }
        
    }
    
    
    //testing on reactions
    private void processReactions(AbstractFile af){
        int intsize;
        long size = af.getSize();
        byte[] jsonBytes;
        if (size > Integer.MAX_VALUE){
            //do later
            jsonBytes = new byte[Integer.MAX_VALUE];
        }
        else {
            intsize = (int)size;
            jsonBytes = new byte[intsize];
        }
        try{
            af.read(jsonBytes, 0, size);
        }
        catch(TskCoreException e){
            logger.log(Level.SEVERE, "File read failure");
        }
        
        String jsonString = new String(jsonBytes, StandardCharsets.UTF_8);
        JsonParser jsonParser = new JsonParser();
        JsonObject json = (JsonObject)jsonParser.parse(jsonString);
        if(json.has("reactions_v2")){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type reactionDate;
            BlackboardAttribute.Type reactionTitle;
            BlackboardAttribute.Type reactionReactionString;
            BlackboardAttribute.Type reactionAuthor;
            BlackboardAttribute.Type reactionUri;
            BlackboardAttribute.Type reactionUrl;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_COMMENT") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_REACTION", "Facebook reaction");
                    reactionDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBREACTION_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    reactionTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBREACTION_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                    reactionReactionString = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBREACTION_REACTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Comment");
                    reactionAuthor = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBREACTION_AUTHOR", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Author");
                    reactionUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBREACTION_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URI");
                    reactionUrl = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBREACTION_URL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URL");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_REACTION");
                    reactionDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBREACTION_DATE");
                    reactionTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FBREACTION_TITLE");
                    reactionReactionString = currentCase.getSleuthkitCase().getAttributeType("LS_FBREACTION_REACTION");
                    reactionAuthor = currentCase.getSleuthkitCase().getAttributeType("LS_FBREACTION_AUTHOR");
                    reactionUri = currentCase.getSleuthkitCase().getAttributeType("LS_FFBREACTION_URI");
                    reactionUrl = currentCase.getSleuthkitCase().getAttributeType("LS_FBREACTION_URL");
                }
            }
            catch (TskCoreException | TskDataException e){
                // TODO handling
                return;
            }
            
            JsonArray reactionsV2 = json.getAsJsonArray("reactions_v2");
            for (JsonElement reaction:reactionsV2){
                
                String date = "";
                String title = "";
                String reactionString = "";
                String actor = "";
                String uri = "";
                String url = "";
                
                if (reaction.isJsonObject()){
                    JsonObject reactionObj = (JsonObject)reaction;
                    title = reactionObj.get("title").getAsString();
                    date = new Date(new Timestamp(reactionObj.get("timestamp").getAsLong()).getTime() * 1000).toString();
                    if (reactionObj.has("data")){
                        JsonObject reactionData = (JsonObject)reactionObj.getAsJsonArray("data").get(0);
                        reactionString = reactionData.getAsJsonObject("reaction").get("reaction").getAsString();
                        actor = reactionData.getAsJsonObject("reaction").get("actor").getAsString();
                    }
                    if (reactionObj.has("attachments")){
                        JsonArray attachments = reactionObj.getAsJsonArray("attachments");
                        for (JsonElement data:attachments){
                            JsonArray attachArray = ((JsonObject)data).getAsJsonArray("data");
                            for (JsonElement obj:attachArray){
                                if (((JsonObject)obj).has("external_context")){
                                    url = ((JsonObject)obj).getAsJsonObject("external_context").get("url").getAsString();
                                }
                                else if (((JsonObject)obj).has("media")){
                                    uri = ((JsonObject)obj).getAsJsonObject("media").get("uri").getAsString();
                                }
                            }
                        }
                    }
                    
                    // add variables to attributes
                    Collection<BlackboardAttribute> attributelist = new ArrayList();
                    attributelist.add(new BlackboardAttribute(reactionDate, FacebookIngestModuleFactory.getModuleName(), date));
                    attributelist.add(new BlackboardAttribute(reactionTitle, FacebookIngestModuleFactory.getModuleName(), title));
                    attributelist.add(new BlackboardAttribute(reactionReactionString, FacebookIngestModuleFactory.getModuleName(), reactionString));
                    attributelist.add(new BlackboardAttribute(reactionAuthor, FacebookIngestModuleFactory.getModuleName(), actor));
                    attributelist.add(new BlackboardAttribute(reactionUri, FacebookIngestModuleFactory.getModuleName(), uri));
                    attributelist.add(new BlackboardAttribute(reactionUrl, FacebookIngestModuleFactory.getModuleName(), url));
                    
                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), "FacebookFileIngestModule");
                    }
                    catch (TskCoreException | BlackboardException e){
                        // handle exception
                        return;
                    }
                }
            }
        }
        else{
            logger.log(Level.INFO, "No reactions_v2 found");
            return;
        }
        
    }
    
    
    
}
