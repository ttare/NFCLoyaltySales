package com.example.edit2014client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.koushikdutta.ion.Ion;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListaArtikala extends ListActivity {
	private List<Artikal> listaArtikala;
	private ArtikalAdapter sta;
	public String brojBodova;
	String jsonString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista);
		
		Bundle bundle = getIntent().getExtras();
		brojBodova = bundle.getString("bodovi");
		
		
		InputStream is = getResources().openRawResource(R.raw.restoran);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
		    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    int n;
		    while ((n = reader.read(buffer)) != -1) {
		        writer.write(buffer, 0, n);
		    }
		    		    
		} catch(Exception ex){
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		jsonString = writer.toString();
		
		   	
		
		listaArtikala = new ArrayList<Artikal>();
		initList();
		
		sta = new ArtikalAdapter(ListaArtikala.this.getBaseContext(), listaArtikala, Float.parseFloat(brojBodova));
		
        // SET AS CURRENT LIST
        setListAdapter(sta);
        
        
        sta.notifyDataSetChanged(); 
	}
	
	@Override
	protected void onListItemClick (ListView l, View v, int pozicija, long id) {
		final Artikal a = listaArtikala.get(pozicija);
		final Dialog dialog = new Dialog(this);
		dialog.setTitle(a.getNaziv());
		
		LayoutInflater inflater = getLayoutInflater();
		
		dialog.setContentView(inflater.inflate(R.layout.custom_dialog, null));
		
		
		TextView naziv = (TextView)dialog.findViewById(R.id.twNaziv);
		TextView barkod = (TextView)dialog.findViewById(R.id.twBarkod);
		TextView cijena = (TextView)dialog.findViewById(R.id.twCijena);
		TextView loyalty = (TextView)dialog.findViewById(R.id.twLoyalty);
		TextView opis = (TextView)dialog.findViewById(R.id.twOpis);
		
		ImageView slika = (ImageView)dialog.findViewById(R.id.slikaDialog);
		
		naziv.setText(a.getNaziv());
		barkod.setText(String.valueOf(a.getBarkod()));
		cijena.setText(String.valueOf(a.getCijena()));
		loyalty.setText(String.valueOf(a.getLoyalty()));
		opis.setText(a.getOpis());
		Ion.with(slika).load(a.getImgUrl());
		dialog.show();
		dialog.setCancelable(true);
	}
	
	private void initList(){

		try{
			JSONObject jsonResponse = new JSONObject(jsonString);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("artikli");

			for(int i = 0; i<jsonMainNode.length();i++){
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String naziv = jsonChildNode.optString("naziv");
				String cijena = jsonChildNode.optString("cijena");
				String barkod = jsonChildNode.optString("barkod");
				String loyaltypoints = jsonChildNode.optString("loyaltypoints");
				String opis = jsonChildNode.optString("opis");
				String url = jsonChildNode.optString("url");
				//DecimalFormat df = new DecimalFormat("#.00");
			   // cijena = df.format(cijena);	    
				Log.d("node", jsonMainNode.toString());
				listaArtikala.add(new Artikal(naziv, cijena + " KM", loyaltypoints, opis, barkod, url));
				
			}
		}
		catch(JSONException e){
			Toast.makeText(getApplicationContext(), "Error"+e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	
}
