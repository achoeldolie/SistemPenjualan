
package Model;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelBarang extends AbstractTableModel {

    List<Barang> list;
    
    public TableModelBarang(List<Barang> list){
        this.list = list;
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getKdBrg();
            case 1:
                return list.get(rowIndex).getNmBrg();
            case 2:
                return list.get(rowIndex).getKategoriBarang().getKdKategori();
            case 3:
                return list.get(rowIndex).getKategoriBarang().getNmKategori();            
            case 4:
                return list.get(rowIndex).getSatuan();
            case 5:
                return list.get(rowIndex).getHargaBrg();
            case 6:
                return list.get(rowIndex).getStok();
            default:
                return null;
        }
    }    

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                return "KODE BARANG";
            case 1:
                return "NAMA BARANG";
            case 2:
                return "KODE KATEGORI";
            case 3:
                return "NAMA KATEGORI";            
            case 4:
                return "SATUAN";
            case 5:
                return "HARGA";
            case 6:
                return "STOK";
            default:
                return null;
            
        }
    }
    
}


