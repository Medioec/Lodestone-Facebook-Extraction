/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 *
 * @author Shane Lim
 */
public class RecentlyVisitedV2 {
        public List<recentLogins> visited_things_v2;
    public class recentLogins{
        public String name;
        public String description;
        public List<Entry> entries;
        public class Entry{
            public long timestamp;
            public Data data;
            public class Data{
                public String dataName;
                public String uri;
                public String value;
            }
        }
    }
}
