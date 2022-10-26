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
public class LoginsAndLogoutsV2 {
        public List<loginAndLogout> account_accesses_v2;
    public class loginAndLogout{
        public String action;
        public long  timestamp;
        public String site;
        public String ip;
    }
}
