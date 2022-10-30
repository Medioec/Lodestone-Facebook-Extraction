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
public class CollectionsV2 {
    public List<Collections_V2> collections_v2;
    public class Collections_V2 {
        public long timestamp;
        public String title;
        public List<Attachment> attachments;
        public class Attachment {
            public List<Data> data;
            public class Data {
                public String name;
            }
        }
    }
}
