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
public class VideosV2 {
    public List<Videos_V2> videos_v2;
    public class Videos_V2 { 
        public String title;
        public String description;
        public String uri;
        public long creation_timestamp;
        public MediaMetadata media_metadata;
        public Thumbnail thumbnail;
        public class MediaMetadata {
            public VideoMetaData video_metadata;
            public class VideoMetaData {
                public List<ExifData> exif_data;
                public class ExifData{
                    public String upload_ip;
                    public long upload_timestamp;
                }
            }
        }
        public class Thumbnail {
            public String uri;
        }
    }
}
