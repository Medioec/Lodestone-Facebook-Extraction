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
public class RecentlyViewedV2 {
    public List<RecentViews> recently_viewed;
    public class RecentViews{
        public String name;
        public String description;
        public List<Child> children;
        public List<Entry> entries;
		
        public class Child {
            public String name;
            public String description;
            public List<Entry> entries;
            public class Entry{
                public long timestamp;
                public Data data;
                public class Data{
                    public String name;
                    public String uri;
                    public String value;
                }
            }
        }

        public class Entry{
                public long timestamp;
                public Data data;
                public class Data{
                        public String name;
                        public String uri;
                        public String value;
                }
        }
    }
}
