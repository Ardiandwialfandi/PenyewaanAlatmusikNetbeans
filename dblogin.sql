-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 10, 2021 at 04:52 AM
-- Server version: 10.1.22-MariaDB
-- PHP Version: 7.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dblogin1`
--

-- --------------------------------------------------------

--
-- Table structure for table `det_peminjaman`
--

CREATE TABLE `det_peminjaman` (
  `No_TransPeminjaman` varchar(30) NOT NULL,
  `Nama_Pelanggan` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `No_Hp` varchar(50) NOT NULL,
  `No_type` varchar(15) NOT NULL,
  `Jumlah_hari` varchar(15) NOT NULL,
  `Total_harga` int(15) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `det_peminjaman`
--

INSERT INTO `det_peminjaman` (`No_TransPeminjaman`, `Nama_Pelanggan`, `alamat`, `No_Hp`, `No_type`, `Jumlah_hari`, `Total_harga`, `status`) VALUES
('PMJMN/000001', 'Dodi', 'Parung,Bogor Jalan MekarSari BlokC5/4', '08935627816', 'F100C', '1', 272000, 'Tuntas'),
('PMJMN/000008', 'Yudi', 'Griya serpong indah blok c2/10', '08951972891', 'C315', '2', 100000, 'Tuntas'),
('PMJMN/000007', 'Adli', 'Bojonggede,Jalan kemuning BlokC2/9', '089265362888', 'Ib100C', '1', 1820000, 'Tuntas'),
('PMJMN/000004', 'Ari', 'Grand Depok City, Jalan Citayam Blok G50', '08929100178', 'C315', '2', 800000, 'Tuntas'),
('PMJMN/000005', 'Mandra', 'Duren Sawit Jalan Dul Blok 5', '08568938920', 'RG350DX', '2', 1000000, 'Tuntas'),
('PMJMN/000006', 'Ginting', 'Cibinong Jalan Raden Saleh No 32', '08967463782', 'SRMD 200', '1', 744000, 'Tuntas'),
('PMJMN/000009', 'Anisa', 'Bukit Waringin Blok C5/8 ', '08179278117', '61', '2', 1288000, 'Tuntas'),
('PMJMN/000010', 'Maya', 'Waringin elok bojonggede blokc5/9', '089628816289', 'DTX920K', '1', 1050000, 'Sedang Meminjam');

-- --------------------------------------------------------

--
-- Table structure for table `det_pengembalian`
--

CREATE TABLE `det_pengembalian` (
  `No_TransPengembalian` varchar(30) NOT NULL,
  `Nama_Pelanggan` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `No_Hp` varchar(30) NOT NULL,
  `No_type` varchar(50) NOT NULL,
  `Jenis_alat` varchar(50) NOT NULL,
  `Warna` varchar(50) NOT NULL,
  `KondisiAwal` varchar(25) NOT NULL,
  `Antar` int(15) NOT NULL,
  `Jumlah` int(4) NOT NULL,
  `jmlh_hari` varchar(5) NOT NULL,
  `total` int(15) NOT NULL,
  `Jumlah_total` int(15) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `det_pengembalian`
--

INSERT INTO `det_pengembalian` (`No_TransPengembalian`, `Nama_Pelanggan`, `alamat`, `No_Hp`, `No_type`, `Jenis_alat`, `Warna`, `KondisiAwal`, `Antar`, `Jumlah`, `jmlh_hari`, `total`, `Jumlah_total`, `status`) VALUES
('PGMBN/000001', 'Dodi', 'Parung,Bogor Jalan MekarSari BlokC5/4', '08935627816', 'F100C', 'GITAR', 'KUNING', 'Bagus', 100000, 1, '1', 272000, 172000, 'Tuntas'),
('PGMBN/000008', 'Adli', 'Bojonggede,Jalan kemuning BlokC2/9', '089265362888', 'Ib100C', 'Bazz Ibanez', 'VARIATIF', 'Bagus', 100000, 1, '1', 1820000, 820000, 'Tuntas'),
('PGMBN/000009', 'Yudi', 'Griya serpong indah blok c2/10', '08951972891', 'C315', 'Gitar Yamaha', 'Kuning', 'Bagus', 0, 1, '2', 100000, 50000, 'Tuntas'),
('PGMBN/000004', 'Ari', 'Grand Depok City, Jalan Citayam Blok G50', '08929100178', 'C315', 'Gitar Yamaha', 'Kuning', 'Bagus', 600000, 2, '2', 800000, 300000, 'Tuntas'),
('PGMBN/000005', 'Mandra', 'Duren Sawit Jalan Dul Blok 5', '08568938920', 'RG350DX', 'Ibanez Gitar Listrik', 'Putih', 'Bagus', 300000, 1, '2', 1000000, 0, 'Tuntas'),
('PGMBN/000007', 'Ginting', 'Cibinong Jalan Raden Saleh No 32', '08967463782', 'SRMD 200', 'BASS', 'MERAH', 'Bagus', 200000, 2, '1', 744000, 244000, 'Tuntas'),
('PGMBN/000010', 'Anisa', 'Bukit Waringin Blok C5/8 ', '08179278117', '61', 'KEYBOARD PIANO', 'HITAM', 'Aman', 200000, 2, '2', 1288000, 560000, 'Tuntas');

--
-- Triggers `det_pengembalian`
--
DELIMITER $$
CREATE TRIGGER `Barangkembali` BEFORE INSERT ON `det_pengembalian` FOR EACH ROW BEGIN
UPDATE tb_alatmusik SET stok=stok+new.Jumlah WHERE No_type=new.No_type;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tblogin`
--

CREATE TABLE `tblogin` (
  `id` varchar(50) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(40) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `status` varchar(30) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `no telp` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblogin`
--

INSERT INTO `tblogin` (`id`, `username`, `password`, `status`, `alamat`, `no telp`) VALUES
('111', 'admin2', '3', 'admin01', 'Griya Tajur Halang', '08962561782'),
('2017', 'Ardi', '0100', 'user', 'Griya', '0826627819');

-- --------------------------------------------------------

--
-- Table structure for table `tb_alatmusik`
--

CREATE TABLE `tb_alatmusik` (
  `No_type` varchar(15) NOT NULL,
  `Tanggal` varchar(15) NOT NULL,
  `Jenis_alat` varchar(50) NOT NULL,
  `Warna_alat` varchar(15) NOT NULL,
  `harga` int(15) NOT NULL,
  `Vendor` varchar(15) NOT NULL,
  `KondisiAwal` varchar(30) NOT NULL,
  `stok` int(4) NOT NULL,
  `status` varchar(25) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_alatmusik`
--

INSERT INTO `tb_alatmusik` (`No_type`, `Tanggal`, `Jenis_alat`, `Warna_alat`, `harga`, `Vendor`, `KondisiAwal`, `stok`, `status`) VALUES
('F100C', '2021-07-15', 'GITAR', 'KUNING', 172000, 'Yamaha', 'Bagus', 3, 'Tersedia'),
('SRMD 200', '2021-07-15', 'BASS', 'MERAH', 272000, 'Ibanez', 'Bagus', 10, 'Tersedia'),
('EXX725SP/C ', '2021-07-15', 'DRUM KIT', 'BIRU', 872000, 'Pearl', 'Bagus', 1, 'Tersedia'),
('61', '2021-07-15', 'KEYBOARD PIANO', 'HITAM', 272000, '61 KEY', 'Aman', 6, 'Tersedia'),
('Ib100C', '2021-07-15', 'Bazz Ibanez', 'VARIATIF', 1720000, 'Variatif', 'Bagus', 7, 'Tersedia'),
('002', '2021-07-16', 'STUDIO', 'VARIATIF', 40000, 'Ardiens Music', 'Sejuk', 1, 'Tersedia'),
('C315', '2021-07-16', 'Gitar Yamaha', 'Kuning', 50000, 'Yamaha', 'Bagus', 6, 'Tersedia'),
('C40', '2021-07-17', 'Yamaha GitarC40', 'Orange', 100000, 'Yamaha', 'Bagus', 5, 'Tersedia'),
('FS100C', '2021-07-17', 'Yamaha FS Hitam', 'Hitam', 150000, 'Yamaha', 'Bagus', 7, 'Tersedia'),
('Apx600li', '2021-07-18', 'Gitar Electrc Acstc', 'Hitam', 200000, 'Yamaha', 'Bagus', 6, 'Tersedia'),
('RG350DX', '2021-07-19', 'Ibanez Gitar Listrik', 'Putih', 350000, 'Ibanez', 'Bagus', 7, 'Tersedia'),
('DTX920K', '2021-07-19', 'Drum Yamaha', 'Hitam Putih', 950000, 'Yamaha', 'Bagus', 4, 'Tersedia');

-- --------------------------------------------------------

--
-- Table structure for table `tb_karyawan`
--

CREATE TABLE `tb_karyawan` (
  `Nik` varchar(20) NOT NULL,
  `Nama_kary` varchar(50) NOT NULL,
  `Alamat` varchar(100) NOT NULL,
  `No_Hp` varchar(13) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_karyawan`
--

INSERT INTO `tb_karyawan` (`Nik`, `Nama_kary`, `Alamat`, `No_Hp`) VALUES
('001', 'Andi', 'Depok', '0988769868'),
('003', 'Nurul', 'Bojonggede', '0868362781'),
('004', 'Rafii', 'Citayem', '08972637289'),
('005', 'Ardian', 'Griya tajur', '0895716256'),
('002', 'Anto', 'Purbalingga', '0895367281'),
('006', 'Anti', 'Bojonegoro', '0896736272'),
('007', 'Rianti', 'Depok baru', '089367283');

-- --------------------------------------------------------

--
-- Table structure for table `tb_pelanggan1`
--

CREATE TABLE `tb_pelanggan1` (
  `id_pelanggan` varchar(40) NOT NULL,
  `nama_pelanggan` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `status` varchar(25) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_pelanggan1`
--

INSERT INTO `tb_pelanggan1` (`id_pelanggan`, `nama_pelanggan`, `alamat`, `no_hp`, `status`) VALUES
('ID010406', 'Ginting', 'Cibinong Jalan Raden Saleh No 32', '08967463782', 'Proses'),
('ID010405', 'Mandra', 'Duren Sawit Jalan Dul Blok 5', '08568938920', 'Proses'),
('ID010404', 'Ari', 'Grand Depok City, Jalan Citayam Blok G50', '08929100178', 'Proses'),
('ID010403', 'Dodi', 'Parung,Bogor Jalan MekarSari BlokC5/4', '08935627816', 'Proses'),
('ID010408', 'Anisa', 'Bukit Waringin Blok C5/8 ', '08179278117', 'Proses'),
('ID010402', 'Adli', 'Bojonggede,Jalan kemuning BlokC2/9', '089265362888', 'Proses'),
('ID010407', 'Yudi', 'Griya serpong indah blok c2/10', '08951972891', 'Proses'),
('ID010409', 'Maya', 'Waringin elok bojonggede blokc5/9', '089628816289', 'Proses');

-- --------------------------------------------------------

--
-- Table structure for table `tb_peminjaman`
--

CREATE TABLE `tb_peminjaman` (
  `No_TransPeminjaman` varchar(30) NOT NULL,
  `tanggalPeminjaman` date NOT NULL,
  `tanggalPengembalian` date NOT NULL,
  `id_pelanggan` varchar(30) NOT NULL,
  `No_type` varchar(20) NOT NULL,
  `NIK` varchar(15) NOT NULL,
  `Nama_karyawan` varchar(30) NOT NULL,
  `Nama_pelanggan` varchar(30) NOT NULL,
  `Jenis_alat` varchar(20) NOT NULL,
  `Warna` varchar(20) NOT NULL,
  `HargaUnit` varchar(20) NOT NULL,
  `jmlh_hari` varchar(15) NOT NULL,
  `Jumlah` int(4) NOT NULL,
  `Antar` int(15) NOT NULL,
  `jmlh_total` int(15) NOT NULL,
  `bayar_Dp` int(15) NOT NULL,
  `sisa_bayar` int(15) NOT NULL,
  `KondisiAwal` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_peminjaman`
--

INSERT INTO `tb_peminjaman` (`No_TransPeminjaman`, `tanggalPeminjaman`, `tanggalPengembalian`, `id_pelanggan`, `No_type`, `NIK`, `Nama_karyawan`, `Nama_pelanggan`, `Jenis_alat`, `Warna`, `HargaUnit`, `jmlh_hari`, `Jumlah`, `Antar`, `jmlh_total`, `bayar_Dp`, `sisa_bayar`, `KondisiAwal`) VALUES
('PMJMN/000001', '2021-07-01', '2021-07-02', 'ID010403', 'F100C', '003', 'Nurul', 'Dodi', 'GITAR', 'KUNING', '172000', '1', 1, 100000, 272000, 100000, 172000, 'Bagus'),
('PMJMN/000008', '2021-08-05', '2021-08-07', 'ID010407', 'C315', '002', 'Anto', 'Yudi', 'Gitar Yamaha', 'Kuning', '50000', '2', 1, 0, 100000, 50000, 50000, 'Bagus'),
('PMJMN/000007', '2021-08-03', '2021-08-04', 'ID010402', 'Ib100C', '007', 'Rianti', 'Adli', 'Bazz Ibanez', 'VARIATIF', '1720000', '1', 1, 100000, 1820000, 1000000, 820000, 'Bagus'),
('PMJMN/000004', '2021-07-02', '2021-07-04', 'ID010404', 'C315', '007', 'Rianti', 'Ari', 'Gitar Yamaha', 'Kuning', '50000', '2', 2, 600000, 800000, 500000, 300000, 'Bagus'),
('PMJMN/000005', '2021-07-29', '2021-07-31', 'ID010405', 'RG350DX', '004', 'Rafii', 'Mandra', 'Ibanez Gitar Listrik', 'Putih', '350000', '2', 1, 300000, 1000000, 1000000, 0, 'Bagus'),
('PMJMN/000006', '2021-08-02', '2021-08-03', 'ID010406', 'SRMD 200', '005', 'Ardian', 'Ginting', 'BASS', 'MERAH', '272000', '1', 2, 200000, 744000, 500000, 244000, 'Bagus'),
('PMJMN/000009', '2021-08-05', '2021-08-07', 'ID010408', '61', '007', 'Rianti', 'Anisa', 'KEYBOARD PIANO', 'HITAM', '272000', '2', 2, 200000, 1288000, 1000000, 288000, 'Aman'),
('PMJMN/000010', '2021-08-06', '2021-08-07', 'ID010409', 'DTX920K', '002', 'Anto', 'Maya', 'Drum Yamaha', 'Hitam Putih', '950000', '1', 1, 100000, 1050000, 500000, 550000, 'Bagus');

--
-- Triggers `tb_peminjaman`
--
DELIMITER $$
CREATE TRIGGER `BarangPinjam` BEFORE INSERT ON `tb_peminjaman` FOR EACH ROW BEGIN
UPDATE tb_alatmusik SET stok=stok-new.Jumlah WHERE No_type=new.No_type;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_pengembalianalat`
--

CREATE TABLE `tb_pengembalianalat` (
  `No_TransPengembalian` varchar(30) NOT NULL,
  `No_TransPeminjaman` varchar(30) NOT NULL,
  `tanggalPeminjaman` varchar(15) NOT NULL,
  `tanggalPengembalian` date NOT NULL,
  `tanggalMengembalikan` date NOT NULL,
  `id_pelanggan` varchar(15) NOT NULL,
  `Nama_karyawan` varchar(15) NOT NULL,
  `Nama_Pelanggan` varchar(20) NOT NULL,
  `HargaUnit` int(20) NOT NULL,
  `keterlambatan` varchar(15) NOT NULL,
  `denda_terlambat` int(15) NOT NULL,
  `kondisi` varchar(25) NOT NULL,
  `denda_kerusakan` int(15) NOT NULL,
  `DP` int(15) NOT NULL,
  `Jumlah` int(4) NOT NULL,
  `jmlah_total` int(15) NOT NULL,
  `bayar` int(15) NOT NULL,
  `kembalian` int(15) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_pengembalianalat`
--

INSERT INTO `tb_pengembalianalat` (`No_TransPengembalian`, `No_TransPeminjaman`, `tanggalPeminjaman`, `tanggalPengembalian`, `tanggalMengembalikan`, `id_pelanggan`, `Nama_karyawan`, `Nama_Pelanggan`, `HargaUnit`, `keterlambatan`, `denda_terlambat`, `kondisi`, `denda_kerusakan`, `DP`, `Jumlah`, `jmlah_total`, `bayar`, `kembalian`) VALUES
('PGMBN/000001', 'PMJMN/000001', '2021-07-01', '2021-07-02', '2021-07-02', 'ID010403', 'Nurul', 'Dodi', 172000, '0', 0, 'Aman', 0, 100000, 1, 172000, 180000, 8000),
('PGMBN/000008', 'PMJMN/000007', '2021-08-03', '2021-08-04', '2021-08-04', 'ID010402', 'Rianti', 'Adli', 1720000, '0', 0, 'Aman', 0, 1000000, 1, 820000, 1000000, 180000),
('PGMBN/000007', 'PMJMN/000006', '2021-08-02', '2021-08-03', '2021-08-03', 'ID010406', 'Ardian', 'Ginting', 272000, '0', 0, 'Aman', 0, 500000, 2, 244000, 300000, 56000),
('PGMBN/000004', 'PMJMN/000004', '2021-07-02', '2021-07-04', '2021-07-04', 'ID010404', 'Rianti', 'Ari', 50000, '0', 0, 'Aman', 0, 500000, 2, 300000, 300000, 0),
('PGMBN/000005', 'PMJMN/000005', '2021-07-29', '2021-07-31', '2021-07-31', 'ID010405', 'Rafii', 'Mandra', 350000, '0', 0, 'Aman', 0, 1000000, 1, 0, 0, 0),
('PGMBN/000009', 'PMJMN/000008', '2021-08-05', '2021-08-07', '2021-08-07', 'ID010407', 'Anto', 'Yudi', 50000, '0', 0, 'Aman', 0, 50000, 1, 50000, 100000, 50000),
('PGMBN/000010', 'PMJMN/000009', '2021-08-05', '2021-08-07', '2021-08-08', 'ID010408', 'Rianti', 'Anisa', 272000, '1', 272000, 'Aman', 0, 1000000, 2, 560000, 600000, 40000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `det_peminjaman`
--
ALTER TABLE `det_peminjaman`
  ADD PRIMARY KEY (`No_TransPeminjaman`);

--
-- Indexes for table `det_pengembalian`
--
ALTER TABLE `det_pengembalian`
  ADD PRIMARY KEY (`No_TransPengembalian`),
  ADD UNIQUE KEY `No_faktur` (`No_TransPengembalian`);

--
-- Indexes for table `tblogin`
--
ALTER TABLE `tblogin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `password` (`password`),
  ADD UNIQUE KEY `username_3` (`username`),
  ADD UNIQUE KEY `username_4` (`username`),
  ADD UNIQUE KEY `username_5` (`username`),
  ADD UNIQUE KEY `username_6` (`username`),
  ADD UNIQUE KEY `username_7` (`username`),
  ADD UNIQUE KEY `username_8` (`username`),
  ADD UNIQUE KEY `username_9` (`username`),
  ADD UNIQUE KEY `username_10` (`username`),
  ADD KEY `username_2` (`username`),
  ADD KEY `password_2` (`password`);

--
-- Indexes for table `tb_alatmusik`
--
ALTER TABLE `tb_alatmusik`
  ADD PRIMARY KEY (`No_type`);

--
-- Indexes for table `tb_karyawan`
--
ALTER TABLE `tb_karyawan`
  ADD PRIMARY KEY (`Nik`);

--
-- Indexes for table `tb_pelanggan1`
--
ALTER TABLE `tb_pelanggan1`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indexes for table `tb_peminjaman`
--
ALTER TABLE `tb_peminjaman`
  ADD PRIMARY KEY (`No_TransPeminjaman`),
  ADD UNIQUE KEY `No_faktur` (`No_TransPeminjaman`);

--
-- Indexes for table `tb_pengembalianalat`
--
ALTER TABLE `tb_pengembalianalat`
  ADD PRIMARY KEY (`No_TransPengembalian`),
  ADD UNIQUE KEY `No_TransPengembalian` (`No_TransPengembalian`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
