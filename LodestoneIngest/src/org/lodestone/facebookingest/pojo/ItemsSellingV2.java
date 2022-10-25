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
public class ItemsSellingV2 {
    public List<ItemsSelling_V2> items_selling_v2;
    public class ItemsSelling_V2{
        public String title;
        public String price;
        public String seller;
        public long created_timestamp;
        public long updated_timestamp;
        public String category;
        public String marketplace;
        public Location location;
        public class Location {
            public String name;
            public Coordinate coordinate;
            public class Coordinate {
                public String latitude;
                public String longitude;
            }
        }
    }
}
