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
public class SupportMessagesV2 {
    public List<SupportMessages_V2> support_messages_v2;
    public class SupportMessages_V2 {
        public long timestamp;
        public String subject;
        public List<Messages> messages;
        public class Messages {
            public String from;
            public String to;
            public String subject;
            public String message;
            public long timestamp;
        }
    }
}
