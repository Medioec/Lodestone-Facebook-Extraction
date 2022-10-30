/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import org.lodestone.facebookingest.pojo.*;
import org.lodestone.facebookingest.pojo.GeneralPosts.General_Posts;
import org.lodestone.facebookingest.utility.TimestampToDate;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.casemodule.NoCurrentCaseException;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.ingest.FileIngestModule;
import org.sleuthkit.autopsy.ingest.IngestJobContext;
import org.sleuthkit.autopsy.ingest.IngestModule.ProcessResult;
import org.sleuthkit.autopsy.ingest.IngestModuleReferenceCounter;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.Blackboard;
import org.sleuthkit.datamodel.Blackboard.BlackboardException;
import org.sleuthkit.datamodel.BlackboardArtifact;
import org.sleuthkit.datamodel.BlackboardAttribute;
import org.sleuthkit.datamodel.BlackboardAttribute.TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.datamodel.TskData;
import org.sleuthkit.datamodel.TskDataException;

/**
 *
 * @author Eric
 */
public class FacebookFileIngestModule implements FileIngestModule{
    
    private static final Logger logger = Logger.getLogger("org.lodestone.facebookingest.FacebookFileIngestModule");
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
                case "group_interactions.json":
                    processJSONgroup_interactions(af);
                    break;
                case "people_and_friends.json":
                    processJSONpeople_and_friends(af);
                    break;
                case "advertisers_using_your_activity_or_information.json":
                    processJSONadvertisers_using_your_activity_or_information(af);
                    break;
                case "advertisers_you've_interacted_with.json":
                    processJSONadvertisers_youve_interacted_with(af);
                    break;
                case "apps_and_websites.json":
                    processJSONapps_and_websites(af);
                    break;
                case "your_off-facebook_activity.json":
                    processJSONyour_off_facebook_activity(af);
                    break;
                case "comments.json":
                    processJSONcomments(af);
                    break;
                case "posts_and_comments.json":
                    processJSONposts_and_comments(af);
                    break;
                case "accounts_center.json":
                    processJSONaccounts_center(af);
                    break;
                case "event_invitations.json":
                    processJSONevent_invitations(af);
                    break;
                case "your_event_responses.json":
                    processJSONyour_event_responses(af);
                    break;
                case "items_sold.json":
                    processJSONitems_sold(af);
                    break;
                case "payment_history.json":
                    processJSONpayment_history(af);
                    break;
                case "friends.json":
                    processJSONfriends(af);
                    break;
                case "friend_requests_received.json":
                    processJSONfriend_requests_received(af);
                    break;
                case "friend_requests_sent.json":
                    processJSONfriend_requests_sent(af);
                    break;
                case "rejected_friend_requests.json":
                    processJSONrejected_friend_requests(af);
                    break;
                case "removed_friends.json":
                    processJSONremoved_friends(af);
                    break;
                case "who_you_follow.json":
                    processJSONwho_you_follow(af);
                    break;
                case "your_comments_in_groups.json":
                    processJSONyour_comments_in_groups(af);
                    break;
                case "your_group_membership_activity.json":
                    processJSONyour_group_membership_activity(af);
                    break;
                case "your_posts_in_groups.json":
                    processJSONgeneralPosts(af, "group_posts_v2");
                    break;
                case "last_location.json":
                    processJSONlast_location(af);
                    break;
                case "primary_public_location.json":
                    processJSONprimary_public_location(af);
                    break;
                case "device_location.json":
                    processJSONdevice_location(af);
                    break;
                case "timezone.json":
                    processJSONtimezone(af);
                    break;
                case "autofill_information.json":
                    processJSONautofill_information(af);
                    break;
                case "secret_conversations.json":
                    processJSONsecret_conversations(af);
                    break;
                case "support_messages.json":
                    processJSONsupport_messages(af);
                    break;
                case "message_1.json":
                    processJSONmessage_1(af);
                    break;
                case "notifications.json":
                    processJSONnotifications(af);
                    break;
                case "ads_interests.json":
                    processJSONads_interests(af);
                    break;
                //case "friend_peer_group.json":
                    //processJSON(af);
                    //break;
                case "pages_and_profiles_you_follow.json":
                    processJSONpages_and_profiles_you_follow(af);
                    break;
                case "pages_you've_liked.json":
                    processJSONpages_youve_liked(af);
                    break;
                case "your_posts_1.json":
                    processJSONgeneralPosts(af, "your_post1");
                    break;
                case "your_uncategorized_photos.json":
                    processJSONyour_uncategorized_photos(af);
                    break;
                case "your_videos.json":
                    processJSONyour_videos(af);
                    break;
                //case "language_and_locale.json":
                    //processJSON(af);
                    //break;
                case "profile_information.json":
                    processJSONprofile_information(af);
                    break;
                case "profile_update_history.json":
                    processJSONgeneralPosts(af, "profile_updates_v2");
                    break;
                case "collections.json":
                    processJSONcollections(af);
                    break;
                case "your_saved_items.json":
                    processJSONgeneralPosts(af, "saves_v2");
                    //break;
                case "your_search_history.json":
                    processJSONyour_search_history(af);
                    break;
                case "account_activity.json":
                    processJSONaccount_activity(af);
                    break;
                //case "browser_cookies.json":
                    //processJSON(af);
                    //break;
                case "email_address_verifications.json":
                    processJSONemail_address_verifications(af);
                    break;
                case "ip_address_activity.json":
                    processJSONip_address_activity(af);
                    break;
                case "logins_and_logouts.json":
                    processJSONlogins_and_logouts(af);
                    break;
                case "login_protection_data.json":
                    processJSONlogin_protection_data(af);
                    break;
                case "mobile_devices.json":
                    processJSONmobile_devices(af);
                    break;
                case "record_details.json":
                    //processJSON(af);
                    break;
                case "where_you're_logged_in.json":
                    processJSONwhere_youre_logged_in(af);
                    break;
                case "your_facebook_activity_history.json":
                    //processJSON(af);
                    break;
                //case "location.json":
                    //processJSON(af);
                    //break;
                //case "voting_reminders.json":
                    //processJSON(af);
                    //break;
                case "recently_viewed.json":
                    processJSONrecently_viewed(af);
                    break;
                case "recently_visited.json":
                    processJSONrecently_visited(af);
                    break;
                case "your_topics.json":
                    //processJSON(af);
                    break;
                case "places_you've_created.json":
                    processJSONplaces_youve_created(af);
                    break;
                case "posts_from_apps_and_websites.json":
                    processJSONposts_from_apps_and_websites(af);
                    break;
                case "your_address_books.json":
                    processJSONyour_address_books(af);
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
    
    /**
    * Process your_address_books.json file and add data as Data Artifact
    * Facebook your_address_books data.
    * Uses POJO YourAddressBooksV2
    *
    * @param  af  JSON file
    */
    private void processJSONyour_address_books(AbstractFile af){
        String json = parseAFtoString(af);
        YourAddressBooksV2 addressBooks = new Gson().fromJson(json, YourAddressBooksV2.class);
        if (addressBooks.address_book_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type bbName;
            BlackboardAttribute.Type bbCreateDate;
            BlackboardAttribute.Type bbUpdateDate;
            BlackboardAttribute.Type bbContactPoint;
            
            try{
                
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_ADDRESSBOOK") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_ADDRESSBOOK", "Facebook Address Book");
                    bbName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADDRESSBOOK_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    bbCreateDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADDRESSBOOK_CREATEDDATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Created Date");
                    bbUpdateDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADDRESSBOOK_UPDATEDDATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Updated Date");
                    bbContactPoint = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADDRESSBOOK_CONTACTPOINT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Contact Point");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_ADDRESSBOOK");
                    bbName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADDRESSBOOK_NAME");
                    bbCreateDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADDRESSBOOK_CREATEDDATE");
                    bbUpdateDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADDRESSBOOK_UPDATEDDATE");
                    bbContactPoint = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADDRESSBOOK_CONTACTPOINT");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (YourAddressBooksV2.Address_Book_V2.Address_Book contact:addressBooks.address_book_v2.address_book){
                String name = "";
                String createDate = "";
                String updateDate = "";
                String contactPoint = "";
                Collection<BlackboardAttribute> attributelist = new ArrayList<>();
                
                name = contact.name;
                createDate = new TimestampToDate(contact.created_timestamp).getDate();
                updateDate = new TimestampToDate(contact.updated_timestamp).getDate();
                
                if(contact.details != null){
                    for(YourAddressBooksV2.Address_Book_V2.Address_Book.Details detail:contact.details){
                        contactPoint = detail.contact_point;
                        attributelist.add(new BlackboardAttribute(bbName, FacebookIngestModuleFactory.getModuleName(), name));
                        attributelist.add(new BlackboardAttribute(bbCreateDate, FacebookIngestModuleFactory.getModuleName(), createDate));
                        attributelist.add(new BlackboardAttribute(bbUpdateDate, FacebookIngestModuleFactory.getModuleName(), updateDate));
                        attributelist.add(new BlackboardAttribute(bbContactPoint, FacebookIngestModuleFactory.getModuleName(), contactPoint));
                        try{
                            blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                        }
                        catch (TskCoreException | BlackboardException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }
                else{
                    attributelist.add(new BlackboardAttribute(bbName, FacebookIngestModuleFactory.getModuleName(), name));
                    attributelist.add(new BlackboardAttribute(bbCreateDate, FacebookIngestModuleFactory.getModuleName(), createDate));
                    attributelist.add(new BlackboardAttribute(bbUpdateDate, FacebookIngestModuleFactory.getModuleName(), updateDate));
                    attributelist.add(new BlackboardAttribute(bbContactPoint, FacebookIngestModuleFactory.getModuleName(), contactPoint));
                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }
    
    /**
    * Process posts_from_apps_and_websites.json file and add data as Data Artifact
    * Facebook posts_from_apps_and_websites data.
    * Uses POJO AppAndWebPostsV2
    *
    * @param  af  JSON file
    */
    private void processJSONposts_from_apps_and_websites(AbstractFile af){
        String json = parseAFtoString(af);
        AppAndWebPostsV2 appPosts = new Gson().fromJson(json, AppAndWebPostsV2.class);
        if (appPosts.app_posts_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type bbDate;
            BlackboardAttribute.Type bbTitle;
            BlackboardAttribute.Type bbName;
            BlackboardAttribute.Type bbUrl;
            
            try{
                
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_APPPOSTS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_APPPOSTS", "Facebook Posts From Apps and Websites");
                    bbDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_APPPOSTS_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    bbTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_APPPOSTS_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                    bbName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_APPPOSTS_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    bbUrl = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_APPPOSTS_URL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URL");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_APPPOSTS");
                    bbDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_APPPOSTS_DATE");
                    bbTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FB_APPPOSTS_TITLE");
                    bbName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_APPPOSTS_NAME");
                    bbUrl = currentCase.getSleuthkitCase().getAttributeType("LS_FB_APPPOSTS_URL");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (AppAndWebPostsV2.App_Posts_V2 post:appPosts.app_posts_v2){
                String date = "";
                String title = "";
                String name = "";
                String url = "";
                
                date = new TimestampToDate(post.timestamp).getDate();
                title = post.title;
                
                if(post.attachments != null){
                    AppAndWebPostsV2.App_Posts_V2.Attachments.Data data = post.attachments.get(0).data.get(0);
                    if(data.external_context != null){
                        name = data.external_context.name;
                        url = data.external_context.url;
                    }
                }
                 // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList<>();
                attributelist.add(new BlackboardAttribute(bbDate, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(bbTitle, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(bbName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(bbUrl, FacebookIngestModuleFactory.getModuleName(), url));
                
                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
    * Process recently_viewed.json file and add data as Data Artifact
    * Facebook recently_viewed data.
    * Uses POJO recently_viewed.json
    *
    * @param  af  JSON file
    */
    private void processJSONrecently_viewed(AbstractFile af){
        String json = parseAFtoString(af);
        RecentlyViewedV2 recentlyViewedV2 = new Gson().fromJson(json, RecentlyViewedV2.class);
        if (recentlyViewedV2.recently_viewed != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type artifactName;
            BlackboardAttribute.Type artifactDescription;
            BlackboardAttribute.Type artifactChildName;
            BlackboardAttribute.Type artifactChildDescription;
            BlackboardAttribute.Type artifactTimeStamp;
            BlackboardAttribute.Type artifactDataName;
            BlackboardAttribute.Type artifactURI;
            BlackboardAttribute.Type artifactValue;
            
            try{
                
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FBRECENTLY_VIEWED") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FBRECENTLY_VIEWED", "Facebook Recently Viewed");
                    artifactName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VIEWED_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    artifactDescription = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VIEWED_DESCRIPTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Description");
                    artifactChildName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VIEWED_CHILD_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Child Name");
                    artifactChildDescription = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VIEWED_CHILD_DESCRIPTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Child Description");
                    artifactTimeStamp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VIEWED_TIME_STAMP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Time Stamp");
                    artifactDataName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VIEWED_DATA_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Data Name");
                    artifactURI = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VIEWED_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URI");
                    artifactValue = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VIEWED_VALUE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Value");
                    
                    
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FBRECENTLY_VIEWED");
                    artifactName = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VIEWED_NAME");
                    artifactDescription = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VIEWED_DESCRIPTION");
                    artifactChildName = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VIEWED_CHILD_NAME");
                    artifactChildDescription = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VIEWED_CHILD_DESCRIPTION");
                    artifactTimeStamp = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VIEWED_TIME_STAMP");
                    artifactDataName = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VIEWED_DATA_NAME");
                    artifactURI = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VIEWED_URI");
                    artifactValue = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VIEWED_VALUE");
                    
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (RecentlyViewedV2.RecentViews recentView:recentlyViewedV2.recently_viewed){
                String childName = "";
                String childDescription = "";
                String timeStamp = "";
                String dataName = "";
                String uri = "";    
                String value = "";
                
                String name = recentView.name;
                String description = recentView.description;
                 // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(artifactName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(artifactDescription, FacebookIngestModuleFactory.getModuleName(), description));
                if (recentView.children != null){
                    for (RecentlyViewedV2.RecentViews.Child child:recentView.children){
                        childName = child.name;
                        childDescription = child.description;
                        attributelist.add(new BlackboardAttribute(artifactChildName, FacebookIngestModuleFactory.getModuleName(), childName));
                        attributelist.add(new BlackboardAttribute(artifactChildDescription, FacebookIngestModuleFactory.getModuleName(), childDescription));
                        if (child.entries != null){
                            for (RecentlyViewedV2.RecentViews.Child.Entry entries:child.entries){
                                timeStamp = new TimestampToDate(entries.timestamp).getDate();
                                dataName = entries.data.name;
                                uri = entries.data.uri;
                                value = entries.data.value;
                                attributelist.add(new BlackboardAttribute(artifactTimeStamp, FacebookIngestModuleFactory.getModuleName(), timeStamp));
                                attributelist.add(new BlackboardAttribute(artifactDataName, FacebookIngestModuleFactory.getModuleName(), dataName));
                                attributelist.add(new BlackboardAttribute(artifactURI, FacebookIngestModuleFactory.getModuleName(), uri));
                                attributelist.add(new BlackboardAttribute(artifactValue, FacebookIngestModuleFactory.getModuleName(), value));
                                try{
                                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                                }
                                catch (TskCoreException | BlackboardException e){
                                    e.printStackTrace();
                                    return;
                                }
                            }
                        }
                    }
                }
                else
                {
                    if (recentView.entries != null){
                        for (RecentlyViewedV2.RecentViews.Entry entries:recentView.entries){
                            timeStamp = new TimestampToDate(entries.timestamp).getDate();
                            dataName = entries.data.name;
                            uri = entries.data.uri;
                            value = entries.data.value;
                            attributelist.add(new BlackboardAttribute(artifactTimeStamp, FacebookIngestModuleFactory.getModuleName(), timeStamp));
                            attributelist.add(new BlackboardAttribute(artifactDataName, FacebookIngestModuleFactory.getModuleName(), dataName));
                            attributelist.add(new BlackboardAttribute(artifactURI, FacebookIngestModuleFactory.getModuleName(), uri));
                            attributelist.add(new BlackboardAttribute(artifactValue, FacebookIngestModuleFactory.getModuleName(), value));
 
                            try{
                                blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                            }
                            catch (TskCoreException | BlackboardException e){
                                e.printStackTrace();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
    * Process visited_things_v2.json file and add data as Data Artifact
    * Facebook visited_things_v2 data.
    * Uses POJO visited_things_v2.json
    *
    * @param  af  JSON file
    */
    private void processJSONrecently_visited(AbstractFile af){
        String json = parseAFtoString(af);
        RecentlyVisitedV2 recentlyVisitedV2 = new Gson().fromJson(json, RecentlyVisitedV2.class);
        if (recentlyVisitedV2.visited_things_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type artifactName;
            BlackboardAttribute.Type artifactDescription;
            BlackboardAttribute.Type artifactTimeStamp;
            BlackboardAttribute.Type artifactDataName;
            BlackboardAttribute.Type artifactURI;
            BlackboardAttribute.Type artifactValue;
            
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FBRECENTLY_VISITED") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FBRECENTLY_VISITED", "Facebook Recently Visited");
                    artifactName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VISITED_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    artifactDescription = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VISITED_DESCRIPTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Description");
                    artifactTimeStamp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VISITED_TIME_STAMP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Time Stamp");
                    artifactDataName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VISITED_DATA_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Data Name");
                    artifactURI = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VISITED_DATA_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URI");
                    artifactValue = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBRECENTLY_VISITED_VALUE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "VALUE");
                    
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FBRECENTLY_VISITED");
                    artifactName = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VISITED_NAME");
                    artifactDescription = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VISITED_DESCRIPTION");
                    artifactTimeStamp = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VISITED_TIME_STAMP");
                    artifactDataName = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VISITED_DATA_NAME");
                    artifactURI = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VISITED_DATA_URI");
                    artifactValue = currentCase.getSleuthkitCase().getAttributeType("LS_FBRECENTLY_VISITED_VALUE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (RecentlyVisitedV2.recentLogins recentLog:recentlyVisitedV2.visited_things_v2){
                
                             
                String timeStamp = "";
                String dataName = "";
                String uri = "";
                String value = "";
                
                String name = recentLog.name;
                String description = recentLog.description;

                 // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(artifactName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(artifactDescription, FacebookIngestModuleFactory.getModuleName(), description));
                if (recentLog.entries != null){
                    for (RecentlyVisitedV2.recentLogins.Entry entry:recentLog.entries){
                        timeStamp = new TimestampToDate(entry.timestamp).getDate();
                        dataName = entry.data.dataName;
                        uri = entry.data.uri;
                        value = entry.data.value;
                        attributelist.add(new BlackboardAttribute(artifactTimeStamp, FacebookIngestModuleFactory.getModuleName(), timeStamp));
                        attributelist.add(new BlackboardAttribute(artifactDataName, FacebookIngestModuleFactory.getModuleName(), dataName));
                        attributelist.add(new BlackboardAttribute(artifactURI, FacebookIngestModuleFactory.getModuleName(), uri));
                        attributelist.add(new BlackboardAttribute(artifactValue, FacebookIngestModuleFactory.getModuleName(), value));
                        try{
                            blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                        }
                        catch (TskCoreException | BlackboardException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }
        }
    }
    
    /**
    * Process your_search_history.json file and add data as Data Artifact
    * Facebook search history data.
    * Uses POJO SearchHistoryV2.java
    *
    * @param  af  JSON file
    */
    private void processJSONyour_search_history(AbstractFile af){
        String json = parseAFtoString(af);
        SearchHistoryV2 history = new Gson().fromJson(json, SearchHistoryV2.class);
        if (history.searches_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type histDate;
            BlackboardAttribute.Type histTitle;
            BlackboardAttribute.Type histText;
            
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_SEARCHHISTORY") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_SEARCHHISTORY", "Facebook Search History");
                    histDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_SEARCHHISTORY_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    histTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_SEARCHHISTORY_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                    histText = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_SEARCHHISTORY_TEXT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Text");
                    
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_SEARCHHISTORY");
                    histDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_SEARCHHISTORY_DATE");
                    histTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FB_SEARCHHISTORY_TITLE");
                    histText = currentCase.getSleuthkitCase().getAttributeType("LS_FB_SEARCHHISTORY_TEXT");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (SearchHistoryV2.Searches_V2 search:history.searches_v2){
                
                String date = new TimestampToDate(search.timestamp).getDate();
                String title = search.title;
                String text = search.data.get(0).text;

                 // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(histDate, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(histTitle, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(histText, FacebookIngestModuleFactory.getModuleName(), text));
                
                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
    * Process active_sessions_v2.json file and add data as Data Artifact
    * Facebook active_sessions_v2 data.
    * Uses POJO active_sessions_v2.json
    *
    * @param  af  JSON file
    */
    private void processJSONwhere_youre_logged_in(AbstractFile af){
        String json = parseAFtoString(af);
        ActiveSessionsV2 activeSessions = new Gson().fromJson(json, ActiveSessionsV2.class);
        if (activeSessions.active_sessions_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type artifactCreatedTimeStamp;
            BlackboardAttribute.Type artifactip_address;
            BlackboardAttribute.Type artifactuser_agent;
            BlackboardAttribute.Type artifactdatr_cookie;
            BlackboardAttribute.Type artifactdevice;
            BlackboardAttribute.Type artifactlocation;
            BlackboardAttribute.Type artifactapp;
            BlackboardAttribute.Type artifactsession_type;
            
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FBACTIVESESSIONS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FBACTIVESESSIONS", "Facebook Active Session");
                    artifactCreatedTimeStamp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVESESSIONS_TIME_STAMP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Time Stamp");
                    artifactip_address = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVESESSIONS_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "IP Address");
                    artifactuser_agent = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVESESSIONS_AGENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Agent");
                    artifactdatr_cookie = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVESESSIONS_COOKIE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Datr Cookie");
                    artifactdevice = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVESESSIONS_DEVICE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Device");
                    artifactlocation = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVESESSIONS_LOCATION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Location");
                    artifactapp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVESESSIONS_APP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "App");
                    artifactsession_type = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVESESSIONS_SESSION_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Session Type");
                    
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FBACTIVESESSIONS");
                    artifactCreatedTimeStamp = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVESESSIONS_TIME_STAMP");
                    artifactip_address = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVESESSIONS_IP");
                    artifactuser_agent = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVESESSIONS_AGENT");
                    artifactdatr_cookie = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVESESSIONS_COOKIE");
                    artifactdevice = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVESESSIONS_DEVICE");
                    artifactlocation = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVESESSIONS_LOCATION");
                    artifactapp = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVESESSIONS_APP");
                    artifactsession_type = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVESESSIONS_SESSION_TYPE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (ActiveSessionsV2.activeSessions actSession:activeSessions.active_sessions_v2){
                
                String timeStamp = new TimestampToDate(actSession.createdTimeStamp).getDate();
                String ip_address = actSession.ip_address;
                String user_agent = actSession.user_agent;
                String datr_cookie = actSession.datr_cookie;
                String device = actSession.device;
                String location = actSession.location;
                String app = actSession.app;
                String session_type = actSession.session_type;
                
                                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(artifactCreatedTimeStamp, FacebookIngestModuleFactory.getModuleName(), timeStamp));
                attributelist.add(new BlackboardAttribute(artifactip_address, FacebookIngestModuleFactory.getModuleName(), ip_address));
                attributelist.add(new BlackboardAttribute(artifactuser_agent, FacebookIngestModuleFactory.getModuleName(), user_agent));
                attributelist.add(new BlackboardAttribute(artifactdatr_cookie, FacebookIngestModuleFactory.getModuleName(), datr_cookie));
                attributelist.add(new BlackboardAttribute(artifactdevice, FacebookIngestModuleFactory.getModuleName(), device));
                attributelist.add(new BlackboardAttribute(artifactlocation, FacebookIngestModuleFactory.getModuleName(), location));
                attributelist.add(new BlackboardAttribute(artifactapp, FacebookIngestModuleFactory.getModuleName(), app));
                attributelist.add(new BlackboardAttribute(artifactsession_type, FacebookIngestModuleFactory.getModuleName(), session_type));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
    * Process account_accesses_v2.json file and add data as Data Artifact
    * Facebook account_accesses_v2 data.
    * Uses POJO account_accesses_v2.json
    *
    * @param  af  JSON file
    */
    private void processJSONlogins_and_logouts(AbstractFile af){
        String json = parseAFtoString(af);
        LoginsAndLogoutsV2 loginsAndLogouts = new Gson().fromJson(json, LoginsAndLogoutsV2.class);
        if (loginsAndLogouts.account_accesses_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type artifactAction;
            BlackboardAttribute.Type artifactTimeStamp;
            BlackboardAttribute.Type artifactSite;
            BlackboardAttribute.Type artifactIP;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FBLOGINS_AND_LOGOUTS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FBLOGINS_AND_LOGOUTS", "Facebook Login and Logouts");
                    artifactAction = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBLOGINS_AND_LOGOUTS_ACTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Action");
                    artifactTimeStamp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBLOGINS_AND_LOGOUTS_TIME_STAMP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Time Stamp");
                    artifactSite = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBLOGINS_AND_LOGOUTS_SITE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Site");
                    artifactIP = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBLOGINS_AND_LOGOUTS_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "IP Address");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FBLOGINS_AND_LOGOUTS");
                    artifactAction = currentCase.getSleuthkitCase().getAttributeType("LS_FBLOGINS_AND_LOGOUTS_ACTION");
                    artifactTimeStamp = currentCase.getSleuthkitCase().getAttributeType("LS_FBLOGINS_AND_LOGOUTS_TIME_STAMP");
                    artifactSite = currentCase.getSleuthkitCase().getAttributeType("LS_FBLOGINS_AND_LOGOUTS_SITE");
                    artifactIP = currentCase.getSleuthkitCase().getAttributeType("LS_FBLOGINS_AND_LOGOUTS_IP");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (LoginsAndLogoutsV2.loginAndLogout loginsdetails:loginsAndLogouts.account_accesses_v2){
                
                String action = loginsdetails.action;
                String timeStamp = new TimestampToDate(loginsdetails.timestamp).getDate();
                String site = loginsdetails.site;
                String ip = loginsdetails.ip;
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(artifactAction, FacebookIngestModuleFactory.getModuleName(), action));
                attributelist.add(new BlackboardAttribute(artifactTimeStamp, FacebookIngestModuleFactory.getModuleName(), timeStamp));
                attributelist.add(new BlackboardAttribute(artifactSite, FacebookIngestModuleFactory.getModuleName(), site));
                attributelist.add(new BlackboardAttribute(artifactIP, FacebookIngestModuleFactory.getModuleName(), ip));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
    * Process login_protection_data.json file and add data as Data Artifact
    * Facebook login protection data.
    *
    * @param  af  JSON file
    */
    private void processJSONlogin_protection_data(AbstractFile af){
        String json = parseAFtoString(af);
        LoginProtectionDataV2 loginProtectionDataV2 = new Gson().fromJson(json,LoginProtectionDataV2.class);
        if (loginProtectionDataV2.login_protection_data_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type artifactName;
            BlackboardAttribute.Type artifactCreatedDate;
            BlackboardAttribute.Type artifactUpdatedTimeStamp;
            BlackboardAttribute.Type artifactIP;
            try{ 
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_LOGIN_PROTECTION_V2") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_LOGIN_PROTECTION_V2", "Facebook Login Protection");
                    artifactName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBLOGINPROTECT_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    artifactCreatedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBLOGINPROTECT_CREATE_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Create Date");
                    artifactUpdatedTimeStamp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBLOGINPROTECT_UPDATED_TIME_STAMP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Updated Time Stamp");
                    artifactIP = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBLOGINPROTECT_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "IP Address");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_LOGIN_PROTECTION_V2");
                    artifactName = currentCase.getSleuthkitCase().getAttributeType("LS_FBLOGINPROTECT_NAME");
                    artifactCreatedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBLOGINPROTECT_CREATE_DATE");
                    artifactUpdatedTimeStamp = currentCase.getSleuthkitCase().getAttributeType("LS_FBLOGINPROTECT_UPDATED_TIME_STAMP");
                    artifactIP = currentCase.getSleuthkitCase().getAttributeType("LS_FBLOGINPROTECT_UPDATED_IP");
                }   
                
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            
            for (LoginProtectionDataV2.loginProtection loginProtect:loginProtectionDataV2.login_protection_data_v2){
                String name = loginProtect.name;
                String createdDate = new TimestampToDate(loginProtect.session.createdDate).getDate();
                String updatedTimeStamp = new TimestampToDate(loginProtect.session.updatedTimeStamp).getDate();
                String ip = loginProtect.session.ip;
                                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(artifactName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(artifactCreatedDate, FacebookIngestModuleFactory.getModuleName(), createdDate));
                attributelist.add(new BlackboardAttribute(artifactUpdatedTimeStamp, FacebookIngestModuleFactory.getModuleName(), updatedTimeStamp));
                attributelist.add(new BlackboardAttribute(artifactIP, FacebookIngestModuleFactory.getModuleName(), ip));
                try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
                        return;
                    }
            }
        }
    }
    
    /**
    * Process mobile_devices.json file and add data as Data Artifact
    * Facebook IP address activity data.
    * Uses POJO DevicesV2.json
    *
    * @param  af  JSON file
    */
    private void processJSONmobile_devices(AbstractFile af){
        String json = parseAFtoString(af);
        MobileDevicesV2 yourDevice = new Gson().fromJson(json, MobileDevicesV2.class);
        if (yourDevice.devices_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type type;
            BlackboardAttribute.Type os;
            BlackboardAttribute.Type updateTime;
            BlackboardAttribute.Type advertiserId;
            BlackboardAttribute.Type uuid;
            BlackboardAttribute.Type tokenType;
            BlackboardAttribute.Type redactToken;
            BlackboardAttribute.Type familyDeviceId;
            BlackboardAttribute.Type deviceLocale;
            BlackboardAttribute.Type disabled;
            BlackboardAttribute.Type clientUpdateTime;
            BlackboardAttribute.Type creationTime;
            BlackboardAttribute.Type appVersion;
            BlackboardAttribute.Type locale;
            BlackboardAttribute.Type osVersion;
            BlackboardAttribute.Type token;
            BlackboardAttribute.Type deviceId;
            
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_MOBILEDEVICES") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_MOBILEDEVICES", "Facebook Mobile Devices");
                    type = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Type");
                    os = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_OS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "OS");
                    updateTime = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_UPDATETIME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Update Time");
                    advertiserId = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_ADVERTISERID", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Advertiser ID");
                    uuid = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_UUID", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "UUID");
                    tokenType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_TOKENTYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Token Type");
                    redactToken = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_REDACTTOKEN", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Redacted Token");
                    familyDeviceId = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_FAMILYDEVICEID", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Family Device ID");
                    deviceLocale = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_DEVICELOCALE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Device Locale");
                    disabled = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_DISABLED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Disabled");
                    clientUpdateTime = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_CLIENTUPDATETIME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Client Update Time");
                    creationTime = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_CREATIONTIME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Creation Time");
                    appVersion = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_APPVERSION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "App Version");
                    locale = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_LOCALE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Locale");
                    osVersion = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_OSVERSION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "OS Version");
                    token = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_TOKEN", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Token");
                    deviceId = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_MOBILEDEVICES_DEVICEID", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Device ID");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_MOBILEDEVICES");
                    type = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_TYPE");
                    os = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_OS");
                    updateTime = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_UPDATETIME");
                    advertiserId = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_ADVERTISERID");
                    uuid = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_UUID");
                    tokenType = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_TOKENTYPE");
                    redactToken = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_REDACTTOKEN");
                    familyDeviceId = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_FAMILYDEVICEID");
                    deviceLocale = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_DEVICELOCALE");
                    disabled = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_DISABLED");
                    clientUpdateTime = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_CLIENTUPDATETIME");
                    creationTime = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_CREATIONTIME");
                    appVersion = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_APPVERSION");
                    locale = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_LOCALE");
                    osVersion = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_OSVERSION");
                    token = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_TOKEN");
                    deviceId = currentCase.getSleuthkitCase().getAttributeType("LS_FB_MOBILEDEVICES_DEVICEID");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (MobileDevicesV2.Devices_V2 device:yourDevice.devices_v2){
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(type, FacebookIngestModuleFactory.getModuleName(), device.type));
                attributelist.add(new BlackboardAttribute(os, FacebookIngestModuleFactory.getModuleName(), device.os));
                attributelist.add(new BlackboardAttribute(updateTime, FacebookIngestModuleFactory.getModuleName(), new TimestampToDate(device.update_time).getDate()));
                attributelist.add(new BlackboardAttribute(advertiserId, FacebookIngestModuleFactory.getModuleName(), device.advertiser_id));
                attributelist.add(new BlackboardAttribute(uuid, FacebookIngestModuleFactory.getModuleName(), device.uuid));
                attributelist.add(new BlackboardAttribute(familyDeviceId, FacebookIngestModuleFactory.getModuleName(), device.family_device_id));
                attributelist.add(new BlackboardAttribute(deviceLocale, FacebookIngestModuleFactory.getModuleName(), device.device_locale));
                for (String deviceToken:device.redact_tokens){
                    String tokenTypeString = "Redacted Token";
                    attributelist.add(new BlackboardAttribute(tokenType, FacebookIngestModuleFactory.getModuleName(), tokenTypeString));
                    
                    attributelist.add(new BlackboardAttribute(redactToken, FacebookIngestModuleFactory.getModuleName(), deviceToken));
                    
                    String empty = "";
                    attributelist.add(new BlackboardAttribute(disabled, FacebookIngestModuleFactory.getModuleName(), empty));
                    attributelist.add(new BlackboardAttribute(clientUpdateTime, FacebookIngestModuleFactory.getModuleName(), empty));
                    attributelist.add(new BlackboardAttribute(creationTime, FacebookIngestModuleFactory.getModuleName(), empty));
                    attributelist.add(new BlackboardAttribute(appVersion, FacebookIngestModuleFactory.getModuleName(), empty));
                    attributelist.add(new BlackboardAttribute(locale, FacebookIngestModuleFactory.getModuleName(), empty));
                    attributelist.add(new BlackboardAttribute(osVersion, FacebookIngestModuleFactory.getModuleName(), empty));
                    attributelist.add(new BlackboardAttribute(token, FacebookIngestModuleFactory.getModuleName(), empty));
                    attributelist.add(new BlackboardAttribute(deviceId, FacebookIngestModuleFactory.getModuleName(), empty));
                    
                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
                        return;
                    }
                    // remove attributes when done
                    int removeCount = 10;
                    ArrayList<BlackboardAttribute> arrList = (ArrayList<BlackboardAttribute>)attributelist;
                    for (int i = 0; i < removeCount; i++){
                        int arrSize = arrList.size();
                        arrList.remove(arrSize - 1);
                    }
                }
                
                for (MobileDevicesV2.Devices_V2.Push_Tokens pushToken:device.push_tokens){
                    String tokenTypeString = "Push Token";
                    attributelist.add(new BlackboardAttribute(tokenType, FacebookIngestModuleFactory.getModuleName(), tokenTypeString));

                    String empty = "";
                    
                    attributelist.add(new BlackboardAttribute(redactToken, FacebookIngestModuleFactory.getModuleName(), empty));
                    
                    attributelist.add(new BlackboardAttribute(disabled, FacebookIngestModuleFactory.getModuleName(), pushToken.disabled));
                    attributelist.add(new BlackboardAttribute(clientUpdateTime, FacebookIngestModuleFactory.getModuleName(), new TimestampToDate(pushToken.client_update_time).getDate()));
                    attributelist.add(new BlackboardAttribute(creationTime, FacebookIngestModuleFactory.getModuleName(), new TimestampToDate(pushToken.creation_time).getDate()));
                    attributelist.add(new BlackboardAttribute(appVersion, FacebookIngestModuleFactory.getModuleName(), pushToken.app_version));
                    attributelist.add(new BlackboardAttribute(locale, FacebookIngestModuleFactory.getModuleName(), pushToken.locale));
                    attributelist.add(new BlackboardAttribute(osVersion, FacebookIngestModuleFactory.getModuleName(), pushToken.os_version));
                    attributelist.add(new BlackboardAttribute(token, FacebookIngestModuleFactory.getModuleName(), pushToken.token));
                    attributelist.add(new BlackboardAttribute(deviceId, FacebookIngestModuleFactory.getModuleName(), pushToken.device_id));
                    
                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
                        return;
                    }
                    // remove attributes when done
                    int removeCount = 1;
                    ArrayList<BlackboardAttribute> arrList = (ArrayList<BlackboardAttribute>)attributelist;
                    for (int i = 0; i < removeCount; i++){
                        int arrSize = arrList.size();
                        arrList.remove(arrSize - 1);
                    }
                }
            }
        }
    }
    
    /**
    * Process your_places_v2.json file and add data as Data Artifact
    * Facebook your_places_v2 data.
    * Uses POJO YourPlacesV2.json
    *
    * @param  af  JSON file
    */
    private void processJSONplaces_youve_created(AbstractFile af){
        String json = parseAFtoString(af);
        YourPlacesV2 yourPlaces = new Gson().fromJson(json, YourPlacesV2.class);
        if (yourPlaces.your_places_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type placeName;
            BlackboardAttribute.Type placeAddress;
            BlackboardAttribute.Type placeUrl;
            BlackboardAttribute.Type placeTimestamp;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_YOURPLACES") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_YOURPLACES", "Facebook Your Places");
                    placeName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_YOURPLACES_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    placeAddress = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_YOURPLACES_ADDRESS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Address");
                    placeUrl = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_YOURPLACES_URL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "External Context URL");
                    placeTimestamp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_YOURPLACES_TIMESTAMP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_YOURPLACES");
                    placeName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_YOURPLACES_NAME");
                    placeAddress = currentCase.getSleuthkitCase().getAttributeType("LS_FB_YOURPLACES_ADDRESS");
                    placeUrl = currentCase.getSleuthkitCase().getAttributeType("LS_FB_YOURPLACES_URL");
                    placeTimestamp = currentCase.getSleuthkitCase().getAttributeType("LS_FB_YOURPLACES_TIMESTAMP");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (YourPlacesV2.Your_Places_V2 place:yourPlaces.your_places_v2){
                String date = new TimestampToDate(place.creation_timestamp).getDate();
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(placeName, FacebookIngestModuleFactory.getModuleName(), place.name));
                attributelist.add(new BlackboardAttribute(placeAddress, FacebookIngestModuleFactory.getModuleName(), place.address));
                attributelist.add(new BlackboardAttribute(placeUrl, FacebookIngestModuleFactory.getModuleName(), place.url));
                attributelist.add(new BlackboardAttribute(placeTimestamp, FacebookIngestModuleFactory.getModuleName(), date));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
	
    /**
    * Process ip_address_activity.json file and add data as Data Artifact
    * Facebook IP address activity data.
    *
    * @param  af  JSON file
    */
    private void processJSONip_address_activity(AbstractFile af){
        String json = parseAFtoString(af);
        UsedIpAddressV2 usedIpAddress = new Gson().fromJson(json, UsedIpAddressV2.class);
        if (usedIpAddress.used_ip_address_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type artifactIP;
            BlackboardAttribute.Type artifactAction;
            BlackboardAttribute.Type artifactTimeStamp;
            BlackboardAttribute.Type artifactUserAgent;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_IP_ADDRESS_ACTIVITY") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_IP_ADDRESS_ACTIVITY", "Facebook IP Address Activity");
                    artifactIP = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBADDRESSACTIVITY_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "IP Address");
                    artifactAction = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBADDRESSACTIVITY_ACTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Action");
                    artifactTimeStamp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBADDRESSACTIVITY_TIME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Time");
                    artifactUserAgent = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBADDRESSACTIVITY_USER_AGENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User Agent");                   
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FBADDRESSACTIVITY_IP_ADDRESS_ACTIVITY");
                    artifactIP = currentCase.getSleuthkitCase().getAttributeType("LS_FBADDRESSACTIVITY_IP");
                    artifactAction = currentCase.getSleuthkitCase().getAttributeType("LS_FBADDRESSACTIVITY_ACTION");
                    artifactTimeStamp = currentCase.getSleuthkitCase().getAttributeType("LS_FBADDRESSACTIVITY_TIME");
                    artifactUserAgent = currentCase.getSleuthkitCase().getAttributeType("LS_FBADDRESSACTIVITY_USER_AGENT");
                }   
                
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (UsedIpAddressV2.IP usedIp:usedIpAddress.used_ip_address_v2){
                String ip = usedIp.ip;
                String action = usedIp.action;
                String date = new TimestampToDate(usedIp.timestamp).getDate();
                String user_agent = usedIp.user_agent;
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(artifactIP, FacebookIngestModuleFactory.getModuleName(), ip));
                attributelist.add(new BlackboardAttribute(artifactAction, FacebookIngestModuleFactory.getModuleName(), action));
                attributelist.add(new BlackboardAttribute(artifactTimeStamp, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(artifactUserAgent, FacebookIngestModuleFactory.getModuleName(), user_agent));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
    * Process email_address_verifications.json file and add data as Data Artifact
    * Facebook email_address_verifications data.
    *
    * @param  af  JSON file
    */
    private void processJSONemail_address_verifications(AbstractFile af){
        String json = parseAFtoString(af);
        ContactVerificationV2 contactVerificationV2 = new Gson().fromJson(json,ContactVerificationV2.class);
        if (contactVerificationV2.contact_verifications_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type artifactContact;
            BlackboardAttribute.Type artifactContact_type;
            BlackboardAttribute.Type artifactVerification_time;
            try{ 
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_CONTACT_VERIFICATIONV2") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_CONTACT_VERIFICATIONV2", "Facebook Contact Verification");
                    artifactContact = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_CONTACT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Contact");
                    artifactContact_type = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_CONTACT_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Contact Type");
                    artifactVerification_time = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_TIME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Verification Time");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_CONTACT_VERIFICATIONV2");
                    artifactContact = currentCase.getSleuthkitCase().getAttributeType("LS_FB_CONTACT");
                    artifactContact_type = currentCase.getSleuthkitCase().getAttributeType("LS_FB_CONTACT_TYPE");
                    artifactVerification_time = currentCase.getSleuthkitCase().getAttributeType("LS_FB_TIME");
                }   
                
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (ContactVerificationV2.Contact validContact:contactVerificationV2.contact_verifications_v2){
                String contact = validContact.contact;
                String contact_type = validContact.contact;
                String verification_time = new TimestampToDate(validContact.verification_time).getDate();
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(artifactContact, FacebookIngestModuleFactory.getModuleName(), contact));
                attributelist.add(new BlackboardAttribute(artifactContact_type, FacebookIngestModuleFactory.getModuleName(), contact_type));
                attributelist.add(new BlackboardAttribute(artifactVerification_time, FacebookIngestModuleFactory.getModuleName(), verification_time));
               
                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
    * Process account_activity.json file and add data as Data Artifact
    * Facebook account_activity data.
    *
    * @param  af  JSON file
    */
    private void processJSONaccount_activity(AbstractFile af){
        String json = parseAFtoString(af);
        AccountActivityV2 accountActivityV2 = new Gson().fromJson(json, AccountActivityV2.class);
        if (accountActivityV2.account_activity_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type artifactAction;
            BlackboardAttribute.Type artifactTimeStamp;
            BlackboardAttribute.Type artifactIP_Address;
            BlackboardAttribute.Type artifactUser_agent;
            BlackboardAttribute.Type artifactDatr_cookie;
            BlackboardAttribute.Type artifactCity;
            BlackboardAttribute.Type artifactRegion;
            BlackboardAttribute.Type artifactCountry;
            BlackboardAttribute.Type artifactSite_name;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_ACCOUNT_ACTIVITY") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_ACCOUNT_ACTIVITY", "Facebook Account Activity");
                    artifactAction = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACCOUNTACTIVITY_ACTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Action");
                    artifactTimeStamp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACCOUNTACTIVITY_TIME_STAMP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Time Stamp");
                    artifactIP_Address = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACCOUNTACTIVITY_IP_ADDRESS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "IP Address");
                    artifactUser_agent = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACCOUNTACTIVITY_USER_AGENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User Agent");
                    artifactDatr_cookie = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACCOUNTACTIVITY_DATR_COOKIE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Datr_cookie");
                    artifactCity = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACCOUNTACTIVITY_DATR_CITY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "City");
                    artifactRegion = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACCOUNTACTIVITY_DATR_REGION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Region");
                    artifactCountry = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACCOUNTACTIVITY_DATR_COUNTRY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Country");
                    artifactSite_name = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACCOUNTACTIVITY_DATR_SITE_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Site Name");
                    
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_ACCOUNT_ACTIVITY");
                    artifactAction = currentCase.getSleuthkitCase().getAttributeType("LS_FBACCOUNTACTIVITY_ACTION");
                    artifactTimeStamp = currentCase.getSleuthkitCase().getAttributeType("LS_FBACCOUNTACTIVITY_TIME_STAMP");
                    artifactIP_Address = currentCase.getSleuthkitCase().getAttributeType("LS_FBACCOUNTACTIVITY_IP_ADDRESS");
                    artifactUser_agent = currentCase.getSleuthkitCase().getAttributeType("LS_FBACCOUNTACTIVITY_USER_AGENT");
                    artifactDatr_cookie = currentCase.getSleuthkitCase().getAttributeType("LS_FBACCOUNTACTIVITY_DATR_COOKIE");
                    artifactCity = currentCase.getSleuthkitCase().getAttributeType("LS_FBACCOUNTACTIVITY_DATR_CITY");
                    artifactRegion = currentCase.getSleuthkitCase().getAttributeType("LS_FBACCOUNTACTIVITY_DATR_REGION");
                    artifactCountry = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ACCOUNTACTIVITY_DATR_COUNTRY");
                    artifactSite_name = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ACCOUNTACTIVITY_DATR_SITE_NAME");
                    
                }   
                
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (AccountActivityV2.Actions accountActivity:accountActivityV2.account_activity_v2){
                String action = accountActivity.action;
                String timeStamp = new TimestampToDate(accountActivity.timestamp).getDate();
                String ip = accountActivity.ip_address;
                String user_agent = accountActivity.user_agent;
                String datr_cookie = accountActivity.datr_cookie;
                String city = accountActivity.city;
                String region = accountActivity.region;
                String country = accountActivity.country;
                String site_name = accountActivity.site_name;
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(artifactAction, FacebookIngestModuleFactory.getModuleName(), action));
                attributelist.add(new BlackboardAttribute(artifactTimeStamp, FacebookIngestModuleFactory.getModuleName(), timeStamp));
                attributelist.add(new BlackboardAttribute(artifactIP_Address, FacebookIngestModuleFactory.getModuleName(), ip));
                attributelist.add(new BlackboardAttribute(artifactUser_agent, FacebookIngestModuleFactory.getModuleName(), user_agent));
                attributelist.add(new BlackboardAttribute(artifactDatr_cookie, FacebookIngestModuleFactory.getModuleName(), datr_cookie));
                attributelist.add(new BlackboardAttribute(artifactCity, FacebookIngestModuleFactory.getModuleName(), city));
                attributelist.add(new BlackboardAttribute(artifactRegion, FacebookIngestModuleFactory.getModuleName(), region));
                attributelist.add(new BlackboardAttribute(artifactCountry, FacebookIngestModuleFactory.getModuleName(), country));
                attributelist.add(new BlackboardAttribute(artifactSite_name, FacebookIngestModuleFactory.getModuleName(), site_name));
                
                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
     * Process friends.json file and add data as Data Artifact
     * Facebook friends data
     * Uses POJO FriendsV2.java
     * 
     * @param af JSON file
     */
    private void processJSONfriends(AbstractFile af){
        String json = parseAFtoString(af);
        FriendsV2 friends = new Gson().fromJson(json, FriendsV2.class);
        if (friends.friends_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type friendName;
            BlackboardAttribute.Type friendDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_FRIENDS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_FRIENDS", "Facebook Friends");
                    friendName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_FRIENDS_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    friendDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_FRIENDS_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Added");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_FRIENDS");
                    friendName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_FRIENDS_NAME");
                    friendDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_FRIENDS_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (FriendsV2.FriendsArrV2 friend:friends.friends_v2){
                String date = new TimestampToDate(friend.timestamp).getDate();
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(friendName, FacebookIngestModuleFactory.getModuleName(), friend.name));
                attributelist.add(new BlackboardAttribute(friendDate, FacebookIngestModuleFactory.getModuleName(), date));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
     * Process friend_requests_received.json file and add data as Data Artifact
     * Facebook friends data
     * Uses POJO FriendRequestsReceived.java
     * 
     * @param af JSON file
     */
    private void processJSONfriend_requests_received(AbstractFile af){
        String json = parseAFtoString(af);
        FriendRequestsReceivedV2 receivedRequests = new Gson().fromJson(json, FriendRequestsReceivedV2.class);
        if (receivedRequests.received_requests_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type receivedRequestName;
            BlackboardAttribute.Type receivedRequestDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_FRIENDREQUESTSRECEIVED") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_FRIENDREQUESTSRECEIVED", "Facebook Friend Requests Received");
                    receivedRequestName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_FRIENDREQUESTRECEIVED_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    receivedRequestDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_FRIENDREQUESTRECEIVED_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Received");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_FRIENDREQUESTSRECEIVED");
                    receivedRequestName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_FRIENDREQUESTRECEIVED_NAME");
                    receivedRequestDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_FRIENDREQUESTRECEIVED_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (FriendRequestsReceivedV2.ReceivedFriendRequest request:receivedRequests.received_requests_v2){
                String date = new TimestampToDate(request.timestamp).getDate();
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(receivedRequestName, FacebookIngestModuleFactory.getModuleName(), request.name));
                attributelist.add(new BlackboardAttribute(receivedRequestDate, FacebookIngestModuleFactory.getModuleName(), date));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
     * Process friend_requests_sent.json file and add data as Data Artifact
     * Facebook friends data
     * Uses POJO FriendRequestsSentV2.java
     * 
     * @param af JSON file
     */
    private void processJSONfriend_requests_sent(AbstractFile af){
        String json = parseAFtoString(af);
        FriendRequestsSentV2 sentRequests = new Gson().fromJson(json, FriendRequestsSentV2.class);
        if (sentRequests.sent_requests_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type sentRequestName;
            BlackboardAttribute.Type sentRequestDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_FRIENDREQUESTSSENT") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_FRIENDREQUESTSSENT", "Facebook Friend Requests Sent");
                    sentRequestName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_FRIENDREQUESTSSENT_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    sentRequestDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_FRIENDREQUESTSSENT_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Sent");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_FRIENDREQUESTSSENT");
                    sentRequestName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_FRIENDREQUESTSSENT_NAME");
                    sentRequestDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_FRIENDREQUESTSSENT_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (FriendRequestsSentV2.SentFriendRequest request:sentRequests.sent_requests_v2){
                String date = new TimestampToDate(request.timestamp).getDate();
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(sentRequestName, FacebookIngestModuleFactory.getModuleName(), request.name));
                attributelist.add(new BlackboardAttribute(sentRequestDate, FacebookIngestModuleFactory.getModuleName(), date));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
     * Process rejected_friend_requests.json file and add data as Data Artifact
     * Facebook friends data
     * Uses POJO RejectedFriendsV2.java
     * 
     * @param af JSON file
     */
    private void processJSONrejected_friend_requests(AbstractFile af){
        String json = parseAFtoString(af);
        RejectedFriendsV2 rejectedFriends = new Gson().fromJson(json, RejectedFriendsV2.class);
        if (rejectedFriends.rejected_requests_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type rejectedName;
            BlackboardAttribute.Type rejectedDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_REJECTEDFRIENDS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_REJECTEDFRIENDS", "Facebook Friend Requests Rejected");
                    rejectedName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_REJECTEDFRIENDS_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    rejectedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_REJECTEDFRIENDS_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Rejected");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_REJECTEDFRIENDS");
                    rejectedName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_REJECTEDFRIENDS_NAME");
                    rejectedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_REJECTEDFRIENDS_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (RejectedFriendsV2.RejectedRequests rejected:rejectedFriends.rejected_requests_v2){
                String date = new TimestampToDate(rejected.timestamp).getDate();
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(rejectedName, FacebookIngestModuleFactory.getModuleName(), rejected.name));
                attributelist.add(new BlackboardAttribute(rejectedDate, FacebookIngestModuleFactory.getModuleName(), date));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
     * Process removed_friends.json file and add data as Data Artifact
     * Facebook friends data
     * Uses POJO RemovedFriendsV2.java
     * 
     * @param af JSON file
     */
    private void processJSONremoved_friends(AbstractFile af){
        String json = parseAFtoString(af);
        RemovedFriendsV2 removedFriends = new Gson().fromJson(json, RemovedFriendsV2.class);
        if (removedFriends.deleted_friends_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type rejectedName;
            BlackboardAttribute.Type rejectedDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_REMOVEDFRIENDS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_REMOVEDFRIENDS", "Facebook Friends Deleted");
                    rejectedName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_REMOVEDFRIENDS_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    rejectedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_REMOVEDFRIENDS_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Removed");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_REMOVEDFRIENDS");
                    rejectedName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_REMOVEDFRIENDS_NAME");
                    rejectedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_REMOVEDFRIENDS_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (RemovedFriendsV2.DeletedFriends deleted:removedFriends.deleted_friends_v2){
                String date = new TimestampToDate(deleted.timestamp).getDate();
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(rejectedName, FacebookIngestModuleFactory.getModuleName(), deleted.name));
                attributelist.add(new BlackboardAttribute(rejectedDate, FacebookIngestModuleFactory.getModuleName(), date));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
     * Process who_you_follow.json file and add data as Data Artifact
     * Facebook friends data
     * Uses POJO FollowingV2.java
     * 
     * @param af JSON file
     */
    private void processJSONwho_you_follow(AbstractFile af){
        String json = parseAFtoString(af);
        FollowingV2 following = new Gson().fromJson(json, FollowingV2.class);
        if (following.following_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type followedName;
            BlackboardAttribute.Type followedDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_FOLLOWING") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_FOLLOWING", "Facebook People and Groups Followed");
                    followedName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_FOLLOWING_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    followedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_FOLLOWING_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Followed");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_FOLLOWING");
                    followedName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_FOLLOWING_NAME");
                    followedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_FOLLOWING_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            for (FollowingV2.Following follow:following.following_v2){
                String date = new TimestampToDate(follow.timestamp).getDate();
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(followedName, FacebookIngestModuleFactory.getModuleName(), follow.name));
                attributelist.add(new BlackboardAttribute(followedDate, FacebookIngestModuleFactory.getModuleName(), date));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
    * Process group_interactions.json file and add data as Data Artifact
    * Facebook group interaction data.
    * Uses POJO GroupInteractionsV2.java
    *
    * @param  af  JSON file
    */
    private void processJSONgroup_interactions(AbstractFile af){
        String json = parseAFtoString(af);
        GroupInteractionsV2 groupInteractions = new Gson().fromJson(json, GroupInteractionsV2.class);

        if(groupInteractions.group_interactions_v2 != null){

            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type groupInteractionName;
            BlackboardAttribute.Type groupInteractionValue;
            BlackboardAttribute.Type groupInteractionUri;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_GROUP_INTERACTIONS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_GROUP_INTERACTIONS", "Facebook Group Interactions");
                    groupInteractionName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_GROUPINTERACTION_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Group Name");
                    groupInteractionValue = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_GROUPINTERACTION_VALUE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Number of Interactions");
                    groupInteractionUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_GROUPINTERACTION_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URI");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_GROUP_INTERACTIONS");
                    groupInteractionName = currentCase.getSleuthkitCase().getAttributeType("LS_GROUPINTERACTION_NAME");
                    groupInteractionValue = currentCase.getSleuthkitCase().getAttributeType("LS_GROUPINTERACTION_VALUE");
                    groupInteractionUri = currentCase.getSleuthkitCase().getAttributeType("LS_GROUPINTERACTION_URI");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }

            for (GroupInteractionsV2.GroupInteractions_V2 groupInteraction:groupInteractions.group_interactions_v2) {
                for (GroupInteractionsV2.GroupInteractions_V2.Entries entry:groupInteraction.entries) {
                    if (entry.data != null) {
                        String name = entry.data.name;
                        String value = entry.data.value;
                        String uri = entry.data.uri;

                        // add variables to attributes
                        Collection<BlackboardAttribute> attributelist = new ArrayList();
                        attributelist.add(new BlackboardAttribute(groupInteractionName, FacebookIngestModuleFactory.getModuleName(), name));
                        attributelist.add(new BlackboardAttribute(groupInteractionValue, FacebookIngestModuleFactory.getModuleName(), value));
                        attributelist.add(new BlackboardAttribute(groupInteractionUri, FacebookIngestModuleFactory.getModuleName(), uri));

                        try{
                            blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                        }
                        catch (TskCoreException | BlackboardException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }
        }
        else{
            logger.log(Level.INFO, "No group_interactions_v2 found");
            return;
        }
    }
    
    /**
    * Process people_and_friends.json file and add data as Data Artifact
    * Facebook people and friend interaction data.
    * Uses POJO PeopleInteractionsV2.java
    *
    * @param  af  JSON file
    */
    private void processJSONpeople_and_friends(AbstractFile af){
        String json = parseAFtoString(af);
        PeopleInteractionsV2 peopleInteractions = new Gson().fromJson(json, PeopleInteractionsV2.class);

        if(peopleInteractions.people_interactions_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type peopleInteractionDate;
            BlackboardAttribute.Type peopleInteractionName;
            BlackboardAttribute.Type peopleInteractionUri;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_PEOPLE_INTERACTIONS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_PEOPLE_INTERACTIONS", "Facebook People and Friends Interactions");
                    peopleInteractionDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_PEOPLE_INTERACTION_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Most recent date");
                    peopleInteractionName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_PEOPLE_INTERACTION_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Group Name");
                    peopleInteractionUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_PEOPLE_INTERACTION_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URI");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_PEOPLE_INTERACTIONS");
                    peopleInteractionDate = currentCase.getSleuthkitCase().getAttributeType("LS_PEOPLE_INTERACTION_DATE");
                    peopleInteractionName = currentCase.getSleuthkitCase().getAttributeType("LS_PEOPLE_INTERACTION_NAME");
                    peopleInteractionUri = currentCase.getSleuthkitCase().getAttributeType("LS_PEOPLE_INTERACTION_URI");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (PeopleInteractionsV2.PeopleInteractions_V2 peopleInteraction:peopleInteractions.people_interactions_v2) {
                for (PeopleInteractionsV2.PeopleInteractions_V2.Entries entry:peopleInteraction.entries) {
                    if (entry.data != null) {
                        String date = new TimestampToDate(entry.timestamp).getDate();
                        String name = entry.data.name;
                        String uri = entry.data.uri;

                        // add variables to attributes
                        Collection<BlackboardAttribute> attributelist = new ArrayList();
                        attributelist.add(new BlackboardAttribute(peopleInteractionDate, FacebookIngestModuleFactory.getModuleName(), date));
                        attributelist.add(new BlackboardAttribute(peopleInteractionName, FacebookIngestModuleFactory.getModuleName(), name));
                        attributelist.add(new BlackboardAttribute(peopleInteractionUri, FacebookIngestModuleFactory.getModuleName(), uri));

                        try{
                            blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                        }
                        catch (TskCoreException | BlackboardException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }
        }
        else{
            logger.log(Level.INFO, "No group_interactions_v2 found");
            return;
        }
    }
    
    /**
    * Process advertisers_using_your_activity_or_information.json file and add data as Data Artifact
    * Facebook data about advertisers using your activity and information.
    *
    * @param  af  JSON file
    */
    private void processJSONadvertisers_using_your_activity_or_information(AbstractFile af){
        String json = parseAFtoString(af);
        CustomAudienceAllTypesV2 advertisers = new Gson().fromJson(json, CustomAudienceAllTypesV2.class);
        if(advertisers.custom_audiences_all_types_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type advertiserName;
            BlackboardAttribute.Type hasDataFileCustomAudience;
            BlackboardAttribute.Type hasRemarketingCustomAudience;
            BlackboardAttribute.Type hasInPersonStoreVisit;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_ADVERTISER_INFO_USAGE") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_ADVERTISER_INFO_USAGE", "Facebook Advertisers using User Activities and Information");
                    advertiserName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADVERTISER_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Advertiser Name");
                    hasDataFileCustomAudience = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADVERTISER_DATAFILE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Has Data File Custom Audience");
                    hasRemarketingCustomAudience = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADVERTISER_REMARKETING", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Has Remarketing Custom Audience");
                    hasInPersonStoreVisit = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADVERTISER_INPERSON_STORE_VISIT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Has in-person Store Visit");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_ADVERTISER_INFO_USAGE");
                    advertiserName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADVERTISER_NAME");
                    hasDataFileCustomAudience = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADVERTISER_DATAFILE");
                    hasRemarketingCustomAudience = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADVERTISER_REMARKETING");
                    hasInPersonStoreVisit = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADVERTISER_INPERSON_STORE_VISIT");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (CustomAudienceAllTypesV2.Advertiser advertiser:advertisers.custom_audiences_all_types_v2){
                
                String name = advertiser.advertiser_name;
                String dataFile = advertiser.has_data_file_custom_audience;
                String remarketing = advertiser.has_remarketing_custom_audience;
                String inPersonStoreVisit = advertiser.has_in_person_store_visit;
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(advertiserName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(hasDataFileCustomAudience, FacebookIngestModuleFactory.getModuleName(), dataFile));
                attributelist.add(new BlackboardAttribute(hasRemarketingCustomAudience, FacebookIngestModuleFactory.getModuleName(), remarketing));
                attributelist.add(new BlackboardAttribute(hasInPersonStoreVisit, FacebookIngestModuleFactory.getModuleName(), inPersonStoreVisit));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No group_interactions_v2 found");
            return;
        }
    }
    
    /**
    * Process processJSONadvertisers_you've_interacted_with.json file and add data as Data Artifact
    * Facebook data about advertisers the user have interacted with.
    *
    * @param  af  JSON file
    */
    private void processJSONadvertisers_youve_interacted_with(AbstractFile af){
        String json = parseAFtoString(af);
        HistoryV2 advertisements = new Gson().fromJson(json, HistoryV2.class);
        if(advertisements.history_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type advertismentTitle;
            BlackboardAttribute.Type advertismentAction;
            BlackboardAttribute.Type advertismentDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_ADVERTISEMENT") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_ADVERTISEMENT", "Facebook Advertisers that User interacted with");
                    advertismentTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADVERTISEMENT_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Advertisement Title");
                    advertismentAction = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADVERTISEMENT_ACTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Action");
                    advertismentDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_ADVERTISEMENT_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_ADVERTISEMENT");
                    advertismentTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADVERTISEMENT_TITLE");
                    advertismentAction = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADVERTISEMENT_ACTION");
                    advertismentDate = currentCase.getSleuthkitCase().getAttributeType("LS_FB_ADVERTISEMENT_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (HistoryV2.Advertisement advertisement:advertisements.history_v2){
                
                String title = advertisement.title;
                String action = advertisement.action;
                String date = new TimestampToDate(advertisement.timestamp).getDate();

                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(advertismentTitle, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(advertismentAction, FacebookIngestModuleFactory.getModuleName(), action));
                attributelist.add(new BlackboardAttribute(advertismentDate, FacebookIngestModuleFactory.getModuleName(), date));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No group_interactions_v2 found");
            return;
        }
    }
    
    /**
    * Process apps_and_websites.json file and add data as Data Artifact
    * Facebook data about apps and websites that user connected with their Facebook account
    *
    * @param  af  JSON file
    */
    private void processJSONapps_and_websites(AbstractFile af){
        String json = parseAFtoString(af);
        InstalledAppsV2 installedApps = new Gson().fromJson(json, InstalledAppsV2.class);
        if(installedApps.installed_apps_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type appName;
            BlackboardAttribute.Type appDateAdded;
            BlackboardAttribute.Type userAppScopedId;
            BlackboardAttribute.Type appCategory;
            BlackboardAttribute.Type appDateRemoved;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FB_APP") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_APP", "Facebook Apps and Websites that User connected with");
                    appName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_APP_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "App Name");
                    appDateAdded = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_APP_DATE_ADDED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Added");
                    userAppScopedId = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_APP_SCOPED_ID", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "App Scoped ID");
                    appCategory = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_APP_STATUS_CATEGORY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Status Category");
                    appDateRemoved = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FB_APP_DATE_REMOVED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Removed");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FB_APP");
                    appName = currentCase.getSleuthkitCase().getAttributeType("LS_FB_APP_NAME");
                    appDateAdded = currentCase.getSleuthkitCase().getAttributeType("LS_FB_APP_DATE_ADDED");
                    userAppScopedId = currentCase.getSleuthkitCase().getAttributeType("LS_FB_APP_SCOPED_ID");
                    appCategory = currentCase.getSleuthkitCase().getAttributeType("LS_FB_APP_STATUS_CATEGORY");
                    appDateRemoved = currentCase.getSleuthkitCase().getAttributeType("LS_FB_APP_DATE_REMOVED");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (InstalledAppsV2.InstalledApp app:installedApps.installed_apps_v2){
                
                String name = app.name;
                String addedDate = new TimestampToDate(app.added_timestamp).getDate();
                String scopedId = app.user_app_scoped_id;
                String category = app.category;
                String removedDate = new TimestampToDate(app.removed_timestamp).getDate();

                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(appName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(appDateAdded, FacebookIngestModuleFactory.getModuleName(), addedDate));
                attributelist.add(new BlackboardAttribute(userAppScopedId, FacebookIngestModuleFactory.getModuleName(), scopedId));
                attributelist.add(new BlackboardAttribute(appCategory, FacebookIngestModuleFactory.getModuleName(), category));
                attributelist.add(new BlackboardAttribute(appDateRemoved, FacebookIngestModuleFactory.getModuleName(), removedDate));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No group_interactions_v2 found");
            return;
        }
    }
    
    /**
    * Process your_off-facebook_activity.json file and add data as Data Artifact
    * Facebook data about activities from the businesses and organisations that user visit off Facebook
    *
    * @param  af  JSON file
    */
    private void processJSONyour_off_facebook_activity(AbstractFile af){
        String json = parseAFtoString(af);
        OffFacebookActivityV2 offFacebookActivity = new Gson().fromJson(json, OffFacebookActivityV2.class);
        if(offFacebookActivity.off_facebook_activity_v2 != null){
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type organisationName;
            BlackboardAttribute.Type organisationId;
            BlackboardAttribute.Type activityType;
            BlackboardAttribute.Type activityDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_OFF_FB_ACTIVITY") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_OFF_FB_ACTIVITY", "Facebook Organisation Activities User visited off-Facebook");
                    organisationName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_OFF_FB_ORG_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Organisation Name");
                    organisationId = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_OFF_FB_ORG_ID", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Activity ID");
                    activityType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_OFF_FB_ACTIVITY_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Activity Type");
                    activityDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_OFF_FB_ACTIVITY_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_OFF_FB_ACTIVITY");
                    organisationName = currentCase.getSleuthkitCase().getAttributeType("LS_OFF_FB_ORG_NAME");
                    organisationId = currentCase.getSleuthkitCase().getAttributeType("LS_OFF_FB_ORG_ID");
                    activityType = currentCase.getSleuthkitCase().getAttributeType("LS_OFF_FB_ACTIVITY_TYPE");
                    activityDate = currentCase.getSleuthkitCase().getAttributeType("LS_OFF_FB_ACTIVITY_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (OffFacebookActivityV2.Organisation organisation:offFacebookActivity.off_facebook_activity_v2){
                
                String id = "";
                String type = "";
                String date = "";
                
                String name = organisation.name;
                if (organisation.events != null){
                    for (OffFacebookActivityV2.Organisation.Event event:organisation.events){
                        id = event.id;
                        type = event.type;
                        date = new TimestampToDate(event.timestamp).getDate();

                        // add variables to attributes
                        Collection<BlackboardAttribute> attributelist = new ArrayList();
                        attributelist.add(new BlackboardAttribute(organisationName, FacebookIngestModuleFactory.getModuleName(), name));
                        attributelist.add(new BlackboardAttribute(organisationId, FacebookIngestModuleFactory.getModuleName(), id));
                        attributelist.add(new BlackboardAttribute(activityType, FacebookIngestModuleFactory.getModuleName(), type));
                        attributelist.add(new BlackboardAttribute(activityDate, FacebookIngestModuleFactory.getModuleName(), date));

                        try{
                            blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                        }
                        catch (TskCoreException | BlackboardException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }
        }
        else{
            logger.log(Level.INFO, "No group_interactions_v2 found");
            return;
        }
    }
    
    /**
    * Process comments.json file and add data as Data Artifact
    * Facebook comment data.
    * Uses POJO CommentsV2.java
    *
    * @param  af  JSON file
    */
    private void processJSONcomments(AbstractFile af){
        String json = parseAFtoString(af);
        CommentsV2 comments = new Gson().fromJson(json, CommentsV2.class);
        if(comments.comments_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type comment_Date;
            BlackboardAttribute.Type comment_Title;
            BlackboardAttribute.Type comment_CommentString;
            BlackboardAttribute.Type comment_Author;
            BlackboardAttribute.Type comment_mediaUri;
            BlackboardAttribute.Type comment_mediaCreatedDate;
            BlackboardAttribute.Type comment_mediaDescription;
            BlackboardAttribute.Type comment_mediaUploadIp;
            BlackboardAttribute.Type comment_mediaDateTaken;
            BlackboardAttribute.Type comment_mediaDateModified;
            BlackboardAttribute.Type comment_mediaDateUploaded;
            BlackboardAttribute.Type comment_mediaIso;
            BlackboardAttribute.Type comment_mediaFocalLength;
            BlackboardAttribute.Type comment_mediaCameraMake;
            BlackboardAttribute.Type comment_mediaCameraModel;
            BlackboardAttribute.Type comment_mediaExposure;
            BlackboardAttribute.Type comment_mediaFstop;
            BlackboardAttribute.Type comment_mediaOrientation;
            BlackboardAttribute.Type comment_EC_url;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_COMMENT") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_COMMENT", "Facebook Comment");
                    comment_Date = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    comment_Title = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                    comment_CommentString = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_COMMENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Comment");
                    comment_Author = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_AUTHOR", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Author");
                    comment_mediaUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media URI");
                    comment_mediaCreatedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_DATE_CREATED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Created");
                    comment_mediaDescription = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_DESCRIPTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Description");
                    comment_mediaUploadIp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Uploaded from IP");
                    comment_mediaDateTaken = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_DATE_TAKEN", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Taken");
                    comment_mediaDateModified = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_DATE_MODIFIED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Modified");
                    comment_mediaDateUploaded = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_DATE_UPLOADED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Uploaded");
                    comment_mediaIso = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_ISO", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "ISO");
                    comment_mediaFocalLength = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_FOCAL_LENGTH", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Focal Length");
                    comment_mediaCameraMake = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_CAMERA_MAKE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Camera Make");
                    comment_mediaCameraModel = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_CAMERA_MODEL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Camera Model");
                    comment_mediaExposure = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_EXPOSURE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Exposure");
                    comment_mediaFstop = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_F_STOP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "F-stop");
                    comment_mediaOrientation = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_MEDIA_ORIENTATION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Orientation");
                    comment_EC_url = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBCOMMENT_URL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "External Context URL");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_COMMENT");
                    comment_Date = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_DATE");
                    comment_Title = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_TITLE");
                    comment_CommentString = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_COMMENT");
                    comment_Author = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_AUTHOR");
                    comment_mediaUri = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_URI");
                    comment_mediaCreatedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_DATE_CREATED");
                    comment_mediaDescription = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_DESCRIPTION");
                    comment_mediaUploadIp = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_IP");
                    comment_mediaDateTaken = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_DATE_TAKEN");
                    comment_mediaDateModified = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_DATE_MODIFIED");
                    comment_mediaDateUploaded = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_DATE_UPLOADED");
                    comment_mediaIso = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_ISO");
                    comment_mediaFocalLength = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_FOCAL_LENGTH");
                    comment_mediaCameraMake = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_CAMERA_MAKE");
                    comment_mediaCameraModel = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_CAMERA_MODEL");
                    comment_mediaExposure = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_EXPOSURE");
                    comment_mediaFstop = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_F_STOP");
                    comment_mediaOrientation = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_MEDIA_ORIENTATION");
                    comment_EC_url = currentCase.getSleuthkitCase().getAttributeType("LS_FBCOMMENT_URL");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (CommentsV2.Comments_V2 comment:comments.comments_v2){
                String commentString = "";
                String author = "";
                // Media attachment variables
                String uri = "";
                String attachmentCreatedDate = "";
                String description = "";
                String mediaUploadIp = "";
                String mediaDateTaken = "";
                String mediaDateModified = "";
                String mediaDateUploaded = "";
                String mediaIso = "";
                String mediaFocalLength = "";
                String mediaCameraMake = "";
                String mediaCameraModel = "";
                String mediaExposure = "";
                String mediaFstop = "";
                String mediaOrientation = "";
                
                // External Context variables
                String url = "";
                
                String date = new TimestampToDate(comment.timestamp).getDate();
                String title = comment.title;
                if (comment.data != null){
                    for (CommentsV2.Comments_V2.Data data:comment.data){
                        commentString = data.comment.comment;
                        author = data.comment.author;
                    }
                }
                if (comment.attachments != null){
                    for (CommentsV2.Comments_V2.Attachments attachment:comment.attachments){
                        for (CommentsV2.Comments_V2.Attachments.Data attachmentData:attachment.data){
                            uri = "";
                            attachmentCreatedDate = "";
                            description = "";
                            mediaUploadIp = "";
                            mediaDateTaken = "";
                            mediaDateModified = "";
                            mediaDateUploaded = "";
                            mediaIso = "";
                            mediaFocalLength = "";
                            mediaCameraMake = "";
                            mediaCameraModel = "";
                            mediaExposure = "";
                            mediaFstop = "";
                            mediaOrientation = "";
                            if (attachmentData.external_context != null){
                                url = attachmentData.external_context.url;
                            }
                            if (attachmentData.media != null){
                                uri = attachmentData.media.uri;
                                attachmentCreatedDate = new TimestampToDate(attachmentData.media.creation_timestamp).getDate();
                                description = attachmentData.media.description;
                                if (attachmentData.media.media_metadata.photo_metadata != null) {
                                    mediaUploadIp = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).upload_ip;
                                    mediaDateTaken = new TimestampToDate(attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).taken_timestamp).getDate();
                                    mediaDateModified = new TimestampToDate(attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).modified_timestamp).getDate();
                                    mediaIso = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).iso;
                                    mediaFocalLength = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).focal_length;
                                    mediaCameraMake = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).camera_make;
                                    mediaCameraModel = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).camera_model;
                                    mediaExposure = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).exposure;
                                    mediaFstop = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).f_stop;
                                    mediaOrientation = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).orientation;
                                }
                                if (attachmentData.media.media_metadata.video_metadata != null) {
                                    mediaUploadIp = attachmentData.media.media_metadata.video_metadata.exif_data.get(0).upload_ip;
                                    mediaDateUploaded = new TimestampToDate(attachmentData.media.media_metadata.video_metadata.exif_data.get(0).upload_timestamp).getDate();
                                }
                            }
                        }
                    }
                }
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(comment_Date, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(comment_Title, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(comment_CommentString, FacebookIngestModuleFactory.getModuleName(), commentString));
                attributelist.add(new BlackboardAttribute(comment_Author, FacebookIngestModuleFactory.getModuleName(), author));
                attributelist.add(new BlackboardAttribute(comment_mediaUri, FacebookIngestModuleFactory.getModuleName(), uri));
                attributelist.add(new BlackboardAttribute(comment_mediaCreatedDate, FacebookIngestModuleFactory.getModuleName(), attachmentCreatedDate));
                attributelist.add(new BlackboardAttribute(comment_mediaDescription, FacebookIngestModuleFactory.getModuleName(), description));
                attributelist.add(new BlackboardAttribute(comment_mediaUploadIp, FacebookIngestModuleFactory.getModuleName(), mediaUploadIp));
                attributelist.add(new BlackboardAttribute(comment_mediaDateTaken, FacebookIngestModuleFactory.getModuleName(), mediaDateTaken));
                attributelist.add(new BlackboardAttribute(comment_mediaDateModified, FacebookIngestModuleFactory.getModuleName(), mediaDateModified));
                attributelist.add(new BlackboardAttribute(comment_mediaDateUploaded, FacebookIngestModuleFactory.getModuleName(), mediaDateUploaded));
                attributelist.add(new BlackboardAttribute(comment_mediaIso, FacebookIngestModuleFactory.getModuleName(), mediaIso));
                attributelist.add(new BlackboardAttribute(comment_mediaFocalLength, FacebookIngestModuleFactory.getModuleName(), mediaFocalLength));
                attributelist.add(new BlackboardAttribute(comment_mediaCameraMake, FacebookIngestModuleFactory.getModuleName(), mediaCameraMake));
                attributelist.add(new BlackboardAttribute(comment_mediaCameraModel, FacebookIngestModuleFactory.getModuleName(), mediaCameraModel));
                attributelist.add(new BlackboardAttribute(comment_mediaExposure, FacebookIngestModuleFactory.getModuleName(), mediaExposure));
                attributelist.add(new BlackboardAttribute(comment_mediaFstop, FacebookIngestModuleFactory.getModuleName(), mediaFstop));
                attributelist.add(new BlackboardAttribute(comment_mediaOrientation, FacebookIngestModuleFactory.getModuleName(), mediaOrientation));
                attributelist.add(new BlackboardAttribute(comment_EC_url, FacebookIngestModuleFactory.getModuleName(), url));
                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No comments_v2 found");
            return;
        }
    }
    
    /**
    * Process posts_and_comments.json file and add data as Data Artifact
    * Facebook reaction data.
    *
    * @param  af  JSON file
    */
    private void processJSONposts_and_comments(AbstractFile af){
        String json = parseAFtoString(af);
        ReactionsV2 reactions = new Gson().fromJson(json, ReactionsV2.class);
        if(reactions.reactions_v2 != null){
            
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
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_REACTION");
                    reactionDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBREACTION_DATE");
                    reactionTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FBREACTION_TITLE");
                    reactionReactionString = currentCase.getSleuthkitCase().getAttributeType("LS_FBREACTION_REACTION");
                    reactionAuthor = currentCase.getSleuthkitCase().getAttributeType("LS_FBREACTION_AUTHOR");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (ReactionsV2.Reactions_V2 reaction:reactions.reactions_v2){
                
                String reactionString = "";
                String actor = "";
                
                String title = reaction.title;
                String date = new TimestampToDate(reaction.timestamp).getDate();
                for (ReactionsV2.Reactions_V2.Data reactionData:reaction.data){
                    reactionString = reactionData.reaction.reaction;
                    actor = reactionData.reaction.actor;
                    // add variables to attributes
                    Collection<BlackboardAttribute> attributelist = new ArrayList();
                    attributelist.add(new BlackboardAttribute(reactionDate, FacebookIngestModuleFactory.getModuleName(), date));
                    attributelist.add(new BlackboardAttribute(reactionTitle, FacebookIngestModuleFactory.getModuleName(), title));
                    attributelist.add(new BlackboardAttribute(reactionReactionString, FacebookIngestModuleFactory.getModuleName(), reactionString));
                    attributelist.add(new BlackboardAttribute(reactionAuthor, FacebookIngestModuleFactory.getModuleName(), actor));

                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
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
    
    /**
    * Process accounts_center.json file and add data as Data Artifact
    * Facebook account center data.
    *
    * @param  af  JSON file
    */
    private void processJSONaccounts_center(AbstractFile af){
        String json = parseAFtoString(af);
        AccountsCenterV2 accountCenter = new Gson().fromJson(json, AccountsCenterV2.class);
        if(accountCenter.accounts_center_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type accountCenterServiceName;
            BlackboardAttribute.Type accountCenterAppId;
            BlackboardAttribute.Type accountCenterUsername;
            BlackboardAttribute.Type accountCenterEmail;
            BlackboardAttribute.Type accountCenterName;
            BlackboardAttribute.Type accountCenterDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_ACCOUNT_CENTER") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_ACCOUNT_CENTER", "Facebook Account Center");
                    accountCenterServiceName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_SERVICENAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Service Name");
                    accountCenterAppId = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_APPID", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "App ID");
                    accountCenterUsername = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_USERNAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Username");
                    accountCenterEmail = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_EMAIL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Email");
                    accountCenterName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    accountCenterDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Linked Date");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_ACCOUNT_CENTER");
                    accountCenterServiceName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_SERVICENAME");
                    accountCenterAppId = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_APPID");
                    accountCenterUsername = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_USERNAME");
                    accountCenterEmail = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_EMAIL");
                    accountCenterName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_NAME");
                    accountCenterDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ACCOUNT_CENTER_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (AccountsCenterV2.AccountsCenter_V2.Service serviceLinked:accountCenter.accounts_center_v2.linked_accounts){
                
                String service_name = serviceLinked.service_name;
                String native_app_id = serviceLinked.native_app_id;
                String username = serviceLinked.username;
                String email = serviceLinked.email;
                String name = serviceLinked.name;
                String date = new TimestampToDate(serviceLinked.linked_time).getDate();
                        
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(accountCenterServiceName, FacebookIngestModuleFactory.getModuleName(), service_name));
                attributelist.add(new BlackboardAttribute(accountCenterAppId, FacebookIngestModuleFactory.getModuleName(), native_app_id));
                attributelist.add(new BlackboardAttribute(accountCenterUsername, FacebookIngestModuleFactory.getModuleName(), username));
                attributelist.add(new BlackboardAttribute(accountCenterEmail, FacebookIngestModuleFactory.getModuleName(), email));
                attributelist.add(new BlackboardAttribute(accountCenterName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(accountCenterDate, FacebookIngestModuleFactory.getModuleName(), date));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else {
            logger.log(Level.INFO, "No accounts_center_v2 found");
            return;
        }
    }
    
    /**
    * Process event_invitations.json file and add data as Data Artifact
    * Facebook events user is invited to data.
    *
    * @param  af  JSON file
    */
    private void processJSONevent_invitations(AbstractFile af){
        String json = parseAFtoString(af);
        EventsInvitedV2 eventResponse = new Gson().fromJson(json, EventsInvitedV2.class);
        if(eventResponse.events_invited_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type eventResponseName;
            BlackboardAttribute.Type eventResponseStartDate;
            BlackboardAttribute.Type eventResponseEndDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_EVENT_INVITATION") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_EVENT_INVITATION", "Facebook Event Invitation");
                    eventResponseName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_EVENT_INVITATION_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Event Name");
                    eventResponseStartDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_EVENT_INVITATION_STARTDATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Start Date");
                    eventResponseEndDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_EVENT_INVITATION_ENDDATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "End Date");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_EVENT_INVITATION");
                    eventResponseName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_EVENT_INVITATION_NAME");
                    eventResponseStartDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_EVENT_INVITATION_STARTDATE");
                    eventResponseEndDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_EVENT_INVITATION_ENDDATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (EventsInvitedV2.EventInvitation eventInvited:eventResponse.events_invited_v2){
                
                String name = eventInvited.name;
                String startDate = new TimestampToDate(eventInvited.start_timestamp).getDate();
                String endDate = new TimestampToDate(eventInvited.end_timestamp).getDate();
                        
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(eventResponseName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(eventResponseStartDate, FacebookIngestModuleFactory.getModuleName(), startDate));
                attributelist.add(new BlackboardAttribute(eventResponseEndDate, FacebookIngestModuleFactory.getModuleName(), endDate));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No events_invited_v2 found");
            return;
        }
    }
    
    /**
    * Process your_event_responses.json file and add data as Data Artifact
    * Facebook event response data.
    *
    * @param  af  JSON file
    */
    private void processJSONyour_event_responses(AbstractFile af){
        String json = parseAFtoString(af);
        EventResponseV2 eventResponse = new Gson().fromJson(json, EventResponseV2.class);
        if(eventResponse.event_responses_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type eventResponseName;
            BlackboardAttribute.Type eventReply;
            BlackboardAttribute.Type eventResponseStartDate;
            BlackboardAttribute.Type eventResponseEndDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_EVENT_RESPONSE") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_EVENT_RESPONSE", "Facebook Event Response");
                    eventResponseName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_EVENT_RESPONSE_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Event Name");
                    eventReply = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_EVENT_REPLY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Response");
                    eventResponseStartDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_EVENT_RESPONSE_STARTDATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Start Date");
                    eventResponseEndDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_EVENT_RESPONSE_ENDDATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "End Date");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_EVENT_RESPONSE");
                    eventResponseName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_EVENT_RESPONSE_NAME");
                    eventReply = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_EVENT_REPLY");
                    eventResponseStartDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_EVENT_RESPONSE_STARTDATE");
                    eventResponseEndDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_EVENT_RESPONSE_ENDDATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            // Loop for events_joined
            for (EventResponseV2.EventResponse_V2.EventsJoined eventJoined:eventResponse.event_responses_v2.events_joined){
                
                String reply = "Joined";
                String name = eventJoined.name;
                String startDate = new TimestampToDate(eventJoined.start_timestamp).getDate();
                String endDate = new TimestampToDate(eventJoined.end_timestamp).getDate();
                        
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(eventResponseName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(eventReply, FacebookIngestModuleFactory.getModuleName(), reply));
                attributelist.add(new BlackboardAttribute(eventResponseStartDate, FacebookIngestModuleFactory.getModuleName(), startDate));
                attributelist.add(new BlackboardAttribute(eventResponseEndDate, FacebookIngestModuleFactory.getModuleName(), endDate));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
            
            // Loop for events_declined
            for (EventResponseV2.EventResponse_V2.EventsJoined eventJoined:eventResponse.event_responses_v2.events_declined){
                
                String reply = "Declined";
                String name = eventJoined.name;
                String startDate = new TimestampToDate(eventJoined.start_timestamp).getDate();
                String endDate = new TimestampToDate(eventJoined.end_timestamp).getDate();
                        
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(eventResponseName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(eventReply, FacebookIngestModuleFactory.getModuleName(), reply));
                attributelist.add(new BlackboardAttribute(eventResponseStartDate, FacebookIngestModuleFactory.getModuleName(), startDate));
                attributelist.add(new BlackboardAttribute(eventResponseEndDate, FacebookIngestModuleFactory.getModuleName(), endDate));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
            
            // Loop for events_interested
            for (EventResponseV2.EventResponse_V2.EventsJoined eventJoined:eventResponse.event_responses_v2.events_interested){
                
                String reply = "Interested";
                String name = eventJoined.name;
                String startDate = new TimestampToDate(eventJoined.start_timestamp).getDate();
                String endDate = new TimestampToDate(eventJoined.end_timestamp).getDate();
                        
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(eventResponseName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(eventReply, FacebookIngestModuleFactory.getModuleName(), reply));
                attributelist.add(new BlackboardAttribute(eventResponseStartDate, FacebookIngestModuleFactory.getModuleName(), startDate));
                attributelist.add(new BlackboardAttribute(eventResponseEndDate, FacebookIngestModuleFactory.getModuleName(), endDate));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No event_responses_v2 found");
            return;
        }
    }
    
    /**
    * Process items_sold.json file and add data as Data Artifact
    * Facebook marketplace items sold data.
    *
    * @param  af  JSON file
    */
    private void processJSONitems_sold(AbstractFile af){
        String json = parseAFtoString(af);
        ItemsSellingV2 itemsSelling = new Gson().fromJson(json, ItemsSellingV2.class);
        if(itemsSelling.items_selling_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type itemsSellingTitle;
            BlackboardAttribute.Type itemsSellingPrice;
            BlackboardAttribute.Type itemsSellingSeller;
            BlackboardAttribute.Type itemsSellingCreatedDate;
            BlackboardAttribute.Type itemsSellingUpdatedDate;
            BlackboardAttribute.Type itemsSellingCategory;
            BlackboardAttribute.Type itemsSellingMarketplace;
            BlackboardAttribute.Type itemsSellingLocationName;
            BlackboardAttribute.Type itemsSellingLocationLatitude;
            BlackboardAttribute.Type itemsSellingLocationLongitude;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_ITEMS_SELLING") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_ITEMS_SELLING", "Facebook Items Selling");
                    itemsSellingTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ITEMS_SELLING_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                    itemsSellingPrice = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ITEMS_SELLING_PRICE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Price");
                    itemsSellingSeller = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ITEMS_SELLING_SELLER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Seller");
                    itemsSellingCreatedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ITEMS_SELLING_CREATED_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Created Date");
                    itemsSellingUpdatedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ITEMS_SELLING_UPDATED_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Latest Updated Date");
                    itemsSellingCategory = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ITEMS_SELLING_CATEGORY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Category");
                    itemsSellingMarketplace = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ITEMS_SELLING_MARKETPLACE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Marketplace Group");
                    itemsSellingLocationName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ITEMS_SELLING_LOCATION_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Location Name");
                    itemsSellingLocationLatitude = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ITEMS_SELLING_LOCATION_LATITUDE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Latitude");
                    itemsSellingLocationLongitude = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_ITEMS_SELLING_LOCATION_LONGITUDE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Longitude");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_ITEMS_SELLING");
                    itemsSellingTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ITEMS_SELLING_TITLE");
                    itemsSellingPrice = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ITEMS_SELLING_PRICE");
                    itemsSellingSeller = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ITEMS_SELLING_SELLER");
                    itemsSellingCreatedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ITEMS_SELLING_CREATED_DATE");
                    itemsSellingUpdatedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ITEMS_SELLING_UPDATED_DATE");
                    itemsSellingCategory = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ITEMS_SELLING_CATEGORY");
                    itemsSellingMarketplace = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ITEMS_SELLING_MARKETPLACE");
                    itemsSellingLocationName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ITEMS_SELLING_LOCATION_NAME");
                    itemsSellingLocationLatitude = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ITEMS_SELLING_LOCATION_LATITUDE");
                    itemsSellingLocationLongitude = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_ITEMS_SELLING_LOCATION_LONGITUDE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (ItemsSellingV2.ItemsSelling_V2 item:itemsSelling.items_selling_v2){
                
                String title = item.title;
                String price = item.price;
                String seller = item.seller;
                String created_timestamp = new TimestampToDate(item.created_timestamp).getDate();
                String updated_timestamp = new TimestampToDate(item.updated_timestamp).getDate();
                String category = item.category;
                String marketplace = item.marketplace;
                String locationName = item.location.name;
                String latitude = item.location.coordinate.latitude;
                String longitude = item.location.coordinate.longitude;
                        
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(itemsSellingTitle, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(itemsSellingPrice, FacebookIngestModuleFactory.getModuleName(), price));
                attributelist.add(new BlackboardAttribute(itemsSellingSeller, FacebookIngestModuleFactory.getModuleName(), seller));
                attributelist.add(new BlackboardAttribute(itemsSellingCreatedDate, FacebookIngestModuleFactory.getModuleName(), created_timestamp));
                attributelist.add(new BlackboardAttribute(itemsSellingUpdatedDate, FacebookIngestModuleFactory.getModuleName(), updated_timestamp));
                attributelist.add(new BlackboardAttribute(itemsSellingCategory, FacebookIngestModuleFactory.getModuleName(), category));
                attributelist.add(new BlackboardAttribute(itemsSellingMarketplace, FacebookIngestModuleFactory.getModuleName(), marketplace));
                attributelist.add(new BlackboardAttribute(itemsSellingLocationName, FacebookIngestModuleFactory.getModuleName(), locationName));
                attributelist.add(new BlackboardAttribute(itemsSellingLocationLatitude, FacebookIngestModuleFactory.getModuleName(), latitude));
                attributelist.add(new BlackboardAttribute(itemsSellingLocationLongitude, FacebookIngestModuleFactory.getModuleName(), longitude));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No event_responses_v2 found");
            return;
        }
    }
    
    /**
    * Process payment_history.json file and add data as Data Artifact
    * Facebook payment history data.
    *
    * @param  af  JSON file
    */
    private void processJSONpayment_history(AbstractFile af){
        String json = parseAFtoString(af);
        PaymentsV2 paymentHistory = new Gson().fromJson(json, PaymentsV2.class);
        if(paymentHistory.payments_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type paymentHistoryPreferredCurrency;
            BlackboardAttribute.Type paymentHistoryPaymentDate;
            BlackboardAttribute.Type paymentHistoryPaymentAmount;
            BlackboardAttribute.Type paymentHistoryPaymentCurrency;
            BlackboardAttribute.Type paymentHistoryPaymentSender;
            BlackboardAttribute.Type paymentHistoryPaymentReceiver;
            BlackboardAttribute.Type paymentHistoryPaymentType;
            BlackboardAttribute.Type paymentHistoryPaymentStatus;
            BlackboardAttribute.Type paymentHistoryPaymentMethod;
            BlackboardAttribute.Type paymentHistoryPaymentIP;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PAYMENT_HISTORY") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PAYMENT_HISTORY", "Facebook Payment History");
                    paymentHistoryPreferredCurrency = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAYMENT_HISTORY_PREFERRED_CURRENCY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User-preferred Currency");
                    paymentHistoryPaymentDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAYMENT_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    paymentHistoryPaymentAmount = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAYMENT_AMOUNT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Amount");
                    paymentHistoryPaymentCurrency = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAYMENT_CURRENCY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Currency");
                    paymentHistoryPaymentSender = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAYMENT_SENDER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Sender");
                    paymentHistoryPaymentReceiver = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAYMENT_RECEIVER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Receiver");
                    paymentHistoryPaymentType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAYMENT_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Type");
                    paymentHistoryPaymentStatus = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAYMENT_STATUS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Status");
                    paymentHistoryPaymentMethod = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAYMENT_METHOD", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Payment Method");
                    paymentHistoryPaymentIP = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAYMENT_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "IP Address");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PAYMENT_HISTORY");
                    paymentHistoryPreferredCurrency = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAYMENT_HISTORY_PREFERRED_CURRENCY");
                    paymentHistoryPaymentDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAYMENT_DATE");
                    paymentHistoryPaymentAmount = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAYMENT_AMOUNT");
                    paymentHistoryPaymentCurrency = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAYMENT_CURRENCY");
                    paymentHistoryPaymentSender = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAYMENT_SENDER");
                    paymentHistoryPaymentReceiver = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAYMENT_RECEIVER");
                    paymentHistoryPaymentType = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAYMENT_TYPE");
                    paymentHistoryPaymentStatus = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAYMENT_STATUS");
                    paymentHistoryPaymentMethod = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAYMENT_METHOD");
                    paymentHistoryPaymentIP = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAYMENT_IP");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            String preferredCurrency = paymentHistory.payments_v2.preferred_currency;
            
            for (PaymentsV2.Payments_V2.Payment payment:paymentHistory.payments_v2.payments){
                
                String paymentDate = new TimestampToDate(payment.created_timestamp).getDate();
                String paymentAmount = payment.amount;
                String paymentCurrency = payment.currency;
                String paymentSender = payment.sender;
                String paymentReceiver = payment.receiver;
                String paymentType = payment.type;
                String paymentStatus = payment.status;
                String paymentMethod = payment.payment_method;
                String paymentIP = payment.ip;
                        
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(paymentHistoryPreferredCurrency, FacebookIngestModuleFactory.getModuleName(), preferredCurrency));
                attributelist.add(new BlackboardAttribute(paymentHistoryPaymentDate, FacebookIngestModuleFactory.getModuleName(), paymentDate));
                attributelist.add(new BlackboardAttribute(paymentHistoryPaymentAmount, FacebookIngestModuleFactory.getModuleName(), paymentAmount));
                attributelist.add(new BlackboardAttribute(paymentHistoryPaymentCurrency, FacebookIngestModuleFactory.getModuleName(), paymentCurrency));
                attributelist.add(new BlackboardAttribute(paymentHistoryPaymentSender, FacebookIngestModuleFactory.getModuleName(), paymentSender));
                attributelist.add(new BlackboardAttribute(paymentHistoryPaymentReceiver, FacebookIngestModuleFactory.getModuleName(), paymentReceiver));
                attributelist.add(new BlackboardAttribute(paymentHistoryPaymentType, FacebookIngestModuleFactory.getModuleName(), paymentType));
                attributelist.add(new BlackboardAttribute(paymentHistoryPaymentStatus, FacebookIngestModuleFactory.getModuleName(), paymentStatus));
                attributelist.add(new BlackboardAttribute(paymentHistoryPaymentMethod, FacebookIngestModuleFactory.getModuleName(), paymentMethod));
                attributelist.add(new BlackboardAttribute(paymentHistoryPaymentIP, FacebookIngestModuleFactory.getModuleName(), paymentIP));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No event_responses_v2 found");
            return;
        }
    }
    
    /**
    * Process your_comments_in_groups.json file and add data as Data Artifact
    * Facebook user comments in groups data.
    *
    * @param  af  JSON file
    */
    private void processJSONyour_comments_in_groups(AbstractFile af){
        String json = parseAFtoString(af);
        GroupCommentsV2 groupComments = new Gson().fromJson(json, GroupCommentsV2.class);
        if(groupComments.group_comments_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type groupComment_Date;
            BlackboardAttribute.Type groupComment_Title;
            BlackboardAttribute.Type groupComment_CommentString;
            BlackboardAttribute.Type groupComment_Author;
            BlackboardAttribute.Type groupComment_Group;
            BlackboardAttribute.Type groupComment_mediaUri;
            BlackboardAttribute.Type groupComment_mediaCreatedDate;
            BlackboardAttribute.Type groupComment_mediaDescription;
            BlackboardAttribute.Type groupComment_mediaUploadIp;
            BlackboardAttribute.Type groupComment_mediaDateTaken;
            BlackboardAttribute.Type groupComment_mediaDateModified;
            BlackboardAttribute.Type groupComment_mediaDateUploaded;
            BlackboardAttribute.Type groupComment_mediaIso;
            BlackboardAttribute.Type groupComment_mediaFocalLength;
            BlackboardAttribute.Type groupComment_mediaCameraMake;
            BlackboardAttribute.Type groupComment_mediaCameraModel;
            BlackboardAttribute.Type groupComment_mediaExposure;
            BlackboardAttribute.Type groupComment_mediaFstop;
            BlackboardAttribute.Type groupComment_mediaOrientation;
            BlackboardAttribute.Type groupComment_EC_url;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_GROUP_COMMENT") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_GROUP_COMMENT", "Facebook Group Comment");
                    groupComment_Date = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    groupComment_Title = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                    groupComment_CommentString = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_COMMENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Comment");
                    groupComment_Author = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_AUTHOR", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Author");
                    groupComment_Group = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_GROUP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Group");
                    groupComment_mediaUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media URI");
                    groupComment_mediaCreatedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_DATE_CREATED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Created");
                    groupComment_mediaDescription = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_DESCRIPTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Description");
                    groupComment_mediaUploadIp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Uploaded from IP");
                    groupComment_mediaDateTaken = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_DATE_TAKEN", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Taken");
                    groupComment_mediaDateModified = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_DATE_MODIFIED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Modified");
                    groupComment_mediaDateUploaded = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_DATE_UPLOADED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Uploaded");
                    groupComment_mediaIso = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_ISO", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "ISO");
                    groupComment_mediaFocalLength = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_FOCAL_LENGTH", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Focal Length");
                    groupComment_mediaCameraMake = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_CAMERA_MAKE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Camera Make");
                    groupComment_mediaCameraModel = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_CAMERA_MODEL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Camera Model");
                    groupComment_mediaExposure = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_EXPOSURE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Exposure");
                    groupComment_mediaFstop = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_F_STOP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "F-stop");
                    groupComment_mediaOrientation = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_MEDIA_ORIENTATION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Orientation");
                    groupComment_EC_url = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_URL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "External Context URL");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_GROUP_COMMENT");
                    groupComment_Date = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_DATE");
                    groupComment_Title = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_TITLE");
                    groupComment_CommentString = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_COMMENT");
                    groupComment_Author = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_AUTHOR");
                    groupComment_Group = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_GROUP");
                    groupComment_mediaUri = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_URI");
                    groupComment_mediaCreatedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_DATE_CREATED");
                    groupComment_mediaDescription = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_DESCRIPTION");
                    groupComment_mediaUploadIp = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_IP");
                    groupComment_mediaDateTaken = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_DATE_TAKEN");
                    groupComment_mediaDateModified = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_DATE_MODIFIED");
                    groupComment_mediaDateUploaded = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_DATE_UPLOADED");
                    groupComment_mediaIso = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_ISO");
                    groupComment_mediaFocalLength = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_FOCAL_LENGTH");
                    groupComment_mediaCameraMake = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_CAMERA_MAKE");
                    groupComment_mediaCameraModel = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_CAMERA_MODEL");
                    groupComment_mediaExposure = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_EXPOSURE");
                    groupComment_mediaFstop = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_F_STOP");
                    groupComment_mediaOrientation = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_MEDIA_ORIENTATION");
                    groupComment_EC_url = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_URL");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (GroupCommentsV2.GroupComments_V2 comment:groupComments.group_comments_v2){
                String commentString = "";
                String author = "";
                String group = "";
                
                // Media attachment variables
                String uri = "";
                String attachmentCreatedDate = "";
                String description = "";
                String mediaUploadIp = "";
                String mediaDateTaken = "";
                String mediaDateModified = "";
                String mediaDateUploaded = "";
                String mediaIso = "";
                String mediaFocalLength = "";
                String mediaCameraMake = "";
                String mediaCameraModel = "";
                String mediaExposure = "";
                String mediaFstop = "";
                String mediaOrientation = "";
                
                // External Context variables
                String url = "";
                
                String date = new TimestampToDate(comment.timestamp).getDate();
                String title = comment.title;
                if (comment.data != null){
                    for (GroupCommentsV2.GroupComments_V2.Data data:comment.data){
                        commentString = data.comment.comment;
                        author = data.comment.author;
                        group = data.comment.group;
                    }
                }
                if (comment.attachments != null){
                    for (GroupCommentsV2.GroupComments_V2.Attachments attachment:comment.attachments){
                        for (GroupCommentsV2.GroupComments_V2.Attachments.Data attachmentData:attachment.data){
                            uri = "";
                            attachmentCreatedDate = "";
                            description = "";
                            mediaUploadIp = "";
                            mediaDateTaken = "";
                            mediaDateModified = "";
                            mediaDateUploaded = "";
                            mediaIso = "";
                            mediaFocalLength = "";
                            mediaCameraMake = "";
                            mediaCameraModel = "";
                            mediaExposure = "";
                            mediaFstop = "";
                            mediaOrientation = "";
                            if (attachmentData.external_context != null){
                                url = attachmentData.external_context.url;
                            }
                            if (attachmentData.media != null){
                                uri = attachmentData.media.uri;
                                attachmentCreatedDate = new TimestampToDate(attachmentData.media.creation_timestamp).getDate();
                                description = attachmentData.media.description;
                                if (attachmentData.media.media_metadata.photo_metadata != null) {
                                    mediaUploadIp = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).upload_ip;
                                    mediaDateTaken = new TimestampToDate(attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).taken_timestamp).getDate();
                                    mediaDateModified = new TimestampToDate(attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).modified_timestamp).getDate();
                                    mediaIso = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).iso;
                                    mediaFocalLength = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).focal_length;
                                    mediaCameraMake = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).camera_make;
                                    mediaCameraModel = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).camera_model;
                                    mediaExposure = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).exposure;
                                    mediaFstop = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).f_stop;
                                    mediaOrientation = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).orientation;
                                }
                                if (attachmentData.media.media_metadata.video_metadata != null) {
                                    mediaUploadIp = attachmentData.media.media_metadata.video_metadata.exif_data.get(0).upload_ip;
                                    mediaDateUploaded = new TimestampToDate(attachmentData.media.media_metadata.video_metadata.exif_data.get(0).upload_timestamp).getDate();
                                }
                            }
                        }
                    }
                }
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(groupComment_Date, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(groupComment_Title, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(groupComment_CommentString, FacebookIngestModuleFactory.getModuleName(), commentString));
                attributelist.add(new BlackboardAttribute(groupComment_Author, FacebookIngestModuleFactory.getModuleName(), author));
                attributelist.add(new BlackboardAttribute(groupComment_Group, FacebookIngestModuleFactory.getModuleName(), group));
                attributelist.add(new BlackboardAttribute(groupComment_mediaUri, FacebookIngestModuleFactory.getModuleName(), uri));
                attributelist.add(new BlackboardAttribute(groupComment_mediaCreatedDate, FacebookIngestModuleFactory.getModuleName(), attachmentCreatedDate));
                attributelist.add(new BlackboardAttribute(groupComment_mediaDescription, FacebookIngestModuleFactory.getModuleName(), description));
                attributelist.add(new BlackboardAttribute(groupComment_mediaUploadIp, FacebookIngestModuleFactory.getModuleName(), mediaUploadIp));
                attributelist.add(new BlackboardAttribute(groupComment_mediaDateTaken, FacebookIngestModuleFactory.getModuleName(), mediaDateTaken));
                attributelist.add(new BlackboardAttribute(groupComment_mediaDateModified, FacebookIngestModuleFactory.getModuleName(), mediaDateModified));
                attributelist.add(new BlackboardAttribute(groupComment_mediaDateUploaded, FacebookIngestModuleFactory.getModuleName(), mediaDateUploaded));
                attributelist.add(new BlackboardAttribute(groupComment_mediaIso, FacebookIngestModuleFactory.getModuleName(), mediaIso));
                attributelist.add(new BlackboardAttribute(groupComment_mediaFocalLength, FacebookIngestModuleFactory.getModuleName(), mediaFocalLength));
                attributelist.add(new BlackboardAttribute(groupComment_mediaCameraMake, FacebookIngestModuleFactory.getModuleName(), mediaCameraMake));
                attributelist.add(new BlackboardAttribute(groupComment_mediaCameraModel, FacebookIngestModuleFactory.getModuleName(), mediaCameraModel));
                attributelist.add(new BlackboardAttribute(groupComment_mediaExposure, FacebookIngestModuleFactory.getModuleName(), mediaExposure));
                attributelist.add(new BlackboardAttribute(groupComment_mediaFstop, FacebookIngestModuleFactory.getModuleName(), mediaFstop));
                attributelist.add(new BlackboardAttribute(groupComment_mediaOrientation, FacebookIngestModuleFactory.getModuleName(), mediaOrientation));
                attributelist.add(new BlackboardAttribute(groupComment_EC_url, FacebookIngestModuleFactory.getModuleName(), url));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No group_comments_v2 found");
            return;
        }
    }
    
    /**
    * Process your_group_membership_activity.json file and add data as Data Artifact
    * Facebook user group membership data.
    *
    * @param  af  JSON file
    */
    private void processJSONyour_group_membership_activity(AbstractFile af){
        String json = parseAFtoString(af);
        GroupsJoinedV2 groupsJoined = new Gson().fromJson(json, GroupsJoinedV2.class);
        if(groupsJoined.groups_joined_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type groupJoinedDate;
            BlackboardAttribute.Type groupName;
            BlackboardAttribute.Type groupJoinedTitle;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_GROUP_JOINED") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_GROUP_JOINED", "Facebook Groups Joined");
                    groupJoinedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUP_JOINED_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Joined");
                    groupName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUP_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Group Name");
                    groupJoinedTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUP_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_GROUP_JOINED");
                    groupJoinedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUP_JOINED_DATE");
                    groupName = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUP_NAME");
                    groupJoinedTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUP_TITLE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (GroupsJoinedV2.GroupsJoined_V2 group:groupsJoined.groups_joined_v2){
                
                String date = new TimestampToDate(group.timestamp).getDate();
                String name = "";
                for (GroupsJoinedV2.GroupsJoined_V2.Data titleData:group.data){
                    name = titleData.name;
                }
                String title = group.title;
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(groupJoinedDate, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(groupName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(groupJoinedTitle, FacebookIngestModuleFactory.getModuleName(), title));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No groups_joined_v2 found");
            return;
        }
    }
    
    /**
    * Process device_location.json file and add data as Data Artifact
    * Facebook user device location data.
    *
    * @param  af  JSON file
    */
    private void processJSONdevice_location(AbstractFile af){
        String json = parseAFtoString(af);
        PhoneNumberLocationV2 phoneNumberLocations = new Gson().fromJson(json, PhoneNumberLocationV2.class);
        if(phoneNumberLocations.phone_number_location_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type phoneNumberSPN;
            BlackboardAttribute.Type phoneNumberCountryCode;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_DEVICE_LOCATION") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_DEVICE_LOCATION", "Facebook Location Associated with User Device");
                    phoneNumberSPN = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_DEVICE_SPN", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Mobile Network Provider");
                    phoneNumberCountryCode = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_DEVICE_COUNTRY_CODE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Mobile Country Code");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_DEVICE_LOCATION");
                    phoneNumberSPN = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_DEVICE_SPN");
                    phoneNumberCountryCode = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_DEVICE_COUNTRY_CODE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (PhoneNumberLocationV2.PhoneNumberLocation_V2 phoneNumberLocation:phoneNumberLocations.phone_number_location_v2){
                
                String spn = phoneNumberLocation.spn;
                String countryCode = phoneNumberLocation.country_code;
                        
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(phoneNumberSPN, FacebookIngestModuleFactory.getModuleName(), spn));
                attributelist.add(new BlackboardAttribute(phoneNumberCountryCode, FacebookIngestModuleFactory.getModuleName(), countryCode));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No phone_number_location_v2 found");
            return;
        }
    }
    
    /**
    * Process last_location.json file and add data as Data Artifact
    * Facebook user device location data.
    *
    * @param  af  JSON file
    */
    private void processJSONlast_location(AbstractFile af){
        String json = parseAFtoString(af);
        LastLocationV2 lastLocation = new Gson().fromJson(json, LastLocationV2.class);
        if(lastLocation.last_location_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type lastLocationDate;
            BlackboardAttribute.Type lastLocationName;
            BlackboardAttribute.Type lastLocationLatitude;
            BlackboardAttribute.Type lastLocationLongitude;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_LAST_LOCATION") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_LAST_LOCATION", "Facebook Last Location");
                    lastLocationDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_LAST_LOCATION_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    lastLocationName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_LAST_LOCATION_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Location Name");
                    lastLocationLatitude = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_LAST_LOCATION_LATITUDE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Latitude");
                    lastLocationLongitude = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_LAST_LOCATION_LONGITUDE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Longitude");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_LAST_LOCATION");
                    lastLocationDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_LAST_LOCATION_DATE");
                    lastLocationName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_LAST_LOCATION_NAME");
                    lastLocationLatitude = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_LAST_LOCATION_LATITUDE");
                    lastLocationLongitude = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_LAST_LOCATION_LONGITUDE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            String date = new TimestampToDate(lastLocation.last_location_v2.time).getDate();
            String locationName = lastLocation.last_location_v2.name;
            String latitude = lastLocation.last_location_v2.coordinate.latitude;
            String longitude = lastLocation.last_location_v2.coordinate.longitude;

            // add variables to attributes
            Collection<BlackboardAttribute> attributelist = new ArrayList();
            attributelist.add(new BlackboardAttribute(lastLocationDate, FacebookIngestModuleFactory.getModuleName(), date));
            attributelist.add(new BlackboardAttribute(lastLocationName, FacebookIngestModuleFactory.getModuleName(), locationName));
            attributelist.add(new BlackboardAttribute(lastLocationLatitude, FacebookIngestModuleFactory.getModuleName(), latitude));
            attributelist.add(new BlackboardAttribute(lastLocationLongitude, FacebookIngestModuleFactory.getModuleName(), longitude));

            try{
                blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
            }
            catch (TskCoreException | BlackboardException e){
                e.printStackTrace();
                return;
            }
        }
        else{
            logger.log(Level.INFO, "No last_location_v2 found");
            return;
        }
    }
    
    /**
    * Process primary_public_location.json file and add data as Data Artifact
    * Facebook user primary public location data.
    *
    * @param  af  JSON file
    */
    private void processJSONprimary_public_location(AbstractFile af){
        String json = parseAFtoString(af);
        PrimaryPublicLocationV2 primaryPublicLocation = new Gson().fromJson(json, PrimaryPublicLocationV2.class);
        if(primaryPublicLocation.primary_public_location_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type primaryPublicLocationCity;
            BlackboardAttribute.Type primaryPublicLocationRegion;
            BlackboardAttribute.Type primaryPublicLocationCountry;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PRIMARY_PUBLIC_LOCATION") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PRIMARY_PUBLIC_LOCATION", "Facebook Primary Public Location");
                    primaryPublicLocationCity = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PRIMARY_PUBLIC_LOCATION_CITY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "City");
                    primaryPublicLocationRegion = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PRIMARY_PUBLIC_LOCATION_REGION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Region");
                    primaryPublicLocationCountry = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PRIMARY_PUBLIC_LOCATION_COUNTRY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Country");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PRIMARY_PUBLIC_LOCATION");
                    primaryPublicLocationCity = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PRIMARY_PUBLIC_LOCATION_CITY");
                    primaryPublicLocationRegion = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PRIMARY_PUBLIC_LOCATION_REGION");
                    primaryPublicLocationCountry = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PRIMARY_PUBLIC_LOCATION_COUNTRY");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            String city = primaryPublicLocation.primary_public_location_v2.city;
            String region = primaryPublicLocation.primary_public_location_v2.region;
            String country = primaryPublicLocation.primary_public_location_v2.country;

            // add variables to attributes
            Collection<BlackboardAttribute> attributelist = new ArrayList();
            attributelist.add(new BlackboardAttribute(primaryPublicLocationCity, FacebookIngestModuleFactory.getModuleName(), city));
            attributelist.add(new BlackboardAttribute(primaryPublicLocationRegion, FacebookIngestModuleFactory.getModuleName(), region));
            attributelist.add(new BlackboardAttribute(primaryPublicLocationCountry, FacebookIngestModuleFactory.getModuleName(), country));

            try{
                blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
            }
            catch (TskCoreException | BlackboardException e){
                e.printStackTrace();
                return;
            }
        }
        else{
            logger.log(Level.INFO, "No primary_public_location_v2 found");
            return;
        }
    }
    
    /**
    * Process timezone.json file and add data as Data Artifact
    * Facebook user timezone data.
    *
    * @param  af  JSON file
    */
    private void processJSONtimezone(AbstractFile af){
        String json = parseAFtoString(af);
        TimezoneV2 timezone = new Gson().fromJson(json, TimezoneV2.class);
        if(timezone.timezone_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type timezoneAttribute;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_USER_TIMEZONE") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_USER_TIMEZONE", "Facebook Timezone Settings");
                    timezoneAttribute = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_TIMEZONE_DATA", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Timezone");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_USER_TIMEZONE");
                    timezoneAttribute = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_TIMEZONE_DATA");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            String timezone_v2 = timezone.timezone_v2;
            // add variables to attributes
            Collection<BlackboardAttribute> attributelist = new ArrayList();
            attributelist.add(new BlackboardAttribute(timezoneAttribute, FacebookIngestModuleFactory.getModuleName(), timezone_v2));

            try{
                blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
            }
            catch (TskCoreException | BlackboardException e){
                e.printStackTrace();
                return;
            }
        }
        else{
            logger.log(Level.INFO, "No timezone_v2 found");
            return;
        }
    }
    
    /**
    * Process autofill_information.json file and add data as Data Artifact
    * Facebook user autofill information data.
    *
    * @param  af  JSON file
    */
    private void processJSONautofill_information(AbstractFile af){
        String json = parseAFtoString(af);
        Autofill_InformationV2 autofill_Information = new Gson().fromJson(json, Autofill_InformationV2.class);
        if(autofill_Information.autofill_information_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type autofillEmail;
            BlackboardAttribute.Type autofillGender;
            BlackboardAttribute.Type autofillFirstName;
            BlackboardAttribute.Type autofillLastName;
            BlackboardAttribute.Type autofillFullName;
            BlackboardAttribute.Type autofillWorkEmail;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_AUTOFILL") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_AUTOFILL", "Facebook Autofill Information");
                    autofillEmail = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_AUTOFILL_EMAIL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Email");
                    autofillGender = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_AUTOFILL_GENDER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Gender");
                    autofillFirstName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_AUTOFILL_FIRST_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "First Name");
                    autofillLastName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_AUTOFILL_LAST_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Last Name");
                    autofillFullName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_AUTOFILL_FULL_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Full Name");
                    autofillWorkEmail = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_AUTOFILL_WORK_EMAIL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Work Email");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_AUTOFILL");
                    autofillEmail = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_AUTOFILL_EMAIL");
                    autofillGender = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_AUTOFILL_GENDER");
                    autofillFirstName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_AUTOFILL_FIRST_NAME");
                    autofillLastName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_AUTOFILL_LAST_NAME");
                    autofillFullName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_AUTOFILL_FULL_NAME");
                    autofillWorkEmail = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_AUTOFILL_WORK_EMAIL");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            String email = "";
            String gender = "";
            String first_name = "";
            String last_name = "";
            String full_name = "";
            String work_email = "";
            
            if (!autofill_Information.autofill_information_v2.EMAIL.isEmpty()) {
                email = autofill_Information.autofill_information_v2.EMAIL.get(0);
            }
            
            if (!autofill_Information.autofill_information_v2.GENDER.isEmpty()) {
                gender = autofill_Information.autofill_information_v2.GENDER.get(0);
            }
            
            if (!autofill_Information.autofill_information_v2.FIRST_NAME.isEmpty()) {
                first_name = autofill_Information.autofill_information_v2.FIRST_NAME.get(0);
            }
            
            if (!autofill_Information.autofill_information_v2.LAST_NAME.isEmpty()) {
                last_name = autofill_Information.autofill_information_v2.LAST_NAME.get(0);
            }
            
            if (!autofill_Information.autofill_information_v2.FULL_NAME.isEmpty()) {
                full_name = autofill_Information.autofill_information_v2.FULL_NAME.get(0);
            }
            
            if (!autofill_Information.autofill_information_v2.WORK_EMAIL.isEmpty()) {
                work_email = autofill_Information.autofill_information_v2.WORK_EMAIL.get(0);
            }
            
            // add variables to attributes
            Collection<BlackboardAttribute> attributelist = new ArrayList();
            attributelist.add(new BlackboardAttribute(autofillEmail, FacebookIngestModuleFactory.getModuleName(), email));
            attributelist.add(new BlackboardAttribute(autofillGender, FacebookIngestModuleFactory.getModuleName(), gender));
            attributelist.add(new BlackboardAttribute(autofillFirstName, FacebookIngestModuleFactory.getModuleName(), first_name));
            attributelist.add(new BlackboardAttribute(autofillLastName, FacebookIngestModuleFactory.getModuleName(), last_name));
            attributelist.add(new BlackboardAttribute(autofillFullName, FacebookIngestModuleFactory.getModuleName(), full_name));
            attributelist.add(new BlackboardAttribute(autofillWorkEmail, FacebookIngestModuleFactory.getModuleName(), work_email));

            try{
                blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
            }
            catch (TskCoreException | BlackboardException e){
                e.printStackTrace();
                return;
            }
        }
        else{
            logger.log(Level.INFO, "No autofill_information_v2 found");
            return;
        }
    }
    
    /**
    * Process secret_conversations.json file and add data as Data Artifact
    * Facebook user device information for secret conversations on armadillo devices .
    *
    * @param  af  JSON file
    */
    private void processJSONsecret_conversations(AbstractFile af){
        String json = parseAFtoString(af);
        SecretConversationsV2 autofill_Information = new Gson().fromJson(json, SecretConversationsV2.class);
        if(autofill_Information.secret_conversations_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type secretConversationType;
            BlackboardAttribute.Type deviceType;
            BlackboardAttribute.Type deviceManufacturer;
            BlackboardAttribute.Type deviceModel;
            BlackboardAttribute.Type deviceOS;
            BlackboardAttribute.Type lastConnnectedIpAttribute;
            BlackboardAttribute.Type lastActiveDateAttribute;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_SECRET_CONVERSATION_INFO") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_SECRET_CONVERSATION_INFO", "Facebook Devices that used Secret Conversations feature");
                    secretConversationType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Secret Conversation Type");
                    deviceType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_DEVICE_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Device Type");
                    deviceManufacturer = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_DEVICE_MANUFACTURER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Manufacturer");
                    deviceModel = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_DEVICE_MODEL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Model");
                    deviceOS = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_DEVICE_OS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "OS Version");
                    lastConnnectedIpAttribute = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_LAST_CONNECTED_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Last Connected IP");
                    lastActiveDateAttribute = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_LAST_ACTIVE_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Last Active Time");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_SECRET_CONVERSATION_INFO");
                    secretConversationType = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_TYPE");
                    deviceType = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_DEVICE_TYPE");
                    deviceManufacturer = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_DEVICE_MANUFACTURER");
                    deviceModel = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_DEVICE_MODEL");
                    deviceOS = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_DEVICE_OS");
                    lastConnnectedIpAttribute = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_LAST_CONNECTED_IP");
                    lastActiveDateAttribute = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SECRET_CONVERSATION_LAST_ACTIVE_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (SecretConversationsV2.SecretConversations_V2.Armadillo armadillo:autofill_Information.secret_conversations_v2.armadillo_devices){
                String type = "Armadillo";
                String devType = armadillo.device_type;
                String devManu = armadillo.device_manufacturer;
                String devModel = armadillo.device_model;
                String devOS = armadillo.device_os_version;
                String lastConnectedIP = armadillo.last_connected_ip;
                String lastActiveDate = new TimestampToDate(armadillo.last_active_time).getDate();

                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(secretConversationType, FacebookIngestModuleFactory.getModuleName(), type));
                attributelist.add(new BlackboardAttribute(deviceType, FacebookIngestModuleFactory.getModuleName(), devType));
                attributelist.add(new BlackboardAttribute(deviceManufacturer, FacebookIngestModuleFactory.getModuleName(), devManu));
                attributelist.add(new BlackboardAttribute(deviceModel, FacebookIngestModuleFactory.getModuleName(), devModel));
                attributelist.add(new BlackboardAttribute(deviceOS, FacebookIngestModuleFactory.getModuleName(), devOS));
                attributelist.add(new BlackboardAttribute(lastConnnectedIpAttribute, FacebookIngestModuleFactory.getModuleName(), lastConnectedIP));
                attributelist.add(new BlackboardAttribute(lastActiveDateAttribute, FacebookIngestModuleFactory.getModuleName(), lastActiveDate));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No secret_conversations_v2 found");
            return;
        }
    }
    
    /**
    * Process support_messages.json file and add data as Data Artifact
    * Facebook user Facebook support message data.
    *
    * @param  af  JSON file
    */
    private void processJSONsupport_messages(AbstractFile af){
        String json = parseAFtoString(af);
        SupportMessagesV2 supportMessageThreads = new Gson().fromJson(json, SupportMessagesV2.class);
        if(supportMessageThreads.support_messages_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type supportMsgThreadDate;
            BlackboardAttribute.Type supportMsgThreadSubject;
            BlackboardAttribute.Type supportMsgFrom;
            BlackboardAttribute.Type supportMsgTo;
            BlackboardAttribute.Type supportMsgSubject;
            BlackboardAttribute.Type supportMsgMessage;
            BlackboardAttribute.Type supportMsgDate;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_SUPPORT_MESSAGE") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_SUPPORT_MESSAGE", "Facebook Support Messages");
                    supportMsgThreadDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_THREAD_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Message Thread Date");
                    supportMsgThreadSubject = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_THREAD_SUBJECT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Message Thread Subject");
                    supportMsgFrom = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_FROM", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "From");
                    supportMsgTo = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_TO", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "To");
                    supportMsgSubject = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_SUBJECT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Subject");
                    supportMsgMessage = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_MESSAGE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Message");
                    supportMsgDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_SUPPORT_MESSAGE");
                    supportMsgThreadDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_THREAD_DATE");
                    supportMsgThreadSubject = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_THREAD_SUBJECT");
                    supportMsgFrom = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_FROM");
                    supportMsgTo = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_TO");
                    supportMsgSubject = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_SUBJECT");
                    supportMsgMessage = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_MESSAGE");
                    supportMsgDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_SUPPORT_MESSAGE_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (SupportMessagesV2.SupportMessages_V2 thread:supportMessageThreads.support_messages_v2){
                String threadDate = new TimestampToDate(thread.timestamp).getDate();
                String threadSubject = thread.subject;
                for (SupportMessagesV2.SupportMessages_V2.Messages message:thread.messages){
                    String from = message.from;
                    String to = message.to;
                    String subject = message.subject;
                    String messageData = message.message;
                    String date = new TimestampToDate(message.timestamp).getDate();

                    // add variables to attributes
                    Collection<BlackboardAttribute> attributelist = new ArrayList();
                    attributelist.add(new BlackboardAttribute(supportMsgThreadDate, FacebookIngestModuleFactory.getModuleName(), threadDate));
                    attributelist.add(new BlackboardAttribute(supportMsgThreadSubject, FacebookIngestModuleFactory.getModuleName(), threadSubject));
                    attributelist.add(new BlackboardAttribute(supportMsgFrom, FacebookIngestModuleFactory.getModuleName(), from));
                    attributelist.add(new BlackboardAttribute(supportMsgTo, FacebookIngestModuleFactory.getModuleName(), to));
                    attributelist.add(new BlackboardAttribute(supportMsgSubject, FacebookIngestModuleFactory.getModuleName(), subject));
                    attributelist.add(new BlackboardAttribute(supportMsgMessage, FacebookIngestModuleFactory.getModuleName(), messageData));
                    attributelist.add(new BlackboardAttribute(supportMsgDate, FacebookIngestModuleFactory.getModuleName(), date));

                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
        else{
            logger.log(Level.INFO, "No support_messages_v2 found");
            return;
        }
    }
    
    /**
    * Process message_1.json file and add data as Data Artifact
    * Facebook user chat message data.
    *
    * @param  af  JSON file
    */
    private void processJSONmessage_1(AbstractFile af){
        String json = parseAFtoString(af);
        Message1 messageChat = new Gson().fromJson(json, Message1.class);
        if(messageChat.messages != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type messageChat_Title;
            BlackboardAttribute.Type messageChat_ThreadType;
            BlackboardAttribute.Type messageChat_ListOfParticipant;
            BlackboardAttribute.Type message_Sender;
            BlackboardAttribute.Type message_Date;
            BlackboardAttribute.Type message_Content;
            BlackboardAttribute.Type message_Type;
            BlackboardAttribute.Type message_ContentImageURI;
            BlackboardAttribute.Type message_ContentMediaDateCreated;
            BlackboardAttribute.Type message_ContentStickerUri;
            BlackboardAttribute.Type message_ContentShare;
            BlackboardAttribute.Type message_IsUnsent;
            BlackboardAttribute.Type message_IsTakenDown;
            BlackboardAttribute.Type messageChat_ThreadPath;
            BlackboardAttribute.Type messageChat_IsStillParticipant;
            BlackboardAttribute.Type messageChat_MediaUri;
            BlackboardAttribute.Type messageChat_MediaDateCreated;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_MESSENGER_CHAT") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_MESSENGER_CHAT", "Facebook Messenger Chat");
                    messageChat_Title = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_CHAT_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Chat Title");
                    messageChat_ThreadType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_CHAT_THREAD_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Thread Type");
                    messageChat_ListOfParticipant = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_CHAT_LIST_OF_PARTICIPANT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "List of Participants");
                    message_Sender = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_MSG_SENDER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Sender");
                    message_Date = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_MSG_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    message_Content = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_MSG_CONTENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Content");
                    message_Type = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_MSG_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Message Type");
                    message_ContentImageURI = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_MSG_CONTENT_IMAGE_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Message Image URI");
                    message_ContentMediaDateCreated = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_MSG_CONTENT_IMAGE_DATE_CREATED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Message Image Date Created");
                    message_ContentStickerUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_MSG_CONTENT_STICKER_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Message Sticker URI");
                    message_ContentShare = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_MSG_CONTENT_SHARE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Share");
                    message_IsUnsent = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_MSG_UNSENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Message is Unsent?");
                    message_IsTakenDown = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_MSG_TAKEN_DOWN", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Message is Taken Down?");
                    messageChat_ThreadPath = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_CHAT_THREAD_PATH", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Chat Thread Path");
                    messageChat_IsStillParticipant = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_CHAT_STILL_PARTICIPANT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User is still Participant?");
                    messageChat_MediaUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_CHAT_IMAGE_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Group Chat Image URI");
                    messageChat_MediaDateCreated = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_MESSENGER_CHAT_IMAGE_DATE_CREATED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Group Chat Image Date Created");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_MESSENGER_CHAT");
                    messageChat_Title = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_CHAT_TITLE");
                    messageChat_ThreadType = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_CHAT_THREAD_TYPE");
                    messageChat_ListOfParticipant = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_CHAT_LIST_OF_PARTICIPANT");
                    message_Sender = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_MSG_SENDER");
                    message_Date = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_MSG_DATE");
                    message_Content = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_MSG_CONTENT");
                    message_Type = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_MSG_TYPE");
                    message_ContentImageURI = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_MSG_CONTENT_IMAGE_URI");
                    message_ContentMediaDateCreated = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_MSG_CONTENT_IMAGE_DATE_CREATED");
                    message_ContentStickerUri = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_MSG_CONTENT_STICKER_URI");
                    message_ContentShare = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_MSG_CONTENT_SHARE");
                    message_IsUnsent = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_MSG_UNSENT");
                    message_IsTakenDown = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_MSG_TAKEN_DOWN");
                    messageChat_ThreadPath = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_CHAT_THREAD_PATH");
                    messageChat_IsStillParticipant = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_CHAT_STILL_PARTICIPANT");
                    messageChat_MediaUri = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_CHAT_IMAGE_URI");
                    messageChat_MediaDateCreated = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_MESSENGER_CHAT_IMAGE_DATE_CREATED");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            String title = messageChat.title;
            String threadType = messageChat.thread_type;
            // Concatenate participant names
            String names = "";
            for (Message1.Participant participant:messageChat.participants){
                names += participant.name;
                names += ";\n";
            }
            String msgSender = "";
            String msgDate = "";
            String msgContent = "";
            String msgType = "";
            String msgContentMediaUri = "";
            String msgContentMediaDateCreated = "";
            String msgContentStickerUri = "";
            String msgContentShare = "";
            String msgIsUnsent = "";
            String msgIsTakenDown = "";
            String threadPath = messageChat.thread_path;
            String chatIsStillParticipant = messageChat.is_still_participant;
            String chatMediaUri = "";
            String chatMediaDateCreated = "";
            if (messageChat.image != null) {
                chatMediaUri = messageChat.image.uri;
                chatMediaDateCreated = new TimestampToDate(messageChat.image.creation_timestamp).getDate();
            }
            for (Message1.Message message:messageChat.messages){
                msgSender = message.sender_name;
                msgDate = new TimestampToDate(message.timestamp_ms,true).getDate();
                msgContent = message.content;
                
                
                msgType = message.type;
                msgContentMediaUri = "";
                msgContentMediaDateCreated = "";
                msgContentStickerUri = "";
                msgContentShare = message.share;
                msgIsUnsent = message.is_unsent;
                msgIsTakenDown = message.is_taken_down;
                
                if (message.sticker != null) {
                    msgContentStickerUri = message.sticker.uri;
                }
                
                if (message.photos != null) {
                    for (Message1.Message.Image contentImage:message.photos) {
                        msgContentMediaUri = contentImage.uri;
                        msgContentMediaDateCreated = new TimestampToDate(contentImage.creation_timestamp).getDate();
                        
                        // add variables to attributes
                        Collection<BlackboardAttribute> attributelist = new ArrayList();
                        attributelist.add(new BlackboardAttribute(messageChat_Title, FacebookIngestModuleFactory.getModuleName(), title));
                        attributelist.add(new BlackboardAttribute(messageChat_ThreadType, FacebookIngestModuleFactory.getModuleName(), threadType));
                        attributelist.add(new BlackboardAttribute(messageChat_ListOfParticipant, FacebookIngestModuleFactory.getModuleName(), names));
                        attributelist.add(new BlackboardAttribute(message_Sender, FacebookIngestModuleFactory.getModuleName(), msgSender));
                        attributelist.add(new BlackboardAttribute(message_Date, FacebookIngestModuleFactory.getModuleName(), msgDate));
                        attributelist.add(new BlackboardAttribute(message_Content, FacebookIngestModuleFactory.getModuleName(), msgContent));
                        attributelist.add(new BlackboardAttribute(message_Type, FacebookIngestModuleFactory.getModuleName(), msgType));
                        attributelist.add(new BlackboardAttribute(message_ContentImageURI, FacebookIngestModuleFactory.getModuleName(), msgContentMediaUri));
                        attributelist.add(new BlackboardAttribute(message_ContentMediaDateCreated, FacebookIngestModuleFactory.getModuleName(), msgContentMediaDateCreated));
                        attributelist.add(new BlackboardAttribute(message_ContentStickerUri, FacebookIngestModuleFactory.getModuleName(), msgContentStickerUri));
                        attributelist.add(new BlackboardAttribute(message_ContentShare, FacebookIngestModuleFactory.getModuleName(), msgContentShare));
                        attributelist.add(new BlackboardAttribute(message_IsUnsent, FacebookIngestModuleFactory.getModuleName(), msgIsUnsent));
                        attributelist.add(new BlackboardAttribute(message_IsTakenDown, FacebookIngestModuleFactory.getModuleName(), msgIsTakenDown));
                        attributelist.add(new BlackboardAttribute(messageChat_ThreadPath, FacebookIngestModuleFactory.getModuleName(), threadPath));
                        attributelist.add(new BlackboardAttribute(messageChat_IsStillParticipant, FacebookIngestModuleFactory.getModuleName(), chatIsStillParticipant));
                        attributelist.add(new BlackboardAttribute(messageChat_MediaUri, FacebookIngestModuleFactory.getModuleName(), chatMediaUri));
                        attributelist.add(new BlackboardAttribute(messageChat_MediaDateCreated, FacebookIngestModuleFactory.getModuleName(), chatMediaDateCreated));

                        try{
                            blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                        }
                        catch (TskCoreException | BlackboardException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }
                else {
                    // add variables to attributes
                    Collection<BlackboardAttribute> attributelist = new ArrayList();
                    attributelist.add(new BlackboardAttribute(messageChat_Title, FacebookIngestModuleFactory.getModuleName(), title));
                    attributelist.add(new BlackboardAttribute(messageChat_ThreadType, FacebookIngestModuleFactory.getModuleName(), threadType));
                    attributelist.add(new BlackboardAttribute(messageChat_ListOfParticipant, FacebookIngestModuleFactory.getModuleName(), names));
                    attributelist.add(new BlackboardAttribute(message_Sender, FacebookIngestModuleFactory.getModuleName(), msgSender));
                    attributelist.add(new BlackboardAttribute(message_Date, FacebookIngestModuleFactory.getModuleName(), msgDate));
                    attributelist.add(new BlackboardAttribute(message_Content, FacebookIngestModuleFactory.getModuleName(), msgContent));
                    attributelist.add(new BlackboardAttribute(message_Type, FacebookIngestModuleFactory.getModuleName(), msgType));
                    attributelist.add(new BlackboardAttribute(message_ContentImageURI, FacebookIngestModuleFactory.getModuleName(), msgContentMediaUri));
                    attributelist.add(new BlackboardAttribute(message_ContentMediaDateCreated, FacebookIngestModuleFactory.getModuleName(), msgContentMediaDateCreated));
                    attributelist.add(new BlackboardAttribute(message_ContentStickerUri, FacebookIngestModuleFactory.getModuleName(), msgContentStickerUri));
                    attributelist.add(new BlackboardAttribute(message_ContentShare, FacebookIngestModuleFactory.getModuleName(), msgContentShare));
                    attributelist.add(new BlackboardAttribute(message_IsUnsent, FacebookIngestModuleFactory.getModuleName(), msgIsUnsent));
                    attributelist.add(new BlackboardAttribute(message_IsTakenDown, FacebookIngestModuleFactory.getModuleName(), msgIsTakenDown));
                    attributelist.add(new BlackboardAttribute(messageChat_ThreadPath, FacebookIngestModuleFactory.getModuleName(), threadPath));
                    attributelist.add(new BlackboardAttribute(messageChat_IsStillParticipant, FacebookIngestModuleFactory.getModuleName(), chatIsStillParticipant));
                    attributelist.add(new BlackboardAttribute(messageChat_MediaUri, FacebookIngestModuleFactory.getModuleName(), chatMediaUri));
                    attributelist.add(new BlackboardAttribute(messageChat_MediaDateCreated, FacebookIngestModuleFactory.getModuleName(), chatMediaDateCreated));

                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
        else{
            logger.log(Level.INFO, "No messages found");
            return;
        }
    }
    
    /**
    * Process notifications.json file and add data as Data Artifact
    * Facebook notifications data
    *
    * @param  af  JSON file
    */
    private void processJSONnotifications(AbstractFile af){
        String json = parseAFtoString(af);
        NotificationsV2 notifications = new Gson().fromJson(json, NotificationsV2.class);
        if(notifications.notifications_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type notificationDate;
            BlackboardAttribute.Type notificationText;
            BlackboardAttribute.Type notificationReadStatus;
            BlackboardAttribute.Type notificationHrefLink;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_NOTIFICATIONS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_NOTIFICATIONS", "Facebook Notifications");
                    notificationDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_NOTIFICATION_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    notificationText = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_NOTIFICATION_TEXT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Text");
                    notificationReadStatus = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_NOTIFICATION_READ_STATUS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Read Status (true:Unread / false:Read)");
                    notificationHrefLink = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_NOTIFICATION_HREF", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Hyperlink");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_NOTIFICATIONS");
                    notificationDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_NOTIFICATION_DATE");
                    notificationReadStatus = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_NOTIFICATION_TEXT");
                    notificationText = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_NOTIFICATION_READ_STATUS");
                    notificationHrefLink = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_NOTIFICATION_HREF");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (NotificationsV2.Notifications_V2 notification:notifications.notifications_v2){
                String date = new TimestampToDate(notification.timestamp).getDate();
                String text = notification.text;
                String readStatus = notification.unread;
                String href = notification.href;
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(notificationDate, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(notificationText, FacebookIngestModuleFactory.getModuleName(), text));
                attributelist.add(new BlackboardAttribute(notificationReadStatus, FacebookIngestModuleFactory.getModuleName(), readStatus));
                attributelist.add(new BlackboardAttribute(notificationHrefLink, FacebookIngestModuleFactory.getModuleName(), href));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No notifications_v2 found");
            return;
        }
    }
    
    /**
    * Process ads_interests.json file and add data as Data Artifact
    * Facebook user's ads interests and topics data
    *
    * @param  af  JSON file
    */
    private void processJSONads_interests(AbstractFile af){
        String json = parseAFtoString(af);
        TopicsV2 topics = new Gson().fromJson(json, TopicsV2.class);
        if(topics.topics_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type topicData;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_TOPICS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_TOPICS", "Facebook Ad Interests");
                    topicData = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_TOPIC", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Topics");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_TOPICS");
                    topicData = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_TOPIC");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (String topic:topics.topics_v2) {
                String text = topic;
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(topicData, FacebookIngestModuleFactory.getModuleName(), text));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No topics_v2 found");
            return;
        }
    }
    
    /**
    * Process pages_and_profiles_you_follow.json file and add data as Data Artifact
    * Facebook pages and profiles user follows
    *
    * @param  af  JSON file
    */
    private void processJSONpages_and_profiles_you_follow(AbstractFile af){
        String json = parseAFtoString(af);
        PagesFollowedV2 pagesFollowed = new Gson().fromJson(json, PagesFollowedV2.class);
        if(pagesFollowed.pages_followed_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type pageFollowedDate;
            BlackboardAttribute.Type pageFollowedName;
            BlackboardAttribute.Type pageFollowedTitle;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PAGE_FOLLOWED") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PAGE_FOLLOWED", "Facebook Pages User Followed");
                    pageFollowedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAGE_FOLLOWED_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    pageFollowedName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAGE_FOLLOWED_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Page Name");
                    pageFollowedTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAGE_FOLLOWED_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PAGE_FOLLOWED");
                    pageFollowedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAGE_FOLLOWED_DATE");
                    pageFollowedName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAGE_FOLLOWED_TITLE");
                    pageFollowedTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAGE_FOLLOWED_NAME");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (PagesFollowedV2.PagesFollowed_V2 page:pagesFollowed.pages_followed_v2){
                String date = new TimestampToDate(page.timestamp).getDate();
                String name = page.data.get(0).name;
                String title = page.title;
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(pageFollowedDate, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(pageFollowedName, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(pageFollowedTitle, FacebookIngestModuleFactory.getModuleName(), title));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No pages_followed_v2 found");
            return;
        }
    }
    
    /**
    * Process pages_you've_liked.json file and add data as Data Artifact
    * Facebook pages user liked
    *
    * @param  af  JSON file
    */
    private void processJSONpages_youve_liked(AbstractFile af){
        String json = parseAFtoString(af);
        PageLikesV2 pagesLiked = new Gson().fromJson(json, PageLikesV2.class);
        if(pagesLiked.page_likes_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type pageFollowedDate;
            BlackboardAttribute.Type pageFollowedName;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PAGE_LIKED") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PAGE_LIKED", "Facebook Pages User Liked");
                    pageFollowedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAGE_LIKED_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    pageFollowedName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PAGE_LIKED_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Page Name");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PAGE_LIKED");
                    pageFollowedDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAGE_LIKED_DATE");
                    pageFollowedName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PAGE_LIKED_NAME");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            for (PageLikesV2.PageLikes_V2 page:pagesLiked.page_likes_v2){
                String date = new TimestampToDate(page.timestamp).getDate();
                String name = page.name;
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(pageFollowedDate, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(pageFollowedName, FacebookIngestModuleFactory.getModuleName(), name));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
        else{
            logger.log(Level.INFO, "No page_likes_v2 found");
            return;
        }
    }
    
    /**
    * Process your_uncategorized_photos.json file and add data as Data Artifact
    * Facebook user uncategorized photos data
    *
    * @param  af  JSON file
    */
    private void processJSONyour_uncategorized_photos(AbstractFile af){
        String json = parseAFtoString(af);
        OtherPhotosV2 yourUncategorizedPhotos = new Gson().fromJson(json, OtherPhotosV2.class);
        if(yourUncategorizedPhotos.other_photos_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type yourPhotos_MediaTitle;
            BlackboardAttribute.Type yourPhotos_MediaDescription;
            BlackboardAttribute.Type yourPhotos_Tags;
            BlackboardAttribute.Type yourPhotos_MediaUri;
            BlackboardAttribute.Type yourPhotos_MediaDateCreated;
            BlackboardAttribute.Type yourPhotos_MediaUploadedIp;
            BlackboardAttribute.Type yourPhotos_MediaDateTaken;
            BlackboardAttribute.Type yourPhotos_MediaDateModified;
            BlackboardAttribute.Type yourPhotos_MediaIso;
            BlackboardAttribute.Type yourPhotos_MediaFocalLength;
            BlackboardAttribute.Type yourPhotos_MediaCameraMake;
            BlackboardAttribute.Type yourPhotos_MediaCameraModel;
            BlackboardAttribute.Type yourPhotos_MediaCameraExposure;
            BlackboardAttribute.Type yourPhotos_MediaFstop;
            BlackboardAttribute.Type yourPhotos_MediaOrientation;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_USER_PHOTOS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_USER_PHOTOS", "Facebook User Uncategorized Photos");
                    yourPhotos_MediaTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Title");
                    yourPhotos_MediaDescription = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_DESCRIPTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Description");
                    yourPhotos_Tags = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_TAGS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Tagged Accounts");
                    yourPhotos_MediaUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media URI");
                    yourPhotos_MediaDateCreated = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_DATE_CREATED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Created");
                    yourPhotos_MediaUploadedIp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_UPLOADED_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Uploaded IP");
                    yourPhotos_MediaDateTaken = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_DATE_TAKEN", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Taken");
                    yourPhotos_MediaDateModified = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_DATE_MODIFIED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Modified");
                    yourPhotos_MediaIso = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_ISO", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "ISO");
                    yourPhotos_MediaFocalLength = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_FOCAL_LENGTH", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Focal Length");
                    yourPhotos_MediaCameraMake = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_CAMERA_MAKE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Camera Make");
                    yourPhotos_MediaCameraModel = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_CAMERA_MODEL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Camera Model");
                    yourPhotos_MediaCameraExposure = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_CAMERA_EXPOSURE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Camera Exposure");
                    yourPhotos_MediaFstop = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_F_STOP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "F-stop");
                    yourPhotos_MediaOrientation = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_ORIENTATION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Orientation");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_USER_PHOTOS");
                    yourPhotos_MediaTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_TITLE");
                    yourPhotos_MediaDescription = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_DESCRIPTION");
                    yourPhotos_Tags = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_TAGS");
                    yourPhotos_MediaUri = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_URI");
                    yourPhotos_MediaDateCreated = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_DATE_CREATED");
                    yourPhotos_MediaUploadedIp = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_UPLOADED_IP");
                    yourPhotos_MediaDateTaken = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_DATE_TAKEN");
                    yourPhotos_MediaDateModified = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_DATE_MODIFIED");
                    yourPhotos_MediaIso = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_ISO");
                    yourPhotos_MediaFocalLength = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_FOCAL_LENGTH");
                    yourPhotos_MediaCameraMake = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_CAMERA_MAKE");
                    yourPhotos_MediaCameraModel = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_CAMERA_MODEL");
                    yourPhotos_MediaCameraExposure = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_CAMERA_EXPOSURE");
                    yourPhotos_MediaFstop = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_F_STOP");
                    yourPhotos_MediaOrientation = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_PHOTOS_MEDIA_ORIENTATION");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            String mediaTags = "";

            // Media
            String title = "";
            String description = "";
            String uri = "";
            String mediaDateCreated = "";
            String mediaUploadIp = "";
            String mediaDateTaken = "";
            String mediaDateModified = "";
            String mediaIso = "";
            String mediaFocalLength = "";
            String mediaCameraMake = "";
            String mediaCameraModel = "";
            String mediaExposure = "";
            String mediaFstop = "";
            String mediaOrientation = "";
            
            for (OtherPhotosV2.OtherPhotos_V2 photo:yourUncategorizedPhotos.other_photos_v2){
                mediaTags = "";
                
                // Media
                mediaDateCreated = "";
                mediaUploadIp = "";
                mediaDateTaken = "";
                mediaDateModified = "";
                mediaIso = "";
                mediaFocalLength = "";
                mediaCameraMake = "";
                mediaCameraModel = "";
                mediaExposure = "";
                mediaFstop = "";
                mediaOrientation = "";
                
                if (photo.tags != null) {
                    for (OtherPhotosV2.OtherPhotos_V2.Tag tag:photo.tags) {
                        if (tag.name != null) {
                            mediaTags += tag.name;
                            mediaTags += ";\n";
                        }
                    }
                }
                title = photo.title;
                description = photo.description;
                uri = photo.uri;
                mediaDateCreated = new TimestampToDate(photo.creation_timestamp).getDate();
                
                if (photo.media_metadata.photo_metadata != null) {
                    mediaUploadIp = photo.media_metadata.photo_metadata.exif_data.get(0).upload_ip;
                    mediaDateTaken = new TimestampToDate(photo.media_metadata.photo_metadata.exif_data.get(0).taken_timestamp).getDate();
                    mediaDateModified = new TimestampToDate(photo.media_metadata.photo_metadata.exif_data.get(0).modified_timestamp).getDate();
                    mediaIso = photo.media_metadata.photo_metadata.exif_data.get(0).iso;
                    mediaFocalLength = photo.media_metadata.photo_metadata.exif_data.get(0).focal_length;
                    mediaCameraMake = photo.media_metadata.photo_metadata.exif_data.get(0).camera_make;
                    mediaCameraModel = photo.media_metadata.photo_metadata.exif_data.get(0).camera_model;
                    mediaExposure = photo.media_metadata.photo_metadata.exif_data.get(0).exposure;
                    mediaFstop = photo.media_metadata.photo_metadata.exif_data.get(0).f_stop;
                    mediaOrientation = photo.media_metadata.photo_metadata.exif_data.get(0).orientation;
                }
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaTitle, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaDescription, FacebookIngestModuleFactory.getModuleName(), description));
                attributelist.add(new BlackboardAttribute(yourPhotos_Tags, FacebookIngestModuleFactory.getModuleName(), mediaTags));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaUri, FacebookIngestModuleFactory.getModuleName(), uri));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaDateCreated, FacebookIngestModuleFactory.getModuleName(), mediaDateCreated));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaUploadedIp, FacebookIngestModuleFactory.getModuleName(), mediaUploadIp));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaDateTaken, FacebookIngestModuleFactory.getModuleName(), mediaDateTaken));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaDateModified, FacebookIngestModuleFactory.getModuleName(), mediaDateModified));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaIso, FacebookIngestModuleFactory.getModuleName(), mediaIso));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaFocalLength, FacebookIngestModuleFactory.getModuleName(), mediaFocalLength));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaCameraMake, FacebookIngestModuleFactory.getModuleName(), mediaCameraMake));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaCameraModel, FacebookIngestModuleFactory.getModuleName(), mediaCameraModel));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaCameraExposure, FacebookIngestModuleFactory.getModuleName(), mediaExposure));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaFstop, FacebookIngestModuleFactory.getModuleName(), mediaFstop));
                attributelist.add(new BlackboardAttribute(yourPhotos_MediaOrientation, FacebookIngestModuleFactory.getModuleName(), mediaOrientation));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
                
            }
        }
        else{
            logger.log(Level.INFO, "No other_photos_v2 found");
            return;
        }
    }
    
    /**
    * Process your_videos.json file and add data as Data Artifact
    * Facebook user videos data
    *
    * @param  af  JSON file
    */
    private void processJSONyour_videos(AbstractFile af){
        String json = parseAFtoString(af);
        VideosV2 yourVideos = new Gson().fromJson(json, VideosV2.class);
        if(yourVideos.videos_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type yourVideos_MediaTitle;
            BlackboardAttribute.Type yourVideos_MediaDescription;
            BlackboardAttribute.Type yourVideos_MediaUri;
            BlackboardAttribute.Type yourVideos_MediaDateCreated;
            BlackboardAttribute.Type yourVideos_MediaUploadedIp;
            BlackboardAttribute.Type yourVideos_MediaDateUploaded;
            BlackboardAttribute.Type yourVideos_ThumbnailUri;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_USER_VIDEOS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_USER_VIDEOS", "Facebook User Videos");
                    yourVideos_MediaTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Title");
                    yourVideos_MediaDescription = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_DESCRIPTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Description");
                    yourVideos_MediaUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media URI");
                    yourVideos_MediaDateCreated = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_DATE_CREATED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Created");
                    yourVideos_MediaUploadedIp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_UPLOADED_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Uploaded IP");
                    yourVideos_MediaDateUploaded = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_DATE_UPLOADED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Uploaded");
                    yourVideos_ThumbnailUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_USER_VIDEOS_THUMBNAIL_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Thumbnail URI");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_USER_VIDEOS");
                    yourVideos_MediaTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_TITLE");
                    yourVideos_MediaDescription = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_DESCRIPTION");
                    yourVideos_MediaUri = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_URI");
                    yourVideos_MediaDateCreated = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_DATE_CREATED");
                    yourVideos_MediaUploadedIp = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_UPLOADED_IP");
                    yourVideos_MediaDateUploaded = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_VIDEOS_MEDIA_DATE_UPLOADED");
                    yourVideos_ThumbnailUri = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_USER_VIDEOS_THUMBNAIL_URI");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }

            // Media
            String title = "";
            String description = "";
            String uri = "";
            String mediaDateCreated = "";
            String mediaUploadIp = "";
            String mediaDateUploaded = "";
            String mediaThumbnailUri = "";
            
            for (VideosV2.Videos_V2 video:yourVideos.videos_v2){
                // Media
                mediaUploadIp = "";
                mediaDateUploaded = "";
                mediaThumbnailUri = "";
                
                title = video.title;
                description = video.description;
                uri = video.uri;
                mediaDateCreated = new TimestampToDate(video.creation_timestamp).getDate();
                
                if (video.media_metadata.video_metadata != null) {
                    mediaUploadIp = video.media_metadata.video_metadata.exif_data.get(0).upload_ip;
                    mediaDateUploaded = new TimestampToDate(video.media_metadata.video_metadata.exif_data.get(0).upload_timestamp).getDate();
                }
                if (video.thumbnail.uri != null) {
                    mediaThumbnailUri = video.thumbnail.uri;
                }
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(yourVideos_MediaTitle, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(yourVideos_MediaDescription, FacebookIngestModuleFactory.getModuleName(), description));
                attributelist.add(new BlackboardAttribute(yourVideos_MediaUri, FacebookIngestModuleFactory.getModuleName(), uri));
                attributelist.add(new BlackboardAttribute(yourVideos_MediaDateCreated, FacebookIngestModuleFactory.getModuleName(), mediaDateCreated));
                attributelist.add(new BlackboardAttribute(yourVideos_MediaUploadedIp, FacebookIngestModuleFactory.getModuleName(), mediaUploadIp));
                attributelist.add(new BlackboardAttribute(yourVideos_MediaDateUploaded, FacebookIngestModuleFactory.getModuleName(), mediaDateUploaded));
                attributelist.add(new BlackboardAttribute(yourVideos_ThumbnailUri, FacebookIngestModuleFactory.getModuleName(), mediaThumbnailUri));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
                
            }
        }
        else{
            logger.log(Level.INFO, "No videos_v2 found");
            return;
        }
    }
    
    /**
    * Process profile_information.json file and add data as Data Artifact
    * Facebook user profile information data
    *
    * @param  af  JSON file
    */
    private void processJSONprofile_information(AbstractFile af){
        String json = parseAFtoString(af);
        ProfileV2 profileInformation = new Gson().fromJson(json, ProfileV2.class);
        if(profileInformation.profile_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactTypeMain;
            BlackboardArtifact.Type artifactTypeEmail;
            BlackboardArtifact.Type artifactTypeOtherName;
            BlackboardArtifact.Type artifactTypeFamily;
            BlackboardArtifact.Type artifactTypeEducation;
            BlackboardArtifact.Type artifactTypeWork;
            BlackboardArtifact.Type artifactTypeLanguage;
            BlackboardArtifact.Type artifactTypePhone;
            
            BlackboardAttribute.Type profileInfo_FullName;
            BlackboardAttribute.Type profileInfo_FirstName;
            BlackboardAttribute.Type profileInfo_MiddleName;
            BlackboardAttribute.Type profileInfo_LastName;
            
            BlackboardAttribute.Type profileInfo_EmailOwner;
            BlackboardAttribute.Type profileInfo_EmailType;
            BlackboardAttribute.Type profileInfo_Email;
            
            BlackboardAttribute.Type profileInfo_Gender;
            BlackboardAttribute.Type profileInfo_Pronouns;
            
            BlackboardAttribute.Type profileInfo_OtherNameOwner;
            BlackboardAttribute.Type profileInfo_OtherName;
            BlackboardAttribute.Type profileInfo_OtherNameType;
            BlackboardAttribute.Type profileInfo_OtherNameDate;
            
            BlackboardAttribute.Type profileInfo_CurrentCity;
            BlackboardAttribute.Type profileInfo_CurrentCityDate;
            
            BlackboardAttribute.Type profileInfo_Hometown;
            BlackboardAttribute.Type profileInfo_HometownDate;
            
            BlackboardAttribute.Type profileInfo_Relationship;
            BlackboardAttribute.Type profileInfo_RelationshipDate;
            
            BlackboardAttribute.Type profileInfo_FamilyMemberOwner;
            BlackboardAttribute.Type profileInfo_FamilyMemberName;
            BlackboardAttribute.Type profileInfo_FamilyMemberRelation;
            BlackboardAttribute.Type profileInfo_FamilyMemberDate;
            
            BlackboardAttribute.Type profileInfo_EducationOwner;
            BlackboardAttribute.Type profileInfo_EducationName;
            BlackboardAttribute.Type profileInfo_EducationGraduatedStatus;
            BlackboardAttribute.Type profileInfo_EducationType;
            BlackboardAttribute.Type profileInfo_EducationDate;
            
            BlackboardAttribute.Type profileInfo_WorkOwner;
            BlackboardAttribute.Type profileInfo_WorkEmployer;
            BlackboardAttribute.Type profileInfo_WorkTitle;
            BlackboardAttribute.Type profileInfo_WorkDateStart;
            BlackboardAttribute.Type profileInfo_WorkDateEnd;
            BlackboardAttribute.Type profileInfo_WorkDate;
            
            BlackboardAttribute.Type profileInfo_LanguageOwner;
            BlackboardAttribute.Type profileInfo_Language;
            BlackboardAttribute.Type profileInfo_LanguageDate;
            
            BlackboardAttribute.Type profileInfo_ReligiousView;
            
            BlackboardAttribute.Type profileInfo_BloodDonorStatus;
            
            BlackboardAttribute.Type profileInfo_PhoneNumberOwner;
            BlackboardAttribute.Type profileInfo_PhoneNumberType;
            BlackboardAttribute.Type profileInfo_PhoneNumber;
            BlackboardAttribute.Type profileInfo_PhoneNumberVerified;
            
            BlackboardAttribute.Type profileInfo_Username;
            BlackboardAttribute.Type profileInfo_AboutMe;
            BlackboardAttribute.Type profileInfo_FavouriteQuote;
            BlackboardAttribute.Type profileInfo_RegistrationDate;
            BlackboardAttribute.Type profileInfo_ProfileURL;
            try{
                // Main
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_MAIN") == null){
                    artifactTypeMain = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PROFILE_INFO_MAIN", "Facebook User Profile Information");
                    profileInfo_Username = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_USERNAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Username");
                    profileInfo_FullName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_FULLNAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Full Name");
                    profileInfo_FirstName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_FIRSTNAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "First Name");
                    profileInfo_MiddleName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_MIDDLENAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Middle Name");
                    profileInfo_LastName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_LASTNAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Last Name");
                    profileInfo_Gender = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_GENDER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Gender");
                    profileInfo_Pronouns = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_PRONOUN", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Pronouns");
                    profileInfo_CurrentCity = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_CURRENTCITY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Current City");
                    profileInfo_CurrentCityDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_CURRENTCITY_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Current City Date Modified");
                    profileInfo_Hometown = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_HOMETOWN", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Hometown");
                    profileInfo_HometownDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_HOMETOWN_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Hometown Date Modified");
                    profileInfo_ReligiousView = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_RELIGIOUS_VIEW", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Religious View");
                    profileInfo_BloodDonorStatus = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_BLOOD_DONOR_STATUS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Blood Donor Status");
                    profileInfo_Relationship = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_RELATIONSHIP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Relationship");
                    profileInfo_RelationshipDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_RELATIONSHIP_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Relationship Date Modified");
                    profileInfo_AboutMe = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_ABOUT_ME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "\"About Me\" Text");
                    profileInfo_FavouriteQuote = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_FAVOURITE_QUOTE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Favourite Quote");
                    profileInfo_RegistrationDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_REGISTRATION_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Registration Date");
                    profileInfo_ProfileURL = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_PROFILE_URL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Profile URL");
                }
                else{
                    artifactTypeMain = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_MAIN");
                    profileInfo_Username = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_USERNAME");
                    profileInfo_FullName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_FULLNAME");
                    profileInfo_FirstName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_FIRSTNAME");
                    profileInfo_MiddleName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_MIDDLENAME");
                    profileInfo_LastName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_LASTNAME");
                    profileInfo_Gender = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_GENDER");
                    profileInfo_Pronouns = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_PRONOUN");
                    profileInfo_CurrentCity = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_CURRENTCITY");
                    profileInfo_CurrentCityDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_CURRENTCITY_DATE");
                    profileInfo_Hometown = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_HOMETOWN");
                    profileInfo_HometownDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_HOMETOWN_DATE");
                    profileInfo_ReligiousView = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_RELIGIOUS_VIEW");
                    profileInfo_BloodDonorStatus = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_BLOOD_DONOR_STATUS");
                    profileInfo_Relationship = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_RELATIONSHIP");
                    profileInfo_RelationshipDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_RELATIONSHIP_DATE");
                    profileInfo_AboutMe = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_ABOUT_ME");
                    profileInfo_FavouriteQuote = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_FAVOURITE_QUOTE");
                    profileInfo_RegistrationDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_REGISTRATION_DATE");
                    profileInfo_ProfileURL = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_PROFILE_URL");
                }
                
                // Emails
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_EMAIL") == null){
                    artifactTypeEmail = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PROFILE_INFO_EMAIL", "Facebook User Profile Information - Emails");
                    profileInfo_EmailOwner = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_EMAIL_OWNER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User");
                    profileInfo_EmailType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_EMAIL_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Type");
                    profileInfo_Email = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_EMAIL_ADDRESS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Email Address");
                }
                else{
                    artifactTypeEmail = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_EMAIL");
                    profileInfo_EmailOwner = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_EMAIL_OWNER");
                    profileInfo_EmailType = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_EMAIL_TYPE");
                    profileInfo_Email = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_EMAIL_ADDRESS");
                }
                
                // Previous/Other Names
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_NAMES") == null){
                    artifactTypeOtherName = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PROFILE_INFO_NAMES", "Facebook User Profile Information - Previous/Other Names");
                    profileInfo_OtherNameOwner = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_OTHER_NAME_OWNER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User");
                    profileInfo_OtherName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_OTHER_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Name");
                    profileInfo_OtherNameType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_OTHER_NAME_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Type");
                    profileInfo_OtherNameDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_OTHER_NAME_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Modified");
                }
                else{
                    artifactTypeOtherName = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_NAMES");
                    profileInfo_OtherNameOwner = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_OTHER_NAME_OWNER");
                    profileInfo_OtherName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_OTHER_NAME");
                    profileInfo_OtherNameType = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_OTHER_NAME_TYPE");
                    profileInfo_OtherNameDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_OTHER_NAME_DATE");
                }
                
                // Family Members
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER") == null){
                    artifactTypeFamily = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER", "Facebook User Profile Information - Family Members");
                    profileInfo_FamilyMemberOwner = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER_OWNER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User");
                    profileInfo_FamilyMemberName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Family Member Name");
                    profileInfo_FamilyMemberRelation = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER_RELATION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Relation");
                    profileInfo_FamilyMemberDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Modified");
                }
                else{
                    artifactTypeFamily = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER");
                    profileInfo_FamilyMemberOwner = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER_OWNER");
                    profileInfo_FamilyMemberName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER_NAME");
                    profileInfo_FamilyMemberRelation = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER_RELATION");
                    profileInfo_FamilyMemberDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_FAMILY_MEMBER_DATE");
                }
                
                // Education
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_EDUCATION") == null){
                    artifactTypeEducation = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PROFILE_INFO_EDUCATION", "Facebook User Profile Information - Education Experience");
                    profileInfo_EducationOwner = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_EDUCATION_OWNER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User");
                    profileInfo_EducationName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_EDUCATION_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Institution Name");
                    profileInfo_EducationGraduatedStatus = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_EDUCATION_GRADUATED_STATUS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Graduated?");
                    profileInfo_EducationType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_EDUCATION_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Institution Type");
                    profileInfo_EducationDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_EDUCATION_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Dated Modified");
                }
                else{
                    artifactTypeEducation = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_EDUCATION");
                    profileInfo_EducationOwner = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_EDUCATION_OWNER");
                    profileInfo_EducationName = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_EDUCATION_NAME");
                    profileInfo_EducationGraduatedStatus = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_EDUCATION_GRADUATED_STATUS");
                    profileInfo_EducationType = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_EDUCATION_TYPE");
                    profileInfo_EducationDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_EDUCATION_DATE");
                }
                
                // Work
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_WORK") == null){
                    artifactTypeWork = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PROFILE_INFO_WORK", "Facebook User Profile Information - Work Experience");
                    profileInfo_WorkOwner = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_OWNER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User");
                    profileInfo_WorkEmployer = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_EMPLOYER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Employer");
                    profileInfo_WorkTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Job Title");
                    profileInfo_WorkDateStart = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_DATE_START", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Started");
                    profileInfo_WorkDateEnd = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_DATE_END", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Ended");
                    profileInfo_WorkDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Added");
                }
                else{
                    artifactTypeWork = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_WORK");
                    profileInfo_WorkOwner = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_OWNER");
                    profileInfo_WorkEmployer = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_EMPLOYER");
                    profileInfo_WorkTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_TITLE");
                    profileInfo_WorkDateStart = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_DATE_START");
                    profileInfo_WorkDateEnd = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_DATE_END");
                    profileInfo_WorkDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_WORK_DATE");
                }
                
                // Languages
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_LANGUAGE") == null){
                    artifactTypeLanguage = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PROFILE_INFO_LANGUAGE", "Facebook User Profile Information - Languages");
                    profileInfo_LanguageOwner = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_LANGUAGE_OWNER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User");
                    profileInfo_Language = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_LANGUAGE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Language");
                    profileInfo_LanguageDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_LANGUAGE_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Modified");
                }
                else{
                    artifactTypeLanguage = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_LANGUAGE");
                    profileInfo_LanguageOwner = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_LANGUAGE_OWNER");
                    profileInfo_Language = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_LANGUAGE");
                    profileInfo_LanguageDate = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_LANGUAGE_DATE");
                }
                
                // Phone Numbers
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER") == null){
                    artifactTypePhone = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER", "Facebook User Profile Information - Phone Numbers");
                    profileInfo_PhoneNumberOwner = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER_OWNER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User");
                    profileInfo_PhoneNumberType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Type");
                    profileInfo_PhoneNumber = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Phone Number");
                    profileInfo_PhoneNumberVerified = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER_VERIFIED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Verified?");
                }
                else{
                    artifactTypePhone = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER");
                    profileInfo_PhoneNumberOwner = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER_OWNER");
                    profileInfo_PhoneNumberType = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER_TYPE");
                    profileInfo_PhoneNumber = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER");
                    profileInfo_PhoneNumberVerified = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_PROFILE_INFO_PHONE_NUMBER_VERIFIED");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            ProfileV2.Profile_V2 profile = profileInformation.profile_v2;
            
            String fullName = "";
            String firstName = "";
            String middleName = "";
            String lastName = "";
            String gender = "";
            String pronoun = "";
            String currentCity = "";
            String currentCityDate = "";
            String hometown = "";
            String hometownDate = "";
            String relationship = "";
            String relationshipDate = "";
            String religiousView = "";
            String bloodDonorStatus = "";
            String username = "";
            String aboutMe = "";
            String favQuote = "";
            String registrationDate = "";
            String profileUrl = "";
            
            fullName = profile.name.full_name;
            firstName = profile.name.first_name;
            middleName = profile.name.middle_name;
            lastName = profile.name.last_name;
            gender = profile.gender.gender_option;
            pronoun = profile.gender.pronoun;
            bloodDonorStatus = profile.blood_info.blood_donor_status;
            
            if (profile.current_city != null) {
                currentCity = profile.current_city.name;
                currentCityDate = new TimestampToDate(profile.current_city.timestamp).getDate();
            }
            if (profile.hometown != null) {
                hometown = profile.hometown.name;
                hometownDate = new TimestampToDate(profile.hometown.timestamp).getDate();
            }
            if (profile.relationship != null) {
                relationship = profile.relationship.status;
                relationshipDate = new TimestampToDate(profile.relationship.timestamp).getDate();
            }
            if (profile.religious_view != null) {
                religiousView = profile.religious_view.name;
            }
            
            if (profile.username != null) {
                username = profile.username;
            }
            if (profile.about_me != null) {
                aboutMe = profile.about_me;
            }
            if (profile.favorite_quotes != null) {
                favQuote = profile.favorite_quotes;
            }
            
            registrationDate = new TimestampToDate(profile.registration_timestamp).getDate();
            profileUrl = profile.profile_uri;
            
            /////////// Add the Main, Email, OtherName ... atttributes, loop them if necessary
//            // add variables to attributes
//            Collection<BlackboardAttribute> attributelist = new ArrayList();
//            attributelist.add(new BlackboardAttribute(profileInfo_MediaTitle, FacebookIngestModuleFactory.getModuleName(), title));
//            attributelist.add(new BlackboardAttribute(profileInfo_MediaDescription, FacebookIngestModuleFactory.getModuleName(), description));
//            attributelist.add(new BlackboardAttribute(profileInfo_MediaUri, FacebookIngestModuleFactory.getModuleName(), uri));
//            attributelist.add(new BlackboardAttribute(profileInfo_MediaDateCreated, FacebookIngestModuleFactory.getModuleName(), mediaDateCreated));
//            attributelist.add(new BlackboardAttribute(profileInfo_MediaUploadedIp, FacebookIngestModuleFactory.getModuleName(), mediaUploadIp));
//            attributelist.add(new BlackboardAttribute(profileInfo_MediaDateUploaded, FacebookIngestModuleFactory.getModuleName(), mediaDateUploaded));
//            attributelist.add(new BlackboardAttribute(profileInfo_ThumbnailUri, FacebookIngestModuleFactory.getModuleName(), mediaThumbnailUri));
//
//            try{
//                blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
//            }
//            catch (TskCoreException | BlackboardException e){
//                e.printStackTrace();
//                return;
//            }
        }
        else{
            logger.log(Level.INFO, "No profile_v2 found");
            return;
        }
    }
    
    /**
    * Process collections.json file and add data as Data Artifact
    * Facebook user collections data
    *
    * @param  af  JSON file
    */
    private void processJSONcollections(AbstractFile af){
        String json = parseAFtoString(af);
        CollectionsV2 userCollections = new Gson().fromJson(json, CollectionsV2.class);
        if(userCollections.collections_v2 != null){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type yourCollections_Title;
            BlackboardAttribute.Type yourCollections_Name;
            BlackboardAttribute.Type yourCollections_Date;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_COLLECTIONS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_COLLECTIONS", "Facebook User Saved Collections");
                    yourCollections_Title = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_COLLECTIONS_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                    yourCollections_Name = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_COLLECTIONS_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Collection Name");
                    yourCollections_Date = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FACEBOOK_COLLECTIONS_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Created");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_COLLECTIONS");
                    yourCollections_Title = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_COLLECTIONS_TITLE");
                    yourCollections_Name = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_COLLECTIONS_NAME");
                    yourCollections_Date = currentCase.getSleuthkitCase().getAttributeType("LS_FACEBOOK_COLLECTIONS_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }

            // Media
            String title = "";
            String name = "";
            String date = "";
            
            for (CollectionsV2.Collections_V2 collection:userCollections.collections_v2){
                title = collection.title;
                name = collection.attachments.get(0).data.get(0).name;
                date = new TimestampToDate(collection.timestamp).getDate();
                
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(yourCollections_Title, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(yourCollections_Name, FacebookIngestModuleFactory.getModuleName(), name));
                attributelist.add(new BlackboardAttribute(yourCollections_Date, FacebookIngestModuleFactory.getModuleName(), date));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
                
            }
        }
        else{
            logger.log(Level.INFO, "No collections_v2 found");
            return;
        }
    }
    
    /**
    * Process json files with Facebook posts and add data as Data Artifact
    * Facebook data for posts sent by user for the following:
    * User posts (your_posts_1.json)
    * Group posts (your_posts_in_groups.json)
    * Profile update history (profile_update_history.json)
    * Saved posts (your_saved_items.json)
    *
    * @param  af  JSON file
    */
    private void processJSONgeneralPosts(AbstractFile af, String type){
        String json = parseAFtoString(af);
        
        String artifactTypeName = "";
        String displayName = "";
        List<General_Posts> generalPostsList = new ArrayList();
        GeneralPosts generalPosts;
        switch (type) {
                case "your_post1":
                    artifactTypeName = "FACEBOOK_USER";
                    displayName = "Facebook User Posts";
                    
                    General_Posts[] yourPostsArray = new Gson().fromJson(json, General_Posts[].class);
                    generalPostsList = Arrays.asList(yourPostsArray);
                    
                    if(generalPostsList == null){
                        logger.log(Level.INFO, "List \"yourPosts\" is empty");
                        return;
                    }
                    break;
                case "group_posts_v2":
                    artifactTypeName = "FACEBOOK_GROUP";
                    displayName = "Facebook Group Posts";
                    generalPosts = new Gson().fromJson(json, GeneralPosts.class);
                    if(generalPosts.group_posts_v2 != null){
                        generalPostsList = generalPosts.group_posts_v2;
                    }
                    else {
                        logger.log(Level.INFO, "No group_posts_v2 found");
                        return;
                    }
                    break;
                case "profile_updates_v2":
                    artifactTypeName = "FACEBOOK_PROFILE_UPDATES";
                    displayName = "Facebook Group Profile Updates";
                    generalPosts = new Gson().fromJson(json, GeneralPosts.class);
                    if(generalPosts.profile_updates_v2 != null){
                        generalPostsList = generalPosts.profile_updates_v2;
                    }
                    else {
                        logger.log(Level.INFO, "No profile_updates_v2 found");
                        return;
                    }
                    break;
                case "saves_v2":
                    artifactTypeName = "FACEBOOK_SAVED_POSTS";
                    displayName = "Facebook Saved Posts";
                    generalPosts = new Gson().fromJson(json, GeneralPosts.class);
                    if(generalPosts.saves_v2 != null){
                        generalPostsList = generalPosts.saves_v2;
                    }
                    else {
                        logger.log(Level.INFO, "No saves_v2 found");
                        return;
                    }
                    break;
        }
            
        // prepare variables for artifact
        BlackboardArtifact.Type artifactType;
        BlackboardAttribute.Type generalPost_date;
        BlackboardAttribute.Type generalPost_dateUpdated;
        BlackboardAttribute.Type generalPost_title;
        BlackboardAttribute.Type generalPost_post;
        BlackboardAttribute.Type generalPost_Tags;
        BlackboardAttribute.Type generalPost_PlaceName;
        BlackboardAttribute.Type generalPost_PlaceAddress;
        BlackboardAttribute.Type generalPost_PlaceUrl;
        BlackboardAttribute.Type generalPost_PlaceLatitude;
        BlackboardAttribute.Type generalPost_PlaceLongitude;
        BlackboardAttribute.Type generalPost_saleTitle;
        BlackboardAttribute.Type generalPost_salePrice;
        BlackboardAttribute.Type generalPost_saleSeller;
        BlackboardAttribute.Type generalPost_saleCreatedDate;
        BlackboardAttribute.Type generalPost_saleUpdatedDate;
        BlackboardAttribute.Type generalPost_saleCategory;
        BlackboardAttribute.Type generalPost_saleMarketplace;
        BlackboardAttribute.Type generalPost_saleLocationName;
        BlackboardAttribute.Type generalPost_saleLocationLatitude;
        BlackboardAttribute.Type generalPost_saleLocationLongitude;
        BlackboardAttribute.Type generalPost_saleDescription;
        BlackboardAttribute.Type generalPost_uri;
        BlackboardAttribute.Type generalPost_mediaCreatedDate;
        BlackboardAttribute.Type generalPost_mediaDescription;
        BlackboardAttribute.Type generalPost_mediaUploadIp;
        BlackboardAttribute.Type generalPost_mediaDateTaken;
        BlackboardAttribute.Type generalPost_mediaDateModified;
        BlackboardAttribute.Type generalPost_mediaDateUploaded;
        BlackboardAttribute.Type generalPost_mediaIso;
        BlackboardAttribute.Type generalPost_mediaFocalLength;
        BlackboardAttribute.Type generalPost_mediaCameraMake;
        BlackboardAttribute.Type generalPost_mediaCameraModel;
        BlackboardAttribute.Type generalPost_mediaExposure;
        BlackboardAttribute.Type generalPost_mediaFstop;
        BlackboardAttribute.Type generalPost_mediaOrientation;
        BlackboardAttribute.Type generalPost_EC_url;
        try{
            // if artifact type does not exist
            if (currentCase.getSleuthkitCase().getArtifactType("LS_" + artifactTypeName + "_POST") == null){
                artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_" + artifactTypeName + "_POST", displayName);
                generalPost_date = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Posted");
                generalPost_dateUpdated = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_DATE_UPDATED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date Updated");
                generalPost_title = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                generalPost_post = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_POST", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Post");
                generalPost_Tags = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_TAGS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Tagged Accounts");
                generalPost_PlaceName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_PLACE_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Place Name");
                generalPost_PlaceAddress = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_PLACE_ADDRESS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Place Address");
                generalPost_PlaceUrl = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_PLACE_URL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Place URL");
                generalPost_PlaceLatitude = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_PLACE_LATITUDE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Place Latitude");
                generalPost_PlaceLongitude = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_PLACE_LONGITUDE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Place Longitude");
                generalPost_saleTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Title");
                generalPost_salePrice = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_PRICE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Price");
                generalPost_saleSeller = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_SELLER", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Seller");
                generalPost_saleCreatedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_DATE_CREATED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Date Created");
                generalPost_saleUpdatedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_DATE_UPDATED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Date Update");
                generalPost_saleCategory = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_CATEGORY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Category");
                generalPost_saleMarketplace = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_MARKETPLACE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Marketplace");
                generalPost_saleLocationName = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_LOCATION_NAME", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Location Name");
                generalPost_saleLocationLatitude = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_LOCATION_LATITUDE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Location Latitude");
                generalPost_saleLocationLongitude = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_LOCATION_LONGITUDE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Location Longitude");
                generalPost_saleDescription = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_IS_DESCRIPTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Item Sale Description");
                generalPost_uri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media URI");
                generalPost_mediaCreatedDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_DATE_CREATED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Created");
                generalPost_mediaDescription = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_DESCRIPTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Description");
                generalPost_mediaUploadIp = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_IP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Uploaded from IP");
                generalPost_mediaDateTaken = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_DATE_TAKEN", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Taken");
                generalPost_mediaDateModified = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_DATE_MODIFIED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Modified");
                generalPost_mediaDateUploaded = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_DATE_UPLOADED", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Media Date Uploaded");
                generalPost_mediaIso = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_ISO", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "ISO");
                generalPost_mediaFocalLength = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_FOCAL_LENGTH", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Focal Length");
                generalPost_mediaCameraMake = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_CAMERA_MAKE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Camera Make");
                generalPost_mediaCameraModel = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_CAMERA_MODEL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Camera Model");
                generalPost_mediaExposure = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_EXPOSURE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Exposure");
                generalPost_mediaFstop = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_F_STOP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "F-stop");
                generalPost_mediaOrientation = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_MEDIA_ORIENTATION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Orientation");
                generalPost_EC_url = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_" + artifactTypeName + "_URL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "External Context URL");
            }
            else{
                artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_" + artifactTypeName + "_POST");
                generalPost_date = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_DATE");
                generalPost_dateUpdated = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_DATE_UPDATED");
                generalPost_title = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_TITLE");
                generalPost_post = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_POST");
                generalPost_Tags = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_TAGS");
                generalPost_PlaceName = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_PLACE_NAME");
                generalPost_PlaceAddress = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_PLACE_ADDRESS");
                generalPost_PlaceUrl = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_PLACE_URL");
                generalPost_PlaceLatitude = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_PLACE_LATITUDE");
                generalPost_PlaceLongitude = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_PLACE_LONGITUDE");
                generalPost_saleTitle = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_TITLE");
                generalPost_salePrice = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_PRICE");
                generalPost_saleSeller = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_SELLER");
                generalPost_saleCreatedDate = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_DATE_CREATED");
                generalPost_saleUpdatedDate = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_DATE_UPDATED");
                generalPost_saleCategory = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_CATEGORY");
                generalPost_saleMarketplace = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_MARKETPLACE");
                generalPost_saleLocationName = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_LOCATION_NAME");
                generalPost_saleLocationLatitude = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_LOCATION_LATITUDE");
                generalPost_saleLocationLongitude = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_LOCATION_LONGITUDE");
                generalPost_saleDescription = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_IS_DESCRIPTION");
                generalPost_uri = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_URI");
                generalPost_mediaCreatedDate = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_DATE_CREATED");
                generalPost_mediaDescription = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_DESCRIPTION");
                generalPost_mediaUploadIp = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_IP");
                generalPost_mediaDateTaken = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_DATE_TAKEN");
                generalPost_mediaDateModified = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_DATE_MODIFIED");
                generalPost_mediaDateUploaded = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_DATE_UPLOADED");
                generalPost_mediaIso = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_ISO");
                generalPost_mediaFocalLength = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_FOCAL_LENGTH");
                generalPost_mediaCameraMake = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_CAMERA_MAKE");
                generalPost_mediaCameraModel = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_CAMERA_MODEL");
                generalPost_mediaExposure = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_EXPOSURE");
                generalPost_mediaFstop = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_F_STOP");
                generalPost_mediaOrientation = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_MEDIA_ORIENTATION");
                generalPost_EC_url = currentCase.getSleuthkitCase().getAttributeType("LS_" + artifactTypeName + "_URL");
            }
        }
        catch (TskCoreException | TskDataException e){
            e.printStackTrace();
            return;
        }

        for (GeneralPosts.General_Posts generalPost:generalPostsList){

            String date = new TimestampToDate(generalPost.timestamp).getDate();
            String title = generalPost.title;
            String post = "";
            String postTags = "";
            String postDateUpdated = "";

            // Place data
            String placeName = "";
            String placeAddress = "";
            String placeUrl = "";
            String placeLatitude = "";
            String placeLongitude = "";

            // Marketplace variables
            String saleTitle = "";
            String salePrice = "";
            String saleSeller = "";
            String saleCreatedDate = "";
            String saleUpdatedDate = "";
            String saleCategory = "";
            String saleMarketplace = "";
            String saleLocationName = "";
            String saleLocationLatitude = "";
            String saleLocationLongitude = "";
            String saleDescription = "";

            // Media attachment variables
            String uri = "";
            String attachmentCreatedDate = "";
            String description = "";
            String mediaUploadIp = "";
            String mediaDateTaken = "";
            String mediaDateModified = "";
            String mediaDateUploaded = "";
            String mediaIso = "";
            String mediaFocalLength = "";
            String mediaCameraMake = "";
            String mediaCameraModel = "";
            String mediaExposure = "";
            String mediaFstop = "";
            String mediaOrientation = "";

            // External Context variables
            String url = "";

            // Post data
            if (generalPost.data != null){
                for (GeneralPosts.General_Posts.Data postData:generalPost.data) {
                    if (postData.post != null) {
                        post = postData.post;
                    }
                    if (postData.update_timestamp != 0) {
                        postDateUpdated = new TimestampToDate(postData.update_timestamp).getDate();
                    }
                }
            }

            if (generalPost.tags != null) {
                for (GeneralPosts.General_Posts.Tag tag:generalPost.tags) {
                    if (tag.name != null) {
                        postTags += tag.name;
                        postTags += ";\n";
                    }
                }
            }

            if (generalPost.attachments != null){
                boolean marketplaceInPost = false;
                boolean attachmentInPost = false;
                boolean externalContextInPost = false;
                // Marketplace and External Context data
                for (GeneralPosts.General_Posts.Attachment attachment:generalPost.attachments) {
                    for (GeneralPosts.General_Posts.Attachment.Data attachmentData:attachment.data) {
                        if (attachmentData.for_sale_item != null){
                            saleTitle = attachmentData.for_sale_item.title;
                            salePrice = attachmentData.for_sale_item.price;
                            saleSeller = attachmentData.for_sale_item.seller;
                            saleCreatedDate = new TimestampToDate(attachmentData.for_sale_item.created_timestamp).getDate();
                            saleUpdatedDate = new TimestampToDate(attachmentData.for_sale_item.updated_timestamp).getDate();
                            saleCategory = attachmentData.for_sale_item.category;
                            saleMarketplace = attachmentData.for_sale_item.marketplace;
                            saleLocationName = attachmentData.for_sale_item.location.name;
                            saleLocationLatitude = attachmentData.for_sale_item.location.coordinate.latitude;
                            saleLocationLongitude = attachmentData.for_sale_item.location.coordinate.longitude;
                            saleDescription = attachmentData.for_sale_item.description;
                            marketplaceInPost = true;
                        }
                        if (attachmentData.place != null) {
                            placeName = attachmentData.place.name;
                            placeAddress = attachmentData.place.address;
                            placeUrl = attachmentData.place.url;
                            placeLatitude = attachmentData.place.coordinate.latitude;
                            placeLongitude = attachmentData.place.coordinate.longitude;
                        }
                        if (attachmentData.external_context != null){
                            url = attachmentData.external_context.url;
                            externalContextInPost = true;
                        }
                        if (attachmentData.media != null || attachmentData.place != null){
                            attachmentInPost = true;
                        }
                    }
                }

                // Attachment data
                if (attachmentInPost) {
                    // Attachment data
                    for (GeneralPosts.General_Posts.Attachment attachment:generalPost.attachments) {
                        for (GeneralPosts.General_Posts.Attachment.Data attachmentData:attachment.data) {
                            uri = "";
                            attachmentCreatedDate = "";
                            description = "";
                            mediaUploadIp = "";
                            mediaDateTaken = "";
                            mediaDateModified = "";
                            mediaDateUploaded = "";
                            mediaIso = "";
                            mediaFocalLength = "";
                            mediaCameraMake = "";
                            mediaCameraModel = "";
                            mediaExposure = "";
                            mediaFstop = "";
                            mediaOrientation = "";

                            if (attachmentData.media != null){
                                uri = attachmentData.media.uri;
                                attachmentCreatedDate = new TimestampToDate(attachmentData.media.creation_timestamp).getDate();
                                description = attachmentData.media.description;
                                if (attachmentData.media.media_metadata.photo_metadata != null) {
                                    mediaUploadIp = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).upload_ip;
                                    mediaDateTaken = new TimestampToDate(attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).taken_timestamp).getDate();
                                    mediaDateModified = new TimestampToDate(attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).modified_timestamp).getDate();
                                    mediaIso = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).iso;
                                    mediaFocalLength = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).focal_length;
                                    mediaCameraMake = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).camera_make;
                                    mediaCameraModel = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).camera_model;
                                    mediaExposure = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).exposure;
                                    mediaFstop = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).f_stop;
                                    mediaOrientation = attachmentData.media.media_metadata.photo_metadata.exif_data.get(0).orientation;
                                }
                                if (attachmentData.media.media_metadata.video_metadata != null) {
                                    mediaUploadIp = attachmentData.media.media_metadata.video_metadata.exif_data.get(0).upload_ip;
                                    mediaDateUploaded = new TimestampToDate(attachmentData.media.media_metadata.video_metadata.exif_data.get(0).upload_timestamp).getDate();
                                }
                                // add variables to attributes
                                Collection<BlackboardAttribute> attributelist = new ArrayList();
                                attributelist.add(new BlackboardAttribute(generalPost_date, FacebookIngestModuleFactory.getModuleName(), date));
                                attributelist.add(new BlackboardAttribute(generalPost_dateUpdated, FacebookIngestModuleFactory.getModuleName(), postDateUpdated));
                                attributelist.add(new BlackboardAttribute(generalPost_title, FacebookIngestModuleFactory.getModuleName(), title));
                                attributelist.add(new BlackboardAttribute(generalPost_post, FacebookIngestModuleFactory.getModuleName(), post));
                                attributelist.add(new BlackboardAttribute(generalPost_Tags, FacebookIngestModuleFactory.getModuleName(), postTags));
                                attributelist.add(new BlackboardAttribute(generalPost_PlaceName, FacebookIngestModuleFactory.getModuleName(), placeName));
                                attributelist.add(new BlackboardAttribute(generalPost_PlaceAddress, FacebookIngestModuleFactory.getModuleName(), placeAddress));
                                attributelist.add(new BlackboardAttribute(generalPost_PlaceUrl, FacebookIngestModuleFactory.getModuleName(), placeUrl));
                                attributelist.add(new BlackboardAttribute(generalPost_PlaceLatitude, FacebookIngestModuleFactory.getModuleName(), placeLatitude));
                                attributelist.add(new BlackboardAttribute(generalPost_PlaceLongitude, FacebookIngestModuleFactory.getModuleName(), placeLongitude));
                                attributelist.add(new BlackboardAttribute(generalPost_saleTitle, FacebookIngestModuleFactory.getModuleName(), saleTitle));
                                attributelist.add(new BlackboardAttribute(generalPost_salePrice, FacebookIngestModuleFactory.getModuleName(), salePrice));
                                attributelist.add(new BlackboardAttribute(generalPost_saleDescription, FacebookIngestModuleFactory.getModuleName(), saleDescription));
                                attributelist.add(new BlackboardAttribute(generalPost_saleSeller, FacebookIngestModuleFactory.getModuleName(), saleSeller));
                                attributelist.add(new BlackboardAttribute(generalPost_saleCreatedDate, FacebookIngestModuleFactory.getModuleName(), saleCreatedDate));
                                attributelist.add(new BlackboardAttribute(generalPost_saleUpdatedDate, FacebookIngestModuleFactory.getModuleName(), saleUpdatedDate));
                                attributelist.add(new BlackboardAttribute(generalPost_saleCategory, FacebookIngestModuleFactory.getModuleName(), saleCategory));
                                attributelist.add(new BlackboardAttribute(generalPost_saleMarketplace, FacebookIngestModuleFactory.getModuleName(), saleMarketplace));
                                attributelist.add(new BlackboardAttribute(generalPost_saleLocationName, FacebookIngestModuleFactory.getModuleName(), saleLocationName));
                                attributelist.add(new BlackboardAttribute(generalPost_saleLocationLatitude, FacebookIngestModuleFactory.getModuleName(), saleLocationLatitude));
                                attributelist.add(new BlackboardAttribute(generalPost_saleLocationLongitude, FacebookIngestModuleFactory.getModuleName(), saleLocationLongitude));
                                attributelist.add(new BlackboardAttribute(generalPost_uri, FacebookIngestModuleFactory.getModuleName(), uri));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaCreatedDate, FacebookIngestModuleFactory.getModuleName(), attachmentCreatedDate));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaDescription, FacebookIngestModuleFactory.getModuleName(), description));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaUploadIp, FacebookIngestModuleFactory.getModuleName(), mediaUploadIp));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaDateTaken, FacebookIngestModuleFactory.getModuleName(), mediaDateTaken));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaDateModified, FacebookIngestModuleFactory.getModuleName(), mediaDateModified));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaDateUploaded, FacebookIngestModuleFactory.getModuleName(), mediaDateUploaded));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaIso, FacebookIngestModuleFactory.getModuleName(), mediaIso));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaFocalLength, FacebookIngestModuleFactory.getModuleName(), mediaFocalLength));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaCameraMake, FacebookIngestModuleFactory.getModuleName(), mediaCameraMake));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaCameraModel, FacebookIngestModuleFactory.getModuleName(), mediaCameraModel));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaExposure, FacebookIngestModuleFactory.getModuleName(), mediaExposure));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaFstop, FacebookIngestModuleFactory.getModuleName(), mediaFstop));
                                attributelist.add(new BlackboardAttribute(generalPost_mediaOrientation, FacebookIngestModuleFactory.getModuleName(), mediaOrientation));
                                attributelist.add(new BlackboardAttribute(generalPost_EC_url, FacebookIngestModuleFactory.getModuleName(), url));
                                
                                try{
                                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                                }
                                catch (TskCoreException | BlackboardException e){
                                    e.printStackTrace();
                                    
                                    return;
                                }
                            }
                        }
                    }
                }
                else if (marketplaceInPost || externalContextInPost) {
                    // add variables to attributes
                    Collection<BlackboardAttribute> attributelist = new ArrayList();
                    attributelist.add(new BlackboardAttribute(generalPost_date, FacebookIngestModuleFactory.getModuleName(), date));
                    attributelist.add(new BlackboardAttribute(generalPost_dateUpdated, FacebookIngestModuleFactory.getModuleName(), postDateUpdated));
                    attributelist.add(new BlackboardAttribute(generalPost_title, FacebookIngestModuleFactory.getModuleName(), title));
                    attributelist.add(new BlackboardAttribute(generalPost_post, FacebookIngestModuleFactory.getModuleName(), post));
                    attributelist.add(new BlackboardAttribute(generalPost_Tags, FacebookIngestModuleFactory.getModuleName(), postTags));
                    attributelist.add(new BlackboardAttribute(generalPost_PlaceName, FacebookIngestModuleFactory.getModuleName(), placeName));
                    attributelist.add(new BlackboardAttribute(generalPost_PlaceAddress, FacebookIngestModuleFactory.getModuleName(), placeAddress));
                    attributelist.add(new BlackboardAttribute(generalPost_PlaceUrl, FacebookIngestModuleFactory.getModuleName(), placeUrl));
                    attributelist.add(new BlackboardAttribute(generalPost_PlaceLatitude, FacebookIngestModuleFactory.getModuleName(), placeLatitude));
                    attributelist.add(new BlackboardAttribute(generalPost_PlaceLongitude, FacebookIngestModuleFactory.getModuleName(), placeLongitude));
                    attributelist.add(new BlackboardAttribute(generalPost_saleTitle, FacebookIngestModuleFactory.getModuleName(), saleTitle));
                    attributelist.add(new BlackboardAttribute(generalPost_salePrice, FacebookIngestModuleFactory.getModuleName(), salePrice));
                    attributelist.add(new BlackboardAttribute(generalPost_saleDescription, FacebookIngestModuleFactory.getModuleName(), saleDescription));
                    attributelist.add(new BlackboardAttribute(generalPost_saleSeller, FacebookIngestModuleFactory.getModuleName(), saleSeller));
                    attributelist.add(new BlackboardAttribute(generalPost_saleCreatedDate, FacebookIngestModuleFactory.getModuleName(), saleCreatedDate));
                    attributelist.add(new BlackboardAttribute(generalPost_saleUpdatedDate, FacebookIngestModuleFactory.getModuleName(), saleUpdatedDate));
                    attributelist.add(new BlackboardAttribute(generalPost_saleCategory, FacebookIngestModuleFactory.getModuleName(), saleCategory));
                    attributelist.add(new BlackboardAttribute(generalPost_saleMarketplace, FacebookIngestModuleFactory.getModuleName(), saleMarketplace));
                    attributelist.add(new BlackboardAttribute(generalPost_saleLocationName, FacebookIngestModuleFactory.getModuleName(), saleLocationName));
                    attributelist.add(new BlackboardAttribute(generalPost_saleLocationLatitude, FacebookIngestModuleFactory.getModuleName(), saleLocationLatitude));
                    attributelist.add(new BlackboardAttribute(generalPost_saleLocationLongitude, FacebookIngestModuleFactory.getModuleName(), saleLocationLongitude));
                    attributelist.add(new BlackboardAttribute(generalPost_uri, FacebookIngestModuleFactory.getModuleName(), uri));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaCreatedDate, FacebookIngestModuleFactory.getModuleName(), attachmentCreatedDate));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaDescription, FacebookIngestModuleFactory.getModuleName(), description));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaUploadIp, FacebookIngestModuleFactory.getModuleName(), mediaUploadIp));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaDateTaken, FacebookIngestModuleFactory.getModuleName(), mediaDateTaken));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaDateModified, FacebookIngestModuleFactory.getModuleName(), mediaDateModified));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaDateUploaded, FacebookIngestModuleFactory.getModuleName(), mediaDateUploaded));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaIso, FacebookIngestModuleFactory.getModuleName(), mediaIso));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaFocalLength, FacebookIngestModuleFactory.getModuleName(), mediaFocalLength));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaCameraMake, FacebookIngestModuleFactory.getModuleName(), mediaCameraMake));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaCameraModel, FacebookIngestModuleFactory.getModuleName(), mediaCameraModel));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaExposure, FacebookIngestModuleFactory.getModuleName(), mediaExposure));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaFstop, FacebookIngestModuleFactory.getModuleName(), mediaFstop));
                    attributelist.add(new BlackboardAttribute(generalPost_mediaOrientation, FacebookIngestModuleFactory.getModuleName(), mediaOrientation));
                    attributelist.add(new BlackboardAttribute(generalPost_EC_url, FacebookIngestModuleFactory.getModuleName(), url));

                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
                        return;
                    }
                }
            }
            else {
                // add variables to attributes
                Collection<BlackboardAttribute> attributelist = new ArrayList();
                attributelist.add(new BlackboardAttribute(generalPost_date, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(generalPost_dateUpdated, FacebookIngestModuleFactory.getModuleName(), postDateUpdated));
                attributelist.add(new BlackboardAttribute(generalPost_title, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(generalPost_post, FacebookIngestModuleFactory.getModuleName(), post));
                attributelist.add(new BlackboardAttribute(generalPost_Tags, FacebookIngestModuleFactory.getModuleName(), postTags));
                attributelist.add(new BlackboardAttribute(generalPost_PlaceName, FacebookIngestModuleFactory.getModuleName(), placeName));
                attributelist.add(new BlackboardAttribute(generalPost_PlaceAddress, FacebookIngestModuleFactory.getModuleName(), placeAddress));
                attributelist.add(new BlackboardAttribute(generalPost_PlaceUrl, FacebookIngestModuleFactory.getModuleName(), placeUrl));
                attributelist.add(new BlackboardAttribute(generalPost_PlaceLatitude, FacebookIngestModuleFactory.getModuleName(), placeLatitude));
                attributelist.add(new BlackboardAttribute(generalPost_PlaceLongitude, FacebookIngestModuleFactory.getModuleName(), placeLongitude));
                attributelist.add(new BlackboardAttribute(generalPost_saleTitle, FacebookIngestModuleFactory.getModuleName(), saleTitle));
                attributelist.add(new BlackboardAttribute(generalPost_salePrice, FacebookIngestModuleFactory.getModuleName(), salePrice));
                attributelist.add(new BlackboardAttribute(generalPost_saleDescription, FacebookIngestModuleFactory.getModuleName(), saleDescription));
                attributelist.add(new BlackboardAttribute(generalPost_saleSeller, FacebookIngestModuleFactory.getModuleName(), saleSeller));
                attributelist.add(new BlackboardAttribute(generalPost_saleCreatedDate, FacebookIngestModuleFactory.getModuleName(), saleCreatedDate));
                attributelist.add(new BlackboardAttribute(generalPost_saleUpdatedDate, FacebookIngestModuleFactory.getModuleName(), saleUpdatedDate));
                attributelist.add(new BlackboardAttribute(generalPost_saleCategory, FacebookIngestModuleFactory.getModuleName(), saleCategory));
                attributelist.add(new BlackboardAttribute(generalPost_saleMarketplace, FacebookIngestModuleFactory.getModuleName(), saleMarketplace));
                attributelist.add(new BlackboardAttribute(generalPost_saleLocationName, FacebookIngestModuleFactory.getModuleName(), saleLocationName));
                attributelist.add(new BlackboardAttribute(generalPost_saleLocationLatitude, FacebookIngestModuleFactory.getModuleName(), saleLocationLatitude));
                attributelist.add(new BlackboardAttribute(generalPost_saleLocationLongitude, FacebookIngestModuleFactory.getModuleName(), saleLocationLongitude));
                attributelist.add(new BlackboardAttribute(generalPost_uri, FacebookIngestModuleFactory.getModuleName(), uri));
                attributelist.add(new BlackboardAttribute(generalPost_mediaCreatedDate, FacebookIngestModuleFactory.getModuleName(), attachmentCreatedDate));
                attributelist.add(new BlackboardAttribute(generalPost_mediaDescription, FacebookIngestModuleFactory.getModuleName(), description));
                attributelist.add(new BlackboardAttribute(generalPost_mediaUploadIp, FacebookIngestModuleFactory.getModuleName(), mediaUploadIp));
                attributelist.add(new BlackboardAttribute(generalPost_mediaDateTaken, FacebookIngestModuleFactory.getModuleName(), mediaDateTaken));
                attributelist.add(new BlackboardAttribute(generalPost_mediaDateModified, FacebookIngestModuleFactory.getModuleName(), mediaDateModified));
                attributelist.add(new BlackboardAttribute(generalPost_mediaDateUploaded, FacebookIngestModuleFactory.getModuleName(), mediaDateUploaded));
                attributelist.add(new BlackboardAttribute(generalPost_mediaIso, FacebookIngestModuleFactory.getModuleName(), mediaIso));
                attributelist.add(new BlackboardAttribute(generalPost_mediaFocalLength, FacebookIngestModuleFactory.getModuleName(), mediaFocalLength));
                attributelist.add(new BlackboardAttribute(generalPost_mediaCameraMake, FacebookIngestModuleFactory.getModuleName(), mediaCameraMake));
                attributelist.add(new BlackboardAttribute(generalPost_mediaCameraModel, FacebookIngestModuleFactory.getModuleName(), mediaCameraModel));
                attributelist.add(new BlackboardAttribute(generalPost_mediaExposure, FacebookIngestModuleFactory.getModuleName(), mediaExposure));
                attributelist.add(new BlackboardAttribute(generalPost_mediaFstop, FacebookIngestModuleFactory.getModuleName(), mediaFstop));
                attributelist.add(new BlackboardAttribute(generalPost_mediaOrientation, FacebookIngestModuleFactory.getModuleName(), mediaOrientation));
                attributelist.add(new BlackboardAttribute(generalPost_EC_url, FacebookIngestModuleFactory.getModuleName(), url));

                try{
                    blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), FacebookIngestModuleFactory.getModuleName());
                }
                catch (TskCoreException | BlackboardException e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    
    /**
    * Read file and parse JSON as JsonObject
    *
    * @param  af  JSON file
    * @return JsonObject
    */
    private JsonObject parseAFtoJson(AbstractFile af){
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
        return json;
    }
    
    /**
    * Read file and parse JSON as String
    *
    * @param  af  JSON file
    * @return String
    */
    private String parseAFtoString(AbstractFile af){
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
        
        String AFString = new String(jsonBytes, StandardCharsets.UTF_8);
        return AFString;
    }
}
