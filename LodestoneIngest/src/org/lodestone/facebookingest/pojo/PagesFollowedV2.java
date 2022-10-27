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
public class PagesFollowedV2 {
    public List<PagesFollowed_V2> pages_followed_v2;
    public class PagesFollowed_V2 {
        public long timestamp;
        public List<Data> data;
        public String title;
        public class Data {
            public String name;
        }
    }
}
