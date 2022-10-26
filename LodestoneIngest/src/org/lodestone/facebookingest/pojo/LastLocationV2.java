/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

/**
 *
 * @author Alford
 */
public class LastLocationV2 {
    public LastLocation_V2 last_location_v2;
    public class LastLocation_V2 {
        public long time;
        public Coordinate coordinate;
        public String name;
        public class Coordinate {
            public String latitude;
            public String longitude;
        }
    }
}
