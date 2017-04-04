/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment5.pkg2;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Invokes Sailor class and its methods
 * @author sharanya
 */
public class SailorApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        // TODO code application logic here
       
        Sailor obj = new Sailor();//create object of sailor class
        obj.readData();// calls readdata method
        obj.go(args);// calls go method to creat table,input data and print reports
      
    }

}
