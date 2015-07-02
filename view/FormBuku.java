package view;

import model.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class FormBuku extends JInternalFrame{
  private Buku buku = new Buku();
  private KategoriBuku kategoriBuku = new KategoriBuku();
  private PenerbitBuku penerbitBuku = new PenerbitBuku();
  private FormLihatBuku formLihatBuku = new FormLihatBuku(null,true);
  
  private Object[][] dataKategoriBuku = null;
  private Object[][] dataPenerbitBuku = null;
  
  public FormBuku(){
    super("Master Data Buku", true, true);
    inisialisasiKomponen();	
  }
  
  private void inisialisasiKomponen(){
    setSize(550,500);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	panel = new JPanel();
	
	kodeBukuLabel = new JLabel("Kode Buku");
	kategoriBukuLabel = new JLabel("Kategori Buku");
	penerbitBukuLabel = new JLabel("Penerbit Buku");
	judulBukuLabel = new JLabel("Judul Buku");
	jumhalBukuLabel =new JLabel("Jumlah Halaman");
	deskBukuLabel = new JLabel("Deskripsi Buku");
	pengarangLabel = new JLabel("Pengarang");
	tahunTerbitLabel = new JLabel("Tahun Terbit");
	
	kodeBukuTextField = new JTextField();
	judulBukuTextField = new JTextField();
	jumhalBukuTextField = new JTextField();
	deskBukuTextField = new JTextField();
	pengarangTextField = new JTextField();
	tahunTerbitTextField = new JTextField();
	
	penerbitBukuCombobox = new JComboBox<String>();
	kategoriBukuComboBox = new JComboBox<String>();
	
	lihatButton = new JButton("Lihat");
	simpanButton = new JButton("Simpan");
	hapusButton = new JButton("Hapus");
	tutupButton = new JButton("Tutup");
	
	panel.setLayout(null);
	getContentPane().add(panel);
	
	panel.add(kodeBukuLabel);
	panel.add(kategoriBukuLabel);
	panel.add(penerbitBukuLabel);
	panel.add(judulBukuLabel);
	panel.add(jumhalBukuLabel);
	panel.add(deskBukuLabel);
	panel.add(pengarangLabel);
	panel.add(tahunTerbitLabel);
	
	panel.add(kodeBukuTextField);
	panel.add(judulBukuTextField);
	panel.add(kategoriBukuComboBox);
	panel.add(penerbitBukuCombobox);
	panel.add(jumhalBukuTextField);
	panel.add(deskBukuTextField);
	panel.add(pengarangTextField);
	panel.add(tahunTerbitTextField);
	
	panel.add(lihatButton);
	panel.add(simpanButton);
	panel.add(hapusButton);
	panel.add(tutupButton);
	
	kodeBukuLabel.setBounds(30,30,90,25);
	judulBukuLabel.setBounds(30,60,90,25);
	kategoriBukuLabel.setBounds(30,90,90,25);
	penerbitBukuLabel.setBounds(30,120,90,25);
	jumhalBukuLabel.setBounds(30,150,90,25);
	deskBukuLabel.setBounds(30,180,90,25);
	pengarangLabel.setBounds(30,210,90,25);
	tahunTerbitLabel.setBounds(30,240,90,25);
	
	kodeBukuTextField.setBounds(130,30,100,25);
	judulBukuTextField.setBounds(130,60,370,25);
	kategoriBukuComboBox.setBounds(130,90,250,25);
	penerbitBukuComboBox.setBounds(130,120,250,25);
	jumhalBukuTextField.setBounds(130,150,250,25);
	deskBukuTextField.setBounds(130,180,250,25);
	pengarangTextField.setBounds(130,210,250,25);
	tahunTerbitTextField.setBounds(130,240,250,25);
	
	lihatButton.setBounds(240,30,70,25);
	simpanButton.setBounds(30,180,100,25);
	hapusButton.setBounds(140,180,70,25);
	tutupButton.setBounds(240,180,70,25);
	
	addInternalFrameListener(new InternalFrameListener() {
	  public void internalFrameActivated(InternalFrameEvent evt) {
	    formInternalFrameActivated(evt);
	  }
	  
	  public void internalFrameClosed(InternalFrameEvent evt) {
	  }
	  
	  public void internalFrameClosing(InternalFrameEvent evt) {
	  }
	  
	  public void internalFrameDeactivated(InternalFrameEvent evt) {
	  }
	  
	  public void internalFrameDeiconified(InternalFrameEvent evt) {
	  }
	  
	  public void internalFrameIconified(InternalFrameEvent evt) {
	  }
	  
	  public void internalFrameOpened(InternalFrameEvent evt) {
	  }
	});
		
	kodeBukuTextField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        kodeBukuTextFieldKeyPressed(evt);
      }
    });
	
	lihatButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    lihatButtonActionPerformed(evt);
	  }
	});
	
	simpanButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    simpanButtonActionPerformed(evt);
	  }
	});
	
	hapusButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    hapusButtonActionPerformed(evt);
	  }
	});
	
	tutupButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent evt){
	    tutupButtonActionPerformed(evt);
	  }
	});
  }
  
  private void formInternalFrameActivated(InternalFrameEvent evt){
    kategoriBukuComboBox.removeAllItems();
	dataKategoriBuku = kategoriBuku.bacaData();
    if ((dataKategoriBuku != null) && (dataKategoriBuku.length > 0)){
	  for (int i=0; i<dataKategoriBuku.length; i++){
	    kategoriBukuComboBox.addItem(dataKategoriBuku[i][0].toString()+": "+dataKategoriBuku[i][1].toString());
	  }
	}
  }

  private void formInternalFrameActivated(InternalFrameEvent evt){
    penerbitBukuComboBox.removeAllItems();
	dataPenerbitBuku = penerbitBuku.bacaData();
    if ((dataPenerbitBuku != null) && (dataPenerbitBuku.length > 0)){
	  for (int i=0; i<dataPenerbitBuku.length; i++){
	    penerbitBukuComboBox.addItem(dataPenerbitBuku[i][0].toString()+": "+dataPenerbitBuku[i][1].toString());
	  }
	}
  }

  private void cariBuku(String kodeBuku){
    if (!kodeBuku.equals("")){
	  if (buku.baca(kodeBuku)){
	    judulBukuTextField.setText(buku.getJudulBuku());
		
		int i=0; 
		while ((i<dataKategoriBuku.length) && (!dataKategoriBuku[i][0].toString().equals(buku.getKodeKategoriBuku()))){
		  i++;
		}
		
		if ((i<dataKategoriBuku.length) && (dataKategoriBuku[i][0].toString().equals(buku.getKodeKategoriBuku()))){
		  kategoriBukuComboBox.setSelectedIndex(i);
		} else {
		  kategoriBukuComboBox.setSelectedIndex(0);
		}
		pengarangTextField.setText(buku.getPengarang());
	  } else {
	  	kategoriBukuComboBox.setSelectedIndex(0);
	  	penerbitBukuCombobox.setSelectedIndex(0);
	    judulBukuTextField.setText("");
		jumhalBukuTextField.setText("");
		deskBukuTextField.setText("");
		pengarangTextField.setText("");
		tahunTerbitTextField.setText("");
	  }
	}
  }
  
  private void kodeBukuTextFieldKeyPressed(KeyEvent evt){
    if (evt.getKeyCode()==KeyEvent.VK_ENTER){
	  if (!kodeBukuTextField.getText().equals("")){
	    cariBuku(kodeBukuTextField.getText());
	  }
	}
  }
  
  private void lihatButtonActionPerformed(ActionEvent evt){
    formLihatBuku.tampilkanData(buku.bacaData());
	formLihatBuku.setVisible(true);
	
	if (!formLihatBuku.getKodeDipilih().equals("")){
	  kodeBukuTextField.setText(formLihatBuku.getKodeDipilih());
	  cariBuku(kodeBukuTextField.getText());
	}
  }
  
  private void simpanButtonActionPerformed(ActionEvent evt){
    if (!kodeBukuTextField.getText().equals("")){
	  buku.setKodeBuku(kodeBukuTextField.getText());
	  buku.setKodeKategoriBuku(dataKategoriBuku[kategoriBukuComboBox.getSelectedIndex()][0].toString());
	  buku.setKodePenerbitBuku(dataPenerbitBuku[penerbitBukuComboBox.getSelectedIndex()][0].toString());
	  buku.setJudulBuku(judulBukuTextField.getText());
	  buku.setJumhalBuku(jumhalBukuTextField.getText());
	  buku.setDeskBuku(deskBukuTextField.getText());
	  buku.setPengarang(pengarangTextField.getText());
	  buku.setTahunTerbit(tahunTerbitTextField.getText());
	  
	  if (buku.simpan()){
	    kodeBukuTextField.setText("");
		judulBukuTextField.setText("");
		jumhalBukuTextField.setText("");
		deskBukuTextField.setText("");
		pengarangTextField.setText("");
		tahunTerbitTextField.setText("");
	  }
	} else {
	  JOptionPane.showMessageDialog(null,"Kode buku masih kosong");
	}
  }
  
  private void hapusButtonActionPerformed(ActionEvent evt){
    if (!kodeBukuTextField.getText().equals("")){
	  if (buku.hapus(kodeBukuTextField.getText())){
	    kodeBukuTextField.setText("");
		judulBukuTextField.setText("");
		jumhalBukuTextField.setText("");
		deskBukuTextField.setText("");
		pengarangTextField.setText("");
		tahunTerbitTextField.setText("");
	  }
	} else {
	  JOptionPane.showMessageDialog(null,"Kode buku masih kosong");
	}
  }
  
  private void tutupButtonActionPerformed(ActionEvent evt){
    dispose();
  }
  
  private JPanel panel;
  
  private JLabel kodeBukuLabel;
  private JLabel kategoriBukuLabel;
  private JLabel penerbitBukuLabel;
  private JLabel judulBukuLabel;
  private JLabel jumhalBukuLabel;
  private JLabel deskBukuLabel;
  private JLabel pengarangLabel;
  private JLabel tahunTerbitLabel;
  
  private JTextField kodeBukuTextField;
  private JTextField judulBukuTextField;
  private JTextField jumhalBukuTextField;
  private JTextField deskBukuTextField;
  private JTextField pengarangTextField;
  private JTextField tahunTerbitTextField;
  
  private JComboBox<String> kategoriBukuComboBox;
  private JComboBox<String> penerbitBukuComboBox;
  
  private JButton lihatButton;
  private JButton simpanButton;
  private JButton hapusButton;
  private JButton tutupButton;
}