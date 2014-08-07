package rowItems;

import java.util.List;

import models.Artikal;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.comtrade.edit2014salesTarik.R;

public class ArtikalAdapter extends ArrayAdapter<Artikal> {
	
	private final Context context;
	private List<Artikal>listaArtikala;
	
	public ArtikalAdapter(Context context, List<Artikal> objects) {
		super(context,R.layout.row_za_listu, objects);
			
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listaArtikala = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.row_za_listu, parent, false);

		TextView barkod = (TextView) rowView.findViewById(R.id.txtRowBarkod);
		TextView naziv = (TextView) rowView.findViewById(R.id.txtRowNaziv);
		TextView cijena = (TextView) rowView.findViewById(R.id.txtRowCijena);
		ImageView slika = (ImageView) rowView.findViewById(R.id.slikaArtikla);
		
		Artikal a = listaArtikala.get(position);
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
		
		
		naziv.setText(a.getNaziv());
		cijena.setText(String.valueOf(a.getCijena())+ " KM");
		barkod.setText(String.valueOf(a.getBarkod()));
		return rowView;

	}
}
