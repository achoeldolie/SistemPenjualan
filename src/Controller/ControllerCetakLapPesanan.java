
package Controller;

import View.CetakLapPesanan;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ControllerCetakLapPesanan {
    CetakLapPesanan form;
    
    public ControllerCetakLapPesanan(CetakLapPesanan form){
        this.form = form;
    }
    
    public void awal(){
        this.form.getJdtTanggalAwal().setDate(new Date());
        this.form.getJdtTanggalAkhir().setDate(new Date());
    }
    
    public void cetakPreview(){
        try{
            Connection conn = Koneksi.Database.KoneksiDB();
            String path = "src/Report/LaporanPesanan.jasper";
            Map<String,Object> parameter = new HashMap<String,Object>();
            InputStream file = new FileInputStream("resource/leaf_banner_green.png");
            parameter.put("GAMBAR", file);            
            parameter.put("TGL_AWAL",form.getJdtTanggalAwal().getDate());
            parameter.put("TGL_AKHIR",form.getJdtTanggalAkhir().getDate());

            JasperReport jp = (JasperReport) JRLoader.loadObject(path);
            JasperPrint print = JasperFillManager.fillReport(jp, parameter, conn);
            JasperViewer.viewReport(print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(form, "Tidak dapat Mencetak Laporan" + ex.getMessage(),
                    "Cetak Laporan", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    public void cetakExcel(){
        try{
            Connection conn = Koneksi.Database.KoneksiDB();
            String path = "src/Report/LaporanPesanan.jasper";
            File xlsx = new File("D:/Laporan Pesanan.xlsx");
            Map<String,Object> parameter = new HashMap<String,Object>();
            InputStream file = new FileInputStream("resource/leaf_banner_green.png");
            parameter.put("GAMBAR", file);            
            parameter.put("TGL_AWAL",form.getJdtTanggalAwal().getDate());
            parameter.put("TGL_AKHIR",form.getJdtTanggalAkhir().getDate());

            JasperPrint print = JasperFillManager.fillReport(path, parameter, conn);
            
            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE, xlsx);
            xlsxExporter.exportReport();
            JOptionPane.showMessageDialog(form, "Cek File pada drive D:/Laporan Pesanan.xlsx",
                    "Cetak Laporan", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(form, "Tidak dapat Mencetak Laporan" + ex.getMessage(),
                    "Cetak Laporan", JOptionPane.ERROR_MESSAGE);
        }
    }
}
