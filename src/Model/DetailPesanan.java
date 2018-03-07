
package Model;

public class DetailPesanan {
    private String noPesan; 
    private String kdBrg;
    private double hrgPesan;
    private int    jmlPesan;
    private Barang barang;

    public String getNoPesan() {
        return noPesan;
    }

    public void setNoPesan(String noPesan) {
        this.noPesan = noPesan;
    }

    public String getKdBrg() {
        return kdBrg;
    }

    public void setKdBrg(String kdBrg) {
        this.kdBrg = kdBrg;
    }

    public double getHrgPesan() {
        return hrgPesan;
    }

    public void setHrgPesan(double hrgPesan) {
        this.hrgPesan = hrgPesan;
    }

    public int getJmlPesan() {
        return jmlPesan;
    }

    public void setJmlPesan(int jmlPesan) {
        this.jmlPesan = jmlPesan;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }
    
    
    
}
