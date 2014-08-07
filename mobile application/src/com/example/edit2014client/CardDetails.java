package com.example.edit2014client;

import android.annotation.SuppressLint;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class CardDetails {
	private String brojKartice;
	private String urlSlikeKupca;
	private String imeKupca;
	private String prezimeKupca;
	private String brojBodova;
	// private String noviBodovi;
	private String datumZadnjePromjene;

	public CardDetails(String brojKartice, String slika, String ime,
			String prezime, String brojBodova, String zadnjiDatum) {
		this.brojKartice = brojKartice;
		this.imeKupca = ime;
		this.prezimeKupca = prezime;
		this.datumZadnjePromjene = zadnjiDatum;
		this.urlSlikeKupca = slika;
		this.brojBodova = brojBodova;
	}

	// Konstruktor prima string koji treba parsirati
	public CardDetails(String text) {
		String[] podaci = new String[6];
		podaci = text.split("\\*"); // zbog regeksa
		this.brojKartice = podaci[0];
		this.urlSlikeKupca = podaci[1];
		this.imeKupca = podaci[2];
		this.prezimeKupca = podaci[3];
		this.brojBodova = podaci[4];
		this.datumZadnjePromjene = podaci[5];
	}

	// Kreiranje stringa za upis na karticu
	public String kreirajString(String noviPoeni) {
		int poeni = Integer.valueOf(brojBodova);
		poeni += Integer.valueOf(noviPoeni);
		String str = brojKartice + "*" + urlSlikeKupca + "*" + imeKupca + "*"
				+ prezimeKupca + "*" + String.valueOf(poeni) + "*";
		Date date = (Date) Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(date);
		str += today;
		return str;
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
