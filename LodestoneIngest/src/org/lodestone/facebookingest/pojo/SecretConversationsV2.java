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
public class SecretConversationsV2 {
    public SecretConversations_V2 secret_conversations_v2;
    public class SecretConversations_V2 {
        public List<Armadillo> armadillo_devices;
        public class Armadillo {
            public String device_type;
            public String device_manufacturer;
            public String device_model;
            public String device_os_version;
            public String last_connected_ip;
            public long last_active_time;
        }
    }
}
