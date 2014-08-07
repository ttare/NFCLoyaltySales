package com.comtrade.edit2014sales;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	private Fragment kasa, lista, dodaj;
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		kasa = new KasaFragment();
		lista = new ListaArtikalaFragment();
		dodaj = new DodajArtikalFragment();
	}

	
	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		switch(index) {
		case 0:
			return kasa;
		case 1:
			return lista;
		case 2:
			return dodaj;
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
