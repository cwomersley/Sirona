package com.example.chris.strokere;

/**
 * Created by edward on 04/07/17.
 */


public class User {

    public String name;
    public String email;
    public String surname;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String surname, String name, String email) {
        this.name = name;
        this.email = email;
        this.surname=surname;
    }

}