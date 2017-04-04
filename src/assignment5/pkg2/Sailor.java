/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment5.pkg2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

/**
 * Operates the Employee DB and prints reports
 * @author sharanya
 */
public class Sailor {

    int score = 0;
    ArrayList<String> sailorData = new ArrayList<>(100);
    private String framework = "network";
    private String protocol = "jdbc:derby://localhost:1527/NEmployeeDB;create=true";
   /**
     * Reads data from Sailor file and adds them to array list
     * @throws IOException 
     */
    public void readData() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("Sailor.txt"));
        String s;
        s = in.readLine();
        while (s != null) {

            sailorData.add(s);
            s = in.readLine();
        }

    }
    
    /**
     * Parses given string to get absolute path of database
     * 
     */
    private void parseArguments(String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("derbyclient")) {
                framework = "derbyclient";
                protocol = "jdbc:derby://localhost:1527/";
            }
        }
    }

   /**
 * Establishes Database connection and inputs data into Sailor table, Retrieves data using SQL queries and prints reports on the console
 * @param args Determines framework  in which application is starting and used to obtain absolute path of DB
 * @throws ClassNotFoundException 
 */

    public void go(String[] args) throws ClassNotFoundException, SQLException {

        framework = "network";
        protocol = "jdbc:derby://localhost:1527/C:\\users\\sharanya\\Documents\\NEmployeeDB;create=true";
        parseArguments(args);

        System.out.println("Starting in " + framework + " mode"); //Prints mode in which application is starting

        Connection conn = null;
        ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
        PreparedStatement psInsert;
        PreparedStatement psUpdate;
        Statement s;
        ResultSet rs = null;
        try {
            Properties props = new Properties(); // connection properties
            // providing a user name and password is optional in the embedded
            // and derbyclient frameworks
            props.put("user", "app");
            props.put("password", "app");

            String dbName = "NEmployeeDB"; // the name of the database

            conn = DriverManager.getConnection(protocol, props);

            System.out.println("Connected to and created database " + dbName);

            conn.setAutoCommit(false);

            s = conn.createStatement();
            statements.add(s);
           //Creating sailor table
            try {
                s.execute("create table sailor(Lastname varchar(100), Firstname varchar(100), Pos varchar(50), Salary int)");
            } catch (SQLException e) {
                s.execute("drop table sailor");  //drop table if already present and recreate table
                s.execute("create table sailor(Lastname varchar(100), Firstname varchar(100), Pos varchar(50), Salary int)");

            }

            psInsert = conn.prepareStatement(
                    "INSERT INTO sailor VALUES(?,?,?,?)");  //create statement for inserting
            statements.add(psInsert);
            String[] sailorDetails;
            //inserting values into sailor table
            for (String questions : sailorData) {
                try {
                    sailorDetails = questions.split(",");

                    psInsert.setString(1, sailorDetails[0]);

                    psInsert.setString(2, sailorDetails[1]);
                    psInsert.setString(3, sailorDetails[2]);

                    psInsert.setInt(4, Integer.parseInt(sailorDetails[3]));
                    psInsert.executeUpdate();

                } catch (Exception e) {
                    //System.out.println("inserting");
                }
            }

            conn.commit();

            
             //Printing reports for different conditions
            try {
                
                //Print list of top earning sailors in each designation

                System.out.println("\nList of top earning sailors in each position\n");
                System.out.printf("%-10s %-10s %-10s %-10s\n\n", "Lastname", "Firstname", "Position", "Salary");
                // SQL query for Top earning Cook
                rs = s.executeQuery("SELECT *FROM Sailor WHERE Salary  IN (SELECT MAX(Salary) FROM Sailor WHERE POS='Cook') AND POS='Cook'");

                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                //System.out.println("" + columnsNumber);
                while (rs.next()) {
                       //Print top earning cook
                    
                        System.out.printf("%-10s %-10s %-10s %-10d", rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                  
                    
                    System.out.println();//Move to the next line to print the next row.   */        
                }
                
                // SQL query for Top earning Mechanic
                rs = s.executeQuery("SELECT *FROM Sailor WHERE Salary  IN (SELECT MAX(Salary) FROM Sailor WHERE POS='Mechanic') AND POS='Mechanic'");

                while (rs.next()) {
                       // Print Top earning Mechanic
                        System.out.printf("%-10s %-10s %-10s %-10d", rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                  
                    
                    System.out.println();//Move to the next line to print the next row.   */        
                }
                
                // SQL query for Top earning Engineer
                rs = s.executeQuery("SELECT *FROM Sailor WHERE Salary  IN (SELECT MAX(Salary) FROM Sailor WHERE POS='Engineer') AND POS='Engineer'");

                while (rs.next()) {
                    //Print top earning ENgineer
                        System.out.printf("%-10s %-10s %-10s %-10d", rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                  
                    
                    System.out.println();//Move to the next line to print the next row.   */        
                }
                // SQL query for Top earning Captain
                rs = s.executeQuery("SELECT *FROM Sailor WHERE Salary  IN (SELECT MAX(Salary) FROM Sailor WHERE POS='Captain') AND POS='Captain'");

                while (rs.next()) {
                    //Print top earning captain
                    
                        System.out.printf("%-10s %-10s %-10s %-10d", rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                  
                    
                    System.out.println();//Move to the next line to print the next row.   */        
                }
                //SQL Query for highest and least paid sailors
                System.out.println("\nSailor with least salary and highest salary\n\n");
                System.out.printf("%-10s %-10s %-10s %-10s\n\n", "Lastname", "Firstname", "Position", "Salary");

                // SQL query for least paid sailor
                rs = s.executeQuery("SELECT * FROM Sailor WHERE Salary IN (SELECT MIN(Salary) FROM Sailor) ");
                 while (rs.next()) {
                    
                        System.out.printf("%-10s %-10s %-10s %-10d", rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                  
                    
                    System.out.println();//Move to the next line to print the next row.   */        
                }
                
                System.out.println("");
                // SQL query for highest paid sailor
                rs = s.executeQuery("SELECT * FROM Sailor WHERE Salary IN (SELECT MAX(Salary) FROM Sailor) ");
                 while (rs.next()) {
                    
                        System.out.printf("%-10s %-10s %-10s %-10d", rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                  
                    
                    System.out.println();//Move to the next line to print the next row.   */        
                }
                 
                 //sql query to get average salary
                int avg = 0;
                rs = s.executeQuery("Select AVG(SALARY) from sailor");
                rsmd = rs.getMetaData();
                columnsNumber = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {

                        avg = Integer.parseInt(rs.getString(i));
                        //System.out.print(avg + " average"); //Print one element of a row
                    }
                    //System.out.println();//Move to the next line to print the next row.   */        
                }
                
                
                System.out.println("\nSailors earning above average (above)"+avg+" salary:\n");
                 System.out.printf("%-10s %-10s %-10s %-10s\n", "Lastname", "Firstname", "Position", "Salary");
                 //SQL query for sailors earning above average salary
                rs = s.executeQuery("SELECT * FROM Sailor WHERE Salary>" + avg + "");
                 while (rs.next()) {
                    //Print Sailors with above average salary
                        System.out.printf("%-10s %-10s %-10s %-10d", rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                  
                    
                    System.out.println();//Move to the next line to print the next row.   */        
                }
                 
                 
                  System.out.println("\nSailors whose last name starts with R and salary above $58543:\n");
                 System.out.printf("%-10s %-10s %-10s %-10s\n", "Lastname", "Firstname", "Position", "Salary");
                 //SQL query for sailors whose last name starts with R and salary above $58543
                 rs = s.executeQuery("SELECT * FROM Sailor WHERE Lastname like 'R%' AND salary>58543");
                 while (rs.next()) {
                    //Print sailors whose last name starts with R and salary above $58543
                        System.out.printf("%-10s %-10s %-10s %-10d", rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                  
                    
                    System.out.println();//Move to the next line to print the next row.   */        
                }
                 
                 

            } catch (SQLException e) {

            } finally {

            }
        } catch (SQLException e) {
            System.out.println("Exception sfd " + e);
        } finally {
            //rs.close();

        }

    }

   
}
