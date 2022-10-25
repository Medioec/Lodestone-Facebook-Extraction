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
public class PaymentsV2 {
    public Payments_V2 payments_v2;
    public class Payments_V2{
        public String preferred_currency;
        public List<Payment> payments;
        public class Payment {
            public long created_timestamp;
            public String amount;
            public String currency;
            public String sender;
            public String receiver;
            public String type;
            public String status;
            public String payment_method;
            public String ip;
        }
    }
}
