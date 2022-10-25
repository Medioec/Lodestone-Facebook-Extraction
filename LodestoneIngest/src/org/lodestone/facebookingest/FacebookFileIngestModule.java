/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest;

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
                    break;
                case "friend_requests_received.json":
                    break;
                case "friend_requests_sent.json":
                    break;
                case "rejected_friend_requests.json":
                    break;
                case "removed_friends.json":
                    break;
                case "who_you_follow.json":
                    break;
                case "your_comments_in_groups.json":
                    processJSONyour_comments_in_groups(af);
                    break;
                case "your_group_membership_activity.json":
                    //processJSON(af);
                    break;
                case "your_posts_in_groups.json":
                    //processJSON(af);
                    break;
                case "last_location.json":
                    //processJSON(af);
                    break;
                case "primary_location.json":
                    //processJSON(af);
                    break;
                case "primary_public_location.json":
                    //processJSON(af);
                    break;
                case "timezone.json":
                    //processJSON(af);
                    break;
                case "autofill_information.json":
                    //processJSON(af);
                    break;
                case "secret_conversations.json":
                    //processJSON(af);
                    break;
                case "support_messages.json":
                    //processJSON(af);
                    break;
                case "message_1.json":
                    //processJSON(af);
                    break;
                case "notifications.json":
                    //processJSON(af);
                    break;
                case "ads_interests.json":
                    //processJSON(af);
                    break;
                case "friend_peer_group.json":
                    //processJSON(af);
                    break;
                case "pages_and_profiles_you_follow.json":
                    //processJSON(af);
                    break;
                case "pages_you've_liked.json":
                    //processJSON(af);
                    break;
                case "your_posts_1.json":
                    //processJSON(af);
                    break;
                case "your_uncategorized_photos.json":
                    //processJSON(af);
                    break;
                case "your_videos.json":
                    //processJSON(af);
                    break;
                case "0.json":
                    //processJSON(af);
                    break;
                case "1.json":
                    //processJSON(af);
                    break;
                case "2.json":
                    //processJSON(af);
                    break;
                case "3.json":
                    //processJSON(af);
                    break;
                case "language_and_locale.json":
                    //processJSON(af);
                    break;
                case "profile_information.json":
                    //processJSON(af);
                    break;
                case "profile_update_history.json":
                    //processJSON(af);
                    break;
                case "collections.json":
                    //processJSON(af);
                    break;
                case "your_saved_items.json":
                    //processJSON(af);
                    break;
                case "your_search_history.json":
                    //processJSON(af);
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
                    //processJSON(af);
                    break;
                case "login_protection_data.json":
                    //processJSON(af);
                    break;
                case "mobile_devices.json":
                    //processJSON(af);
                    break;
                case "record_details.json":
                    //processJSON(af);
                    break;
                case "where_you're_logged_in.json":
                    //processJSON(af);
                    break;
                case "your_facebook_activity_history.json":
                    //processJSON(af);
                    break;
                case "location.json":
                    //processJSON(af);
                    break;
                case "voting_reminders.json":
                    //processJSON(af);
                    break;
                case "recently_viewed.json":
                    //processJSON(af);
                    break;
                case "recently_visited.json":
                    //processJSON(af);
                    break;
                case "your_topics.json":
                    //processJSON(af);
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
    * Facebook reaction data.
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
    * Facebook reaction data.
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
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FB_ACCOUNT_ACTIVITY", "Account Activity");
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
                String commentString = "";
                String author = "";
                String uri = "";
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
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_REACTION") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_REACTION", "Facebook reaction");
                    reactionDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBREACTION_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    reactionTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBREACTION_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                    reactionReactionString = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBREACTION_REACTION", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Reaction");
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
                    date = longToDate(reactionObj.get("verification_time").getAsLong());
                  
                    
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
    * Process ip_address_activity.json file and add data as Data Artifact
    * Facebook reaction data.
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
                    date = longToDate(reactionObj.get("timestamp").getAsLong());
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
                    date = longToDate(activityObj.get("timestamp").getAsLong());
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
    * Input long and parse as Datetime
    * If value is 0, return N/A
    *
    * @param  timestamp long value
    * @return String
    */
    private String longToDate(long timestamp){
        if (timestamp == 0){
            return "N/A";
        }
        else {
            return new Date(new Timestamp(timestamp).getTime() * 1000).toString();
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
            BlackboardAttribute.Type groupCommentDate;
            BlackboardAttribute.Type groupCommentTitle;
            BlackboardAttribute.Type groupCommentCommentString;
            BlackboardAttribute.Type groupCommentAuthor;
            BlackboardAttribute.Type groupCommentGroup;
            BlackboardAttribute.Type groupCommentUri;
            BlackboardAttribute.Type groupCommentUrl;
            try{
                // if artifact type does not exist
                if (currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_GROUP_COMMENT") == null){
                    artifactType = currentCase.getSleuthkitCase().addBlackboardArtifactType("LS_FACEBOOK_GROUP_COMMENT", "Facebook Group Comment");
                    groupCommentDate = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_DATE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Date");
                    groupCommentTitle = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_TITLE", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Title");
                    groupCommentCommentString = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_COMMENT", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Comment");
                    groupCommentAuthor = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_AUTHOR", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Author");
                    groupCommentGroup = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_GROUP", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "Group");
                    groupCommentUri = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_URI", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URI");
                    groupCommentUrl = currentCase.getSleuthkitCase().addArtifactAttributeType("LS_FBGROUPCOMMENT_URL", TSK_BLACKBOARD_ATTRIBUTE_VALUE_TYPE.STRING, "URL");
                }
                else{
                    artifactType = currentCase.getSleuthkitCase().getArtifactType("LS_FACEBOOK_GROUP_COMMENT");
                    groupCommentDate = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_DATE");
                    groupCommentTitle = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_TITLE");
                    groupCommentCommentString = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_COMMENT");
                    groupCommentAuthor = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_AUTHOR");
                    groupCommentGroup = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_GROUP");
                    groupCommentUri = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_URI");
                    groupCommentUrl = currentCase.getSleuthkitCase().getAttributeType("LS_FBGROUPCOMMENT_URL");
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
                String uri = "";
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
                attributelist.add(new BlackboardAttribute(groupCommentDate, FacebookIngestModuleFactory.getModuleName(), date));
                attributelist.add(new BlackboardAttribute(groupCommentTitle, FacebookIngestModuleFactory.getModuleName(), title));
                attributelist.add(new BlackboardAttribute(groupCommentCommentString, FacebookIngestModuleFactory.getModuleName(), commentString));
                attributelist.add(new BlackboardAttribute(groupCommentAuthor, FacebookIngestModuleFactory.getModuleName(), author));
                attributelist.add(new BlackboardAttribute(groupCommentGroup, FacebookIngestModuleFactory.getModuleName(), group));
                attributelist.add(new BlackboardAttribute(groupCommentUri, FacebookIngestModuleFactory.getModuleName(), uri));
                attributelist.add(new BlackboardAttribute(groupCommentUrl, FacebookIngestModuleFactory.getModuleName(), url));

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
