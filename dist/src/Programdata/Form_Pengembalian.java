/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Programdata;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.print.DocFlavor;
import javax.swing.table.TableModel;
/**
 *
 * @author Ardi01
 */
public class Form_Pengembalian extends javax.swing.JFrame {
private Statement st;
    private Connection Con;
    DefaultTableModel tabModel;
    private ResultSet Rsalatmusik;
    private ResultSet Rspeminjaman;
    private ResultSet Rsauto;
    private ResultSet Rspelanggan;
    private ResultSet RsDetail;
    private ResultSet RsTransaksi;
    private String Vtanggal="";
    private String Vtanggal1="";
    private String sql="";
    private ResultSet Rskary;
    String No_TransPengembalian,tgl,No_TransPeminjaman,id_pelanggan,Nama_pelanggan,kondisibalik, Nama_karyawan,No_type,
            Jenis_alat,Warna,nama_pelanggan,Alamat,Status,nohp,Antar,HargaUnit,Hari,Jumlah,statalat,KondAwal;
    int bayar, kembalian,total, jmlah_total;
    int denda_kerusakan,denda_terlambat,keterlambatan,DP;
    /**
     * Creates new form Form_Pengembalian
     */
    private void auto(){
 try {
            sql = "SELECT MAX(RIGHT(No_TransPengembalian,6)) AS NO FROM tb_pengembalianalat";
            Statement st = Con.createStatement();
            Rsauto = st.executeQuery(sql);
            while (Rsauto.next()) {
                if (Rsauto.first() == false) {
                    txtNo_TransPengembalian.setText("PGMBN/000001");
                } else {
                    Rsauto.last();
                    int auto_id = Rsauto.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int NomorJual = no.length();
                    //MENGATUR jumlah 0
                    for (int j = 0; j < 6 - NomorJual; j++) {
                        no = "0" + no;
                    }
                    txtNo_TransPengembalian.setText("PGMBN/" + no);
                }
            }
            Rsauto.close();
            st.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }
    public Form_Pengembalian() {
        initComponents();
        Koneksi();
        auto();
        tabel("SELECT * FROM `tb_peminjaman`");
        tampilTransaksi("SELECT * FROM `tb_pengembalianalat`");
        this.setLocationRelativeTo(null);
    }
    
    private void tampilTransaksi(String sql){
    DefaultTableModel kolom = new DefaultTableModel();
        kolom.addColumn("No");
        kolom.addColumn("No_TransPengembalian");
        kolom.addColumn("No_TransPeminjaman");
        kolom.addColumn("tanggalMengembalikan");
        kolom.addColumn("id_pelanggan");
        kolom.addColumn("Nama pelanggan");
        kolom.addColumn("alamat");
        kolom.addColumn("No Hp");
        kolom.addColumn("Nama karyawan");
        kolom.addColumn("keterlambatan");
        kolom.addColumn("denda_terlambat");
        kolom.addColumn("kondisi Balik");
        kolom.addColumn("denda_kerusakan");
        kolom.addColumn("DP");
        kolom.addColumn("jumlah total");
        kolom.addColumn("bayar");
        kolom.addColumn("kembalian");
        try{
            int i=1;
            st=Con.createStatement();
            RsTransaksi=st.executeQuery("SELECT * FROM tb_pengembalianalat JOIN det_pengembalian ON tb_pengembalianalat.No_TransPengembalian=det_pengembalian.No_TransPengembalian");
            while (RsTransaksi.next()){
                kolom.addRow(new Object[]{
                (""+i++),RsTransaksi.getString("No_TransPengembalian"),
                    RsTransaksi.getString("No_TransPeminjaman"),
                    RsTransaksi.getString("tanggalMengembalikan"),
                    RsTransaksi.getString("id_pelanggan"),
                    RsTransaksi.getString("Nama_pelanggan"),
                    RsTransaksi.getString("alamat"),
                    RsTransaksi.getString("No_Hp"),
                    RsTransaksi.getString("Nama_karyawan"),
                    RsTransaksi.getString("keterlambatan"),
                    RsTransaksi.getString("denda_terlambat"),
                    RsTransaksi.getString("Kondisi"),
                    RsTransaksi.getString("denda_kerusakan"),
                    RsTransaksi.getString("DP"),
                    RsTransaksi.getString("Jumlah_total"),
                    RsTransaksi.getString("bayar"),
                    RsTransaksi.getString("kembalian"),
            });
                tampilanTransaksi.setModel(kolom);
                tampilanTransaksi.enable(false);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan Data \n"
                    +e.getMessage());
        }

}
private void Koneksi (){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Con=DriverManager.getConnection("jdbc:mysql://localhost:3306/DBLogin","root","");
            System.out.println("Selamat, Koneksi Berhasil");
        }catch (Exception e) {
            System.out.println("Koneksi gagal"+e);
        }
    }

private void tabel(String sql){
try {
            Object[] judul_kolom = {"No_TransPeminjaman", "id_pelanggan","No Type","tglPengembalian",
                "Nama Pelanggan", "Nama Karyawan","Jumlah total", "Bayar DP", "Sisa Bayar"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            table.setModel(tabModel);
            Con = (Connection) koneksi.connect();
            st = Con.createStatement();
            tabModel.getDataVector().removeAllElements();
            Rspeminjaman = st.executeQuery("SELECT * FROM `tb_peminjaman` ");
            while (Rspeminjaman.next()) {
                Object[] data = {
                    Rspeminjaman.getString("No_TransPeminjaman"),
                    Rspeminjaman.getString("id_pelanggan"),
                    Rspeminjaman.getString("No_type"),
                    Rspeminjaman.getString("tanggalPengembalian"),
                    Rspeminjaman.getString("Nama_pelanggan"),
                    Rspeminjaman.getString("Nama_karyawan"),
                    Rspeminjaman.getString("jmlh_total"),
                    Rspeminjaman.getString("bayar_Dp"),
                    Rspeminjaman.getString("sisa_bayar")
                };
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
}
private void tabell(String key){
try {
            Object[] judul_kolom = {"No_TransPeminjaman", "id_pelanggan","No Type","tglPengembalian",
                "Nama Pelanggan", "Nama Karyawan","Jumlah total", "Bayar DP", "Sisa Bayar"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            table.setModel(tabModel);
            Con = (Connection) koneksi.connect();
            st = Con.createStatement();
            tabModel.getDataVector().removeAllElements();
            Rspeminjaman = st.executeQuery("SELECT * FROM `tb_peminjaman`  WHERE Nama_Pelanggan = '"+key+"'");
            while (Rspeminjaman.next()) {
                Object[] data = {
                    Rspeminjaman.getString("No_TransPeminjaman"),
                    Rspeminjaman.getString("id_pelanggan"),
                    Rspeminjaman.getString("No_type"),
                    Rspeminjaman.getString("tanggalPengembalian"),
                    Rspeminjaman.getString("Nama_pelanggan"),
                    Rspeminjaman.getString("Nama_karyawan"),
                    Rspeminjaman.getString("jmlh_total"),
                    Rspeminjaman.getString("bayar_Dp"),
                    Rspeminjaman.getString("sisa_bayar")
                };
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
}
private void Kosongkan (){
     txtNo_TransPeminjaman.setText("");
     tanggalpeminjaman.setText("");
     txtid.setText("");
     txtnotype.setText("");
     txt_karyawan.setText("");
     txt_pelanggan.setText("");
     txt_alamat.setText("");
     txt_nohp.setText("");
     txt_antar.setText("");
     txt_statpel.setText("");
     txt_jenis.setText("");
     txt_warna.setText("");
     txt_lamapinjam.setText("");
     txt_hrgsw.setText("");
     txt_jumlah.setText("");
     txt_Stok.setText("");
     txt_Status.setText("");
     txt_kondisiawal.setText("");
     txt_total.setText("");
     txt_keterlambatan.setText("");
     txt_terlambat.setText("");
     txt_kondisi.setText("");
     txt_dendakerusakan.setText("");
     txt_DP.setText("");
     txt_jumlahtot.setText("");
     txt_jumlahbay.setText("");
     txt_kembalian.setText("");
     txt_kondisiawal.setText("");
     }

private void prosestambah(){
    try{
        DefaultTableModel tableModel=
                (DefaultTableModel)Grid.getModel();
    String[]data = new String [9];
            data[0] = txtid.getText();
            data[1] = txt_pelanggan.getText();
            data[2] = txtnotype.getText();
            data[3] = txt_jenis.getText();
            data[4] = txt_warna.getText();
            data[5] = txt_hrgsw.getText();
            data[6] = txt_keterlambatan.getText();
            data[7] = txt_jumlahtot.getText();
            data[8] = txt_statpel.getText();
            tableModel.addRow(data);
    }catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
                "Gagal Tampilan \n"+e.getMessage());
    }
}
private void prosesKurang(){
    try{
        DefaultTableModel model =
                (DefaultTableModel)Grid.getModel();
        int baris = Grid.getSelectedRow();
        if( baris>=0){
            int ok = JOptionPane.showConfirmDialog(null, 
                    " Yakin Data Ingin Dihapus?","KONFIRMASI",
                    JOptionPane.YES_NO_OPTION);
            if (ok==0){
                model.removeRow(baris);
            }
        }
            }catch(Exception e){
            }
}
private void total(){
    int jumlahBaris = Grid.getRowCount();
    int jlhtotal = 0;
    int  TotalHarga;
    TableModel tblmodel;
    tblmodel = Grid.getModel();
    for(int i=0; i<jumlahBaris; i++){
        TotalHarga = Integer.parseInt(tblmodel.getValueAt(i, 7).toString());
        jlhtotal = jlhtotal+TotalHarga;
        txt_jumlahtot.setText(String.valueOf(jlhtotal));
    }
}

private void simpandetail(){
        No_TransPengembalian = txtNo_TransPengembalian.getText();
        Nama_pelanggan = txt_pelanggan.getText();
        Alamat = txt_alamat.getText();
        nohp = txt_nohp.getText();
        No_type = txtnotype.getText();
        Jenis_alat = txt_jenis.getText();
        Warna = txt_warna.getText();
        KondAwal = txt_kondisiawal.getText();
        Antar = txt_antar.getText();
        Jumlah = txt_jumlah.getText();
        Hari = txt_lamapinjam.getText();
        total = Integer.parseInt(txt_total.getText());
        jmlah_total=Integer.parseInt(txt_jumlahtot.getText());
        Status = txt_statpel.getText();
        try{
            sql="INSERT INTO det_pengembalian"
        +"(No_TransPengembalian,Nama_pelanggan,alamat,No_Hp,No_type,"
        +" Jenis_alat,Warna,KondisiAwal,Antar,Jumlah,jmlh_hari,"
        +" total,Jumlah_total,status) VALUES"
        +"('"+ No_TransPengembalian +"','"+Nama_pelanggan+"',"
        +"'"+ Alamat +"','"+nohp+"',"
        +"'"+ No_type +"','"+Jenis_alat+"',"
        + "'"+Warna+"','"+ KondAwal + "',"
        + "'"+Antar+"','"+ Jumlah + "',"
        + "'"+Hari+"','"+total+"',"
        + "'"+ jmlah_total+"','"+Status+"')";
            st=Con.createStatement();
            st.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "error \n" +e.getMessage());
        }
}

private void ubah1(){
        No_type = txtnotype.getText();
        Jenis_alat = txt_jenis.getText();
        statalat=txt_Status.getText();
        try{
            sql="UPDATE tb_alatmusik"
            + " set Jenis_alat='"+Jenis_alat+"', "
            + " status = '"+statalat+"' "
            + " WHERE No_type = '"+No_type +"' ";
            st = Con.createStatement();
            st.execute(sql);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "error \n" +e.getMessage());
        }
}
private void ubah(){
        No_TransPeminjaman = txtNo_TransPeminjaman.getText();
        nama_pelanggan = txt_pelanggan.getText();
        Status=txt_statpel.getText();
        try{
            sql="UPDATE det_peminjaman"
            + " set Nama_Pelanggan='"+nama_pelanggan+"', "
            + " status = '"+Status+"' "
            + " WHERE No_TransPeminjaman = '"+No_TransPeminjaman +"' ";
            st = Con.createStatement();
            st.execute(sql);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "error \n" +e.getMessage());
        }
}
private void status (){
    No_TransPeminjaman = txtNo_TransPeminjaman.getText();
            try {
                sql = "SELECT * FROM det_peminjaman "
                + "WHERE No_TransPeminjaman='" + txtNo_TransPeminjaman.getText() + "'";
                st = Con.createStatement();
                RsDetail = st.executeQuery(sql);
                boolean ada = RsDetail.next();
                if(ada){
                   txt_statpel.setText(RsDetail.getString("status"));    
                } 
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null,"GAGAL CARI DATA\n"
                    + e.getMessage());
        }
}
private void id (){
    id_pelanggan = txtid.getText();

            //TxtKode.getText().Length==0
            try {
                sql = "SELECT * FROM tb_pelanggan1 "
                + "WHERE id_pelanggan='" + txtid.getText() + "'";
                st = Con.createStatement();
                Rspelanggan = st.executeQuery(sql);
                boolean ada = Rspelanggan.next();
                if(ada){
                   txt_alamat.setText(Rspelanggan.getString("alamat"));
                   txt_nohp.setText(Rspelanggan.getString("no_hp")); 
                } 
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null,"GAGAL CARI DATA\n"
                    + e.getMessage());
        }
}
private void notype(){
            try {
                sql = "SELECT * FROM tb_alatmusik "
                + "WHERE No_type='" + txtnotype.getText() + "'";
                st = Con.createStatement();
                Rsalatmusik = st.executeQuery(sql);
                boolean ada = Rsalatmusik.next();
                if(ada){
                   txt_Stok.setText(Rsalatmusik.getString("stok"));
                   txt_Status.setText(Rsalatmusik.getString("status"));    
                }
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null,"GAGAL CARI DATA\n"
                    + e.getMessage()); 
            }
}
private void Hitung(){
    int Harga, BanyakHari, HargaUnit;
        Harga = Integer.parseInt(txt_hrgsw.getText());
        BanyakHari = Integer.parseInt(txt_keterlambatan.getText());
        HargaUnit = Harga * BanyakHari;
        txt_terlambat.setText(String.valueOf(HargaUnit));
}
private void ket(){
     statalat = txt_Status.getText();
        int Jumlah,stok,jumlah;
        Jumlah = Integer.parseInt(txt_jumlah.getText());
        stok = Integer.parseInt(txt_Stok.getText());
        if (Jumlah>=stok){
            txt_Status.setText("Tersedia");
        }else if (Jumlah==stok){
            txt_Status.setText("Tersedia");
        }else if (Jumlah<=stok){
            txt_Status.setText("Tersedia");
        }
        jumlah = stok + Jumlah ;
        txt_Stok.setText(String.valueOf(jumlah)); 
}        
private void no(){
    No_TransPeminjaman = txtNo_TransPeminjaman.getText();
            try {
                sql = "SELECT * FROM tb_peminjaman "
                + "WHERE No_TransPeminjaman='" + txtNo_TransPeminjaman.getText() + "'";
                st = Con.createStatement();
                Rspeminjaman = st.executeQuery(sql);
                boolean ada = Rspeminjaman.next();
                if(ada){
                   tanggalpeminjaman.setText(Rspeminjaman.getString("tanggalPeminjaman"));
                   txtid.setText(Rspeminjaman.getString("id_pelanggan"));
                   txtnotype.setText(Rspeminjaman.getString("No_type"));
                   txt_karyawan.setText(Rspeminjaman.getString("Nama_karyawan"));
                   txt_pelanggan.setText(Rspeminjaman.getString("Nama_pelanggan"));
                   txt_pelanggan.setText(Rspeminjaman.getString("Nama_pelanggan"));
                   txt_antar.setText(Rspeminjaman.getString("Antar"));
                   txt_jenis.setText(Rspeminjaman.getString("Jenis_alat"));
                   txt_warna.setText(Rspeminjaman.getString("Warna"));
                   txt_hrgsw.setText(Rspeminjaman.getString("HargaUnit"));
                   txt_lamapinjam.setText(Rspeminjaman.getString("jmlh_hari"));
                   txt_jumlah.setText(Rspeminjaman.getString("Jumlah"));
                   txt_kondisiawal.setText(Rspeminjaman.getString("KondisiAwal"));
                   txt_total.setText(Rspeminjaman.getString("jmlh_total"));
                   txt_DP.setText(Rspeminjaman.getString("bayar_DP"));
                status();
                id();
                notype();    
                }
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null,"GAGAL CARI DATA\n"
                    + e.getMessage());
        }  
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tanggal = new com.toedter.calendar.JDateChooser();
        txtNo_TransPengembalian = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtid = new javax.swing.JTextField();
        txtnotype = new javax.swing.JTextField();
        tanggalmengembalikan = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        tanggalpeminjaman = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtNo_TransPeminjaman = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btn_hitung = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txt_hrgsw = new javax.swing.JTextField();
        txt_warna = new javax.swing.JTextField();
        txt_jenis = new javax.swing.JTextField();
        txt_lamapinjam = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_jumlah = new javax.swing.JTextField();
        txt_Stok = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txt_Status = new javax.swing.JTextField();
        txt_kondisiawal = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_jumlahtot = new javax.swing.JTextField();
        txt_kembalian = new javax.swing.JTextField();
        txt_jumlahbay = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txt_keterlambatan = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        btn_kurang = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tampilanTransaksi = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_karyawan = new javax.swing.JTextField();
        txt_pelanggan = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_nohp = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txt_statpel = new javax.swing.JTextField();
        txt_antar = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txt_terlambat = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_kondisi = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_dendakerusakan = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txt_DP = new javax.swing.JTextField();
        btn_keluar = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Grid = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 255, 255));
        jLabel5.setText("No.TransPengembalian  :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 255, 255));
        jLabel4.setText("Tanggal Pengembalian   :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 255, 255));
        jLabel3.setText("ID Pelanggan                 :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 255, 255));
        jLabel2.setText("No type                         :");

        tanggal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tanggalPropertyChange(evt);
            }
        });

        txtNo_TransPengembalian.setEnabled(false);

        jPanel5.setBackground(new java.awt.Color(0, 204, 204));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        tanggalmengembalikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tanggalmengembalikanPropertyChange(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 255, 255));
        jLabel15.setText("Tanggal Mengembalikan :");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 255, 255));
        jLabel25.setText("Tanggal Peminjaman     :");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 255, 255));
        jLabel16.setText("No.TransPeminjaman    :");

        btn_hitung.setBackground(new java.awt.Color(51, 51, 51));
        btn_hitung.setForeground(new java.awt.Color(255, 255, 255));
        btn_hitung.setText("HITUNG");
        btn_hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hitungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel15)
                    .addComponent(jLabel25)
                    .addComponent(jLabel16))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNo_TransPeminjaman)
                    .addComponent(tanggalpeminjaman)
                    .addComponent(txtid)
                    .addComponent(txtNo_TransPengembalian)
                    .addComponent(txtnotype)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tanggalmengembalikan, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_hitung))
                    .addComponent(tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtNo_TransPengembalian, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNo_TransPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tanggalpeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(tanggalmengembalikan, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hitung))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnotype, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 255, 255));
        jLabel7.setText("Jenis Alat Musik :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 255, 255));
        jLabel8.setText("Warna Dominan Alat :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 255, 255));
        jLabel9.setText("Harga Sewa Alat        :");

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 255, 255));
        jLabel22.setText("Lama Pinjam             :");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 255, 255));
        jLabel30.setText("Jumlah / Stok           :");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("/");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 255, 255));
        jLabel32.setText("Kondisi Pinjam Alat   :");

        jLabel24.setBackground(new java.awt.Color(102, 255, 255));
        jLabel24.setForeground(new java.awt.Color(102, 255, 255));
        jLabel24.setText("HARI/JAM");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 255, 255));
        jLabel21.setText("Total                        :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_warna)
                            .addComponent(txt_jenis)
                            .addComponent(txt_hrgsw)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_lamapinjam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel24))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(14, 14, 14)
                        .addComponent(txt_total))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel32))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_kondisiawal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_Stok, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_Status)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txt_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txt_warna, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txt_hrgsw, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_lamapinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Stok, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kondisiawal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 255, 255));
        jLabel11.setText("Jumlah Total :");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 255, 255));
        jLabel12.setText("Kembalian :");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 255, 255));
        jLabel13.setText("Jumlah Bayar :");

        txt_jumlahbay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlahbayKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txt_jumlahtot, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_kembalian)
                            .addComponent(txt_jumlahbay))
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_jumlahtot, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_jumlahbay, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(16, 16, 16))
        );

        jPanel7.setBackground(new java.awt.Color(0, 153, 153));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 255, 255));
        jLabel14.setText("Keterlambatan :");

        btn_tambah.setBackground(new java.awt.Color(51, 51, 51));
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setText("+");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_kurang.setBackground(new java.awt.Color(51, 51, 51));
        btn_kurang.setForeground(new java.awt.Color(255, 255, 255));
        btn_kurang.setText("-");
        btn_kurang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kurangActionPerformed(evt);
            }
        });

        jLabel19.setBackground(new java.awt.Color(102, 255, 255));
        jLabel19.setForeground(new java.awt.Color(102, 255, 255));
        jLabel19.setText("HARI/JAM");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_keterlambatan, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel19)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_kurang, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_keterlambatan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah)
                    .addComponent(btn_kurang))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tampilanTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tampilanTransaksi);

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Menu Utama.png"))); // NOI18N
        jButton1.setText("MENU AWAL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(0, 153, 153));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jPanel10.setBackground(new java.awt.Color(0, 204, 204));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 255, 255));
        jLabel17.setText("Nama Karyawan   :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 255, 255));
        jLabel6.setText("Nama Pelanggan  :");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 255, 255));
        jLabel27.setText("Alamat                 :");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 255, 255));
        jLabel28.setText("No Handphone    :");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 255, 255));
        jLabel29.setText("Status Menjadi     :");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 255, 255));
        jLabel33.setText("Biaya Antar          :");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(txt_karyawan))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txt_pelanggan))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_antar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_nohp)
                                    .addComponent(txt_statpel)
                                    .addComponent(txt_alamat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nohp, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(14, 14, 14)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txt_statpel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_antar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(0, 153, 153));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 255, 255));
        jLabel10.setText("Denda Terlambat          :");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 255));
        jLabel1.setText("Kondisi Kembali             :");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 255, 255));
        jLabel18.setText("Denda Kerusakan         :");

        txt_dendakerusakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dendakerusakanKeyReleased(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 255, 255));
        jLabel20.setText("DP Terbayar                 :");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel1)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_dendakerusakan, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(txt_kondisi)
                            .addComponent(txt_terlambat)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_DP)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_terlambat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kondisi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_dendakerusakan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_DP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap())
        );

        btn_keluar.setBackground(new java.awt.Color(0, 153, 153));
        btn_keluar.setForeground(new java.awt.Color(0, 51, 51));
        btn_keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Keluar.png"))); // NOI18N
        btn_keluar.setText("KELUAR");
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });

        btn_batal.setBackground(new java.awt.Color(0, 153, 153));
        btn_batal.setForeground(new java.awt.Color(0, 51, 51));
        btn_batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Batal.png"))); // NOI18N
        btn_batal.setText("BATAL");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        btn_simpan.setBackground(new java.awt.Color(0, 153, 153));
        btn_simpan.setForeground(new java.awt.Color(0, 51, 51));
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Simpan.png"))); // NOI18N
        btn_simpan.setText("SIMPAN");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        Grid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));
        Grid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID pelanggan", "Nama Pelanggan", "No Type", "Jenis Alat", "Warna", "Harga/Unit", "Terlambat", "Total", "Status Pelanggan"
            }
        ));
        jScrollPane2.setViewportView(Grid);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(table);

        jButton2.setBackground(new java.awt.Color(0, 153, 153));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/print.png"))); // NOI18N
        jButton2.setText("CETAK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 153, 153));
        jButton3.setText("REFRESH");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 255, 255));
        jLabel26.setText("NAMA PEMINJAM");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 255, 255));
        jLabel23.setText("CARI");

        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_simpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_keluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(1, 1, 1)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1)
                        .addGap(10, 10, 10))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        No_TransPengembalian = txtNo_TransPengembalian.getText();
        No_TransPeminjaman = txtNo_TransPeminjaman.getText();
        tgl = tanggalpeminjaman.getText();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Vtanggal=format.format(tanggal.getDate());
        Vtanggal1=format.format(tanggalmengembalikan.getDate());
        id_pelanggan= txtid.getText();
        Nama_karyawan= txt_karyawan.getText();
        Nama_pelanggan = txt_pelanggan.getText();
        HargaUnit = txt_hrgsw.getText();
        keterlambatan=Integer.parseInt(txt_keterlambatan.getText());
        denda_terlambat=Integer.parseInt(txt_terlambat.getText());
        kondisibalik = txt_kondisi.getText();
        denda_kerusakan=Integer.parseInt(txt_dendakerusakan.getText());
        DP=Integer.parseInt(txt_DP.getText());
        Jumlah = txt_jumlah.getText();
        jmlah_total=Integer.parseInt(txt_jumlahtot.getText());
        bayar=Integer.parseInt(txt_jumlahbay.getText());
        kembalian=Integer.parseInt(txt_kembalian.getText());
        try{
            sql="INSERT INTO `tb_pengembalianalat`(`No_TransPengembalian`, `No_TransPeminjaman`, "
            + "`tanggalPeminjaman`, `tanggalPengembalian`, "
            + "`tanggalMengembalikan`, `id_pelanggan`, "
            + "`Nama_karyawan`, `Nama_Pelanggan`, `HargaUnit`, "
            + "`keterlambatan`, `denda_terlambat`, `kondisi`, `denda_kerusakan`,"
            + " `DP`, `Jumlah`, `jmlah_total`, `bayar`, `kembalian`) VALUES"
            +"('"+ No_TransPengembalian +"','"+No_TransPeminjaman+"',"
            + "'"+ tgl +"','"+ Vtanggal +"',"
            + "'"+Vtanggal1+"','"+ id_pelanggan +"',"
            + "'"+Nama_karyawan+"','"+ Nama_pelanggan +"',"
            + "'"+ HargaUnit+"','"+ keterlambatan+"',"
            + "'"+denda_terlambat+"',"
            + "'"+ kondisibalik+"','"+denda_kerusakan+"',"
            + "'"+DP+"','"+ Jumlah+"',"
            + "'"+ jmlah_total+"','"+bayar+"',"
            + "'"+ kembalian+"')";
            st=Con.createStatement();
            st.execute(sql);
        }catch (Exception e){
           JOptionPane.showMessageDialog(null, "error \n" +e.getMessage());
        }
        simpandetail();
        tampilTransaksi("SELECT * FROM `tb_pengembalianalat`");
        ubah();
        ubah1();
        total();
        Kosongkan();
        auto();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        Kosongkan();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
int ok = JOptionPane.showConfirmDialog(null, 
                    " Yakin Ingin Keluar?","KONFIRMASI",
                    JOptionPane.YES_NO_OPTION);
            if (ok==0){
        System.exit(0);
            }
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
 

    private void btn_kurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kurangActionPerformed
        // TODO add your handling code here:
        prosesKurang();
        total();
    }//GEN-LAST:event_btn_kurangActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        txt_statpel.setText ("Tuntas");
        if(txt_keterlambatan.getText().length()==0){
            JOptionPane.showMessageDialog(null,
                "hari \n Masih Kosong");
            txt_keterlambatan.requestFocus();
        }else{}
        prosestambah();
        total();
        ket ();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_jumlahbayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahbayKeyReleased
        // TODO add your handling code here:
        int jmlh_total, jmlah_bayar,kembalian;
        jmlh_total = Integer.parseInt(txt_jumlahtot.getText());
        jmlah_bayar = Integer.parseInt(txt_jumlahbay.getText());
        kembalian = jmlah_bayar - jmlh_total ;
        txt_kembalian.setText(String.valueOf(kembalian));
    }//GEN-LAST:event_txt_jumlahbayKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new Reportpengembalian().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_dendakerusakanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dendakerusakanKeyReleased
        // TODO add your handling code here:
        int totaldenda, denda, jmlh_total,total,DP;
        totaldenda = Integer.parseInt(txt_terlambat.getText());
        denda = Integer.parseInt(txt_dendakerusakan.getText());
        DP = Integer.parseInt(txt_DP.getText());
        total = Integer.parseInt(txt_total.getText());
        jmlh_total = total + totaldenda  + denda  - DP ;
        txt_jumlahtot.setText(String.valueOf(jmlh_total));
    }//GEN-LAST:event_txt_dendakerusakanKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         this.setVisible(false);
        new Form_Pengembalian().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tanggalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tanggalPropertyChange
        // TODO add your handling code here:
        if (tanggal.getDate()!=null){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Vtanggal = format.format(tanggal.getDate());
        }
    }//GEN-LAST:event_tanggalPropertyChange

    private void tanggalmengembalikanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tanggalmengembalikanPropertyChange
        // TODO add your handling code here:
        if (tanggalmengembalikan.getDate()!=null){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Vtanggal1 = format.format(tanggalmengembalikan.getDate());
        }
    }//GEN-LAST:event_tanggalmengembalikanPropertyChange

    private void btn_hitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hitungActionPerformed
        // TODO add your handling code here:
        try{
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date tanggalpengembalian = format.parse(Vtanggal);
            Date tanggalkembali = format.parse(Vtanggal1);
            long tanggalpengembalian1 = tanggalpengembalian.getTime();
            long tanggalkembali1 = tanggalkembali.getTime();
            long diff = tanggalkembali1 - tanggalpengembalian1;
            long lama = diff / (24 * 60 * 60 * 1000);
            txt_keterlambatan.setText (Long.toString(lama)+"");
            Hitung();
        }catch (Exception e){
            System.out.println(""+e.getMessage());
              }
    }//GEN-LAST:event_btn_hitungActionPerformed

    private void tableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            txtNo_TransPeminjaman.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
        no();
        }
        try{
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            int index = table.getSelectedRow();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(index, 3));
            tanggal.setDate(date);
        } catch (ParseException ex) {
        Logger.getLogger(Form_Pengembalian.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_tableMousePressed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        // TODO add your handling code here:
        String key = txt_search.getText();
        System.out.println(key);
        if (key != "") {
            tabell(key);
        } else {
            tabel("select*from tb_peminjaman");
        }
    }//GEN-LAST:event_txt_searchKeyReleased
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Pengembalian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Grid;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_hitung;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_kurang;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable table;
    private javax.swing.JTable tampilanTransaksi;
    private com.toedter.calendar.JDateChooser tanggal;
    private com.toedter.calendar.JDateChooser tanggalmengembalikan;
    private javax.swing.JTextField tanggalpeminjaman;
    private javax.swing.JTextField txtNo_TransPeminjaman;
    private javax.swing.JTextField txtNo_TransPengembalian;
    private javax.swing.JTextField txt_DP;
    private javax.swing.JTextField txt_Status;
    private javax.swing.JTextField txt_Stok;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_antar;
    private javax.swing.JTextField txt_dendakerusakan;
    private javax.swing.JTextField txt_hrgsw;
    private javax.swing.JTextField txt_jenis;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_jumlahbay;
    private javax.swing.JTextField txt_jumlahtot;
    private javax.swing.JTextField txt_karyawan;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_keterlambatan;
    private javax.swing.JTextField txt_kondisi;
    private javax.swing.JTextField txt_kondisiawal;
    private javax.swing.JTextField txt_lamapinjam;
    private javax.swing.JTextField txt_nohp;
    private javax.swing.JTextField txt_pelanggan;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_statpel;
    private javax.swing.JTextField txt_terlambat;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_warna;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnotype;
    // End of variables declaration//GEN-END:variables
}
