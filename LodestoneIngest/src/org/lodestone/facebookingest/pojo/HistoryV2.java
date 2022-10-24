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
public class HistoryV2 {
    public List<Advertisement> history_v2;
    public class Advertisement{
        public String title;
        public String action;
        public long timestamp;
    }
}
