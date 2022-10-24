/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for comments.json
 * @author Eric
 */
public class CommentsV2 {
    public List<Comments_V2> comments_v2;
    public class Comments_V2{
        public long timestamp;
        public List<Data> data;
        public List<Attachments> attachments;
        public String title;
        public class Data{
            public Comment comment;
            public class Comment{
                public String comment;
                public String author;
            }
        }
        public class Attachments{
            public List<Data> data;
            public class Data{
                public Media media;
                public ExternalContext external_context;
                public class Media{
                    public String uri;
                }
                public class ExternalContext{
                    public String url;
                }
            }
        }
    }
}
