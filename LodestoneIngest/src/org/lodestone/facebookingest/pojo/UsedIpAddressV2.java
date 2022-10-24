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
public class UsedIpAddressV2 {
    public List<IP> used_ip_address_v2;
    public class IP{
        public String ip;
        public String action;
        public long timestamp;
        public String user_agent;
    }
}
