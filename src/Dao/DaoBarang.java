
package Dao;

import Koneksi.Database;
import Model.Barang;
import Model.KategoriBarang;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DaoBarang implements Dao.ModelDao<Barang> {

    final String NAMA_TABEL = "barang";
    final String INSERT = "INSERT INTO " + NAMA_TABEL 
            + " (KdBrg, NmBrg, Satuan, HargaBrg, Stok, KdKategori ) VALUES(?,?,?,?,?,?)";
    final String UPDATE = "UPDATE " + NAMA_TABEL 
            + " SET NmBrg=?, Satuan=?, HargaBrg=?, Stok=?  WHERE KdBrg = ?";
    final String DELETE = "DELETE FROM " + NAMA_TABEL + " WHERE KdBrg=?";
    final String SELECT = "SELECT a.*, b.NmKategori FROM " + NAMA_TABEL + " as a INNER JOIN kategori b "
            + "ON a.KdKategori = b.KdKategori ";    
    final String CARI = "SELECT a.*, b.NmKategori FROM " + NAMA_TABEL + " as a INNER JOIN kategori b "
            + "ON a.KdKategori = b.KdKategori  WHERE  a.KdBrg LIKE ?";
    
    //konstruktor
    public DaoBarang(){}
    
    @Override
    public void insert(Barang object) {
        PreparedStatement statementCari = null;
        PreparedStatement statementInsert = null;                
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            statementCari = conn.prepareStatement(CARI);
            statementCari.setString(1, object.getKdBrg());
            ResultSet rs = statementCari.executeQuery();
            if(rs.next()){ // jika data sudah pernah disimpan
                JOptionPane.showMessageDialog(null, "Data sudah pernah disimpan");
            }else{ //  jika data belum pernah disimpan
                statementInsert = conn.prepareStatement(INSERT);
                statementInsert.setString(1,object.getKdBrg());
                statementInsert.setString(2, object.getNmBrg());
                statementInsert.setString(3, object.getSatuan());
                statementInsert.setDouble(4, object.getHargaBrg());
                statementInsert.setInt(5, object.getStok());
                statementInsert.setString(6, object.getKategoriBarang().getKdKategori());
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
    public void update(Barang object) {
        PreparedStatement statement = null;              
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(UPDATE);
            statement.setString(1, object.getNmBrg());
            statement.setString(2, object.getSatuan());
            statement.setDouble(3, object.getHargaBrg());
            statement.setInt(4, object.getStok());
            statement.setString(5, object.getKdBrg());
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
    
    public void updateStok(String kdBarang, int stok) {
        PreparedStatement statement = null;              
        Connection conn = null;
        try{
            String sqlUpdateStok = "UPDATE barang SET Stok=?  WHERE KdBrg = ?";
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(sqlUpdateStok);
            statement.setInt(1, stok);
            statement.setString(2, kdBarang);
            statement.executeUpdate();
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
    public List<Barang> getAll() {
        List<Barang> list = null;
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            list = new ArrayList<>();
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                // mempersiapkan objek barang
                Barang barang = new Barang();
                barang.setKdBrg(rs.getString("KdBrg"));
                barang.setNmBrg(rs.getString("NmBrg"));
                barang.setSatuan(rs.getString("Satuan"));
                barang.setHargaBrg( Double.parseDouble(rs.getString("HargaBrg")) );
                barang.setStok(Integer.parseInt(rs.getString("Stok")) );
                
                //membuat objek kategori
                KategoriBarang kategoriBarang = new KategoriBarang();
                kategoriBarang.setKdKategori(rs.getString("KdKategori"));
                kategoriBarang.setNmKategori(rs.getString("NmKategori"));
                
                // memberikan nilai dari objek kategori ke objek barang
                barang.setKategoriBarang(kategoriBarang);
                
                // menambahkan objek barang ke list
                list.add(barang);
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
    public List<Barang> getCari(String key) {
        List<Barang> list = null;
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            list = new ArrayList<>();
            conn = Database.KoneksiDB();
            
            statement = conn.prepareStatement(CARI);
            statement.setString(1, "%" + key + "%");
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                // mempersiapkan objek barang
                Barang barang = new Barang();
                barang.setKdBrg(rs.getString("KdBrg"));
                barang.setNmBrg(rs.getString("NmBrg"));
                barang.setSatuan(rs.getString("Satuan"));
                barang.setHargaBrg( Double.parseDouble(rs.getString("HargaBrg")) );
                barang.setStok(Integer.parseInt(rs.getString("Stok")) );
                
                //membuat objek kategori
                KategoriBarang kategoriBarang = new KategoriBarang();
                kategoriBarang.setKdKategori(rs.getString("KdKategori"));
                kategoriBarang.setNmKategori(rs.getString("NmKategori"));
                
                // memberikan nilai dari objek kategori ke objek barang
                barang.setKategoriBarang(kategoriBarang);
                
                // menambahkan objek barang ke list
                list.add(barang);
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
       
    public List<Barang> getByContainData(String keyword) {
        List<Barang> list = null;
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            
            String cariByContain = "SELECT a.*, b.NmKategori "
                    + " FROM barang as a INNER JOIN kategori b "
                    + " ON a.KdKategori = b.KdKategori  "
                    + " WHERE   a.KdBrg LIKE ? OR "
                    + "         a.NmBrg LIKE ? OR "
                    + "         a.Satuan LIKE ? OR "
                    + "         a.KdKategori LIKE ? OR "
                    + "         b.NmKategori LIKE ? ";
    
            list = new ArrayList<>();
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(cariByContain);
            statement.setString(1, "%"+keyword+"%");
            statement.setString(2, "%"+keyword+"%");
            statement.setString(3, "%"+keyword+"%");
            statement.setString(4, "%"+keyword+"%");
            statement.setString(5, "%"+keyword+"%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                // mempersiapkan objek barang
                Barang barang = new Barang();
                barang.setKdBrg(rs.getString("KdBrg"));
                barang.setNmBrg(rs.getString("NmBrg"));
                barang.setSatuan(rs.getString("Satuan"));
                barang.setHargaBrg( Double.parseDouble(rs.getString("HargaBrg")) );
                barang.setStok(Integer.parseInt(rs.getString("Stok")) );
                
                //membuat objek kategori
                KategoriBarang kategoriBarang = new KategoriBarang();
                kategoriBarang.setKdKategori(rs.getString("KdKategori"));
                kategoriBarang.setNmKategori(rs.getString("NmKategori"));
                
                // memberikan nilai dari objek kategori ke objek barang
                barang.setKategoriBarang(kategoriBarang);
                list.add(barang);
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
    
    public Barang getSingleData(String key) {
        Barang barang = null;
        PreparedStatement statement = null;
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            statement = conn.prepareStatement(CARI);
            statement.setString(1, key );
                
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                // mempersiapkan objek barang
                barang = new Barang();
                barang.setKdBrg(rs.getString("KdBrg"));
                barang.setNmBrg(rs.getString("NmBrg"));
                barang.setSatuan(rs.getString("Satuan"));
                barang.setHargaBrg( Double.parseDouble(rs.getString("HargaBrg")) );
                barang.setStok(Integer.parseInt(rs.getString("Stok")) );
                
                //membuat objek kategori
                KategoriBarang kategoriBarang = new KategoriBarang();
                kategoriBarang.setKdKategori(rs.getString("KdKategori"));
                kategoriBarang.setNmKategori(rs.getString("NmKategori"));
                
                // memberikan nilai dari objek kategori ke objek barang
                barang.setKategoriBarang(kategoriBarang);
                
            }
        }
        catch (SQLException | NumberFormatException ex){
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
        return barang;
    }
}

