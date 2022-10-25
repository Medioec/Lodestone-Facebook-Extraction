/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import org.lodestone.facebookingest.pojo.*;
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
                case "use_of_wallets_across_accounts.json":
                    break;
                case "items_sold.json":
                    break;
                case "event_invitations.json":
                    break;
                case "your_event_responses.json":
                    processJSONyour_event_responses(af);
                    break;
                case "marketplace_notifications.json":
                    break;
                case "payment_history.json":
                    break;
                case "reduce.json":
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
                    break;
                case "your_group_membership_activity.json":
                    break;
                case "your_posts_in_groups.json":
                    break;
                case "last_location.json":
                    break;
                case "primary_location.json":
                    break;
                case "primary_public_location.json":
                    break;
                case "timezone.json":
                    break;
                case "autofill_information.json":
                    break;
                case "secret_conversations.json":
                    break;
                case "support_messages.json":
                    break;
                case "message_1.json":
                    break;
                case "notifications.json":
                    break;
                case "ads_interests.json":
                    break;
                case "friend_peer_group.json":
                    break;
                case "pages_and_profiles_you_follow.json":
                    break;
                case "pages_you've_liked.json":
                    break;
                case "your_posts_1.json":
                    break;
                case "your_uncategorized_photos.json":
                    break;
                case "your_videos.json":
                    break;
                case "0.json":
                    break;
                case "1.json":
                    break;
                case "2.json":
                    break;
                case "3.json":
                    break;
                case "language_and_locale.json":
                    break;
                case "profile_information.json":
                    break;
                case "profile_update_history.json":
                    break;
                case "collections.json":
                    break;
                case "your_saved_items.json":
                    break;
                case "your_search_history.json":
                    break;
                case "account_activity.json":
                    processJSONaccount_activity(af);
                    break;
                case "browser_cookies.json":
                    break;
                case "email_address_verifications.json":
                    processJSONemail_address_verifications(af);
                    break;
                case "ip_address_activity.json":
                    processJSONip_address_activity(af);
                    break;
                case "logins_and_logouts.json":
                    break;
                case "login_protection_data.json":
                    break;
                case "mobile_devices.json":
                    break;
                case "record_details.json":
                    break;
                case "where_you're_logged_in.json":
                    break;
                case "your_facebook_activity_history.json":
                    break;
                case "location.json":
                    break;
                case "voting_reminders.json":
                    break;
                case "recently_viewed.json":
                    break;
                case "recently_visited.json":
                    break;
                case "your_topics.json":
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
    
	
    /**
    * Process ip_address_activity.json file and add data as Data Artifact
    * Facebook IP address activity data.
    *
    * @param  af  JSON file
    */
    private void processJSONip_address_activity(AbstractFile af){
        JsonObject json = parseAFtoJson(af);
        
        if(json.has("used_ip_address_v2")){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type ipAddressIP;
            BlackboardAttribute.Type ipAddressAction;
            BlackboardAttribute.Type ipAddressDate;
            BlackboardAttribute.Type ipAddressUserAgent;

            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_IP_ADDRESS_ACTIVITY") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_IP_ADDRESS_ACTIVITY", "Facebook ip address acvitivity");
                    ipAddressIP = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBIPACTIVITY_IPADDRESS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "IP Address");
                    ipAddressAction = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBIPACTIVITY_ACTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "IP Address Action");
                    ipAddressDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBIPACTIVITY_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    ipAddressUserAgent = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBIPACTIVITY_USER_AGENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User Agent");
  
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_IP_ADDRESS_ACTIVITY");
                    ipAddressIP = currentCase.getSleuthkitCase().getAttributeType("LS_FBIPACTIVITY_IPADDRESS");
                    ipAddressAction = currentCase.getSleuthkitCase().getAttributeType("LS_FBIPACTIVITY_ACTION");
                    ipAddressDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBIPACTIVITY_DATE");
                    ipAddressUserAgent = currentCase.getSleuthkitCase().getAttributeType("LS_FBIPACTIVITY_USER_AGENT");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            JsonArray ipAddressActivityV2 = json.getAsJsonArray("used_ip_address_v2");
            for (JsonElement ipAddressActivity:ipAddressActivityV2){
                
                String ipAddress = "";
                String action = "";
                String date = "";
                String userAgent = "";

                if (ipAddressActivity.isJsonObject()){
                    JsonObject reactionObj = (JsonObject)ipAddressActivity;
                    ipAddress = reactionObj.get("ip").getAsString();
                    action = reactionObj.get("action").getAsString();
                    date = new TimestampToDate(ipAddressActivity.getAsJsonObject().get("timestamp").getAsLong()).getDate();
                    userAgent = reactionObj.get("user_agent").getAsString();
                  
                    
                    // add variables to attributes
                    Collection<BlackboardAttribute> attributelist = new ArrayList();
                    attributelist.add(new BlackboardAttribute(ipAddressIP, FacebookIngestModuleFactory.getModuleName(), ipAddress));
                    attributelist.add(new BlackboardAttribute(ipAddressAction, FacebookIngestModuleFactory.getModuleName(), action));
                    attributelist.add(new BlackboardAttribute(ipAddressDate, FacebookIngestModuleFactory.getModuleName(), date));
                    attributelist.add(new BlackboardAttribute(ipAddressUserAgent, FacebookIngestModuleFactory.getModuleName(), userAgent));

                    
                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), "FacebookFileIngestModule");
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
        else{
            logger.log(Level.INFO, "No used_ip_address_v2 found");
            return;
        }
    }
    
    /**
    * Process email_address_verifications.json file and add data as Data Artifact
    * Facebook reaction data.
    *
    * @param  af  JSON file
    */
    private void processJSONemail_address_verifications(AbstractFile af){
        JsonObject json = parseAFtoJson(af);
        
        if(json.has("contact_verifications_v2")){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type emailVerifyContact;
            BlackboardAttribute.Type emailVerifyContactType;
            BlackboardAttribute.Type emailVerifyDate;

            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_EMAIL_ADDRESS_VERIFICATIONS") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_EMAIL_ADDRESS_VERIFICATIONS", "Facebook email address verifications");
                    emailVerifyContact = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBEMAILVERIFY_CONTACT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Contact");
                    emailVerifyContactType = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBEMAILVERIFY_CONTACT_TYPE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Contact Type");
                    emailVerifyDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBEMAILVERIFY_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
  
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_EMAIL_ADDRESS_VERIFICATIONS");
                    emailVerifyContact = currentCase.getSleuthkitCase().getAttributeType("LS_FBEMAILVERIFY_CONTACT");
                    emailVerifyContactType = currentCase.getSleuthkitCase().getAttributeType("LS_FBEMAILVERIFY_CONTACT_TYPE");
                    emailVerifyDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBEMAILVERIFY_DATE");
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            JsonArray emailVerifyV2 = json.getAsJsonArray("contact_verifications_v2");
            for (JsonElement emailVerify:emailVerifyV2){
                
                String contact = "";
                String contactType = "";
                String date = "";

                if (emailVerify.isJsonObject()){
                    JsonObject reactionObj = (JsonObject)emailVerify;
                    contact = reactionObj.get("contact").getAsString();
                    contactType = reactionObj.get("contact_type").getAsString();
                    date = new TimestampToDate(emailVerify.getAsJsonObject().get("verification_time").getAsLong()).getDate();
                  
                    
                    // add variables to attributes
                    Collection<BlackboardAttribute> attributelist = new ArrayList();
                    attributelist.add(new BlackboardAttribute(emailVerifyContact, FacebookIngestModuleFactory.getModuleName(), contact));
                    attributelist.add(new BlackboardAttribute(emailVerifyContactType, FacebookIngestModuleFactory.getModuleName(), contactType));
                    attributelist.add(new BlackboardAttribute(emailVerifyDate, FacebookIngestModuleFactory.getModuleName(), date));

                    
                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), "FacebookFileIngestModule");
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
        else{
            logger.log(Level.INFO, "No contact_verifications_v2 found");
            return;
        }
    }
    
    /**
    * Process account_activity.json file and add data as Data Artifact
    * Facebook reaction data.
    *
    * @param  af  JSON file
    */
    private void processJSONaccount_activity(AbstractFile af){
        JsonObject json = parseAFtoJson(af);
        
        if(json.has("account_activity_v2")){
            
            // prepare variables for artifact
            BlackboardArtifact.Type artifactType;
            BlackboardAttribute.Type activityAction;
            BlackboardAttribute.Type activityDate;
            BlackboardAttribute.Type activityIPAdd;
            BlackboardAttribute.Type activityUserAgent;
            BlackboardAttribute.Type activitydatr_cookie;
            BlackboardAttribute.Type activityCity;
            BlackboardAttribute.Type activityRegion;
            BlackboardAttribute.Type activityCountry;
            BlackboardAttribute.Type activitySite;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_ACCOUNT_ACTIVITY") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_ACCOUNT_ACTIVITY", "Facebook account activity");
                    activityAction = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVITY_ACTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Action");
                    activityDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVITY_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    activityIPAdd = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVITY_IPADDRESS", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "IP Address");
                    activityUserAgent = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVITY_USER_AGENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "User Agent");
                    activitydatr_cookie = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVITY_DATR_COOKIE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Datr_Cookie");
                    activityCity = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVITY_CITY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "City");
                    activityRegion = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVITY_REGION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Region");
                    activityCountry = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVITY_COUNTRY", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Country");
                    activitySite = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBACTIVITY_SITE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Site");        
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_ACCOUNT_ACTIVITY");
                    activityAction = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVITY_ACTION");
                    activityDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVITY_DATE");
                    activityIPAdd = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVITY_IPADDRESS");
                    activityUserAgent = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVITY_USER_AGENT");
                    activitydatr_cookie = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVITY_DATR_COOKIE");
                    activityCity = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVITY_CITY");
                    activityRegion = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVITY_REGION");
                    activityCountry = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVITY_COUNTRY");
                    activitySite = currentCase.getSleuthkitCase().getAttributeType("LS_FBACTIVITY_SITE");                         
                }
            }
            catch (TskCoreException | TskDataException e){
                e.printStackTrace();
                return;
            }
            
            JsonArray activityV2 = json.getAsJsonArray("account_activity_v2");
            for (JsonElement activity:activityV2){
                
                String action = "";
                String date = "";
                String ipAdd = "";
                String userAgent = "";
                String datr_cookie = "";
                String city = "";
                String region = "";
                String country = "";
                String site = "";
                
                if (activity.isJsonObject()){
                    JsonObject activityObj = (JsonObject)activity;
                    action = activityObj.get("action").getAsString();
                    date = new TimestampToDate(activity.getAsJsonObject().get("timestamp").getAsLong()).getDate();
                    ipAdd = activityObj.get("ip_address").getAsString();
                    userAgent = activityObj.get("user_agent").getAsString();
                    datr_cookie = activityObj.get("datr_cookie").getAsString();
                    city = activityObj.get("city").getAsString();
                    region = activityObj.get("region").getAsString();
                    country = activityObj.get("country").getAsString();
                    site = activityObj.get("site_name").getAsString();
                    
                    
                    // add variables to attributes
                    Collection<BlackboardAttribute> attributelist = new ArrayList();
                    attributelist.add(new BlackboardAttribute(activityAction, FacebookIngestModuleFactory.getModuleName(), action));
                    attributelist.add(new BlackboardAttribute(activityDate, FacebookIngestModuleFactory.getModuleName(), date));
                    attributelist.add(new BlackboardAttribute(activityIPAdd, FacebookIngestModuleFactory.getModuleName(), ipAdd));
                    attributelist.add(new BlackboardAttribute(activityUserAgent, FacebookIngestModuleFactory.getModuleName(), userAgent));
                    attributelist.add(new BlackboardAttribute(activitydatr_cookie, FacebookIngestModuleFactory.getModuleName(), datr_cookie));
                    attributelist.add(new BlackboardAttribute(activityCity, FacebookIngestModuleFactory.getModuleName(), city));
                    attributelist.add(new BlackboardAttribute(activityRegion, FacebookIngestModuleFactory.getModuleName(), region));
                    attributelist.add(new BlackboardAttribute(activityCountry, FacebookIngestModuleFactory.getModuleName(), country));
                    attributelist.add(new BlackboardAttribute(activitySite, FacebookIngestModuleFactory.getModuleName(), site));
                    
                    try{
                        blackboard.postArtifact(af.newDataArtifact(artifactType, attributelist), "FacebookFileIngestModule");
                    }
                    catch (TskCoreException | BlackboardException e){
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
        else{
            logger.log(Level.INFO, "No account_activity_v2 found");
            return;
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
                    String name = "";
                    String value = "";
                    String uri = "";

                    if (entry.data != null) {
                        name = entry.data.name;
                        value = entry.data.value;
                        uri = entry.data.uri;

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
                    String date = "";
                    String name = "";
                    String uri = "";

                    if (entry.data != null) {
                        date = new TimestampToDate(entry.timestamp).getDate();
                        name = entry.data.name;
                        uri = entry.data.uri;

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
                
                String name = "";
                String dataFile = "";
                String remarketing = "";
                String inPersonStoreVisit = "";
                
                name = advertiser.advertiser_name;
                dataFile = advertiser.has_data_file_custom_audience;
                remarketing = advertiser.has_remarketing_custom_audience;
                inPersonStoreVisit = advertiser.has_in_person_store_visit;
                
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
                
                String title = "";
                String action = "";
                String date = "";
                
                title = advertisement.title;
                action = advertisement.action;
                date = new TimestampToDate(advertisement.timestamp).getDate();

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
                
                String name = "";
                String addedDate = "";
                String scopedId = "";
                String category = "";
                String removedDate = "";
                
                name = app.name;
                addedDate = new TimestampToDate(app.added_timestamp).getDate();
                scopedId = app.user_app_scoped_id;
                category = app.category;
                removedDate = new TimestampToDate(app.removed_timestamp).getDate();

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
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_OFF_FB_ACTIVITY", "Organisation Activities User visited off-Facebook");
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
                
                String name = "";
                String id = "";
                String type = "";
                String date = "";
                
                name = organisation.name;
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
    *
    * @param  af  JSON file
    */
    private void processJSONcomments(AbstractFile af){
        String json = parseAFtoString(af);
        CommentsV2 comments = new Gson().fromJson(json, CommentsV2.class);
        if(comments.comments_v2 != null){
            
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
                e.printStackTrace();
                return;
            }
            
            for (CommentsV2.Comments_V2 comment:comments.comments_v2){
                String date = "";
                String title = "";
                String commentString = "";
                String author = "";
                String uri = "";
                String url = "";
                
                date = new TimestampToDate(comment.timestamp).getDate();
                title = comment.title;
                if (comment.data != null){
                    for (CommentsV2.Comments_V2.Data data:comment.data){
                        commentString = data.comment.comment;
                        author = data.comment.author;
                    }
                }
                if (comment.attachments != null){
                    for (CommentsV2.Comments_V2.Attachments attachment:comment.attachments){
                        for (CommentsV2.Comments_V2.Attachments.Data attachmentData:attachment.data){
                            if (attachmentData.external_context != null){
                                url = attachmentData.external_context.url;
                            }
                            if (attachmentData.media != null){
                                uri = attachmentData.media.uri;
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
                
                String date = "";
                String title = "";
                String reactionString = "";
                String actor = "";
                
                title = reaction.title;
                date = new TimestampToDate(reaction.timestamp).getDate();
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
    * Process posts_and_comments.json file and add data as Data Artifact
    * Facebook reaction data.
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
                
                String name = "";
                String reply = "Joined";
                String startDate = "";
                String endDate = "";
                
                name = eventJoined.name;
                startDate = new TimestampToDate(eventJoined.start_timestamp).getDate();
                endDate = new TimestampToDate(eventJoined.end_timestamp).getDate();
                        
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
                
                String name = "";
                String reply = "Declined";
                String startDate = "";
                String endDate = "";
                
                name = eventJoined.name;
                startDate = new TimestampToDate(eventJoined.start_timestamp).getDate();
                endDate = new TimestampToDate(eventJoined.end_timestamp).getDate();
                        
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
                
                String name = "";
                String reply = "Interested";
                String startDate = "";
                String endDate = "";
                
                name = eventJoined.name;
                startDate = new TimestampToDate(eventJoined.start_timestamp).getDate();
                endDate = new TimestampToDate(eventJoined.end_timestamp).getDate();
                        
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
            logger.log(Level.INFO, "No reactions_v2 found");
            return;
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
