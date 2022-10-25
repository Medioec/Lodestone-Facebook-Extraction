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
public class EventResponseV2 {
    public EventResponse_V2 event_responses_v2;
    public class EventResponse_V2{
        public List<EventsJoined> events_joined;
        public List<EventsJoined> events_declined;
        public List<EventsJoined> events_interested;
        public class EventsJoined {
            public String name;
            public long start_timestamp;
            public long end_timestamp;
        }
    }
}
