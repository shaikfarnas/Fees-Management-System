/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author S FARNAS
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    
    static public Connection getConnection()
    {
        Connection con = null;
        try{
            Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
            con=DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management","root","W7301@jqir#");
        }
        catch(ClassNotFoundException | SQLException e){
        }
        return con;
    }
}
