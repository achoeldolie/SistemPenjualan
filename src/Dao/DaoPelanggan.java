
package Dao;

import Model.Pelanggan;
import java.util.List;
import java.sql.Connection;
import Koneksi.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPelanggan implements ModelDao<Pelanggan>{
    
    final String NAMA_TABEL = "pelanggan";
    final String INSERT = "INSERT INTO " + NAMA_TABEL 
            + " (KdPlg, NmPlg, AlamatPlg, TelpPlg) VALUES(?,?,?,?)";
    final String UPDATE = "UPDATE " + NAMA_TABEL 
            + " SET NmPlg=?, AlamatPlg=?, TelpPlg=? WHERE KdPlg = ?";
    final String DELETE = "DELETE FROM " + NAMA_TABEL + " WHERE KdPlg=?";
    final String SELECT = "SELECT * FROM " + NAMA_TABEL ;
    final String CARI = "SELECT * FROM " + NAMA_TABEL + " WHERE  KdPlg = ?";
    
    //konstruktor
    public DaoPelanggan(){}
    
    @Override
    public void insert(Pelanggan object) {
        PreparedStatement statementCari = null;
        PreparedStatement statementInsert = null;                
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            statementCari = conn.prepareStatement(CARI);
            statementCari.setString(1, object.getKdPlg());
            ResultSet rs = statementCari.executeQuery();
            if(rs.next()){ // jika data sudah pernah disimpan
                JOptionPane.showMessageDialog(null, "Data sudah pernah disimpan");
            }else{ //  jika data belum pernah disimpan
                statementInsert = conn.prepareStatement(INSERT);
                statementInsert.setString(1,object.getKdPlg());
                statementInsert.setString(2, object.getNmPlg());
                statementInsert.setString(3, object.getAlamatPlg());
                statementInsert.setString(4, object.getTelpPlg());
                statementInsert.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        finally{
            try{
                if(statementCari != null) statementCari.close();
                if(statementInsert != null) statementInsert.close();
                if(conn != null) conn.close();
            }catch(SQLException ex){
                Logger.getLogger(DaoPelanggan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Pelanggan object) {
        PreparedStatement statement = null;              
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(UPDATE);
            statement.setString(1, object.getNmPlg());
            statement.setString(2, object.getAlamatPlg());
            statement.setString(3, object.getTelpPlg());            
            statement.setString(4,object.getKdPlg());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        finally{
            try{
                if(!statement.isClosed()) statement.close();
                if(!conn.isClosed()) conn.close();
            }catch(SQLException ex){
                Logger.getLogger(DaoPelanggan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(String id) {
        PreparedStatement statement = null;              
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(DELETE);          
            statement.setString(1,id);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        finally{
            try{
                if(!statement.isClosed()) statement.close();
                if(!conn.isClosed()) conn.close();
            }catch(SQLException ex){
                Logger.getLogger(DaoPelanggan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Pelanggan> getAll() {
        List<Pelanggan> list = null;
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            list = new ArrayList<>();
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Pelanggan plg = new Pelanggan();
                plg.setKdPlg(rs.getString("KdPlg"));
                plg.setNmPlg(rs.getString("NmPlg"));
                plg.setAlamatPlg(rs.getString("AlamatPlg"));
                plg.setTelpPlg(rs.getString("TelpPlg"));
                list.add(plg);
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
        return list;
    }

    @Override
    public List<Pelanggan> getCari(String key) {
        List<Pelanggan> list = null;
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            list = new ArrayList<>();
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(CARI);
            statement.setString(1, key);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Pelanggan plg = new Pelanggan();
                plg.setKdPlg(rs.getString("KdPlg"));
                plg.setNmPlg(rs.getString("NmPlg"));
                plg.setAlamatPlg(rs.getString("AlamatPlg"));
                plg.setTelpPlg(rs.getString("TelpPlg"));
                list.add(plg);
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
        return list;
    }
        
    public List<Pelanggan> getByContainData(String keyword) {
        List<Pelanggan> list = null;
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            String cariByContain = "SELECT * FROM pelanggan WHERE  "
                    + " KdPlg LIKE ? OR "
                    + " NmPlg LIKE ? OR "
                    + " AlamatPlg LIKE ? OR "
                    + " TelpPlg LIKE ? " ;
    
            list = new ArrayList<>();
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(cariByContain);
            statement.setString(1, "%"+keyword+"%");
            statement.setString(2, "%"+keyword+"%");
            statement.setString(3, "%"+keyword+"%");
            statement.setString(4, "%"+keyword+"%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Pelanggan plg = new Pelanggan();
                plg.setKdPlg(rs.getString("KdPlg"));
                plg.setNmPlg(rs.getString("NmPlg"));
                plg.setAlamatPlg(rs.getString("AlamatPlg"));
                plg.setTelpPlg(rs.getString("TelpPlg"));
                list.add(plg);
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
                Logger.getLogger(DaoPelanggan.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }   
    
}

