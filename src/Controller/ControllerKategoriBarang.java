
package Controller;

import Dao.DaoKategoriBarang;
import Model.KategoriBarang;
import Model.TableModelKategoriBarang;
import View.MasterKategoriBarang;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerKategoriBarang {
    MasterKategoriBarang form;
    DaoKategoriBarang dao;
    List<KategoriBarang> listKategoriBarang;
    
    // kontruktor
    public ControllerKategoriBarang(MasterKategoriBarang form){
        this.form = form;
        dao = new DaoKategoriBarang();
    }
    
    public void reset(){
        form.getTxtKdKategori().setText("");
        form.getTxtNmKategori().setText("");
        form.getTxtKdKategori().setEnabled(true);
        form.getBtnSimpan().setEnabled(true);
        form.getBtnHapus().setEnabled(false);
        form.getBtnUbah().setEnabled(false);
        form.getTxtKdKategori().requestFocus();
    }
    
    public void isiTable(){
        listKategoriBarang = dao.getAll();
        TableModelKategoriBarang tableKategori = new TableModelKategoriBarang(listKategoriBarang);
        form.getTblKategoriBarang().setModel(tableKategori);
    }
    
    public void isiField(int row){
        form.getTxtKdKategori().setText(String.valueOf(listKategoriBarang.get(row).getKdKategori()));
        form.getTxtNmKategori().setText(String.valueOf(listKategoriBarang.get(row).getNmKategori()));
        form.getTxtKdKategori().setEnabled(false);
        form.getBtnSimpan().setEnabled(false);
        form.getBtnHapus().setEnabled(true);
        form.getBtnUbah().setEnabled(true);
    }
    
    private KategoriBarang getDataFormKategoriBarang(){
        KategoriBarang kategoriBarang = new KategoriBarang();
        kategoriBarang.setKdKategori(form.getTxtKdKategori().getText().trim());
        kategoriBarang.setNmKategori(form.getTxtNmKategori().getText().trim());
        return kategoriBarang;
    }
    
    public void insert(){
        KategoriBarang kategoriBarang = getDataFormKategoriBarang();        
        if(kategoriBarang.getKdKategori().isEmpty()){
            JOptionPane.showMessageDialog(form, "Kode Kategori tidak boleh kosong");
        }else{
            dao.insert(kategoriBarang);
        }
        
    }
    
    public void update(){
        KategoriBarang kategoriBarang = getDataFormKategoriBarang();        
        dao.update(kategoriBarang);
    }
    
    public void delete(){
        if(!form.getTxtKdKategori().getText().trim().isEmpty()){
            String kode = form.getTxtKdKategori().getText();
            dao.delete(kode);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
    
    public void cari(){
        listKategoriBarang = dao.getCari(form.getTxtKdKategori().getText().trim());
        TableModelKategoriBarang tableKategori = new TableModelKategoriBarang(listKategoriBarang);
        form.getTblKategoriBarang().setModel(tableKategori);
    }
    
}

