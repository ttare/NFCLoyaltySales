package com.example.edit2014client;

import java.util.ArrayList;
import java.util.List;

import com.koushikdutta.ion.Ion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ArtikalAdapter extends BaseAdapter {
	 private Context context;
    private LayoutInflater mInflater;
    private float bodovi;
    private List items = new ArrayList();
 
    public ArtikalAdapter(Context context, List items, float bodovi) {
    	this.context = context;
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.bodovi = bodovi;
    }

	public int getCount() {
        return items.size();
    }
 
    public Artikal getItem(int position) {
        return (Artikal) items.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
    
   
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Artikal s = (Artikal) items.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row, null);
            holder = new ViewHolder();
            holder.tNaziv = (TextView) convertView.findViewById(R.id.tNaziv);
            holder.tCijena = (TextView) convertView.findViewById(R.id.tCijena);
            holder.tLoyalty = (TextView) convertView.findViewById(R.id.tLoyalty);
            holder.tOpis = (TextView) convertView.findViewById(R.id.tOpis);
            holder.tBarkod = (TextView) convertView.findViewById(R.id.tBarkod);
            holder.lLayout = (LinearLayout) convertView.findViewById(R.id.lLayout);
            holder.iSlika = (ImageView) convertView.findViewById(R.id.iSlika);
            
            convertView.setTag(holder);
        } 
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        String poeni = s.getLoyalty();
        holder.tNaziv.setText(s.getNaziv());
        holder.tCijena.setText(s.getCijena());
        holder.tLoyalty.setText(poeni + " pts");
        holder.tOpis.setText(s.getOpis());
        holder.tBarkod.setText(s.getBarkod());
       Ion.with(holder.iSlika)
		 .error(R.drawable.ic_launcher)
		 .load(s.getImgUrl());
        if(Float.parseFloat(s.getLoyalty()) <= bodovi) {
        	Log.d("NAZIV", s.getNaziv());
        	convertView.setBackground(context.getResources().getDrawable(R.drawable.pozadinalw));
        	//holder.lLayout.setBackgroundColor(Color.parseColor("#FFC1C1"));
        }
        
        return convertView;
    }
 
    static class ViewHolder {
        TextView tNaziv;
        TextView tCijena;
        TextView tLoyalty;
        TextView tOpis;
        TextView tBarkod;
        LinearLayout lLayout;
        ImageView iSlika;
    }
    
    

 
}
