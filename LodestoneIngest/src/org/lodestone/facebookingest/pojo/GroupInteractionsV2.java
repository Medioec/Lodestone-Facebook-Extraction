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

public class GroupInteractionsV2 {
    public List<GroupInteractions_V2> group_interactions_v2;
    public class GroupInteractions_V2{
        //public String name;
        //public String description;
        public List<Entries> entries;
        public class Entries {
            public Data data;
            public class Data{
                public String name;
                public String value;
                public String uri;
            }
        }
    }
}
