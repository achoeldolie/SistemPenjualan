
package Model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableModelPelanggan extends AbstractTableModel{

    List<Pelanggan> list;
    public TableModelPelanggan(List<Pelanggan> list){
        this.list = list;
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getKdPlg();
            case 1:
                return list.get(rowIndex).getNmPlg();
            case 2:
                return list.get(rowIndex).getAlamatPlg();
            case 3:
                return list.get(rowIndex).getTelpPlg();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                return "KODE";
            case 1:
                return "NAMA PELANGGAN";
            case 2:
                return "ALAMAT";
            case 3:
                return "TELEPON";
            default:
                return null;
        }
    }
    
}

