/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for your_search_history.json
 * @author Eric
 */
public class SearchHistoryV2 {
    public List<Searches_V2> searches_v2;
    public class Searches_V2{
        public long timestamp;
        public List<Attachments> attachments;
        public List<Data> data;
        public String title;
        public class Attachments{
            public List<Data> data;
            public class Data{
                public String text;
            }
        }
        public class Data{
            public String text;
        }
    }
}
