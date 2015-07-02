package model;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import javax.swing.JOptionPane;

public class Buku{  
  private String kodeBuku, kodeKategori, kodePenerbit, judulBuku, jumhalBuku, deskBuku, pengarang, tahunTerbit;
  private Koneksi koneksi = new Koneksi();
  
  public Buku(){
    kodeBuku = "";
    kodeKategori = "";
    kodePenerbit = "";
    judulBuku = "";
    jumhalBuku = "";
    deskBuku = "";
	pengarang = "";
	tahunTerbit ="";
  }
  
  public void setKodeBuku(String kodeBuku){
    this.kodeBuku = kodeBuku;
  }
  public void setKodeKategori(String kodeKategori){
    this.kodeKategori = kodeKategori;
  }
  
  public void setKodePenerbit(String kodePenerbit){
    this.kodePenerbit = kodePenerbit;
  }
  
  public void setJudulBuku(String judulBuku){
    this.judulBuku = judulBuku;
  }
  
  public void setJumhalBuku(String jumhalBuku){
    this.jumhalBuku = jumhalBuku;
  }

  public void setDeskBuku(String deskBuku){
    this.deskBuku = deskBuku;
  }
  public void setPengarang(String pengarang){
    this.pengarang = pengarang;
  }

  public void setTahunTerbit(String tahunTerbit){
    this.tahunTerbit = tahunTerbit;
  }    
  public String getKodeBuku(){
    return kodeBuku;
  }
  public String getKodeKategori(){
    return kodeKategori;
  }
  public String getKodePenerbit(){
    return kodePenerbit;
  }  
  public String getJudulBuku(){
    return judulBuku;
  }

  public String getJumhalBuku(){
    return jumhalBuku;
  }

  public String getDeskBuku(){
    return deskBuku;
  }
  
  public String getPengarang(){
    return pengarang;
  }  
 
  public String getTahunTerbit(){
    return tahunTerbit;
  }

  public boolean simpan(){
    boolean adaKesalahan = false;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    int jumlahSimpan=0; 
		boolean simpan = false; 
		
		try { 
		  String SQLStatemen = "select * from tb_buku where kode_buku='"+kodeBuku+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    sta.close(); 
			rset.close(); 
			Object[] arrOpsi = {"Ya","Tidak"}; 
			int pilih=JOptionPane.showOptionDialog(null,"Kode buku sudah ada\nApakah data diupdate?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,arrOpsi,arrOpsi[0]); 
			if (pilih==0){ 
			  simpan = true; 
			  SQLStatemen = "update tb_buku set kode_kategori='"+kodeKategori+
			                "', kode_penerbit='"+kodePenerbit+ "', judul_buku='"+judulBuku+"', jumhal_buku='"+jumhalBuku+"', desk_buku='"+deskBuku+"', pengarang_buku='"+pengarang+"', tahun_terbit='"+tahunTerbit+ 
							"' where kode_buku='"+kodeBuku+"'"; 
			  sta = connection.createStatement();
			  jumlahSimpan = sta.executeUpdate(SQLStatemen); 
			}
		  } else {
		    sta.close();
			rset.close(); 
			
			simpan = true; 
			SQLStatemen = "insert into tb_buku(kode_buku, kode_kategori, kode_penerbit, judul_buku, jumhal_buku, desk_buku, pengarang_buku, tahun_terbit) values ('"+ kodeBuku +"','"+ 
			              kodeKategori+"','"+ kodePenerbit +"','"+ judulBuku +"','"+ jumhalBuku +"','"+ deskBuku +"','"+ pengarang +"','"+ tahunTerbit +"')"; 
            sta = connection.createStatement(); 
			jumlahSimpan = sta.executeUpdate(SQLStatemen); 
		  }
		  
		  if (simpan) {
		    if (jumlahSimpan > 0){
			  JOptionPane.showMessageDialog(null,"Data buku sudah tersimpan","Informasi",JOptionPane.INFORMATION_MESSAGE);
			} else {
			  adaKesalahan = true; 
			  JOptionPane.showMessageDialog(null,"Gagal menyimpan data buku","Kesalahan",JOptionPane.ERROR_MESSAGE); 
			} 
		  } else {
		    adaKesalahan = true; 
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tbbuku\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public boolean baca(String kodeBuku){
    boolean adaKesalahan = false;	
	
	this.kodeBuku = "";
    this.kodeKategori = "";
	this.kodePenerbit = "";
	this.judulBuku = "";
	this.jumhalBuku = "";
	this.deskBuku = "";
	this.pengarang = "";
	this.tahunTerbit = "";
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select * from tb_buku where kode_buku='"+kodeBuku+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    this.kodeBuku = rset.getString("kode_buku"); 
		    this.kodeKategori = rset.getString("kode_kategori"); 
			this.kodePenerbit = rset.getString("kode_penerbit");
			this.judulBuku = rset.getString("judul_buku");
			this.jumhalBuku = rset.getString("jumhal_buku");
			this.deskBuku = rset.getString("desk_buku");
			this.pengarang = rset.getString("pengarang_buku");
			this.tahunTerbit = rset.getString("tahun_terbit"); 
			sta.close(); 
			rset.close(); 
		  } else {
		    sta.close();
			rset.close(); 
			adaKesalahan = true; 
			JOptionPane.showMessageDialog(null,"Kode buku \""+kodeBuku+"\" tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_buku\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public Object[][] bacaData(){
    boolean adaKesalahan = false;	
	
	Object[][] bukuList = new Object[0][0] ;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select kode_buku,judul_buku from tb_buku"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  rset.last();
		  if (rset.getRow() > 0){
		    bukuList = new Object[rset.getRow()][2];
		    rset.first();
			int i=0;
			do { 
		      bukuList[i] = new Object[]{rset.getString("kode_buku"), rset.getString("judul_buku")}; 		    
			  i++;
			} while (rset.next());
		  }
		  sta.close(); 
		  rset.close(); 
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_buku\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return bukuList;
  }
  
  public boolean hapus(String kodeBuku){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    int jumlahHapus=0; 
		
		try { 
		  String SQLStatemen = "delete from tb_buku where kode_buku='"+kodeBuku+"'"; 
		  Statement sta = connection.createStatement(); 
		  jumlahHapus = sta.executeUpdate(SQLStatemen);                    
		  
		  if (jumlahHapus>0){ 
		    sta.close(); 
			JOptionPane.showMessageDialog(null,"Data buku sudah dihapus","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  } else {
		    sta.close();
			JOptionPane.showMessageDialog(null,"Kode buku tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
			adaKesalahan = true;
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_buku\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
}