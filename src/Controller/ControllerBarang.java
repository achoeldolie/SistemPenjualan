
package Controller;

//testf
import Dao.DaoBarang;
import Dao.DaoKategoriBarang;
import Helper.AutoNumber;
import Helper.MyPlainDocument;
import Model.Barang;
import Model.KategoriBarang;
import Model.TableModelBarang;
import View.MasterBarang;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerBarang {
    MasterBarang form;
    DaoBarang daoBarang;
    DaoKategoriBarang daoKategoriBarang;
    List<Barang> listBarang;
    List<KategoriBarang> listKategoriBarang;
    List<String> listSatuan;
    AutoNumber autoNumber = new AutoNumber("BARANG");
    
    // konstruktor
    public ControllerBarang(MasterBarang form){
        this.form = form;
        daoBarang = new DaoBarang();
        daoKategoriBarang = new DaoKategoriBarang();
        listSatuan = new ArrayList<String>();
        listSatuan.add("-Pilih-");
        listSatuan.add("PCS");
        listSatuan.add("KG");
        listSatuan.add("LUSIN");
        
        this.form.getTxtNamaBarang().setDocument(new MyPlainDocument(MyPlainDocument.TYPE_TEXT, 50) );
        this.form.getTxtHarga().setDocument(new MyPlainDocument(MyPlainDocument.TYPE_DOUBLE) );
        this.form.getTxtStok().setDocument(new MyPlainDocument(MyPlainDocument.TYPE_INT, 5) );
    }
    
    public void reset(){
        form.getCmbKodeKategori().setSelectedIndex(0);
        form.getTxtNamaBarang().setText("");
        form.getTxtHarga().setText("");
        form.getTxtKodeBarang().setText(autoNumber.getNextNumber());
        form.getTxtNamaBarang().setText("");
        form.getTxtKataKunciPencarian().setText("");
        form.getCmbSatuan().setSelectedIndex(0);
        form.getTxtStok().setText("");
        form.getBtnSimpan().setEnabled(true);
        form.getBtnHapus().setEnabled(false);
        form.getBtnUbah().setEnabled(false);
        form.getTxtKodeBarang().setEditable(false);
        form.getCmbKodeKategori().setEnabled(true);
        form.getCmbKodeKategori().requestFocus();
        isiComboKategori();
        isiComboSatuan();
        isiTable();
    }
    
    public void isiTable(){
        listBarang = daoBarang.getAll();
        TableModelBarang tableBarang = new TableModelBarang(listBarang);
        form.getTblBarang().setModel(tableBarang);
    }
    
    public void isiField(int row){
        String kodeKategori = listBarang.get(row).getKategoriBarang().getKdKategori();
        int indexKategori ;
        boolean isKategoriKetemu = false;
        for(indexKategori = 0; indexKategori < listKategoriBarang.size(); indexKategori++){
            if(listKategoriBarang.get(indexKategori).getKdKategori().equals(kodeKategori)){
                isKategoriKetemu = true;
                break;
            }
        }
        // jika kode kategori ketemu pilih combo kategori sesuai dengan indekKategori
        if(isKategoriKetemu){
            form.getCmbKodeKategori().setSelectedIndex(indexKategori);
        }
        form.getTxtHarga().setText( String.valueOf(listBarang.get(row).getHargaBrg()));
        form.getTxtKodeBarang().setText(listBarang.get(row).getKdBrg());
        form.getTxtNamaBarang().setText(listBarang.get(row).getNmBrg());
        form.getTxtStok().setText(String.valueOf(listBarang.get(row).getStok()));
        form.getCmbSatuan().setSelectedItem(listBarang.get(row).getSatuan());
        
        form.getCmbKodeKategori().setEnabled(false);
        form.getBtnSimpan().setEnabled(false);
        form.getBtnHapus().setEnabled(true);
        form.getBtnUbah().setEnabled(true);
    }
    
    public void isiComboKategori(){
        form.getCmbKodeKategori().removeAllItems();
        
        listKategoriBarang = new ArrayList<KategoriBarang>();
        KategoriBarang katDefault = new KategoriBarang();
        katDefault.setKdKategori("");
        katDefault.setNmKategori("-Pilih-");
        listKategoriBarang.add(katDefault);
        
        form.getCmbKodeKategori().addItem("-Pilih-");
        
        List<KategoriBarang> allKategori = daoKategoriBarang.getAll();
        for(int i= 0; i <allKategori.size(); i++){
            listKategoriBarang.add(allKategori.get(i));
            form.getCmbKodeKategori().addItem(allKategori.get(i).getKdKategori() + " - " 
                    + allKategori.get(i).getNmKategori());
        }
        
    }
    
    public void isiComboSatuan(){
        form.getCmbSatuan().removeAllItems();
        for(int i= 0; i <listSatuan.size(); i++){
            form.getCmbSatuan().addItem(listSatuan.get(i));
        }
    }
    
    private Barang getDataFormBarang(){
        Barang barang = new Barang();
        barang.setKategoriBarang(listKategoriBarang.get(form.getCmbKodeKategori().getSelectedIndex()));
        barang.setHargaBrg(Double.parseDouble(form.getTxtHarga().getText()));
        barang.setKdBrg(form.getTxtKodeBarang().getText());
        barang.setNmBrg(form.getTxtNamaBarang().getText());
        barang.setSatuan(form.getCmbSatuan().getSelectedItem().toString());
        barang.setStok(Integer.parseInt(form.getTxtStok().getText()));
        return barang;
    }
    
    private boolean validasiData(Barang barang){
        boolean result = false;
        if(barang.getKategoriBarang().getKdKategori().isEmpty()){
            JOptionPane.showMessageDialog(form, "Kategori Barang tidak boleh kosong");
        }
        else if(barang.getKdBrg().isEmpty()){
            JOptionPane.showMessageDialog(form, "Kode Barang tidak boleh kosong");
        }else if(barang.getNmBrg().isEmpty()){
            JOptionPane.showMessageDialog(form, "Nama Barang tidak boleh kosong");
        }else if(barang.getSatuan().isEmpty()){
            JOptionPane.showMessageDialog(form, "Satuan tidak boleh kosong");
        }else if(String.valueOf(barang.getHargaBrg()).isEmpty()){
            JOptionPane.showMessageDialog(form, "Harga tidak boleh kosong");
        }else if(String.valueOf(barang.getStok()).isEmpty()){
            JOptionPane.showMessageDialog(form, "Stok tidak boleh kosong");
        }else{
            result = true;
        }
        return result;
    }
    
    public boolean insert(){
        boolean result = false;
        Barang barang = getDataFormBarang();        
        if(validasiData(barang)){
            daoBarang.insert(barang);
            autoNumber.update();
            isiTable();
            reset();
            result = true;
        }
        return result;
    }
    
    public boolean update(){
        boolean result = false;
        Barang barang = getDataFormBarang();        
        if(validasiData(barang)){
            daoBarang.update(barang);
            isiTable();
            reset();
            result = true;
        }
        return result;
    }
    
    public boolean delete(){
        boolean result = false;
        if(!form.getTxtKodeBarang().getText().trim().isEmpty()){
            String kode = form.getTxtKodeBarang().getText();
            daoBarang.delete(kode);
            isiTable();
            reset();
            result = true;
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
        return result;
    }
    
    public void cari(){
        listBarang = daoBarang.getCari(form.getTxtKataKunciPencarian().getText().trim());
        TableModelBarang tableBarang = new TableModelBarang(listBarang);
        form.getTblBarang().setModel(tableBarang);
    }
}
