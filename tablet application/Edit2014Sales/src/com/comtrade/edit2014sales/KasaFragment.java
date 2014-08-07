package com.comtrade.edit2014sales;

import java.util.ArrayList;
import java.util.List;

import rowItems.ArtikalAdapter;
import rowItems.KupljeniArtikliAdapter;
import rowItems.SpisakArtikalaAdapter;
import models.Artikal;
import models.ArtikliDAO;
import models.KupljeniArtikal;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.comtrade.edit2014salesTarik.R;

public class KasaFragment extends Fragment {
	private ArtikliDAO dao;
	private List<Artikal>artikli;
	
	private ArrayList<KupljeniArtikal>kupljeni;
	
	private float total;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dao = new ArtikliDAO(getActivity());
		dao.open();
		artikli = dao.uzmiListuSvihArtikala();
		dao.close();
		
		kupljeni = new ArrayList<KupljeniArtikal>();
		total = 0;
		
		preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		editor = preferences.edit();

	}
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		final View view = inflater.inflate(R.layout.fragment_kasa, container,false);

		
		final ListView listaKupljenih = (ListView)view.findViewById(R.id.listView1);
		listaKupljenih.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				for (KupljeniArtikal art : kupljeni) {
					dao.open();
					dao.promijeniKolicinu(art.getKupljeniArtikal().getId(), art.getKupljeniArtikal().getKolicina());
					dao.close();
					Toast.makeText(getActivity(), "Azurirano stanje prodatih artikala", Toast.LENGTH_SHORT).show();
				}
				
				kupljeni.clear();
				KupljeniArtikliAdapter adapter = new KupljeniArtikliAdapter(getActivity(), kupljeni);
				listaKupljenih.setAdapter(adapter);
				artikli.clear();
				dao.open();
				artikli = dao.uzmiListuSvihArtikala();
				dao.close();
				
				ListView lista = (ListView)getActivity().findViewById(R.id.spisak);
				SpisakArtikalaAdapter adapter1 = new SpisakArtikalaAdapter(getActivity(), artikli);
				lista.setAdapter(adapter1);
				
				total = 0;
				editor.putString("loyaltyPoints", "0.0");
				editor.apply();
				
				TextView t = (TextView)getActivity().findViewById(R.id.txtBarkod);
				TextView p= (TextView)getActivity().findViewById(R.id.txtNaziv);
				t.setText("0.0");
				p.setText("0");
				
				return false;
			}
		});
		
		
		ListView lista = (ListView)view.findViewById(R.id.spisak);
		SpisakArtikalaAdapter adapter = new SpisakArtikalaAdapter(getActivity(), artikli);
		lista.setAdapter(adapter);
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// TODO Auto-generated method stub
				Artikal a = artikli.get(position);
				if (a.getKolicina() > 0) {
					Toast.makeText(getActivity().getApplicationContext(), a.getNaziv(), Toast.LENGTH_SHORT).show();
					
					boolean postoji = false;
					for (KupljeniArtikal k : kupljeni) {
						if (k.getKupljeniArtikal().getId() == a.getId()) {
							k.povecajKolicinu();
							postoji = true;
							break;
						}	
					}
					if (!postoji) {
						KupljeniArtikal kup = new KupljeniArtikal(a, 1);
						kupljeni.add(kup);
					}
					artikli.get(position).smanjiKolicinu();
	
					total += a.getCijena();
					
					editor.putString("loyaltyPoints", String.valueOf((int)total/10));
					editor.apply();
					
					ListView listaKupljenih = (ListView)view.findViewById(R.id.listView1);
					
					KupljeniArtikliAdapter adapter = new KupljeniArtikliAdapter(getActivity(), kupljeni);
					listaKupljenih.setAdapter(adapter);
					
					TextView t = (TextView)view.findViewById(R.id.txtBarkod);
					TextView p= (TextView)view.findViewById(R.id.txtNaziv);
					t.setText(String.valueOf(total));
					p.setText(String.valueOf((int)total/10));
				} else {
					Toast.makeText(getActivity(), a.getNaziv() + " vise nema na stanju!!!", Toast.LENGTH_SHORT).show();
				}
			}
		});	
		
		
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
				// TODO Auto-generated method stub
				final Artikal a = artikli.get(position);
				
				AlertDialog.Builder dialog = new Builder(getActivity());
				dialog.setTitle(a.getNaziv());
				dialog.setMessage("unesite kolicinu: ");
				final EditText input = new EditText(getActivity());
				input.setInputType( InputType.TYPE_CLASS_NUMBER);
				dialog.setView(input);
				
				dialog.setPositiveButton("Uredu", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int kol = Integer.valueOf(input.getText().toString());
						if (a.getKolicina() >= kol) {
							boolean postoji = false;
							for (KupljeniArtikal k : kupljeni) {
								if (k.getKupljeniArtikal().getId() == a.getId()) {
									k.povecajKolicinu(kol);
									postoji = true;
									break;
								}	
							}
							if (!postoji) {
								KupljeniArtikal kup = new KupljeniArtikal(a, kol);
								kupljeni.add(kup);
							}
							Toast.makeText(getActivity(),a.getNaziv() + " " +  kol, Toast.LENGTH_SHORT).show();
							artikli.get(position).smanjiKolicinu(kol);
							total += kol * a.getCijena();
							
							editor.putString("loyaltyPoints", String.valueOf((int)total/10));
							editor.apply();
							
							ListView listaKupljenih = (ListView)getActivity().findViewById(R.id.listView1);
							
							KupljeniArtikliAdapter adapter = new KupljeniArtikliAdapter(getActivity(), kupljeni);
							listaKupljenih.setAdapter(adapter);
							
							
							TextView t = (TextView)getActivity().findViewById(R.id.txtBarkod);
							TextView p= (TextView)getActivity().findViewById(R.id.txtNaziv);
							t.setText(String.valueOf(total));
							p.setText(String.valueOf((int)total/10));	
						} else {
							Toast.makeText(getActivity(), a.getNaziv() + " vise nema na stanju!!!", Toast.LENGTH_SHORT).show();
						}
						
						
					}
				});
				dialog.setNegativeButton("Odustani",new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				dialog.create();
				dialog.show();
				
				return false;
			}
		});
		
		

		
		

		
		return view;
	}
	
}
