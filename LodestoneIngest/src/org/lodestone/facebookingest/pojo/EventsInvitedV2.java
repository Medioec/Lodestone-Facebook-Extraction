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
public class EventsInvitedV2 {
    public List<EventInvitation> events_invited_v2;
    public class EventInvitation{
        public String name;
        public long start_timestamp;
        public long end_timestamp;
    }
}
