package com.comtrade.edit2014sales;

import models.Artikal;
import models.ArtikliDAO;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.comtrade.edit2014salesTarik.R;



public class DodajArtikalFragment extends Fragment {
	private ArtikliDAO dao;
	
	private static EditText barkod, naziv, cijena, kolicina;
	private static Button btn;
	
	private static boolean izmjena;
	static Artikal artikal;
	

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dao = new ArtikliDAO(getActivity());
		izmjena = false;

	}
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_dodaj_artikal, container,false);
		
		barkod = (EditText)view.findViewById(R.id.txtBarkod);
		naziv = (EditText)view.findViewById(R.id.txtNaziv);
		cijena = (EditText)view.findViewById(R.id.txtCijena);
		kolicina = (EditText)view.findViewById(R.id.txtKolicina);
		
		
		btn = (Button)view.findViewById(R.id.btnDodaj);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (barkod.length()== 0) {
					barkod.setError("prazno polje");
				}
				if (naziv.length()== 0) {
					naziv.setError("prazno polje");
				}				
				if (cijena.length()== 0) {
					cijena.setError("prazno polje");
				}
				if (kolicina.length()== 0) {
					kolicina.setError("prazno polje");
				}
				if (barkod.length()==0 || naziv.length()==0 || cijena.length()==0)
					return;
				
				
				int bar = Integer.valueOf(barkod.getText().toString());
				float cij = Float.valueOf(cijena.getText().toString());
				int kol = Integer.valueOf(kolicina.getText().toString());
				dao.open();
				
				if (izmjena == false) {			
					dao.kreirajArtikal(bar, naziv.getText().toString(), cij, kol);
					dao.close();
					Toast.makeText(getActivity(),"Uspješno ste dodali artikal" , Toast.LENGTH_SHORT).show();
				} else {
					Artikal a = new Artikal(artikal.getId(), bar, naziv.getText().toString(), cij, kol);
					dao.izmijeniArtikal(a);
					dao.close();
					izmjena = false;
					btn.setText("DODAJ");
					Toast.makeText(getActivity(),"Uspješno ste izmijenili artikal" , Toast.LENGTH_SHORT).show();
				}
				barkod.setText("");
				barkod.requestFocus();
				naziv.setText("");
				cijena.setText("");
				kolicina.setText("");
			}
		});
		
		return view;
	}
	
	
	public static void Izmijeni(Artikal a) {
		artikal = a;
		izmjena = true;
		btn.setText("UREDI");
		barkod.setText(String.valueOf(a.getBarkod()));
		barkod.requestFocus();
		naziv.setText(a.getNaziv());
		cijena.setText(String.valueOf(a.getCijena()));
		kolicina.setText(String.valueOf(a.getKolicina()));
	}


}
