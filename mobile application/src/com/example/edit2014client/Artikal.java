package com.example.edit2014client;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class Artikal {
	private String naziv; 
	private String cijena;
	private String loyalty;
	private String opis;
	private String barkod;
	private String imgUrl;
	
    private ArtikalAdapter sta;
	
    public Artikal(String naziv, String cijena, String loyalty, String opis, String barkod, String url) {
    	this.naziv = naziv;
    	this.cijena = cijena;
    	this.loyalty = loyalty;
    	this.opis = opis;
    	this.barkod = barkod;
    	this.imgUrl = url;
    }
    
	public String getBarkod() {
		return barkod;
	}
	public void setBarkod(String barkod) {
		this.barkod = barkod;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getCijena() {
		return cijena;
	}
	public String getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(String loyalty) {
		this.loyalty = loyalty;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}


	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public ArtikalAdapter getSta() {
		return sta;
	}

	public void setSta(ArtikalAdapter sta) {
		this.sta = sta;
	}


	
}
