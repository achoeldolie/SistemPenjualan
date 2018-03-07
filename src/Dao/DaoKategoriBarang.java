
package Dao;

import Model.KategoriBarang;
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

public class DaoKategoriBarang implements Dao.ModelDao<KategoriBarang>{
    
    final String NAMA_TABEL = "kategori";
    final String INSERT = "INSERT INTO " + NAMA_TABEL 
            + " (KdKategori, NmKategori) VALUES(?,?)";
    final String UPDATE = "UPDATE kategori " + NAMA_TABEL 
            + " SET NmKategori=? WHERE KdKategori = ?";
    final String DELETE = "DELETE FROM " + NAMA_TABEL + " WHERE KdKategori=?";
    final String SELECT = "SELECT * FROM " + NAMA_TABEL ;
    final String CARI = "SELECT * FROM " + NAMA_TABEL + " WHERE  KdKategori = ?";
    
    //konstruktor
    public DaoKategoriBarang(){}
    
    @Override
    public void insert(KategoriBarang object) {
        PreparedStatement statementCari = null;
        PreparedStatement statementInsert = null;                
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            statementCari = conn.prepareStatement(CARI);
            statementCari.setString(1, object.getKdKategori());
            ResultSet rs = statementCari.executeQuery();
            if(rs.next()){ // jika data sudah pernah disimpan
                JOptionPane.showMessageDialog(null, "Data sudah pernah disimpan");
            }else{ //  jika data belum pernah disimpan
                statementInsert = conn.prepareStatement(INSERT);
                statementInsert.setString(1,object.getKdKategori());
                statementInsert.setString(2, object.getNmKategori());
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
    public void update(KategoriBarang object) {
        PreparedStatement statement = null;              
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(UPDATE);
            statement.setString(1, object.getNmKategori());
            statement.setString(2, object.getKdKategori());
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
    public List<KategoriBarang> getAll() {
        List<KategoriBarang> list = null;
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            list = new ArrayList<>();
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                KategoriBarang kategoriBarang = new KategoriBarang();
                kategoriBarang.setKdKategori(rs.getString("KdKategori"));
                kategoriBarang.setNmKategori(rs.getString("NmKategori"));
                list.add(kategoriBarang);
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
    public List<KategoriBarang> getCari(String key) {
        List<KategoriBarang> list = null;
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            list = new ArrayList<>();
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(CARI);
            statement.setString(1, key);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                KategoriBarang kategoriBarang = new KategoriBarang();
                kategoriBarang.setKdKategori(rs.getString("KdKategori"));
                kategoriBarang.setNmKategori(rs.getString("NmKategori"));
                list.add(kategoriBarang);
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
    
}






