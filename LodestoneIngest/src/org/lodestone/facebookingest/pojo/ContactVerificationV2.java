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
public class ContactVerificationV2 {
    public List<Contact> contact_verifications_v2;
    public class Contact{
        public String contact;
        public String contact_type;
        public long verification_time;
    }
}
