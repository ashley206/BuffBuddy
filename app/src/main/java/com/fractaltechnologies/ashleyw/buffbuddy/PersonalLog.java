package com.fractaltechnologies.ashleyw.buffbuddy;

import java.util.Date;

/**
 * Created by Ashley H on 2/28/2017.
 */

public class PersonalLog {

    int m_workoutID;
    String m_message;
    Date m_date;

    PersonalLog(){
        m_date = null;
        m_message = "";
        m_workoutID = -1;
    }

    PersonalLog(Date date){
        m_date = date;
        m_message = "";
        m_workoutID = -1;
    }

    PersonalLog(Date date, int workoutID, String message){
        m_date = date;
        m_workoutID = workoutID;
        m_message = message;
    }

    public void SetMessage(String message){
        m_message = message;
    }

    public String GetMessage(){
        return m_message;
    }

    public void SetWorkout(int workoutID){
        m_workoutID = workoutID;
    }

    public int GetWorkout(){
        return m_workoutID;
    }

    public void SetDate(Date date){
        m_date = date;
    }

    public Date GetDate(){
        return m_date;
    }

    public PersonalLog GetTodaysLog(){
        return null;
    }

    public PersonalLog GetLogByDate(Date date){
        return null;
    }
}
