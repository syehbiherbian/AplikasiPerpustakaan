package model;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import javax.swing.JOptionPane;

public class Anggota{  
  private String kodeAnggota, namaAnggota, alamatAnggota, noTelp;
  private Koneksi koneksi = new Koneksi();
  
  public Anggota(){
    kodeAnggota = "";
    namaAnggota = "";
	alamatAnggota = "";
	noTelp = "";
  }
  
  public void setKodeAnggota(String kodeAnggota){
    this.kodeAnggota = kodeAnggota;
  }
  
  public void setNamaAnggota(String namaAnggota){
    this.namaAnggota = namaAnggota;
  }
  
  public void setAlamatAnggota(String alamatAnggota){
    this.alamatAnggota = alamatAnggota;
  }

  public void setNoTelp(String noTelp){
    this.noTelp = noTelp;
  }

  public String getKodeAnggota(){
    return kodeAnggota;
  }
  
  public String getNamaAnggota(){
    return namaAnggota;
  }
  
  public String getAlamatAnggota(){
    return alamatAnggota;
  }

  public String getNoTelp(){
    return noTelp;
  }  
  
  public boolean simpan(){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    int jumlahSimpan=0; 
		boolean simpan = false; 
		
		try { 
		  String SQLStatemen = "select * from tb_anggota where kode_anggota='"+kodeAnggota+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    sta.close(); 
			rset.close(); 
			Object[] arrOpsi = {"Ya","Tidak"}; 
			int pilih=JOptionPane.showOptionDialog(null,"Kode anggota sudah ada\nApakah data diupdate?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,arrOpsi,arrOpsi[0]); 
			if (pilih==0){ 
			  simpan = true; 
			  SQLStatemen = "update tb_anggota set nama_anggota='"+namaAnggota+
			                "', alamat='"+alamatAnggota+"', no_telp='"+noTelp+ 
							"' where kode_anggota='"+kodeAnggota+"'"; 
			  sta = connection.createStatement();
			  jumlahSimpan = sta.executeUpdate(SQLStatemen); 
			}
		  } else {
		    sta.close();
			rset.close(); 
			
			simpan = true; 
			SQLStatemen = "insert into tb_anggota values ('"+ kodeAnggota +"','"+ 
			              namaAnggota+"','"+ alamatAnggota +"','"+ noTelp +"')"; 
            sta = connection.createStatement(); 
			jumlahSimpan = sta.executeUpdate(SQLStatemen); 
		  }
		  
		  if (simpan) {
		    if (jumlahSimpan > 0){
			  JOptionPane.showMessageDialog(null,"Data anggota sudah tersimpan","Informasi",JOptionPane.INFORMATION_MESSAGE);
			} else {
			  adaKesalahan = true; 
			  JOptionPane.showMessageDialog(null,"Gagal menyimpan data anggota","Kesalahan",JOptionPane.ERROR_MESSAGE); 
			} 
		  } else {
		    adaKesalahan = true; 
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_anggota\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public boolean baca(String kodeAnggota){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select * from tb_anggota where kode_anggota='"+kodeAnggota+"'"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  if (rset.getRow()>0){ 
		    kodeAnggota = rset.getString("kode_anggota"); 
		    namaAnggota = rset.getString("nama_anggota"); 
			alamatAnggota = rset.getString("alamat");
			noTelp = rset.getString("no_telp"); 
			sta.close(); 
			rset.close(); 
		  } else {
		    sta.close();
			rset.close(); 
			adaKesalahan = true; 
			JOptionPane.showMessageDialog(null,"Kode anggota \""+kodeAnggota+"\" tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_anggota\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
  public Object[][] bacaData(){
    boolean adaKesalahan = false;	
	Object[][] anggotaList = new Object[0][0] ;
	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    try { 
		  String SQLStatemen = "select kode_anggota,nama_anggota from tb_anggota"; 
		  Statement sta = connection.createStatement(); 
		  ResultSet rset = sta.executeQuery(SQLStatemen);                    
		  
		  rset.next(); 
		  rset.last();
		  if (rset.getRow()>0){
		    anggotaList = new Object[rset.getRow()][2];
		    rset.first();
			int i=0;
			do { 
		      anggotaList[i] = new Object[]{rset.getString("kode_anggota"), rset.getString("nama_anggota")}; 		    
			  i++;
		    } while (rset.next());
		  }
		  
		  sta.close(); 
		  rset.close(); 
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_anggota\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return anggotaList;
  }
  
  public boolean hapus(String kodeAnggota){
    boolean adaKesalahan = false;	
	Connection connection; 
    if ((connection = koneksi.getConnection()) != null){
	    int jumlahHapus=0; 
		
		try { 
		  String SQLStatemen = "delete from tb_anggota where kode_anggota='"+kodeAnggota+"'"; 
		  Statement sta = connection.createStatement(); 
		  jumlahHapus = sta.executeUpdate(SQLStatemen);                    
		  
		  if (jumlahHapus>0){ 
		    sta.close(); 
			JOptionPane.showMessageDialog(null,"Data anggota sudah dihapus","Informasi",JOptionPane.INFORMATION_MESSAGE);
		  } else {
		    sta.close();
			JOptionPane.showMessageDialog(null,"Kode anggota tidak ditemukan","Informasi",JOptionPane.INFORMATION_MESSAGE);
			adaKesalahan = true;
		  }
		} catch (Exception ex){
		  adaKesalahan = true; 
		  JOptionPane.showMessageDialog(null,"Tidak dapat membuka tabel tb_anggota\n"+ex,"Kesalahan",JOptionPane.ERROR_MESSAGE);
		}
	} else {
      adaKesalahan = true;
      JOptionPane.showMessageDialog(null,"Tidak dapat melakukan koneksi ke server\n"+koneksi.getPesanKesalahan(),"Kesalahan",JOptionPane.ERROR_MESSAGE);
    }
	
	return !adaKesalahan;
  }
  
}