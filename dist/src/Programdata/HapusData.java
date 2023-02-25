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
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.print.DocFlavor;
import javax.swing.table.TableModel;
/**
 *
 * @author Ardi01
 */
public class HapusData extends javax.swing.JFrame {
private Statement st;
    private Connection Con;
   DefaultTableModel tabModel;
    private ResultSet Rsalatmusik;
    ResultSet Rspeminjaman = null; 
    ResultSet RsPengembalian = null; 
    ResultSet Rspelanggan = null;
    private String Vtanggal="";
    private String sql="";
    String No_TransPeminjaman,id_pelanggan,No_TransPengembalian,Status;
    /**
     * Creates new form HapusData
     */
    public HapusData() {
     initComponents();
        Koneksi();
        tampilData();
        tampilData1();
        tampilData2();
        this.setLocationRelativeTo(null);
    }
    private void Koneksi (){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Con=DriverManager.getConnection("jdbc:mysql://localhost:3306/DBLogin","root","");
            System.out.println("Selamat, Koneksi Berhasil");
            
        }catch (Exception e) {
            System.out.println("Koneksi gagal"+e);
        }}
    
    private void Kosongkan (){
     txtnotranspeng.setText("");
     txtpelanggan.getText();
     txtnamapelanggan.getText();
    }
    
private void tampilData() {
        try {
            Object[] judul_kolom = {"No TansPeminjaman","Nama Pelanggan","ID Pelanggan","Total","DP","Sisa Bayar","Status"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            tabelpeminjaman.setModel(tabModel);
            Con = (Connection) koneksi.connect();
            st = Con.createStatement();
            tabModel.getDataVector().removeAllElements();
            Rspeminjaman = st.executeQuery("SELECT * from `tb_peminjaman` JOIN det_peminjaman ON tb_peminjaman.No_TransPeminjaman = det_peminjaman.No_Transpeminjaman ");
            while (Rspeminjaman.next()) {
                Object[] data = {
                    Rspeminjaman.getString("No_TransPeminjaman"),
                    Rspeminjaman.getString("Nama_pelanggan"),
                    Rspeminjaman.getString("id_pelanggan"),
                    Rspeminjaman.getString("jmlh_total"),
                    Rspeminjaman.getString("bayar_Dp"),
                    Rspeminjaman.getString("sisa_bayar"),
                    Rspeminjaman.getString("status")
                };
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }}

    private void cariData(String key) {
        try {
            Object[] judul_kolom = {"No TansPeminjaman","Nama Pelanggan","ID Pelanggan","Total","DP","Sisa Bayar","Status"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            tabelpeminjaman.setModel(tabModel);
            Con = (Connection) koneksi.connect();
            st = Con.createStatement();
            tabModel.getDataVector().removeAllElements();
            Rspeminjaman = st.executeQuery("SELECT * from `tb_peminjaman` JOIN det_peminjaman ON tb_peminjaman.No_TransPeminjaman = det_peminjaman.No_Transpeminjaman WHERE "
                    + " status = '" + key + "'");
            while (Rspeminjaman.next()) {
                Object[] data = {
                    Rspeminjaman.getString("No_TransPeminjaman"),
                    Rspeminjaman.getString("Nama_pelanggan"),
                    Rspeminjaman.getString("id_pelanggan"),
                    Rspeminjaman.getString("jmlh_total"),
                    Rspeminjaman.getString("bayar_Dp"),
                    Rspeminjaman.getString("sisa_bayar"),
                    Rspeminjaman.getString("status")
                };
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }}
    
    private void tampilData1() {
        try {
            int i=1;
            Object[] judul_kolom = {"No", "ID Pelanggan", "Nama Pelanggan","Alamat","No HP", "Status"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            tabelpelanggan.setModel(tabModel);
            Con = (Connection) koneksi.connect();
            st = Con.createStatement();
            tabModel.getDataVector().removeAllElements();
            Rspelanggan = st.executeQuery("SELECT * FROM `tb_pelanggan1` ");
            while (Rspelanggan.next()) {
                Object[] data = {
                    (""+i++),Rspelanggan.getString("id_pelanggan"),
            Rspelanggan.getString("nama_pelanggan"),
            Rspelanggan.getString("alamat"),
            Rspelanggan.getString("no_hp"),
           Rspelanggan.getString("Status")};
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }}

    private void cariData1(String key) {
        try {
         int i=1;
            Object[] judul_kolom = {"No", "ID Pelanggan", "Nama Pelanggan","Alamat","No HP", "Status"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            tabelpelanggan.setModel(tabModel);
            Con = (Connection) koneksi.connect();
            st = Con.createStatement();
            tabModel.getDataVector().removeAllElements();
            Rspelanggan = st.executeQuery("SELECT * from `tb_pelanggan1` WHERE nama_pelanggan = '" + key + "'");
            while (Rspelanggan.next()) {
                Object[] data = {
                    (""+i++),Rspelanggan.getString("id_pelanggan"),
            Rspelanggan.getString("nama_pelanggan"),
            Rspelanggan.getString("alamat"),
            Rspelanggan.getString("no_hp"),
           Rspelanggan.getString("Status")};
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private void tampilData2() {
        try {
            Object[] judul_kolom = {"No TransPengembalian","No TransPeminjaman","Nama Pelanggan","ID Pelanggan","Jumlah Total","Bayar","Kembalian","Status"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            tabelpengembalian.setModel(tabModel);
            st = Con.createStatement();
            tabModel.getDataVector().removeAllElements();
            RsPengembalian = st.executeQuery("SELECT * FROM `tb_pengembalianalat` JOIN det_pengembalian ON "
                    + "tb_pengembalianalat.No_TransPengembalian=det_pengembalian.No_TransPengembalian ");
            while (RsPengembalian.next()) {
                Object[] data = {
                    RsPengembalian.getString("No_TransPengembalian"),
                    RsPengembalian.getString("No_TransPeminjaman"),
                    RsPengembalian.getString("Nama_Pelanggan"),
                    RsPengembalian.getString("id_pelanggan"),
                    RsPengembalian.getString("jmlah_total"),
                    RsPengembalian.getString("bayar"),
                    RsPengembalian.getString("kembalian"),
                    RsPengembalian.getString("status"),
                };
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }}

    private void cariData2(String key) {
        try {
            Object[] judul_kolom = {"No TransPengembalian","No TransPeminjaman","Nama Pelanggan","ID Pelanggan","Jumlah Total","Bayar","Kembalian","Status"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            tabelpengembalian.setModel(tabModel);
            st = Con.createStatement();
            tabModel.getDataVector().removeAllElements();
            RsPengembalian = st.executeQuery("SELECT * FROM `tb_pengembalianalat` JOIN det_pengembalian ON "
                    + "tb_pengembalianalat.No_TransPengembalian=det_pengembalian.No_TransPengembalian "
                    + " WHERE status = '" + key + "'");
            while (RsPengembalian.next()) {
                Object[] data = {
                    RsPengembalian.getString("No_TransPengembalian"),
                    RsPengembalian.getString("No_TransPeminjaman"),
                    RsPengembalian.getString("Nama_Pelanggan"),
                    RsPengembalian.getString("id_pelanggan"),
                    RsPengembalian.getString("jmlah_total"),
                    RsPengembalian.getString("bayar"),
                    RsPengembalian.getString("kembalian"),
                    RsPengembalian.getString("status"),
                };
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }}
    
private void Hapus() {
        No_TransPeminjaman = txtnotranspem.getText();
        try {
            st.executeUpdate("DELETE FROM tb_peminjaman "
                    + "WHERE No_TransPeminjaman='" + No_TransPeminjaman + "'");
            JOptionPane.showMessageDialog(null, "Data Peminjaman Dan Pengembalian Berhasil Dihapus", "Alert", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Hapus Data Gagal", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        tampilData();
    }

private void Hapuss() {
        No_TransPeminjaman = txtnotranspem.getText();
        try {
            st.executeUpdate("DELETE FROM det_peminjaman "
                    + "WHERE No_TransPeminjaman='" +  No_TransPeminjaman + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Hapus Data Gagal", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        tampilData();
    }

private void Hapus1() {
        id_pelanggan = txtpelanggan.getText();
        try {
            st.executeUpdate("DELETE FROM tb_pelanggan1 WHERE id_pelanggan='" + id_pelanggan + "'");
            JOptionPane.showMessageDialog(null, "Data Pelanggan Berhasil Dihapus", "Alert", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Hapus Data Gagal", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        tampilData1();
    }
private void Hapus2() {
        No_TransPengembalian = txtnotranspeng.getText();
        try {
            st.executeUpdate("DELETE FROM tb_pengembalianalat WHERE No_TransPengembalian='" + No_TransPengembalian + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Hapus Data Gagal", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        tampilData2();
    }
private void Hapuss2() {
        No_TransPengembalian = txtnotranspeng.getText();
        try {
            st.executeUpdate("DELETE FROM det_pengembalian WHERE No_TransPengembalian='" + No_TransPengembalian + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Hapus Data Gagal", "Alert", JOptionPane.ERROR_MESSAGE);
        }
        tampilData2();
    }
private void nama(){
    String key = txtnamapelanggan.getText();
        System.out.println(key);
        if (key != "") {
            cariData1(key);
        } else {
            tampilData1();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelpeminjaman = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtnotranspeng = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelpengembalian = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelpelanggan = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtpelanggan = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtnamapelanggan = new javax.swing.JTextField();
        btnHapus1 = new javax.swing.JButton();
        txtstatus = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtnotranspem = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnHapus2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        tabelpeminjaman.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelpeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelpeminjamanMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabelpeminjaman);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("No Transaksi Pengembalian : ");

        txtnotranspeng.setEnabled(false);

        tabelpengembalian.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelpengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelpengembalianMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabelpengembalian);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Transaksi Peminjaman");

        tabelpelanggan.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelpelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelpelangganMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tabelpelanggan);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID Pelanggan :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Data Pelanggan ");

        txtpelanggan.setEnabled(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Menu Utama.png"))); // NOI18N
        jButton1.setText("Menu Awal");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nama Pelanggan : ");

        txtnamapelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnamapelangganKeyReleased(evt);
            }
        });

        btnHapus1.setBackground(new java.awt.Color(255, 51, 0));
        btnHapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Hapus.png"))); // NOI18N
        btnHapus1.setText("Hapus Data ");
        btnHapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapus1ActionPerformed(evt);
            }
        });

        txtstatus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtstatusKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cari Status          : ");

        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Data Transaksi Pebgembalian");

        txtnotranspem.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("No Transaksi Peminjaman : ");

        btnHapus2.setBackground(new java.awt.Color(255, 51, 0));
        btnHapus2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Hapus.png"))); // NOI18N
        btnHapus2.setText("Hapus Data ");
        btnHapus2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapus2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnotranspeng, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnotranspem, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnamapelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(145, 145, 145)
                                .addComponent(btnHapus2)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(btnHapus1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(btnHapus1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtnotranspeng, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtnotranspem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtnamapelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapus2))))
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new admin().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabelpeminjamanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpeminjamanMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            txtnotranspem.setText(String.valueOf(tabelpeminjaman.getValueAt(tabelpeminjaman.getSelectedRow(), 0)));
            txtnamapelanggan.setText(String.valueOf(tabelpeminjaman.getValueAt(tabelpeminjaman.getSelectedRow(), 1)));
            nama();
        }   
    }//GEN-LAST:event_tabelpeminjamanMousePressed

    private void tabelpengembalianMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpengembalianMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            txtnotranspeng.setText(String.valueOf(tabelpengembalian.getValueAt(tabelpengembalian.getSelectedRow(), 0)));
            txtnotranspem.setText(String.valueOf(tabelpengembalian.getValueAt(tabelpengembalian.getSelectedRow(), 1)));
            txtnamapelanggan.setText(String.valueOf(tabelpengembalian.getValueAt(tabelpengembalian.getSelectedRow(), 2)));
            nama();
        }   
    }//GEN-LAST:event_tabelpengembalianMousePressed

    private void tabelpelangganMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpelangganMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            txtpelanggan.setText(String.valueOf(tabelpelanggan.getValueAt(tabelpelanggan.getSelectedRow(), 1)));
            txtnamapelanggan.setText(String.valueOf(tabelpelanggan.getValueAt(tabelpelanggan.getSelectedRow(), 2)));
        }
    }//GEN-LAST:event_tabelpelangganMousePressed

    private void txtnamapelangganKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamapelangganKeyReleased
        // TODO add your handling code here:
        nama();
    }//GEN-LAST:event_txtnamapelangganKeyReleased

    private void btnHapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapus1ActionPerformed
        // TODO add your handling code here:
          Hapus();
        Hapuss();
        Hapus2();
        Hapuss2();
        Kosongkan();
    }//GEN-LAST:event_btnHapus1ActionPerformed

    private void txtstatusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstatusKeyReleased
        // TODO add your handling code here:
        String key = txtstatus.getText();
        System.out.println(key);
        if (key != "") {
            cariData(key);
        } else {
            tampilData();
        }
        if (key != "") {
            cariData2(key);
        } else {
            tampilData2();
        }
    }//GEN-LAST:event_txtstatusKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new HapusData().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnHapus2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapus2ActionPerformed
        // TODO add your handling code here:
        Hapus1();
    }//GEN-LAST:event_btnHapus2ActionPerformed


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
            java.util.logging.Logger.getLogger(HapusData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HapusData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HapusData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HapusData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HapusData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus1;
    private javax.swing.JButton btnHapus2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tabelpelanggan;
    private javax.swing.JTable tabelpeminjaman;
    private javax.swing.JTable tabelpengembalian;
    private javax.swing.JTextField txtnamapelanggan;
    private javax.swing.JTextField txtnotranspem;
    private javax.swing.JTextField txtnotranspeng;
    private javax.swing.JTextField txtpelanggan;
    private javax.swing.JTextField txtstatus;
    // End of variables declaration//GEN-END:variables

    
}
