package rowItems;


import java.util.List;

import models.Artikal;
import models.KupljeniArtikal;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.comtrade.edit2014salesTarik.R;

public class KupljeniArtikliAdapter extends ArrayAdapter<KupljeniArtikal> {
	
	private final Context context;
	private List<KupljeniArtikal>listaKupljenih;
	
	public KupljeniArtikliAdapter(Context context, List<KupljeniArtikal> objects) {
		super(context, R.layout.row_za_kupljene, objects);
			
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listaKupljenih = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.row_za_kupljene, parent, false);

		TextView barkod = (TextView) rowView.findViewById(R.id.txtRowBarkod);
		TextView naziv = (TextView) rowView.findViewById(R.id.txtRowNaziv);
		TextView cijena = (TextView) rowView.findViewById(R.id.txtRowCijena);
		
		KupljeniArtikal kup = listaKupljenih.get(position);
		
		
		naziv.setText(kup.getKupljeniArtikal().getNaziv());
		barkod.setText(String.valueOf(kup.getKolicina()) + "x" + (String.valueOf(kup.getKupljeniArtikal().getCijena()) ));
		cijena.setText(String.valueOf(kup.getIznos()));
		

		return rowView;
	}
}
