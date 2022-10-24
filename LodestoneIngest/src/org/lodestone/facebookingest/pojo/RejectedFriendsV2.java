/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for rejected_friend_requests.json
 * @author Eric
 */
public class RejectedFriendsV2 {
    public List<RejectedRequests> rejected_requests_v2;
    public class RejectedRequests{
        public String name;
        public long timestamp;
    }
}
