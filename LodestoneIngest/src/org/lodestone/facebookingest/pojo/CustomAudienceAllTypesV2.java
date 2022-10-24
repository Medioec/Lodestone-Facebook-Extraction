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
public class CustomAudienceAllTypesV2 {
    public List<Advertiser> custom_audiences_all_types_v2;
    public class Advertiser{
        public String advertiser_name;
        public String has_data_file_custom_audience;
        public String has_remarketing_custom_audience;
        public String has_in_person_store_visit;
    }
}
