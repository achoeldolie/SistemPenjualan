
package Controller;

import Dao.DaoBarang;
import Model.Barang;
import Model.TableModelBarang;
import View.LookupBarang;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerLookupBarang {
    LookupBarang form;
    DaoBarang dao;
    List<Barang> listBarang;
    Barang selectedBarang;
    
    // kontruktor
    public ControllerLookupBarang(LookupBarang form){
        this.form = form;
        dao = new DaoBarang(); 
        form.getTxtPencarian().setText("");
        isiTable();
    }
    
    public void isiTable(){    
        listBarang = dao.getByContainData(form.getTxtPencarian().getText());
        TableModelBarang tableBarang = new TableModelBarang(listBarang);
        form.getTblLookupBarang().setModel(tableBarang);
    }
    
    public Barang getSelectedBarang(){
        return selectedBarang;
    }
    
    public boolean pilihBarang(){
        boolean result = false;
        int rowSelected = this.form.getTblLookupBarang().getSelectedRow();
        if(rowSelected >= 0 ){
            selectedBarang = listBarang.get(rowSelected);
            result = true;
        }else{
            JOptionPane.showMessageDialog(form, "Anda belum memilih barang");
        }
        return result;
    }
}
