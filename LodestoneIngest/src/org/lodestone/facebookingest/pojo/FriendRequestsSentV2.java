/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for friend_requests_sent.json
 * @author Eric
 */
public class FriendRequestsSentV2 {
    public List<SentFriendRequest> sent_requests_v2;
    public class SentFriendRequest{
        public String name;
        public long timestamp;
    }
}
