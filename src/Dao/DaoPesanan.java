
package Dao;

import Koneksi.Database;
import Model.Pesanan;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DaoPesanan implements ModelDao<Pesanan>{

    final String INSERT_PESANAN = "INSERT INTO buktipesan " 
            + " (NoPesan, TglPesan, KdPlg) VALUES(?,?,?)";
    final String UPDATE_PESANAN = "UPDATE buktipesan "
            + " SET TglPesan=?, KdPlg=? WHERE NoPesan = ?";
    final String DELETE_PESANAN = "DELETE FROM buktipesan WHERE NoPesan=?";
    final String SELECT_PESANAN = "SELECT * FROM buktipesan" ;
    final String CARI_PESANAN = "SELECT * FROM buktipesan WHERE  NoPesan = ?";
    
    final String INSERT_DETIL_PESANAN = "INSERT INTO detilpesan " 
            + " (NoPesan, KdBrg, HrgPesan, JmlPesan) VALUES(?,?,?,?)";
    final String UPDATE_DETIL_PESANAN = "UPDATE detilpesan "
            + " SET HrgPesan=?, JmlPesan=? WHERE NoPesan = ? AND KdBrg=? ";
    final String DELETE_DETIL_PESANAN = "DELETE FROM detilpesan WHERE NoPesan=? ";
    final String SELECT_DETIL_PESANAN = "SELECT * FROM detilpesan WHERE NoPesan=? " ;
    final String CARI_DETIL_PESANAN = "SELECT * FROM detilpesan WHERE  NoPesan = ? ";
    
    public DaoPesanan(){}
    
    @Override
    public void insert(Pesanan object) {
        PreparedStatement statementCari = null;
        PreparedStatement statementInsert = null;                
        Connection conn = null;
        try{
            conn = Database.KoneksiDB();
            statementCari = conn.prepareStatement(CARI_PESANAN);
            statementCari.setString(1, object.getNoPesan());
            ResultSet rs = statementCari.executeQuery();
            if(rs.next()){ // jika data sudah pernah disimpan
                JOptionPane.showMessageDialog(null, "Data sudah pernah disimpan");
            }else{ //  jika data belum pernah disimpan
                // konversi date util ke date sql                
                Date dtSql = new Date(object.getTglPesan().getTime());           
                
                statementInsert = conn.prepareStatement(INSERT_PESANAN);
                statementInsert.setString(1,object.getNoPesan());
                statementInsert.setDate(2, dtSql);
                statementInsert.setString(3, object.getKdPlg());
                statementInsert.executeUpdate();
                // setelah berhasil menyimpan data pesanan
                // selanjutnya menyimpan detil dari pesanan
                PreparedStatement statementInsertDetil = conn.prepareStatement(INSERT_DETIL_PESANAN);
                DaoBarang daoBarang = new DaoBarang();
                for(int i=0; i < object.getListDetailPesanan().size(); i++ ){
                    //NoPesan, KdBrg, HrgPesan, JmlPesan
                    statementInsertDetil.setString(1, object.getNoPesan());
                    statementInsertDetil.setString(2, object.getListDetailPesanan().get(i).getKdBrg());
                    statementInsertDetil.setDouble(3, object.getListDetailPesanan().get(i).getHrgPesan());
                    statementInsertDetil.setInt(4, object.getListDetailPesanan().get(i).getJmlPesan());
                    // jika berhasil menyimpan/menambahkan detil pesanan
                    // maka selanjutnya update stok barang
                    if(statementInsertDetil.executeUpdate() > 0){
                        int curStok = object.getListDetailPesanan().get(i).getBarang().getStok() - 
                                object.getListDetailPesanan().get(i).getJmlPesan();
                        daoBarang.updateStok(object.getListDetailPesanan().get(i).getBarang().getKdBrg(), curStok);
                    }
                }
                JOptionPane.showMessageDialog(null, "Data Berhasil disimpan");
                
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
    public void update(Pesanan object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public List<Pesanan> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public List<Pesanan> getCari(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

