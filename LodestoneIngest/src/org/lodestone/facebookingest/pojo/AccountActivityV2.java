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
public class AccountActivityV2 {
    public List<Actions> account_activity_v2;
    public class Actions{
        public String action;
        public long timestamp;
        public String ip_address;
        public String user_agent;
        public String datr_cookie;
        public String city;
        public String region;
        public String country;
        public String site_name;
    }
}
