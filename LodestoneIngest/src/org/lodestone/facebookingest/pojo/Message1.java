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
public class Message1 {
    public String title;
    public String thread_type;
    public List<Participant> participants;
    public List<Message> messages;
    public String thread_path;
    public String is_still_participant;
    public Image image;
    
    public class Participant {
        public String name;
    }
    public class Message {
        public String sender_name;
        public long timestamp_ms;
        public String content;
        public String type;
        public List<Image> photos;
        public Sticker sticker;
        public List<File> files;
        public String share;
        public String is_unsent;
        public String is_taken_down;
        public class Image {
            public String uri;
            public long creation_timestamp;
        }
        public class Sticker {
            public String uri;
        }
        public class File {
            public String uri;
            public long creation_timestamp;
        }
    }
    public class Image {
        public String uri;
        public long creation_timestamp;
    }
}
