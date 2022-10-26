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
    private String pattern = "yyyy-MM-dd HH:mm:ss z";
    
    /**
    * Add timestamp and convert to Date format
    * Use timestamp as seconds form
    *
    * @param  timestamp  long
    */
    public TimestampToDate(long timestamp){
        if (timestamp != 0){
            date = new SimpleDateFormat(pattern).format(new Date(timestamp*1000));
        }
        else{
            date = "";
        }
    }
    
    /**
    * Add timestamp and convert to Date format
    *
    * @param  timestamp  long
    * @param  milliseconds  boolean value to use timestamp as milliseconds form
    */
    public TimestampToDate(long timestamp, boolean milliseconds){
        if (timestamp != 0){
            if (milliseconds) {
            date = new SimpleDateFormat(pattern).format(new Date(timestamp));
            }
            else {
            date = new SimpleDateFormat(pattern).format(new Date(timestamp*1000));
            }
        }
        else{
            date = "";
        }
    }
    
    public String getDate(){
        return date;
    }
}
