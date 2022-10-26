/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 *
 * @author Alford
 */
public class GroupsJoinedV2 {
    public List<GroupsJoined_V2> groups_joined_v2;
    public class GroupsJoined_V2{
        public long timestamp;
        public List<Data> data;
        public String title;
        public class Data {
            public String name;
        }
    }
}
