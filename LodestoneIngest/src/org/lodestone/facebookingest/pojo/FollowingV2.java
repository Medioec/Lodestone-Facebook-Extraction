/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for who_you_follow.json
 * @author Eric
 */
public class FollowingV2 {
    public List<Following> following_v2;
    public class Following{
        public String name;
        public long timestamp;
    }
}
