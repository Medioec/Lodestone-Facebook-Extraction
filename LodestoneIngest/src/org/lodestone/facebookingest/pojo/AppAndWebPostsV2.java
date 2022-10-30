/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for posts_from_apps_and_websites.json
 * @author Eric
 */
public class AppAndWebPostsV2 {
    public List<App_Posts_V2> app_posts_v2;
    public class App_Posts_V2{
        public long timestamp;
        public String title;
        public List<Attachments> attachments;
        public class Attachments{
            public List<Data> data;
            public class Data{
                public External_Context external_context;
                public class External_Context{
                    public String name;
                    public String url;
                }
            }
        }
    }
}
