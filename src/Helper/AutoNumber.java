
package Helper;

import Dao.DaoPelanggan;
import Koneksi.Database;
import Koneksi.Database;
import Model.Barang;
import Model.KategoriBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AutoNumber {
    private String jenis;
    private int curNumber;
    
    public AutoNumber(String jenis){
        this.jenis = jenis;
    }
    
    public String getNextNumber(){
        String strCurNumber="";
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            String sql = "SELECT * FROM autonumber WHERE JenisAutonumber=?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, jenis);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                int increment = Integer.parseInt(rs.getString("Increment"));
                String pola = rs.getString("PolaAutonumber");
                curNumber = Integer.parseInt(rs.getString("CurNumber")) + increment;
                strCurNumber = format(pola);
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        finally{
            try{
                if(statement != null) statement.close();
                if(conn != null) conn.close();
            }catch(SQLException ex){
                Logger.getLogger(DaoPelanggan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return strCurNumber;
    }
    
    public String update(){
        String strCurNumber="";
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            String sql = "UPDATE autonumber SET CurNumber=? WHERE JenisAutonumber=?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, curNumber);
            statement.setString(2, jenis);
            statement.executeUpdate();
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        finally{
            try{
                if(statement != null) statement.close();
                if(conn != null) conn.close();
            }catch(SQLException ex){
                Logger.getLogger(DaoPelanggan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return strCurNumber;
    }
    
    private String format(String pola){
        String numbering = "";
        String[] splitPola = pola.split("\\|");
        for(int i =0; i < splitPola.length; i++){            
            switch(splitPola[i]){
                case "0" :
                case "00" :
                case "000" :
                case "0000" :
                case "00000" :
                case "000000" :
                case "0000000" :
                case "00000000" :
                    numbering = numbering + 
                            splitPola[i].substring(String.valueOf(curNumber).length()) + 
                            String.valueOf(curNumber) ;
                    break;
                default:
                    numbering = numbering + splitPola[i];
                    break;
            }
        }
        
        return numbering;
    }
}
