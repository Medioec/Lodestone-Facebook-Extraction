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
public class AdminRecordsV2 {
    public List<AdminRecords_V2> admin_records_v2;
    public class AdminRecords_V2 {
        public String event;
        public Session session;
        public ExtraInfo extra_info;
        public class Session {
            public long created_timestamp;
            public String ip_address;
            public String user_agent;
            public String datr_cookie;
        }
        public class ExtraInfo {
            public String old_name;
            public String new_name;
            public String old_number;
            public String old_vanity;
            public String new_vanity;
            public String old_email;
            public String new_email;
            public String new_number;
        }
    }
}
