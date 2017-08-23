package com.example.chris.strokere;

/**
 * Created by Edward on 04/07/17.
 */


public class User {

    public String name;
    public String email;
    public String surname;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    /**
     * Gets the name on the patient
     * @return
     */
    public String getName()
    {
        return name;
    }


    /**
     * Sets the name of the patient
     * @param name the patient's name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the email of the patient
     * @return
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the email of the patient
     * @param email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Gets the surname of the patient
     * @return
     */
    public String getSurname()
    {
        return surname;
    }


    /**
     * Sets the surname of the patient
     * @param surname the surname of the patient
     */
    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    /**
     * Used for creation of a new account, to write values to Firebase database
     * @param surname the patient's surname
     * @param name the patient's name
     * @param email the patient's email
     */
    public User(String surname, String name, String email) {
        this.name = name;
        this.email = email;
        this.surname=surname;
    }

}