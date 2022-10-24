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
public class InstalledAppsV2 {
    public List<InstalledApp> installed_apps_v2;
    public class InstalledApp{
        public String name;
        public long added_timestamp;
        public String user_app_scoped_id;
        public String category;
        public long removed_timestamp;
    }
}
