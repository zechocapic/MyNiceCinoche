package org.zechocapic.mynicecinoche;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SeanceArrayAdapter extends ArrayAdapter<Element>{
	private Context context;
	private ArrayList<Element> values;

	public SeanceArrayAdapter(Context context, ArrayList<Element> values) {
		super(context, R.layout.row_layout, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = layoutInflater.inflate(R.layout.row_layout, parent, false);
		TextView twTitre = (TextView) rowView.findViewById(R.id.titre);
		TextView twSeances = (TextView) rowView.findViewById(R.id.seances);
		TextView twNotes = (TextView) rowView.findViewById(R.id.notes);
		
		// titre du film
		twTitre.setText(values.get(position).select("div.titre").first().text());
		
		// seances du film
		Elements seances = values.get(position).select("ul.cine-seances li");
		for (Element seance : seances) {
			// Version du film
			String version = seance.select("span.cine-version").first().text();
            
            // Horaire du film
			String horaire = seance.select("span.cine-horaires").first().text().replace("Séances à ", "").replace("Film à ", "");
			if (twSeances.getText().equals("")) {
				twSeances.setText(version + " : " + horaire);
			} else {
				twSeances.setText(twSeances.getText() + "\n" + version + " : " + horaire);
			}
		}
		
		// notes du film
		twNotes.setText(values.get(position).select("li.infos_notes").first().text());
		
		rowView.setTag(values.get(position).select("div.titre a[href]").first().attr("href"));
		return rowView;
	}

}
