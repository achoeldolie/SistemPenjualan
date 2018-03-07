
package Model;

import java.util.Date;
import java.util.List;

public class Pesanan {
    private String noPesan;
    private Date tglPesan;
    private String kdPlg;
    private Pelanggan pelanggan;
    private List<DetailPesanan> listDetailPesanan;

    public String getNoPesan() {
        return noPesan;
    }

    public void setNoPesan(String noPesan) {
        this.noPesan = noPesan;
    }

    public Date getTglPesan() {
        return tglPesan;
    }

    public void setTglPesan(Date tglPesan) {
        this.tglPesan = tglPesan;
    }

    public String getKdPlg() {
        return kdPlg;
    }

    public void setKdPlg(String kdPlg) {
        this.kdPlg = kdPlg;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public List<DetailPesanan> getListDetailPesanan() {
        return listDetailPesanan;
    }

    public void setListDetailPesanan(List<DetailPesanan> listDetailPesanan) {
        this.listDetailPesanan = listDetailPesanan;
    }

}

