
package Controller;

import Dao.DaoBarang;
import Dao.DaoPelanggan;
import Dao.DaoPesanan;
import Helper.AutoNumber;
import Helper.MyPlainDocument;
import Model.Barang;
import Model.DetailPesanan;
import Model.Pelanggan;
import Model.Pesanan;
import Model.TableModelDetailPesanan;
import View.LookupBarang;
import View.LookupPelanggan;
import View.TransaksiPesanan;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerPesanan {
    TransaksiPesanan form;
    DaoBarang daoBarang;
    DaoPelanggan daoPelanggan;
    DaoPesanan daoPesanan;
    Barang selectedBarang = null;
    Pelanggan selectedPelanggan = null;
    List<DetailPesanan> listDetailPesanan;
    AutoNumber autoNumber = new AutoNumber("PESANAN");
    
    // konstruktor
    public ControllerPesanan(TransaksiPesanan form){
        this.form = form;
        daoBarang = new DaoBarang();
        daoPelanggan = new DaoPelanggan();
        daoPesanan = new DaoPesanan();
                
        this.form.getTxtNamaBarang().setDocument(new MyPlainDocument(MyPlainDocument.TYPE_TEXT, 50) );
        this.form.getTxtHarga().setDocument(new MyPlainDocument(MyPlainDocument.TYPE_DOUBLE) );
        this.form.getTxtStok().setDocument(new MyPlainDocument(MyPlainDocument.TYPE_INT, 5) );
        this.form.getTxtJumlah().setDocument(new MyPlainDocument(MyPlainDocument.TYPE_INT, 3) );
    }
    
    public static String formatCurrency (Double number) {
        if(number == 0) return "0,00";
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("###,###,###.00", otherSymbols);
        return formatter.format(number);
    }
    
    private void updateTotalPesanan(){
        Double total = 0d;
        for(int i=0; i < listDetailPesanan.size(); i++){
            total = total + (listDetailPesanan.get(i).getHrgPesan() * listDetailPesanan.get(i).getJmlPesan());
        }
        this.form.getLblTotal().setText("Rp " + formatCurrency(total));
        
    }
    
    private boolean isExistBarang(String kdBarang){
        boolean result = false;    
        for(int i = 0 ; i < listDetailPesanan.size(); i++ )
        {
            if(listDetailPesanan.get(i).getKdBrg().equals(kdBarang)){
                result = true;
                break;
            }
        }
        return result;
    }
    
    private void updateIsiTabelDetailPesanan(){
        TableModelDetailPesanan tableModelDetail = new TableModelDetailPesanan(listDetailPesanan);
        this.form.getTblDetailPesanan().setModel(tableModelDetail);
    }
    
    public void resetFormPesanan(){
        this.form.getTxtNoTransaksi().setText(autoNumber.getNextNumber());
        this.form.getTxtKdPelanggan().setText("");
        this.form.getTxtNamaPelanggan().setText("");
        this.form.getTxtNamaPelanggan().setEditable(false);
        this.form.getTxtNoTransaksi().setEditable(false);
        this.form.getTxtTglPesanan().setEditable(false);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // contoh yyyy-MM-dd HH:mm:ss
        Date date = new Date();
        this.form.getTxtTglPesanan().setText(dateFormat.format(date)); 

        selectedPelanggan = null;
        listDetailPesanan = new ArrayList<DetailPesanan>();
        resetFormDetailPesanan();
    }
    
    public void resetFormDetailPesanan(){
        this.form.getTxtKodeBarang().setText("");
        this.form.getTxtNamaBarang().setText("");
        this.form.getTxtHarga().setText("");
        this.form.getTxtKategoriBarang().setText("");
        this.form.getTxtStok().setText("");
        this.form.getTxtJumlah().setText("");
        this.form.getTxtNamaBarang().setEditable(false);
        this.form.getTxtHarga().setEditable(false);
        this.form.getTxtKategoriBarang().setEditable(false);
        this.form.getTxtStok().setEditable(false);
        selectedBarang = null;
        updateIsiTabelDetailPesanan();
        updateTotalPesanan();
    }
            
    private void isiFormDetailBarang(){
        if (selectedBarang != null){
            this.form.getTxtKodeBarang().setText(selectedBarang.getKdBrg());
            this.form.getTxtNamaBarang().setText(selectedBarang.getNmBrg());
            this.form.getTxtHarga().setText(String.valueOf(selectedBarang.getHargaBrg()));
            this.form.getTxtKategoriBarang().setText(selectedBarang.getKategoriBarang().getNmKategori());
            this.form.getTxtStok().setText(String.valueOf(selectedBarang.getStok()));
        }else{
            this.form.getTxtNamaBarang().setText("");
            this.form.getTxtHarga().setText("0");
            this.form.getTxtKategoriBarang().setText("");
            this.form.getTxtStok().setText("0");
        }
    }
    
    private void isiFormDataPelanggan(){
        if (selectedPelanggan != null){
            this.form.getTxtKdPelanggan().setText(selectedPelanggan.getKdPlg());
            this.form.getTxtNamaPelanggan().setText(selectedPelanggan.getNmPlg());
        }else{
            this.form.getTxtNamaPelanggan().setText("");
        }
    }    
        
    private boolean validasiDataDetailTransaksi(){
        boolean result = false;
        if(selectedBarang == null){
            JOptionPane.showMessageDialog(form, "Anda belum memilih barang");
        }
        else if(isExistBarang(selectedBarang.getKdBrg()) ){
            JOptionPane.showMessageDialog(form, "Barang sudah ada");
        }
        else if(this.form.getTxtJumlah().getText().isEmpty()){
            JOptionPane.showMessageDialog(form, "Jumlah Barang yang dipesan tidak boleh kosong");
        }else if(Integer.parseInt(this.form.getTxtJumlah().getText()) <= 0 ){
            JOptionPane.showMessageDialog(form, "Jumlah Barang yang dipesan harus lebih besar dari 0");
        }else if(Integer.parseInt(this.form.getTxtJumlah().getText()) > selectedBarang.getStok() ){
            JOptionPane.showMessageDialog(form, "Stok Barang tidak mencukupi");
        }
        else{
            result = true;
        }
        return result;
    }
    
    private boolean validasiDataPesanan(){
        boolean result = false;
        if(selectedPelanggan == null){
            JOptionPane.showMessageDialog(form, "Anda belum memilih pelanggan");
        }
        else if(listDetailPesanan== null || listDetailPesanan.size() == 0){
            JOptionPane.showMessageDialog(form, "Belum menambahkan detail pesanan");
        }
        else{
            result = true;
        }
        return result;
    }
    
    public boolean simpanPesanan() {
        boolean result = false;       
        if(validasiDataPesanan()){
            Pesanan pesanan = new Pesanan();
            pesanan.setKdPlg(selectedPelanggan.getKdPlg());
            pesanan.setListDetailPesanan(listDetailPesanan);
            pesanan.setNoPesan(this.form.getTxtNoTransaksi().getText());
            pesanan.setPelanggan(selectedPelanggan);            
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date tglPesan = formatter.parse(this.form.getTxtTglPesanan().getText());
                pesanan.setTglPesan(tglPesan);
            }catch(Exception ex){}
            
            daoPesanan.insert(pesanan);
            autoNumber.update();
            resetFormPesanan();
            result = true;
        }
        return result;
    }
    
    public boolean tambahDetailPesanan(){
        boolean result = false;       
        if(validasiDataDetailTransaksi()){
            DetailPesanan detail = new DetailPesanan();
            detail.setBarang(selectedBarang);
            detail.setHrgPesan(selectedBarang.getHargaBrg());
            detail.setJmlPesan(Integer.parseInt(this.form.getTxtJumlah().getText()));
            detail.setKdBrg(selectedBarang.getKdBrg());
            detail.setNoPesan(this.form.getTxtNoTransaksi().getText());
            listDetailPesanan.add(detail);            
            resetFormDetailPesanan();
            result = true;
        }
        return result;
    }
    
    public boolean hapusDetailPesanan(){
        boolean result = false;       
        int rowSelected = this.form.getTblDetailPesanan().getSelectedRow();
        if(rowSelected >= 0 ){
            listDetailPesanan.remove(rowSelected);
            updateIsiTabelDetailPesanan();
            updateTotalPesanan();
            result = true;
        }else{
            JOptionPane.showMessageDialog(form, "Anda belum memilih detail barang");
        }
        return result;
    }
    
    public void getDataBarang(){
        selectedBarang = daoBarang.getSingleData(this.form.getTxtKodeBarang().getText()); 
        isiFormDetailBarang();
    }
    
    public void getDataPelanggan(){
        List<Pelanggan> listPelanggan = daoPelanggan.getCari(this.form.getTxtKdPelanggan().getText()); 
        if(listPelanggan == null || listPelanggan.isEmpty()|| listPelanggan.size() > 1){
            selectedPelanggan = null;
        }else{
            selectedPelanggan = listPelanggan.get(0);
        }
        isiFormDataPelanggan();
    }    
        
    public void showLookupPelanggan(){
        LookupPelanggan lookup = new LookupPelanggan(form, true);
        lookup.setVisible(true);
        if(lookup.selectedPelanggan != null){
            selectedPelanggan = lookup.selectedPelanggan;
            isiFormDataPelanggan();
        }
        lookup.dispose();
    }
    
    public void showLookupBarang(){
        LookupBarang lookup = new LookupBarang(form, true);
        lookup.setVisible(true);
        if(lookup.selectedBarang != null){
            selectedBarang = lookup.selectedBarang;
            isiFormDetailBarang();
        }
        lookup.dispose();
    }
    
}
