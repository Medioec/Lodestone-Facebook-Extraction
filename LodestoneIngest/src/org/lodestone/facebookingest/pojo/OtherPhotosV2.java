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
public class OtherPhotosV2 {
    public List<OtherPhotos_V2> other_photos_v2;
    public class OtherPhotos_V2 {
        public String uri;
        public long creation_timestamp;
        public String title;
        public MediaMetaData media_metadata;
        public String description;
        public List<Tag> tags;
        public class MediaMetaData {
            public PhotoMetaData photo_metadata;
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
        }
        public class Tag {
            public String name;
        }
    }
}
