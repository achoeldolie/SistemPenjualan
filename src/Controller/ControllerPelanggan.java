
package Controller;

import Dao.DaoPelanggan;
import Model.Pelanggan;
import Model.TableModelPelanggan;
import View.MasterPelanggan;
import java.util.List;
import javax.swing.JOptionPane;


public class ControllerPelanggan {
    MasterPelanggan form;
    DaoPelanggan dao;
    List<Pelanggan> listPelanggan;
    
    // kontruktor
    public ControllerPelanggan(MasterPelanggan form){
        this.form = form;
        dao = new DaoPelanggan();
        
    }
    

    public void reset(){
        form.getTxtKdPlg().setText("");
        form.getTxtNmPlg().setText("");
        form.getTxtAlamat().setText("");
        form.getTxtTelp().setText("");    
        form.getTxtKdPlg().setEnabled(true);
        form.getBtnSimpan().setEnabled(true);
        form.getBtnHapus().setEnabled(false);
        form.getBtnUbah().setEnabled(false);
        form.getTxtKdPlg().requestFocus();
    }
    
    public void isiTable(){
        listPelanggan = dao.getAll();
        TableModelPelanggan tablePelanggan = new TableModelPelanggan(listPelanggan);
        form.getTblPlg().setModel(tablePelanggan);
    }
    
    public void isiField(int row){
        form.getTxtKdPlg().setText(String.valueOf(listPelanggan.get(row).getKdPlg()));
        form.getTxtNmPlg().setText(String.valueOf(listPelanggan.get(row).getNmPlg()));
        form.getTxtAlamat().setText(String.valueOf(listPelanggan.get(row).getAlamatPlg()));
        form.getTxtTelp().setText(String.valueOf(listPelanggan.get(row).getTelpPlg()));
        form.getTxtKdPlg().setEnabled(false);
        form.getBtnSimpan().setEnabled(false);
        form.getBtnHapus().setEnabled(true);
        form.getBtnUbah().setEnabled(true);
    }
    
    private Pelanggan getDataFormPelanggan(){
        Pelanggan plg = new Pelanggan();
        plg.setKdPlg( form.getTxtKdPlg().getText().trim());
        plg.setNmPlg(form.getTxtNmPlg().getText().trim());
        plg.setAlamatPlg(form.getTxtAlamat().getText().trim());
        plg.setTelpPlg(form.getTxtTelp().getText().trim());
        return plg;
    }
    
    public void insert(){
        Pelanggan plg = getDataFormPelanggan();        
        if(plg.getKdPlg().isEmpty()){
            JOptionPane.showMessageDialog(form, "Kode Pelanggan tidak boleh kosong");
        }else{
            dao.insert(plg);
        }
        
    }
    
    public void update(){
        Pelanggan plg = getDataFormPelanggan();        
        dao.update(plg);
    }
    
    public void delete(){
        if(!form.getTxtKdPlg().getText().trim().isEmpty()){
            String kode = form.getTxtKdPlg().getText();
            dao.delete(kode);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
    
    public void cari(){
        listPelanggan = dao.getCari(form.getTxtKdPlg().getText().trim());
        TableModelPelanggan tablePelanggan = new TableModelPelanggan(listPelanggan);
        form.getTblPlg().setModel(tablePelanggan);
    }
}
