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
public class ReactionsV2 {
    public List<Reactions_V2> reactions_v2;
    public class Reactions_V2{
        public long timestamp;
        public List<Data> data;
        public String title;
        public class Data{
            public Reaction reaction;
            public class Reaction{
                public String reaction;
                public String actor;
            }
        }
    }
}
