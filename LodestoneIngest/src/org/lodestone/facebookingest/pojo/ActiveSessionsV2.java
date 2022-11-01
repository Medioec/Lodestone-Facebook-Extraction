/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 *
 * @author Shane Lim
 */
public class ActiveSessionsV2 {
        public List<activeSessions> active_sessions_v2;
    public class activeSessions{
        public long created_timestamp;
        public long updated_timestamp;
        public String ip_address;
        public String user_agent;
        public String datr_cookie;
        public String device;
        public String location;
        public String app;
        public String session_type;
    }
}
