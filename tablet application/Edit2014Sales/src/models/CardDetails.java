package models;

import java.sql.Date;

public class CardDetails {

	private String brojKartice;
	private String imeKupca;
	private String prezimeKupca;
	private String datumZadnjePromjene;
	private String urlSlikeKupca;
	private String brojBodova;
	
	public CardDetails(String brojKartice, String imeKupca,
			String prezimeKupca, String datumZadnjePromjene,
			String urlSlikeKupca, String brojBodova) {
		super();
		
		this.brojKartice = brojKartice;
		this.imeKupca = imeKupca;
		this.prezimeKupca = prezimeKupca;
		this.datumZadnjePromjene = datumZadnjePromjene;
		this.urlSlikeKupca = urlSlikeKupca;
		this.brojBodova = brojBodova;
	}
	
	public String UpisKartice () {
		return brojKartice + "*" + urlSlikeKupca + "*" + imeKupca + "*" + prezimeKupca + "*" + brojBodova + "*" + datumZadnjePromjene;
	}
	
	
	public String getBrojKartice() {
		return brojKartice;
	}
	public void setBrojKartice(String brojKartice) {
		this.brojKartice = brojKartice;
	}
	public String getImeKupca() {
		return imeKupca;
	}
	public void setImeKupca(String imeKupca) {
		this.imeKupca = imeKupca;
	}
	public String getPrezimeKupca() {
		return prezimeKupca;
	}
	public void setPrezimeKupca(String prezimeKupca) {
		this.prezimeKupca = prezimeKupca;
	}
	public String getDatumZadnjePromjene() {
		return datumZadnjePromjene;
	}
	public void setDatumZadnjePromjene(String datumZadnjePromjene) {
		this.datumZadnjePromjene = datumZadnjePromjene;
	}
	public String getUrlSlikeKupca() {
		return urlSlikeKupca;
	}
	public void setUrlSlikeKupca(String urlSlikeKupca) {
		this.urlSlikeKupca = urlSlikeKupca;
	}
	public String getBrojBodova() {
		return brojBodova;
	}
	public void setBrojBodova(String brojBodova) {
		this.brojBodova = brojBodova;
	}

}
