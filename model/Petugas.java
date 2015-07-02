package model;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import javax.swing.JOptionPane;

public class Petugas{  
  private String kodePetugas, namaPetugas, alamatPetugas, password;
  private Koneksi koneksi = new Koneksi();
  
  public Petugas(){
    kodePetugas = "";
    namaPetugas = "";
	alamatPetugas = "";
  }
  
  public void setKodePetugas(String kodePetugas){
    this.kodePetugas = kodePetugas;
  }
  
  public void setNamaPetugas(String namaPetugas){
    this.namaPetugas = namaPetugas;
  }
  
  public void setAlamatPetugas(String alamatPetugas){
    this.alamatPetugas = alamatPetugas;
  }
  
  public void setPassword(String password){
    this.password = password;
  }
    
  public String getKodePetugas(){
    return kodePetugas;
  }
  
  public String getNamaPetugas(){
    return namaPetugas;
  }
  
  public String getAlamatPetugas(){
    return alamatPetugas;
  }
  
  public String getPassword(){
    return password;
  }
  
  public boolean simpan(){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    int jumlahSimpan=0; 
		boolean simpan = false; 
		
		try { 
		  String SQLStatemen = "select * from tb_petugas where id_petugas='"+kodePetugas+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    sta.close(); 
			rset.close(); 
			Object[] arrOpsi = {"Ya","Tidak"}; 
			int pilih=JOptionPane.showOptionDialog(null,"Kode petugas sudah ada\nApakah data diupdate?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,arrOpsi,arrOpsi[0]); 
			if (pilih==0){ 
			  simpan = true; 
			  SQLStatemen = "update tb_petugas set nama_petugas='"+namaPetugas+
			                "', alamat_petugas='"+alamatPetugas+ "', password='"+password+ 
							"' where id_petugas='"+kodePetugas+"'"; 
			  sta = connection.createStatement();
			  jumlahSimpan = sta.executeUpdate(SQLStatemen); 
			}
		  } else {
		    sta.close();
			rset.close(); 
			
			simpan = true; 
			SQLStatemen = "insert into tb_petugas values ('"+ kodePetugas +"','"+ 
			              namaPetugas+"','"+ alamatPetugas +"','"+ password +"')"; 
            sta = connection.createStatement(); 
			jumlahSimpan = sta.executeUpdate(SQLStatemen); 
		  }
		  
		  if (simpan) {
		    if (jumlahSimpan > 0){
			  JOptionPane.showMessageDialog(null,"Data petugas sudah tersimpan","Informasi",JOptionPane.INFORMATION_MESSAGE);
			} else {
			  adaKesalahan = true; 
			  JOptionPane.showMessageDialog(null,"Gagal menyimpan data petugas","Kesalahan",JOptionPane.ERROR_MESSAGE); 
			} 
		  } else {
		    adaKesalahan = true; 
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_petugas\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public boolean baca(String kodePetugas){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select * from tb_petugas where id_petugas='"+kodePetugas+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    kodePetugas = rset.getString("id_petugas"); 
		    namaPetugas = rset.getString("nama_petugas"); 
			alamatPetugas = rset.getString("alamat_Petugas"); 
			password = rset.getString("password"); 
			sta.close(); 
			rset.close(); 
		  } else {
		    sta.close();
			rset.close(); 
			adaKesalahan = true; 
			JOptionPane.showMessageDialog(null,"Kode petugas \""+kodePetugas+"\" tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_petugas\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public Object[][] bacaData(){
    boolean adaKesalahan = false;	
	Object[][] petugasList = new Object[0][0] ;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select id_petugas,nama_petugas from tb_petugas"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  rset.last();
		  if (rset.getRow()>0){
		    petugasList = new Object[rset.getRow()][2];
		    rset.first();
			int i=0;
			do { 
		      petugasList[i] = new Object[]{rset.getString("id_petugas"), rset.getString("nama_petugas")}; 		    
			  i++;
		    } while (rset.next());
		  }
		  
		  sta.close(); 
		  rset.close(); 
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_petugas\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return petugasList;
  }
  
  public boolean hapus(String kodePetugas){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    int jumlahHapus=0; 
		
		try { 
		  String SQLStatemen = "delete from tb_petugas where id_petugas='"+kodePetugas+"'"; 
		  Statement sta = connection.createStatement(); 
		  jumlahHapus = sta.executeUpdate(SQLStatemen);                    
		  
		  if (jumlahHapus>0){ 
		    sta.close(); 
			JOptionPane.showMessageDialog(null,"Data petugas sudah dihapus","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  } else {
		    sta.close();
			JOptionPane.showMessageDialog(null,"Kode petugas tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
			adaKesalahan = true;
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_petugas\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
}