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
public class PageLikesV2 {
    public List<PageLikes_V2> page_likes_v2;
    public class PageLikes_V2 {
        public String name;
        public long timestamp;
    }
}
