
package Model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableModelKategoriBarang extends AbstractTableModel{
    
    List<KategoriBarang> list ;
    
    public TableModelKategoriBarang(List<KategoriBarang> list){
        this.list = list;
    }
     
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getKdKategori();
            case 1:
                return list.get(rowIndex).getNmKategori();
            default:
                return null;
        }
    } 
    
    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                return "KODE KATEGORI";
            case 1:
                return "NAMA KATEGORI";
            default:
                return null;
        }
    } 
        
}


