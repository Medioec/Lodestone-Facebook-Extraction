/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for friend_requests_received.json
 * @author Eric
 */
public class FriendRequestsReceivedV2 {
    public List<ReceivedFriendRequest> received_requests_v2;
    public class ReceivedFriendRequest{
        public String name;
        public long timestamp;
    }
}
