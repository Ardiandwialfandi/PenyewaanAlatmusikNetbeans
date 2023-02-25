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
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.print.DocFlavor;
import javax.swing.table.TableModel;
/**
 *
 * @author Ardi01
 */
public class Transaksi extends javax.swing.JFrame {
 private Statement st;
    private Connection Con;
    private ResultSet Rsalatmusik;
    private ResultSet Rspelanggan1;
    private ResultSet RsDetail;
    private ResultSet Rsauto;
    private ResultSet RsTransaksi;
    private String Vtanggal="";
    private String Vtanggal1="";
    private String sql="";
    private ResultSet Rskary;
    String No_TransPem,id_pelanggan,Nama_Pelanggan,alamat,No_Hp,No_type,NIK,Nama_kary,Nama_pel,Jenis_alat,
            Warna,HargaUnit,status,KondisiAwal;
    int Jumlah,bayarDP, SisaBayar, jmlh_total,Deliv;
    int bnyakHari, jmlh_hari;

    private void auto(){
  try {
            //--> melakukan eksekusi query untuk mengambil data dari tabel
            sql = "SELECT MAX(RIGHT(No_TransPeminjaman,6)) AS NO FROM tb_peminjaman";
            Statement st = Con.createStatement();
            Rsauto = st.executeQuery(sql);
            while (Rsauto.next()) {
                if (Rsauto.first() == false) {
                    txtnotrans.setText("PMJMN/000001");
                } else {
                    Rsauto.last();
                    int auto_id = Rsauto.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int NomorJual = no.length();
                    //MENGATUR jumlah 0
                    for (int j = 0; j < 6 - NomorJual; j++) {
                        no = "0" + no;
                    }
                    txtnotrans.setText("PMJMN/" + no);
                }}
            Rsauto.close();
            st.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }}
    
    public Transaksi() {
        initComponents();
        Koneksi();
        PilihanAlatmusik();
        PilihanPelanggan();
        PilihanKaryawan();
        tampilTransaksi("SELECT * FROM `tb_peminjaman`");
        auto();
        this.setLocationRelativeTo(null);
    }
    private void tampilTransaksi(String sql){
    DefaultTableModel kolom = new DefaultTableModel();
        kolom.addColumn("No");
        kolom.addColumn("No TransPeminjaman");
        kolom.addColumn("Tanggal Peminjaman");
        kolom.addColumn("Tanggal Pengembalian");
        kolom.addColumn("id Pelanggan");
        kolom.addColumn("No Type");
        kolom.addColumn("Nama Karyawan");
        kolom.addColumn("Nama Pelanggan");
        kolom.addColumn("alamat");
        kolom.addColumn("No_Hp");
        kolom.addColumn("Jenis Alat");
        kolom.addColumn("Jumlah Hari");
        kolom.addColumn("Jumlah");
        kolom.addColumn("Antar");
        kolom.addColumn("Jumlah total");
        kolom.addColumn("Bayar dp");
        kolom.addColumn("Sisa bayar");
        kolom.addColumn("KondisiAwal");
        try{
            int i=1;
            st=Con.createStatement();
            RsTransaksi=st.executeQuery("SELECT * FROM tb_peminjaman JOIN det_peminjaman ON tb_peminjaman.No_TransPeminjaman=det_peminjaman.No_TransPeminjaman");
            while (RsTransaksi.next()){
                kolom.addRow(new Object[]{
                (""+i++),RsTransaksi.getString("No_TransPeminjaman"),
                    RsTransaksi.getString("tanggalPeminjaman"),
                    RsTransaksi.getString("tanggalPengembalian"),
                    RsTransaksi.getString("id_pelanggan"),
                    RsTransaksi.getString("No_type"),
                    RsTransaksi.getString("Nama_karyawan"),
                    RsTransaksi.getString("Nama_pelanggan"),
                    RsTransaksi.getString("alamat"),
                    RsTransaksi.getString("No_Hp"),
                    RsTransaksi.getString("Jenis_alat"),
                    RsTransaksi.getString("jmlh_hari"),
                    RsTransaksi.getString("Jumlah"),
                    RsTransaksi.getString("Antar"),
                    RsTransaksi.getString("jmlh_total"),
                    RsTransaksi.getString("bayar_Dp"),
                    RsTransaksi.getString("sisa_bayar"),
                    RsTransaksi.getString("KondisiAwal"),
            });
                tampilanTransaksi.setModel(kolom);
                tampilanTransaksi.enable(false);
                btn_tambah.requestFocus();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan Data \n"
                    +e.getMessage());}
}
 private void Koneksi (){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Con=DriverManager.getConnection("jdbc:mysql://localhost:3306/DBLogin","root","");
            System.out.println("Selamat, Koneksi Berhasil");
        }catch (Exception e) {
            System.out.println("Koneksi gagal"+e);}
    }
 private void Kosongkan (){
     txtnotrans.setText("");
     cmb_id.setSelectedIndex(0);
     cmb_notype.setSelectedIndex(0);
     cmb_Nik.setSelectedIndex(0);
     txt_karyawan.setText("");
     txt_pelanggan.setText("");
     txt_alamat.setText("");
     txt_NoHp.setText("");
     txt_jenis.setText("");
     txt_warna.setText("");
     txt_hrgsw.setText("");
     txt_jumlah.setText("");
     txt_totalhrg.setText("");
     txt_jumlahtot.setText("");
     txt_bayarDP.setText("");
     txt_sisabayar.setText("");
     txt_bynkhr.setText("");
     txt_jmlahari.setText("");
     delornodel.setText("");
     txt_jmlahari.setText("");
     txt_Stok.setText("");
     txt_Kondisiawal.setText("");
     txt_Status.setText("");
     txt_statpel.setText("");
 }

private void prosestambah(){
    try{
        DefaultTableModel tableModel=
                (DefaultTableModel)Grid.getModel();
    String[]data = new String [12];
            data[0] = String.valueOf(cmb_notype.getSelectedItem());
            data[1] = txt_jenis.getText();
            data[2] = txt_warna.getText();
            data[3] = txt_hrgsw.getText();
            data[4] = txt_pelanggan.getText();
            data[5] = txt_alamat.getText();
            data[6] = txt_NoHp.getText();
            data[7] = txt_bynkhr.getText();
            data[8] = txt_totalhrg.getText();
            data[9] = delornodel.getText();
            data[10]= txt_jumlah.getText();
            data[11]=txt_statpel.getText();
            tableModel.addRow(data);
    }catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
                "Gagal Tampilan \n"+e.getMessage());}
}
private void ProsesKurang(){
    try{
        DefaultTableModel model =(DefaultTableModel)Grid.getModel();
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
    int jlhtotal = 0, jmlhHari=0,del=0,Jum=0;
    int BanyakHari, TotalHarga,Deliv,Jumlah;
    TableModel tblmodel;
    tblmodel = Grid.getModel();
    for(int i=0; i<jumlahBaris; i++){
        BanyakHari = Integer.parseInt(tblmodel.getValueAt(i, 7).toString());
        jmlhHari=jmlhHari + BanyakHari;
        TotalHarga = Integer.parseInt(tblmodel.getValueAt(i, 8).toString());
        jlhtotal = jlhtotal+TotalHarga;
        Deliv= Integer.parseInt(tblmodel.getValueAt(i, 9).toString());
        del = del+Deliv;
        Jumlah= Integer.parseInt(tblmodel.getValueAt(i, 10).toString());
        Jum = Jum+Jumlah;
        txt_jmlahari.setText(String.valueOf(jmlhHari));
        txt_jumlahtot.setText(String.valueOf(jlhtotal*Jum+del));}
}
private void PilihanAlatmusik(){
    cmb_notype.removeAllItems();
    cmb_notype.addItem("Pilih");
    try{
        status = "Tersedia";
        String sql = "SELECT * FROM tb_alatmusik where status ='"+status+"' ";
        Statement st=Con.createStatement();
        Rsalatmusik=st.executeQuery(sql);
        while (Rsalatmusik.next()){
            String DaftarAlat=Rsalatmusik.getString("No_type");
            cmb_notype.addItem(DaftarAlat);}
    }catch (Exception e){
        JOptionPane.showMessageDialog(null, "Gagal Manampilkan No_type \n"+e.getMessage());}
}
private void PilihanPelanggan(){
    cmb_id.removeAllItems();
    cmb_id.addItem("Pilih");
    try{
        String sql = "SELECT * FROM tb_pelanggan1 ";
        Statement St= Con.createStatement();
        Rspelanggan1=St.executeQuery(sql);
        while (Rspelanggan1.next()){
            String DaftarBarang=Rspelanggan1.getString("id_pelanggan");
            cmb_id.addItem(DaftarBarang);}
    }catch (Exception e){
        JOptionPane.showMessageDialog(null, "Gagal Menampilkan id_pelanggan \n"+e.getMessage());}
}
private void PilihanKaryawan(){
    cmb_Nik.removeAllItems();
    cmb_Nik.addItem("Pilih");
    try{
        String sql = "SELECT * FROM tb_karyawan";
        Statement St= Con.createStatement();
        Rskary=St.executeQuery(sql);
        while (Rskary.next()){
            String Daftarkarya=Rskary.getString("Nik");
            cmb_Nik.addItem(Daftarkarya);
        }
    }catch (Exception e){
        JOptionPane.showMessageDialog(null, "Gagal Menampilkan Nik \n"+e.getMessage());
    }
}
private void banyakHari(){
int Harga, BanyakHari, TotalHarga;
        Harga = Integer.parseInt(txt_hrgsw.getText());
        BanyakHari = Integer.parseInt(txt_bynkhr.getText());
        TotalHarga = Harga * BanyakHari;
        txt_totalhrg.setText(String.valueOf(TotalHarga));
}
private void simpandetail(){
            int jumlahBaris = Grid.getRowCount();
        if( jumlahBaris==0){
            JOptionPane.showMessageDialog(rootPane, "Table Masih Kosong");
        }else{
            try{
                int i=0;
                while (i<jumlahBaris){
                    st.executeUpdate(" insert into det_peminjaman "
                    + "(No_TransPeminjaman,Nama_Pelanggan,alamat,No_Hp,No_type,Jumlah_hari,Total_harga,status)"
                    + "VALUES ('"+ txtnotrans.getText()+"',"
                    + "'"+ Grid.getValueAt(i, 4)+"',   "
                    + "'"+ Grid.getValueAt(i, 5)+"',   "
                    + "'"+ Grid.getValueAt(i, 6)+"',   "
                    + "'"+ Grid.getValueAt(i, 0)+"',   "        
                    + "'"+ Grid.getValueAt(i, 7)+"',   "
                    + "'"+ txt_jumlahtot.getText()+"', "
                    + "'"+ Grid.getValueAt(i, 11)+"')");
                    i++;
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan! ERROR :\n"+e.getMessage());
            }} 
}
private void ubah (){
    No_type = cmb_notype.getItemAt(cmb_notype.getSelectedIndex()).toString();
        Jenis_alat = txt_jenis.getText();
        status=txt_Status.getText();
        try{
            sql="UPDATE tb_alatmusik"
            + " set Jenis_alat='"+Jenis_alat+"', "
            + " status = '"+status+"' "
            + " WHERE No_type = '"+No_type +"' "
            ;
            st = Con.createStatement();
            st.execute(sql); 
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "error \n" +e.getMessage());
        }}
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
        txtnotrans = new javax.swing.JTextField();
        cmb_id = new javax.swing.JComboBox<>();
        cmb_notype = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        tanggal1 = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txt_hrgsw = new javax.swing.JTextField();
        txt_warna = new javax.swing.JTextField();
        txt_totalhrg = new javax.swing.JTextField();
        txt_jenis = new javax.swing.JTextField();
        txt_jumlah = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txt_Stok = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txt_Status = new javax.swing.JTextField();
        txt_Kondisiawal = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        Tmbh = new javax.swing.JButton();
        kurng = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_jumlahtot = new javax.swing.JTextField();
        txt_sisabayar = new javax.swing.JTextField();
        txt_bayarDP = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_kurang = new javax.swing.JButton();
        txt_bynkhr = new javax.swing.JTextField();
        Delivery = new javax.swing.JRadioButton();
        NoDelivery = new javax.swing.JRadioButton();
        delornodel = new javax.swing.JTextField();
        Delivery1 = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txt_jmlahari = new javax.swing.JTextField();
        btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_keluar1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tampilanTransaksi = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Grid = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmb_Nik = new javax.swing.JComboBox<>();
        txt_karyawan = new javax.swing.JTextField();
        txt_pelanggan = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_alamat = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_NoHp = new javax.swing.JTextField();
        txt_statpel = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("No Transaksi Peminjaman :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Tanggal Peminjaman :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("ID Pelanggan  :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("No type          :");

        tanggal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tanggalPropertyChange(evt);
            }
        });

        txtnotrans.setEnabled(false);

        cmb_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_idActionPerformed(evt);
            }
        });

        cmb_notype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_notypeActionPerformed(evt);
            }
        });

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

        tanggal1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tanggal1PropertyChange(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Tanggal Pengembalian :");

        jButton3.setText("Hitung");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
                    .addComponent(jLabel21))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnotrans)
                    .addComponent(tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_id, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_notype, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tanggal1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtnotrans, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tanggal1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_id, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_notype, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Jenis Alat Musik         :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Warna Dominan Alat :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Harga Sewa Alat        :");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Total Harga               :");

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        txt_jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyReleased(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Jumlah / Stok           :");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("/");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Kondisi Alat Musik      :");

        Tmbh.setText("+");
        Tmbh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TmbhActionPerformed(evt);
            }
        });

        kurng.setText("-");
        kurng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kurngActionPerformed(evt);
            }
        });

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
                            .addComponent(jLabel20))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_warna, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 3, Short.MAX_VALUE)
                                .addComponent(kurng)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Tmbh)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_Stok, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_hrgsw)
                            .addComponent(txt_jenis)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel23))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Kondisiawal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_totalhrg))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_warna, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_hrgsw, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kurng)
                        .addComponent(Tmbh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Stok, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Kondisiawal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_totalhrg, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)))
        );

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Jumlah Total :");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Sisa Bayar     :");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Bayar DP      :");

        txt_bayarDP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarDPKeyReleased(evt);
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
                        .addComponent(txt_jumlahtot, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(txt_bayarDP)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txt_sisabayar)
                        .addGap(9, 9, 9))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_jumlahtot, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txt_bayarDP, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_sisabayar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(0, 153, 153));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Banyak Hari/Jam :");

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

        txt_bynkhr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bynkhrKeyReleased(evt);
            }
        });

        Delivery.setText("Antar <10KM");
        Delivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeliveryActionPerformed(evt);
            }
        });

        NoDelivery.setText("Bawa Sendiri");
        NoDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoDeliveryActionPerformed(evt);
            }
        });

        Delivery1.setText("Antar >10KM");
        Delivery1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delivery1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_bynkhr)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btn_tambah)
                                .addGap(18, 18, 18)
                                .addComponent(btn_kurang)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Delivery, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(NoDelivery, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(delornodel)
                            .addComponent(Delivery1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Delivery)
                    .addComponent(Delivery1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NoDelivery)
                    .addComponent(delornodel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_bynkhr, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah)
                    .addComponent(btn_kurang))
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(0, 153, 153));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Jumlah Hari :");

        btn_simpan.setBackground(new java.awt.Color(0, 153, 153));
        btn_simpan.setForeground(new java.awt.Color(0, 51, 51));
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Simpan.png"))); // NOI18N
        btn_simpan.setText("SIMPAN");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
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

        btn_keluar.setBackground(new java.awt.Color(0, 153, 153));
        btn_keluar.setForeground(new java.awt.Color(0, 51, 51));
        btn_keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Keluar.png"))); // NOI18N
        btn_keluar.setText("KELUAR");
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/print.png"))); // NOI18N
        jButton2.setText("CETAK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btn_keluar1.setBackground(new java.awt.Color(0, 153, 153));
        btn_keluar1.setForeground(new java.awt.Color(0, 51, 51));
        btn_keluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Menu Utama.png"))); // NOI18N
        btn_keluar1.setText("Menu");
        btn_keluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluar1ActionPerformed(evt);
            }
        });

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_jmlahari, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_simpan)
                .addGap(16, 16, 16)
                .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(btn_keluar1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jmlahari)
                    .addComponent(jLabel15)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_keluar1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
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

        Grid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No type", "Jenis Alat Musik", "Warna Alat", "Harga Sewa Alat", "Nama Pelanggan", "Alamat", "No Handphone", "Banyak Hari", "Total Harga", "Delivery", "Jumlah Barang", "Status"
            }
        ));
        jScrollPane2.setViewportView(Grid);

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

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Nik Karyawan                 :");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Nama Karyawan             :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Nama Pelanggan            :");

        cmb_Nik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_NikActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Alamat                           :");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("No Handphone              :");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Status Pelanggan           :");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel6)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel24))
                .addGap(33, 33, 33)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_statpel)
                    .addComponent(cmb_Nik, 0, 213, Short.MAX_VALUE)
                    .addComponent(txt_karyawan)
                    .addComponent(txt_alamat, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_pelanggan, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_NoHp, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_Nik, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_NoHp, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_statpel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
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
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, 
                    " Yakin Ingin Keluar?","KONFIRMASI",
                    JOptionPane.YES_NO_OPTION);
            if (ok==0){
        System.exit(0);
    }//GEN-LAST:event_btn_keluarActionPerformed
    }
    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
    // TODO add your handling code here:
    No_TransPem = txtnotrans.getText();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Vtanggal=format.format(tanggal.getDate());
    Vtanggal1=format.format(tanggal1.getDate());
    id_pelanggan=cmb_id.getItemAt(cmb_id.getSelectedIndex()).toString();
    No_type=cmb_notype.getItemAt(cmb_notype.getSelectedIndex()).toString();
    NIK=cmb_Nik.getItemAt(cmb_Nik.getSelectedIndex()).toString();
    Nama_kary = txt_karyawan.getText();
    Nama_pel=txt_pelanggan.getText();
    Jenis_alat=txt_jenis.getText();
    Warna=txt_warna.getText();
    HargaUnit=txt_hrgsw.getText();
    jmlh_hari=Integer.parseInt(txt_jmlahari.getText());
    Jumlah=Integer.parseInt(txt_jumlah.getText());
    Deliv= Integer.parseInt(delornodel.getText());
    jmlh_total=Integer.parseInt(txt_jumlahtot.getText());
    bayarDP=Integer.parseInt(txt_bayarDP.getText());
    SisaBayar=Integer.parseInt(txt_sisabayar.getText());
    KondisiAwal=txt_Kondisiawal.getText();
    try{
        sql="INSERT INTO tb_peminjaman"
        +"(No_TransPeminjaman,tanggalPeminjaman,tanggalPengembalian, "
        +" id_pelanggan,No_type,NIK,Nama_karyawan,Nama_pelanggan,Jenis_alat,Warna,HargaUnit,jmlh_hari,Jumlah,Antar, "
        +" jmlh_total, bayar_Dp, Sisa_Bayar,KondisiAwal) VALUES"
        +"('"+ No_TransPem +"','"+Vtanggal+"',"
        +"'"+ Vtanggal1 +"','"+id_pelanggan+"',"
        +"'"+ No_type +"','"+NIK+"',"
        + "'"+Nama_kary+"','"+ Nama_pel + "',"
        + "'"+Jenis_alat+"','"+ Warna + "',"
        + "'"+HargaUnit+"','"+jmlh_hari+"',"
        + "'"+ Jumlah + "','"+ Deliv + "',"
        + "'" +jmlh_total+"','" + bayarDP+ "',"
        + "'"+ SisaBayar +"',"
        + "'"+ KondisiAwal+"')";
        st=Con.createStatement();
        st.execute(sql);
        simpandetail();
      tampilTransaksi("SELECT * FROM `tb_peminjaman`");
      total();
      ubah();
      Kosongkan();
      auto();
        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
    }catch (Exception e){
        JOptionPane.showMessageDialog(null, "error \n" +e.getMessage());       
    }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void cmb_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_idActionPerformed
        // TODO add your handling code here:
        try{
        sql="SELECT * FROM tb_pelanggan1 WHERE "
                + "id_pelanggan='" + cmb_id.getSelectedItem()+"'";
        st=Con.createStatement();
        Rspelanggan1=st.executeQuery(sql);
        while (Rspelanggan1.next()){
            txt_pelanggan.setText(Rspelanggan1.getString("nama_pelanggan"));
            txt_alamat.setText(Rspelanggan1.getString("alamat"));
            txt_NoHp.setText(Rspelanggan1.getString("no_hp"));
            txt_statpel.setText(Rspelanggan1.getString("status"));}
        txt_jumlah.setText("0");
    }catch (Exception e){
        System.out.println("error \n"+e);
    }                                       
    }//GEN-LAST:event_cmb_idActionPerformed

    private void cmb_notypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_notypeActionPerformed
        // TODO add your handling code here:
        try{
        sql="SELECT * FROM tb_alatmusik WHERE "
                + "No_type='" + cmb_notype.getSelectedItem()+"'";
        st=Con.createStatement();
        Rsalatmusik=st.executeQuery(sql);
        while (Rsalatmusik.next()){
            txt_jenis.setText(Rsalatmusik.getString("Jenis_alat"));
            txt_warna.setText(Rsalatmusik.getString("Warna_alat"));
            txt_hrgsw.setText(Rsalatmusik.getString("harga"));
            txt_Kondisiawal.setText(Rsalatmusik.getString("KondisiAwal"));
            txt_Stok.setText(Rsalatmusik.getString("stok"));
            txt_Status.setText(Rsalatmusik.getString("status"));
           banyakHari();
        }
    }catch (Exception e){
        System.out.println("error \n"+e);
    }
    }//GEN-LAST:event_cmb_notypeActionPerformed

    private void tanggalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tanggalPropertyChange
        // TODO add your handling code here:
        if (tanggal.getDate()!=null){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Vtanggal = format.format(tanggal.getDate());
        }
    }//GEN-LAST:event_tanggalPropertyChange

    private void txt_bayarDPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarDPKeyReleased
        // TODO add your handling code here:
        int jmlh_total, jmlah_bayar, kembalian;
        jmlh_total = Integer.parseInt(txt_jumlahtot.getText());
        jmlah_bayar = Integer.parseInt(txt_bayarDP.getText());
        kembalian = jmlh_total - jmlah_bayar  ;
        txt_sisabayar.setText(String.valueOf(kembalian));
    }//GEN-LAST:event_txt_bayarDPKeyReleased

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        if(txt_bynkhr.getText().length()==0){
            JOptionPane.showMessageDialog(null, 
                    "hari \n Masih Kosong");
            
            txt_bynkhr.requestFocus();
        }else{    
        }
            prosestambah();
            total();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
     Kosongkan();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_kurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kurangActionPerformed
        // TODO add your handling code here:
        ProsesKurang();
        total();
    }//GEN-LAST:event_btn_kurangActionPerformed

    private void cmb_NikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_NikActionPerformed
        // TODO add your handling code here:
        try{
        sql="SELECT * FROM tb_karyawan WHERE "
                + "Nik='" + cmb_Nik.getSelectedItem()+"'";
        st=Con.createStatement();
        Rskary=st.executeQuery(sql);
        while (Rskary.next()){
            txt_karyawan.setText(Rskary.getString("Nama_kary"));
        }
    }catch (Exception e){
        System.out.println("error \n"+e);
    }//GEN-LAST:event_cmb_NikActionPerformed
    }
    private void txt_bynkhrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bynkhrKeyReleased
        // TODO add your handling code here:
        banyakHari();
    }//GEN-LAST:event_txt_bynkhrKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new user().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_keluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluar1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new Menu().setVisible(true);
    }//GEN-LAST:event_btn_keluar1ActionPerformed

    private void DeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeliveryActionPerformed
        // TODO add your handling code here:
        if (Delivery.isSelected())
            NoDelivery.setSelected(false);
            Delivery1.setSelected(false);
            delornodel.setText("100000");
            txt_statpel.setText ("Sedang Meminjam");
            int jum, del,jumlah;
        jum = Integer.parseInt(txt_jumlah.getText());
        del = Integer.parseInt(delornodel.getText());
        jumlah = del* jum ;
        delornodel.setText(String.valueOf(jumlah));   
    }//GEN-LAST:event_DeliveryActionPerformed

    private void NoDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoDeliveryActionPerformed
        // TODO add your handling code here:
        if (NoDelivery.isSelected())
            Delivery.setSelected(false);
            Delivery1.setSelected(false);
        delornodel.setText("0");
        txt_statpel.setText ("Sedang Meminjam");
    }//GEN-LAST:event_NoDeliveryActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new Transaksi().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_jumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyReleased
        // TODO add your handling code here:
        status = txt_Status.getText();
        int Jumlah,stok,jumlah;
        Jumlah = Integer.parseInt(txt_jumlah.getText());
        stok = Integer.parseInt(txt_Stok.getText());
        
        if (Jumlah>=stok){
            txt_Status.setText("Disewa");
        }else if (Jumlah==stok){
            txt_Status.setText("Disewa");
        }else if (Jumlah<=stok){
            txt_Status.setText("Tersedia");
        }
    }//GEN-LAST:event_txt_jumlahKeyReleased

    private void tanggal1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tanggal1PropertyChange
        // TODO add your handling code here:
        if (tanggal1.getDate()!=null){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Vtanggal1 = format.format(tanggal1.getDate());
        
        }
    }//GEN-LAST:event_tanggal1PropertyChange

    private void Delivery1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delivery1ActionPerformed
        // TODO add your handling code here:
        if (Delivery1.isSelected())
            Delivery.setSelected(false);
            NoDelivery.setSelected(false);
        delornodel.setText("300000");
        txt_statpel.setText ("Sedang Meminjam");
        int jum, del,jumlah;
        jum = Integer.parseInt(txt_jumlah.getText());
        del = Integer.parseInt(delornodel.getText());
        jumlah = del* jum ;
        delornodel.setText(String.valueOf(jumlah));
    }//GEN-LAST:event_Delivery1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try{
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date tanggalpinjam = format.parse(Vtanggal);
            Date tanggalkembali = format.parse(Vtanggal1);
            long tanggalpinjam1 = tanggalpinjam.getTime();
            long tanggalkembali1 = tanggalkembali.getTime();
            long diff = tanggalkembali1 - tanggalpinjam1;
            long lama = diff / (24 * 60 * 60 * 1000);
            txt_bynkhr.setText (Long.toString(lama)+"");
        }catch (Exception e){
            System.out.println(""+e.getMessage());
              }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void TmbhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TmbhActionPerformed
        // TODO add your handling code here:
        int Jumlah,jum,stok,hitung;
        Jumlah = Integer.parseInt(txt_jumlah.getText());
        stok = Integer.parseInt(txt_Stok.getText());
        if (stok<=1){
            txt_Status.setText("Disewa");
        }
        jum = Jumlah + 1;
        hitung = stok - 1;
        txt_jumlah.setText(String.valueOf(jum));
        txt_Stok.setText(String.valueOf(hitung));
        
    }//GEN-LAST:event_TmbhActionPerformed

    private void kurngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kurngActionPerformed
        // TODO add your handling code here:
        int Jumlah,jum,stok,hitung;
        Jumlah = Integer.parseInt(txt_jumlah.getText());
        stok = Integer.parseInt(txt_Stok.getText());
        if (stok>=0){
            txt_Status.setText("Tersedia");
        }
        jum = Jumlah - 1;
        hitung = stok + 1;
        txt_jumlah.setText(String.valueOf(jum));
        txt_Stok.setText(String.valueOf(hitung));
    }//GEN-LAST:event_kurngActionPerformed
  

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
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Delivery;
    private javax.swing.JRadioButton Delivery1;
    private javax.swing.JTable Grid;
    private javax.swing.JRadioButton NoDelivery;
    private javax.swing.JButton Tmbh;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_keluar1;
    private javax.swing.JButton btn_kurang;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> cmb_Nik;
    private javax.swing.JComboBox<String> cmb_id;
    private javax.swing.JComboBox<String> cmb_notype;
    private javax.swing.JTextField delornodel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton kurng;
    private javax.swing.JTable tampilanTransaksi;
    private com.toedter.calendar.JDateChooser tanggal;
    private com.toedter.calendar.JDateChooser tanggal1;
    private javax.swing.JTextField txt_Kondisiawal;
    private javax.swing.JTextField txt_NoHp;
    private javax.swing.JTextField txt_Status;
    private javax.swing.JTextField txt_Stok;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_bayarDP;
    private javax.swing.JTextField txt_bynkhr;
    private javax.swing.JTextField txt_hrgsw;
    private javax.swing.JTextField txt_jenis;
    private javax.swing.JTextField txt_jmlahari;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_jumlahtot;
    private javax.swing.JTextField txt_karyawan;
    private javax.swing.JTextField txt_pelanggan;
    private javax.swing.JTextField txt_sisabayar;
    private javax.swing.JTextField txt_statpel;
    private javax.swing.JTextField txt_totalhrg;
    private javax.swing.JTextField txt_warna;
    private javax.swing.JTextField txtnotrans;
    // End of variables declaration//GEN-END:variables
}
