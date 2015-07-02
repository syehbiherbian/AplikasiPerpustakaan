package model;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Peminjaman{  
  private String noPeminjaman;
  private String kodeAnggota;
  private String kodePetugas;
  private String waktuPeminjaman;
  private Object[][] peminjamanBuku;
  private String pesanError;
  private Koneksi koneksi = new Koneksi();
  
  public static String noPeminjamanDicari="";
  
  public Peminjaman(){
    noPeminjaman = "";
    peminjamanBuku=null;
	pesanError = "";
  }
  
  public void setNoPeminjaman(String noPeminjaman){
    this.noPeminjaman = noPeminjaman;
  }
  
  public void setKodeAnggota(String kodeAnggota){
    this.kodeAnggota = kodeAnggota;
  }
  
  public void setKodePetugas(String kodePetugas){
    this.kodePetugas = kodePetugas;
  }
  
  public void setWaktuPeminjaman(String waktuPeminjaman){
    this.waktuPeminjaman = waktuPeminjaman;
  }
  
  public void setPeminjamanBuku(Object[][] peminjamanBuku){
    this.peminjamanBuku = peminjamanBuku;
  }
  
  public String getNoPeminjaman(){
    return noPeminjaman;
  }
  
  public String getKodeAnggota(){
    return kodeAnggota;
  }
  
  public String getKodePetugas(){
    return kodePetugas;
  }
  
  public String getWaktuPeminjaman(){
    return waktuPeminjaman;
  }
  
  public Object[][] getPeminjamanBuku(){
    return peminjamanBuku;
  }
  
  public String getPesanError(){
    return pesanError;
  }
  
  public String buatNoBaru(){
    boolean adaKesalahan = false;	
	Connection connection = null;

    String noTerakhir = "KK-0000001";
	
	if ((connection = koneksi.getConnection()) != null){ 
	    try { 
		  String SQLStatemen = "select kode_peminjaman from tb_peminjaman order by kode_peminjaman desc limit 0,1"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    noTerakhir = rset.getString("kode_peminjaman");
			sta.close(); 
			rset.close();
			
			int no = 0;
			try {
			  no = Integer.parseInt(noTerakhir.substring(3, noTerakhir.length()))+1;
			} catch (Exception ex){}
			
			noTerakhir = noTerakhir.substring(0,3)+("0000000").substring(0,7-Integer.toString(no).length())+Integer.toString(no);
		  } else {
		    sta.close();
			rset.close(); 
		  }
		} catch (Exception ex){}
	}
	
	return noTerakhir;
  }
  
  public boolean simpan(){
    boolean adaKesalahan = false;
	Connection connection; 
	
	if ((connection = koneksi.getConnection()) != null){
	  int jumlahSimpan=0;
	  boolean simpan = false;
	  SimpleDateFormat waktu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  
	  try { 
	    String SQLStatemen = "select * from tb_peminjaman where kode_peminjaman='"+noPeminjaman+"'"; 
		Statement sta = connection.createStatement(); 
		ResultSet rset = sta.executeQuery(SQLStatemen);                    
		
		rset.next(); 
		if (rset.getRow()>0){
		  sta.close();
		  rset.close();
		  Object[] arrOpsi = {"Ya","Tidak"};
		  int pilih=JOptionPane.showOptionDialog(null,"No. peminjaman "+noPeminjaman+" sudah ada\nApakah data diupdate?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,arrOpsi,arrOpsi[0]); 
		  if (pilih==0){ 
		    simpan = true; 
			SQLStatemen = "update tb_peminjaman set kode_anggota='"+kodeAnggota+
			              "', kode_petugas='"+kodePetugas+
						  "', waktu_pinjam='"+waktu.format(new Date())+
						  "' where kode_peminjaman='"+noPeminjaman+"'"; 
			sta = connection.createStatement();
			jumlahSimpan = sta.executeUpdate(SQLStatemen); 
		  }
		} else {
		  sta.close();
		  rset.close();
		  
		  simpan = true;
		  SQLStatemen = "insert into tb_peminjaman(kode_peminjaman, kode_anggota, kode_petugas, waktu_pinjam) values ('"+ noPeminjaman +"','"+ 
			              kodeAnggota+"','"+ kodePetugas+"','"+ waktu.format(new Date()) +"')"; 
          sta = connection.createStatement(); 
		  jumlahSimpan = sta.executeUpdate(SQLStatemen); 
		}
		  
		if (simpan) {
		  try{
		    SQLStatemen = "delete from tb_detail_pinjam where kode_peminjaman='"+noPeminjaman+"'"; 
			sta = connection.createStatement();
			sta.executeUpdate(SQLStatemen);
		  } catch (Exception ex){}
		  
		  for (int i=0; i < peminjamanBuku.length; i++){
		    try {
			  SQLStatemen = "insert into tb_detail_pinjam(kode_peminjaman,kode_buku,detail_tgl_kembali, detail_denda, detail_status_kembali) values ('"+ noPeminjaman +"','"+ 
			  peminjamanBuku[i][0] + "','" + waktu.format(new Date()) + "','T')";
			  sta = connection.createStatement();
			  jumlahSimpan += sta.executeUpdate(SQLStatemen);
		    } catch (Exception ex){}
		  }
		}
	  } catch (Exception ex){
	    adaKesalahan = true; 
		JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel peminjaman\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
	  }
	} else {
	  adaKesalahan = true;
	  JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
        
    return !adaKesalahan;
  }
  
  public boolean baca(String noPeminjaman){
    boolean adaKesalahan = false;	
	peminjamanBuku = null;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select * from tb_peminjaman where kode_peminjaman='"+noPeminjaman+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    this.noPeminjaman = rset.getString("kode_peminjaman");
			this.kodeAnggota = rset.getString("kode_anggota"); 
			this.kodePetugas = rset.getString("kode_petugas"); 
		    this.waktuPeminjaman = rset.getString("waktu_pinjam"); 
			sta.close(); 
			rset.close(); 
			
			SQLStatemen = "select tb_detail_pinjam.kode_buku, tb_buku.judul_buku from tb_detail_pinjam inner join tb_buku on tb_detail_pinjam.kode_buku=tb_buku.kode_buku where kode_peminjaman='"+noPeminjaman+"'";  
			sta = connection.createStatement(); 
			rset = sta.executeQuery(SQLStatemen);                    
			
			rset.next(); 
			rset.last();
			if (rset.getRow() > 0){
			  peminjamanBuku = new Object[rset.getRow()][2];
			  rset.first();
			  int i=0;
			  do { 
			    peminjamanBuku[i] = new Object[]{rset.getString("tb_detail_pinjam.kode_buku"), rset.getString("tb_buku.judul_buku")}; 		    
			    i++;
			  } while (rset.next());
			}
			sta.close(); 
			rset.close(); 
		  } else {
		    sta.close();
			rset.close(); 
			adaKesalahan = true; 
			JOptionPane.showMessageDialog(null,"No. peminjaman \""+noPeminjaman+"\" tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tbpeminjaman\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public Object[][] bacaDaftar(){
    boolean adaKesalahan = false;	
	Object[][] daftarPeminjaman = new Object[0][0] ;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select tb_peminjaman.kode_peminjaman, tb_anggota.nama_anggota from tb_peminjaman inner join tb_anggota on tb_peminjaman.kode_anggota=tb_anggota.kode_anggota"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  rset.last();
		  if (rset.getRow() > 0){
		    daftarPeminjaman = new Object[rset.getRow()][2];
		    rset.first();
			int i=0;
			do { 
		      daftarPeminjaman[i] = new Object[]{rset.getString("tb_peminjaman.kode_peminjaman"), rset.getString("tb_anggota.nama_anggota")}; 		    
			  i++;
			} while (rset.next());
		  }
		  sta.close(); 
		  rset.close(); 
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tbpeminjaman dan tbanggota\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return daftarPeminjaman;
  }
  
}