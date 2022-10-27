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
public class PhoneNumberLocationV2 {
    public List<PhoneNumberLocation_V2> phone_number_location_v2;
    public class PhoneNumberLocation_V2 {
        public String spn;
        public String country_code;
    }
}
