package com.example.chris.strokere;

/**
 * Created by edward on 09/08/17.
 */

public class UserInfo {

        public String name;
        public String email;
        public String surname;

        public UserInfo() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }


    }
