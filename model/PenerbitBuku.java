package model;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import javax.swing.JOptionPane;

public class Buku{  
  private String kodePenerbit, namaPenerbit, alamatPenerbit, telpPenerbit;
  private Koneksi koneksi = new Koneksi();
  
  public Buku(){
    kodePenerbit = "";
    namaPenerbit = "";
    alamatPenerbit = "";
    telpPenerbit = "";
  }
  
  public void setKodePenerbit(String kodePenerbit){
    this.kodePenerbit = kodePenerbit;
  }
  public void setNamaPenerbit(String namaPenerbit){
    this.namaPenerbit = namaPenerbit;
  }
  
  public void setAlamatPenerbit(String alamatPenerbit){
    this.alamatPenerbit = alamatPenerbit;
  }
  
  public void setTelpPenerbit(String telpPenerbit){
    this.telpPenerbit = telpPenerbit;
  }

  public String getKodePenerbit(){
    return kodePenerbit;
  }
  public String getKodeKategori(){
    return namaPenerbit;
  }
  public String getAlamatPenerbit(){
    return alamatPenerbit;
  }  
  public String getTelpPenerbit(){
    return telpPenerbit;
  }

  public boolean simpan(){
    boolean adaKesalahan = false;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    int jumlahSimpan=0; 
		boolean simpan = false; 
		
		try { 
		  String SQLStatemen = "select * from tb_penerbit where kode_penerbit='"+kodePenerbit+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    sta.close(); 
			rset.close(); 
			Object[] arrOpsi = {"Ya","Tidak"}; 
			int pilih=JOptionPane.showOptionDialog(null,"Kode penerbit sudah ada\nApakah data diupdate?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,arrOpsi,arrOpsi[0]); 
			if (pilih==0){ 
			  simpan = true; 
			  SQLStatemen = "update tb_penerbit set nama_penerbit='"+namaPenerbit+
			                "', alamat_penerbit='"+alamatPenerbit+"', telp_penerbit='"+telpPenerbit+ 
							"' where kode_penerbit='"+kodePenerbit+"'"; 
			  sta = connection.createStatement();
			  jumlahSimpan = sta.executeUpdate(SQLStatemen); 
			}
		  } else {
		    sta.close();
			rset.close(); 
			
			simpan = true; 
			SQLStatemen = "insert into tb_penerbit(kode_penerbit, nama_penerbit, alamat_penerbit, telp_penerbit) values ('"+ kodePenerbit +"','"+ namaPenerbit +"','"+ alamat_penerbit +"','"+ telpPenerbit +"')"; 
            sta = connection.createStatement(); 
			jumlahSimpan = sta.executeUpdate(SQLStatemen); 
		  }
		  
		  if (simpan) {
		    if (jumlahSimpan > 0){
			  JOptionPane.showMessageDialog(null,"Data penerbit sudah tersimpan","Informasi",JOptionPane.INFORMATION_MESSAGE);
			} else {
			  adaKesalahan = true; 
			  JOptionPane.showMessageDialog(null,"Gagal menyimpan data buku","Kesalahan",JOptionPane.ERROR_MESSAGE); 
			} 
		  } else {
		    adaKesalahan = true; 
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_penerbit\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public boolean baca(String kodePenerbit){
    boolean adaKesalahan = false;	
	
	this.kodePenerbit = "";
    this.namaPenerbit = "";
	this.alamatPenerbit = "";
	this.telpPenerbit = "";
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select * from tb_penerbit where kode_penerbit='"+kodePenerbit+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    this.kodePenerbit = rset.getString("kode_penerbit"); 
		    this.namaPenerbit = rset.getString("nama_penerbit"); 
			this.alamatPenerbit = rset.getString("alamat_penerbit");
			this.telpPenerbit = rset.getString("telp_penerbit");
			sta.close(); 
			rset.close(); 
		  } else {
		    sta.close();
			rset.close(); 
			adaKesalahan = true; 
			JOptionPane.showMessageDialog(null,"Kode penerbit \""+kodePenerbit+"\" tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_penerbit\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public Object[][] bacaData(){
    boolean adaKesalahan = false;	
	
	Object[][] penerbitList = new Object[0][0] ;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select kode_penerbit, nama_penerbit from tb_penerbit"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  rset.last();
		  if (rset.getRow() > 0){
		    penerbitList = new Object[rset.getRow()][2];
		    rset.first();
			int i=0;
			do { 
		      penerbitList[i] = new Object[]{rset.getString("kode_penerbit"), rset.getString("nama_penerbit")}; 		    
			  i++;
			} while (rset.next());
		  }
		  sta.close(); 
		  rset.close(); 
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_penerbit\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return penerbitList;
  }
  
  public boolean hapus(String kodePenerbit){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    int jumlahHapus=0; 
		
		try { 
		  String SQLStatemen = "delete from tb_penerbit where kode_penerbit='"+kodePenerbit+"'"; 
		  Statement sta = connection.createStatement(); 
		  jumlahHapus = sta.executeUpdate(SQLStatemen);                    
		  
		  if (jumlahHapus>0){ 
		    sta.close(); 
			JOptionPane.showMessageDialog(null,"Data penerbit sudah dihapus","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  } else {
		    sta.close();
			JOptionPane.showMessageDialog(null,"Kode penerbit tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
			adaKesalahan = true;
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_penerbit\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
}