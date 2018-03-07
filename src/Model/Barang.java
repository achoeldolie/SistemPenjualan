
package Model;

public class Barang {
    private String kdBrg;
    private String nmBrg;
    private String satuan;
    private double hargaBrg;
    private int stok;
    private KategoriBarang kategoriBarang;

    public String getKdBrg() {
        return kdBrg;
    }

    public void setKdBrg(String kdBrg) {
        this.kdBrg = kdBrg;
    }

    public String getNmBrg() {
        return nmBrg;
    }

    public void setNmBrg(String nmBrg) {
        this.nmBrg = nmBrg;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public double getHargaBrg() {
        return hargaBrg;
    }

    public void setHargaBrg(double hargaBrg) {
        this.hargaBrg = hargaBrg;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public KategoriBarang getKategoriBarang() {
        return kategoriBarang;
    }

    public void setKategoriBarang(KategoriBarang kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }
          
}


