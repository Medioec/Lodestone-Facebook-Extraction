/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for removed_friends.json
 * @author Eric
 */
public class RemovedFriendsV2 {
    public List<DeletedFriends> deleted_friends_v2;
    public class DeletedFriends{
        public String name;
        public long timestamp;
    }
}
