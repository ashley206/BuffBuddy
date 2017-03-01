package com.fractaltechnologies.ashleyw.buffbuddy;

import android.content.Intent;

import java.util.Stack;

/**
 * Created by Ashley H on 2/28/2017.
 */

// This class implements a Singleton approach to maintaining the order of parents
// in the application.
public class ApplicationParents {

    private static  ApplicationParents m_instance = null;

    public Stack<Intent> parents = new Stack<Intent>();

    protected ApplicationParents(){

    }

    public static synchronized ApplicationParents getInstance(){
        if(m_instance == null){
            m_instance = new ApplicationParents();
        }
        return m_instance;
    }
}
