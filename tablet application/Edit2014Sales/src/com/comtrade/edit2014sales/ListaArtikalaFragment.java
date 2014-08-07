package com.comtrade.edit2014sales;

import java.util.ArrayList;
import java.util.List;

import models.Artikal;
import models.ArtikliDAO;
import rowItems.ArtikalAdapter;
import QuickAction.QuickActionItem;
import QuickAction.QuickActionPopup;
import QuickAction.QuickActionPopup.OnActionItemClickListener;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.comtrade.edit2014salesTarik.R;

public class ListaArtikalaFragment extends ListFragment {
	
		private List<Artikal> artikli;
		private ArtikliDAO dao;
		
		private int pozicija;
		
		private QuickActionPopup popup;
		

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			dao = new ArtikliDAO(getActivity());
			dao.open();
			artikli = dao.uzmiListuSvihArtikala();
			dao.close();
			pozicija = -1;
			ArtikalAdapter adapter = new ArtikalAdapter(getActivity(), artikli);
			setListAdapter(adapter);
			
			popup = new QuickActionPopup(getActivity(), QuickActionPopup.HORIZONTAL);
		    QuickActionItem detalji = new QuickActionItem(1, "Detalji", getResources().getDrawable(R.drawable.detalji));
	        QuickActionItem uredi = new QuickActionItem(2, "Uredi", getResources().getDrawable(R.drawable.uredi));
	        QuickActionItem izbrisi = new QuickActionItem(3, "Izbrisi", getResources().getDrawable(R.drawable.izbrisi));
	        popup.addActionItem(detalji);
	        popup.addActionItem(uredi);
	        popup.addActionItem(izbrisi);
	        
	        popup.setOnActionItemClickListener(new OnActionItemClickListener() {
				
				@Override
				public void onItemClick(QuickActionPopup source, int pos, int actionId) {
					// TODO Auto-generated method stub
					if (actionId == 1) {
						final Artikal a = artikli.get(pozicija);
						final Dialog dialog = new Dialog(getActivity());
						dialog.setTitle(a.getNaziv());
						
						LayoutInflater inflater = getActivity().getLayoutInflater();
						
						dialog.setContentView(inflater.inflate(R.layout.custom_dialog, null));
						
						TextView id = (TextView)dialog.findViewById(R.id.twId);
						TextView naziv = (TextView)dialog.findViewById(R.id.twNaziv);
						TextView barkod = (TextView)dialog.findViewById(R.id.twBarkod);
						TextView cijena = (TextView)dialog.findViewById(R.id.twCijena);
						TextView kolicina = (TextView)dialog.findViewById(R.id.twKolicina);
						ImageView slika = (ImageView)dialog.findViewById(R.id.slikaDialog);
						
						id.setText(String.valueOf(a.getId()));
						naziv.setText(a.getNaziv());
						barkod.setText(String.valueOf(a.getBarkod()));
						cijena.setText(String.valueOf(a.getCijena())+" KM");
						kolicina.setText(String.valueOf(a.getKolicina())+ " kom");
						
						if (a.getNaziv().contains("kolac")) {
							slika.setImageResource(R.drawable.kolac);
						} else if (a.getNaziv().contains("kafa")) {
							slika.setImageResource(R.drawable.coffee);
						} else if (a.getNaziv().contains("caj")) {
							slika.setImageResource(R.drawable.tea);
						} else if (a.getNaziv().contains("juice")) {
							slika.setImageResource(R.drawable.juice);
						} else if (a.getNaziv().contains("borovnica")) {
							slika.setImageResource(R.drawable.borovnica);
						} else if (a.getNaziv().contains("limunada")) {
							slika.setImageResource(R.drawable.limunada);
						} else if (a.getNaziv().contains("sladoled") && a.getCijena()==1) {
							slika.setImageResource(R.drawable.icecream_kugla);
						} else if (a.getNaziv().contains("sladoled") && a.getCijena()>=2) {
							slika.setImageResource(R.drawable.icecream_casa);
						} else if (a.getNaziv().contains("coca cola") && a.getCijena()>=2) {
							slika.setImageResource(R.drawable.cola);
						} else if (a.getNaziv().contains("pepsi") && a.getCijena()>=2) {
							slika.setImageResource(R.drawable.pepsi);
						} else if (a.getNaziv().contains("red bull") && a.getCijena()>=2) {
							slika.setImageResource(R.drawable.redbull);
						} else if (a.getNaziv().contains("torta") && a.getCijena()>=2) {
							slika.setImageResource(R.drawable.torta);
						}
						
						Button uredi = (Button)dialog.findViewById(R.id.btnUredi);
						Button izbrisi = (Button)dialog.findViewById(R.id.btnIzbrisi);
						
						uredi.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								MainActivity.PreusmjeriNaUredi(a);
								dialog.dismiss();
							}
						});
						izbrisi.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Izbrisi();
							}
						});						
						

						

						
						dialog.setCancelable(true);
						
						dialog.show();	
					}
					else if (actionId == 2) {
						Artikal a = artikli.get(pozicija);
						MainActivity.PreusmjeriNaUredi(a);
					}
					if (actionId == 3) {
						Izbrisi();
					}
				}
			});
		}
		
		@Override
	    public void setUserVisibleHint(boolean isVisibleToUser) {
			super.setUserVisibleHint(isVisibleToUser);
			if (isVisibleToUser) {
				dao.open();
				artikli.clear();
				artikli = dao.uzmiListuSvihArtikala();
				dao.close();
				ArtikalAdapter adapter = new ArtikalAdapter(getActivity(), artikli);
				setListAdapter(adapter);
			}
		
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			pozicija = position;
			
        
	        popup.show(v);
	    }
		
		public void Izbrisi() {
			dao.open();
			dao.izbrisiArtikal(artikli.get(pozicija).getId());
			dao.close();
			pozicija = -1;
	    	setUserVisibleHint(true);
	        Toast.makeText(getActivity(), "Uspješno ste izbrisali artikal!", Toast.LENGTH_SHORT).show();
		}

}
