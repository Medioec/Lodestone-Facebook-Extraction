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
public class OffFacebookActivityV2 {
    public List<Organisation> off_facebook_activity_v2;
    public class Organisation{
        public String name;
        public List<Events> events;
        public class Events {
            public Event event;
            public class Event {
                public String id;
                public String type;
                public long timestamp;
            }
        }
    }
}
