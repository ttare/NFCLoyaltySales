package com.comtrade.edit2014sales;

import models.Artikal;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;

import com.comtrade.edit2014salesTarik.R;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	private TabsPagerAdapter mHelper;
	private ActionBar actionBar;
	private static ViewPager viewPager;
	private String[] tabs = { "KASA", "LISTA ARTIKALA", "DODAJ ARTIKAL" };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		actionBar = getActionBar();
		
		
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		
		viewPager = (ViewPager)findViewById(R.id.pager);
		viewPager.setBackgroundResource(R.drawable.podloga);
		
		mHelper = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setHomeButtonEnabled(false);
		
		viewPager.setAdapter(mHelper);
		
		for(String tab : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab).setTabListener(this));
		}
		
		
	}

	
	
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public static void PreusmjeriNaUredi(Artikal a) {
		viewPager.setCurrentItem(2);
		if (a != null) DodajArtikalFragment.Izmijeni(a);
	}
}
