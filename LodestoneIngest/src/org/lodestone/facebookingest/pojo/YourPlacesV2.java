/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for your_places_v2.json
 * @author Eric
 */
public class YourPlacesV2 {
    public List<Your_Places_V2> your_places_v2;
    public class Your_Places_V2{
        public String name;
        public String address;
        public String url;
        public long creation_timestamp;
    }
}
