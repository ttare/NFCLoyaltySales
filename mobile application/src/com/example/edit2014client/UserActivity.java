package com.example.edit2014client;

import com.koushikdutta.ion.Ion;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends Activity {
	
	private TextView ime;
	private TextView prezime;
	private TextView kartica;
	private TextView bodovi;
	private TextView datum;
	
	private ImageView slika;
	private Button potvrdiDugme;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		ime = (TextView) findViewById(R.id.tIme);
		prezime = (TextView) findViewById(R.id.tPrezime);
		kartica = (TextView) findViewById(R.id.tBrojKartice);
		bodovi = (TextView) findViewById(R.id.tBodovi);
		datum = (TextView) findViewById(R.id.tZadnjiUnos);
		potvrdiDugme = (Button) findViewById(R.id.potvrdiButton);
		slika = (ImageView) findViewById(R.id.slika);
		
		Bundle bundle = getIntent().getExtras();
		String imeKupca = bundle.getString("imeKupca");
		String prezimeKupca = bundle.getString("prezimeKupca");
		String brojKartice = bundle.getString("brojKartice");
		final String brojBodova = bundle.getString("brojBodova");
		String datumZadnjePromjene = bundle.getString("datumZadnjePromjene");
		String slikaKupca = bundle.getString("urlSlikeKupca");
		
		ime.setText(imeKupca);
		prezime.setText(prezimeKupca);
		kartica.setText(brojKartice);
		bodovi.setText(brojBodova);
		datum.setText(datumZadnjePromjene);

		Ion.with(slika)
        .error(R.drawable.ic_launcher)
        .load(slikaKupca);
		
		potvrdiDugme.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(getApplicationContext(), ListaArtikala.class);
				 intent.putExtra("bodovi", brojBodova);
				 startActivity(intent);
				
			}
		});
	}
}
