
package Controller;

import Dao.DaoPelanggan;
import Model.Pelanggan;
import Model.TableModelPelanggan;
import View.LookupPelanggan;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerLookupPelanggan {
    LookupPelanggan form;
    DaoPelanggan dao;
    List<Pelanggan> listPelanggan;
    Pelanggan selectedPelanggan;
    
    // kontruktor
    public ControllerLookupPelanggan(LookupPelanggan form){
        this.form = form;
        dao = new DaoPelanggan(); 
        form.getTxtPencarian().setText("");
        isiTable();
    }
    
    public void isiTable(){    
        listPelanggan = dao.getByContainData(form.getTxtPencarian().getText());
        TableModelPelanggan tablePelanggan = new TableModelPelanggan(listPelanggan);
        form.getTblLookupPelanggan().setModel(tablePelanggan);
    }
    
    public Pelanggan getSelectedPelanggan(){
        return selectedPelanggan;
    }
    
    public boolean pilihPelanggan(){
        boolean result = false;
        int rowSelected = this.form.getTblLookupPelanggan().getSelectedRow();
        if(rowSelected >= 0 ){
            selectedPelanggan = listPelanggan.get(rowSelected);
            result = true;
        }else{
            JOptionPane.showMessageDialog(form, "Anda belum memilih pelanggan");
        }
        return result;
    }
    
    
}
