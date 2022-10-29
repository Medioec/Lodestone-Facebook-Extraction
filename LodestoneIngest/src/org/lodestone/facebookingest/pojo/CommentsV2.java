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
                    public long creation_timestamp;
                    public long updated_timestamp;
                    public MediaMetaData media_metadata;
                    public String description;
                    public class MediaMetaData {
                        public PhotoMetaData photo_metadata;
                        public VideoMetaData video_metadata;
                        public class PhotoMetaData {
                            public List<ExifData> exif_data;
                            public class ExifData{
                                public String upload_ip;
                                public long taken_timestamp;
                                public long modified_timestamp;
                                public String iso;
                                public String focal_length;
                                public String camera_make;
                                public String camera_model;
                                public String exposure;
                                public String f_stop;
                                public String orientation;
                            }
                        }
                        public class VideoMetaData {
                            public List<ExifData> exif_data;
                            public class ExifData{
                                public String upload_ip;
                                public long upload_timestamp;
                            }
                        }
                    }
                }
                public class ExternalContext{
                    public String url;
                }
            }
        }
    }
}
