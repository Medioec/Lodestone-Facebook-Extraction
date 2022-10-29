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
public class GroupPostsV2 {
    public List<GroupPosts_V2> group_posts_v2;
    public class GroupPosts_V2{
        public long timestamp;
        public List<Attachments> attachments;
        public List<Data> data;
        public String title;
        public class Attachments {
            public List<AttachmentData> data;
            public class AttachmentData {
                public Media media;
                public ForSaleItem for_sale_item;
                public ExternalContext external_context;
                public class ForSaleItem {
                    public String title;
                    public String price;
                    public String seller;
                    public long created_timestamp;
                    public long updated_timestamp;
                    public String category;
                    public String marketplace;
                    public Location location;
                    public String description;
                    public class Location {
                        public String name;
                        public Coordinate coordinate;
                        public class Coordinate {
                            public String latitude;
                            public String longitude;
                        }
                    }
                }
                public class Media{
                    public String uri;
                    public long creation_timestamp;
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
        public class Data {
            public String post;
        }
    }
}
