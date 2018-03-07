
package Koneksi;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Database {
    static Properties myPanel;
    static String driver, database, user, pass;
    
    public static Connection KoneksiDB(){
        Connection conn = null;
        try{
            myPanel = new Properties();
            myPanel.load(new FileInputStream("lib/database.ini"));
            driver = myPanel.getProperty("DBDriver");
            database = myPanel.getProperty("DBDatabase");
            user = myPanel.getProperty("DBUsername");
            pass = myPanel.getProperty("DBPassword");

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(database, user, pass);
            System.out.println("Koneksi Berhasil");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Pesan",JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Error : " + ex.getMessage());
        }
        return conn;
    }
}

