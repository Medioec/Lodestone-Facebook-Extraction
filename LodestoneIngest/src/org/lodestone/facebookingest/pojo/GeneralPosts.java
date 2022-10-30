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
public class GeneralPosts {
    public List<General_Posts> group_posts_v2;
    public List<General_Posts> profile_updates_v2;
    public List<General_Posts> collections_v2;
    public List<General_Posts> saves_v2;
    public class General_Posts {
        public long timestamp;
        public String title;
        public List<Attachment> attachments;
        public List<Data> data;
        public List<Tag> tags;
        public class Attachment {
            public List<Data> data;
            public class Data {
                public Media media;
                public ForSaleItem for_sale_item;
                public Place place;
                public ExternalContext external_context;
                public LifeEvent life_event;
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
                public class Media {
                    public String uri;
                    public long creation_timestamp;
                    public MediaMetadata media_metadata;
                    public String title;
                    public String description;
                    public class MediaMetadata {
                        public PhotoMetadata photo_metadata;
                        public VideoMetaData video_metadata;
                        public class PhotoMetadata {
                            public List<Exifdata> exif_data;
                            public class Exifdata {
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
                public class Place {
                    public String name;
                    public String address;
                    public String url;
                    public Coordinate coordinate;
                    public class Coordinate {
                        public String latitude;
                        public String longitude;
                    }
                }
                public class LifeEvent {
                    public String title;
                    public StartDate start_date;
                    public class StartDate {
                        public String year;
                        public String month;
                        public String day;
                    }
                }
                public class ExternalContext {
                    public String url;
                }
            }
        }
        public class Data {
            public String post;
            public long update_timestamp;
        }
        public class Tag {
            public String name;
        }
    }
}
