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
public class AccountsCenterV2 {
    public AccountsCenter_V2 accounts_center_v2;
    public class AccountsCenter_V2{
        public List<Service> linked_accounts;
        public class Service {
            public String service_name;
            public String native_app_id;
            public String username;
            public String email;
            public String name;
            public long linked_time;
        }
    }
}
