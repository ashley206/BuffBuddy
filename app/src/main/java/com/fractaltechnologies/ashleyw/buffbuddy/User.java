package com.fractaltechnologies.ashleyw.buffbuddy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ashley H on 1/19/2017.
 */

public class User implements Serializable {
    private String m_firstname;
    private String m_lastname;
    private String m_username;
    private String m_email;
    private String m_password;
    private int m_id;
    private boolean m_google;
    private List<Workout> m_workouts;

    public User(){
        m_firstname = "";
        m_lastname = "";
        m_username = "";
        m_id = -1;
        m_email = "";
        m_password = "";
        m_google = false;
    }
    public User(String firstname, String lastname, String email, String username, String password, boolean google){
        m_firstname = firstname;
        m_lastname = lastname;
        m_username = username;
        m_email = email;
        m_password = password;
        m_google = google;
    }

    public User(String firstname, String lastname, String email, String username, String password, int userId, boolean google){
        m_firstname = firstname;
        m_lastname = lastname;
        m_username = username;
        m_email = email;
        m_password = password;
        m_id = userId;
        m_google = google;
    }

    public static boolean UserExists(String email, DBAdapter db){
        return db.UserExists(email);
    }

    public static User GoogleLogin(String email, DBAdapter db){
        return db.GoogleLogin(email);
    }

    public static User Login(String username, String password, DBAdapter db){
        // Attempt to fetch the account based on the user object
        return db.Login(username, password);
    }

    public static boolean Register(String username, String email, String password, String cPassword, String fname, String lname, DBAdapter db){
        return db.Register(username, email, password, fname, lname);
    }

    // Can only be passed email because no assumptions can be made about the format of other
    // information Google mayy have (ie. given name, display name, etc.)
    public static int GoogleRegister(String email, DBAdapter db){
        return db.GoogleRegister(email);
    }

    public void setFirstName(String fname){
        m_firstname = fname;
    }

    public void setLastName(String lname){
        m_lastname = lname;
    }

    public void setUsername(String username){
        m_username = username;
    }

    public void setGoogleAccount(boolean googleAcct){
        m_google = googleAcct;
    }

    public void setID(int id){
        m_id = id;
    }

    public int getID(){
        return m_id;
    }

    public static int GetIDFromDatabase(String email, DBAdapter db){
        return db.getUserId(email);
    }

}
