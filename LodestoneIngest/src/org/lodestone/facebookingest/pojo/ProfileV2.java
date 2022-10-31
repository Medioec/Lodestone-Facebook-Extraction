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
public class ProfileV2 {
    public Profile_V2 profile_v2;
    public class Profile_V2 {
        public Name name;
        public Emails emails;
        public Birthday birthday;
        public Gender gender;
        public List<PreviousNames> previous_names;
        public List<OtherNames> other_names;
        public Location current_city;
        public Location hometown;
        public Relationship relationship;
        public List<PreviousRelationship> previous_relationships;
        public List<FamilyMembers> family_members;
        public List<EducationExperience> education_experiences;
        public List<WorkExperience> work_experiences;
        public List<Language> languages;
        public ReligiousView religious_view;
        public BloodInfo blood_info;
        public List<Website> websites;
        public List<ScreenName> screen_names;
        public Address address;
        public List<PhoneNumber> phone_numbers;
        public String username;
        public String about_me;
        public String favorite_quotes;
        public long registration_timestamp;
        public String profile_uri;
        
        public class Name {
            public String full_name;
            public String first_name;
            public String middle_name;
            public String last_name;
        }
        public class Emails {
            public List<String> emails;
            public List<String> previous_emails;
            public List<String> pending_emails;
            public List<String> ad_account_emails;
        }
        public class Birthday {
            public String year;
            public String month;
            public String day;
        }
        public class Gender {
            public String gender_option;
            public String pronoun;
        }
        public class PreviousNames {
            public String name;
            public long timestamp;
        }
        public class OtherNames {
            public String name;
            public String type;
            public long timestamp;
        }
        public class Location {
            public String name;
            public long timestamp;
        }
        public class Relationship {
            public String status;
            public String partner;
            public long timestamp;
        }
        public class PreviousRelationship {
            public String name;
            public long timestamp;
        }
        public class FamilyMembers {
            public String name;
            public String relation;
            public long timestamp;
        }
        public class EducationExperience {
            public String name;
            public String graduated;
            public String school_type;
            public String description;
            public long start_timestamp;
            public long end_timestamp;
            public long timestamp;
            public List<String> concentrations;
            public List<Module> modules;
            public class Module {
                public String name;
                public List<Tag> tags;
                public class Tag {
                    public String name;
                }
            }
        }
        public class WorkExperience {
            public String employer;
            public String title;
            public String location;
            public String description;
            public long start_timestamp;
            public long end_timestamp;
            public long timestamp;
        }
        public class Language {
            public String name;
            public long timestamp;
        }
        public class ReligiousView {
            public String name;
        }
        public class BloodInfo {
            public String blood_donor_status;
        }
        public class Website {
            public String address;
        }
        public class ScreenName {
            public String service_name;
            public List<Screen_Name> names;
            public class Screen_Name {
                public String name;
                public long timestamp;
            }
        }
        public class Address {
            public String street;
            public String city;
            public String country;
            public String country_code;
            public String region;
        }
        public class PhoneNumber {
            public String phone_type;
            public String phone_number;
            public String verified;
        }
    }
}
