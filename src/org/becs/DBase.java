/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.becs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author User 2
 */
public class DBase {

    public static Connection conn() throws Exception {
        Class.forName("org.h2.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:h2:./FingerRec", "root", "password");
        createTab();
        System.out.println("DB and Table created");
        return con;
    }

    public static void createTab() throws Exception {

        //Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:h2:./FingerRec", "root", "password");
        Statement st = conn.createStatement();
        String s = "CREATE TABLE IF NOT EXISTS FingerPrints (FirstName varchar(30),LastName varchar(30),MatricNo varchar(30),Department varchar(30),"
                + "Faculty varchar(30),Gender varchar(7),Passport blob,Finger blob);";//,Finger1 blob,Finger2 blob,Finger3 blob);";

        st.executeUpdate(s);

        System.out.println("User table created");

    }

}
