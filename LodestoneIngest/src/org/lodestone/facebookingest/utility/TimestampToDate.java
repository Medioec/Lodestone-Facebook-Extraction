/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.utility;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Eric
 */
public class TimestampToDate {
    private String date;
    private String pattern = "yyy-mm-dd HH:mm:ss z";
    public TimestampToDate(long timestamp){
        if (timestamp != 0){
            date = new SimpleDateFormat(pattern).format(new Date(timestamp*1000));
        }
        else{
            date = "N/A";
        }
    }
    
    public String getDate(){
        return date;
    }
}
