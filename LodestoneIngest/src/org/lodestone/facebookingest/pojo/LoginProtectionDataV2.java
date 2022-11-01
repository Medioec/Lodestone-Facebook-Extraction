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
public class LoginProtectionDataV2 {
        public List<loginProtection> login_protection_data_v2;
    public class loginProtection{
        public String name;
        public Session session;
        public class Session{
            public long created_timestamp;
            public long updated_timestamp;
            public String ip;
        }
    }
}
