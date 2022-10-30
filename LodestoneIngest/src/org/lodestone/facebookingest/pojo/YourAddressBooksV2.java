/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lodestone.facebookingest.pojo;

import java.util.List;

/**
 * POJO for your_address_books.json
 * @author Eric
 */
public class YourAddressBooksV2 {
    public Address_Book_V2 address_book_v2;
    public class Address_Book_V2{
        public List<Address_Book> address_book;
        public class Address_Book{
            public String name;
            public List<Details> details;
            public long created_timestamp;
            public long updated_timestamp;
            public class Details{
                public String contact_point;
            }
        }
    }
}
