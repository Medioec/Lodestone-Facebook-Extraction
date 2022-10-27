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
public class NotificationsV2 {
    public List<Notifications_V2> notifications_v2;
    public class Notifications_V2 {
        public long timestamp;
        public String unread;
        public String text;
        public String href;
    }
}
