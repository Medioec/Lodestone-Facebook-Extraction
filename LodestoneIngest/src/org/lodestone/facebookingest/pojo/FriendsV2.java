/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for friends.json
 * @author Eric
 */
public class FriendsV2 {
    public List<FriendsArrV2> friends_v2;
    public class FriendsArrV2{
        public String name;
        public long timestamp;
    }
}
