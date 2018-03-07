
package Model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelDetailPesanan extends AbstractTableModel{

    List<DetailPesanan> list;
            
    public TableModelDetailPesanan(List<DetailPesanan> list){
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
                return list.get(rowIndex).getBarang().getKategoriBarang().getKdKategori();
            case 1:
                return list.get(rowIndex).getBarang().getKategoriBarang().getNmKategori();
            case 2:
                return list.get(rowIndex).getBarang().getKdBrg();
            case 3:
                return list.get(rowIndex).getBarang().getNmBrg();
            case 4:
                return list.get(rowIndex).getHrgPesan();
            case 5:
                return list.get(rowIndex).getJmlPesan();
            case 6:
                return list.get(rowIndex).getHrgPesan() * list.get(rowIndex).getJmlPesan();
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
            case 2:
                return "KODE BARANG";
            case 3:
                return "NAMA BARANG";
            case 4:
                return "HARGA";
            case 5:
                return "QTY";
            case 6:
                return "TOTAL";
            default:
                return null;
        }
    } 
}
