/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for mobile_devices.json
 * @author Eric
 */
public class MobileDevicesV2 {
    public List<Devices_V2> devices_v2;
    public class Devices_V2{
        public String type;
        public String os;
        public long update_time;
        public String advertiser_id;
        public String uuid;
        public List<String> redact_tokens;
        public List<Push_Tokens> push_tokens;
        public String family_device_id;
        public String device_locale;
        public class Push_Tokens{
            public String disabled;
            public long client_update_time;
            public long creation_time;
            public String app_version;
            public String locale;
            public String os_version;
            public String token;
            public String device_id;
        }
    }
}
