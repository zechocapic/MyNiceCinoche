package org.zechocapic.mynicecinoche;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SeanceArrayAdapter extends ArrayAdapter<Element>{
	private Context context;
	private ArrayList<Element> values;
    int titleTextSize, subtitleTextSize, simpleTextSize;
    int titleBackgroundColor, subtitleBackgroundColor, simpleBackgroundColor;
    int titleTextColor, subtitleTextColor, simpleTextColor;

	public SeanceArrayAdapter(Context context, ArrayList<Element> values) {
		super(context, R.layout.row_layout, values);
		this.context = context;
		this.values = values;
    	titleTextSize = 20;
    	subtitleTextSize = 16;
    	simpleTextSize = 14;
    	titleBackgroundColor = Color.rgb(47, 55, 64);
    	subtitleBackgroundColor = Color.rgb(192, 192, 192);
    	simpleBackgroundColor = Color.WHITE;
    	titleTextColor = Color.WHITE;
    	subtitleTextColor = Color.BLACK;
    	simpleTextColor = Color.BLACK;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = layoutInflater.inflate(R.layout.row_layout, parent, false);
		
		// Layout pour chaque film
		LinearLayout layoutBloc = (LinearLayout) rowView.findViewById(R.id.layout_bloc);
		layoutBloc.setOrientation(LinearLayout.VERTICAL);
		
		// Titre du film
		Element titre = values.get(position).select("div.titre").first(); 
        TextView twTitre = new TextView(context);
        twTitre.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        twTitre.setTextSize(titleTextSize);
        twTitre.setTypeface(null, Typeface.BOLD);
        twTitre.setBackgroundColor(titleBackgroundColor);
        twTitre.setTextColor(titleTextColor);
        twTitre.setText(titre.text());
        layoutBloc.addView(twTitre);
		
		// Seances du film
		Elements seances = values.get(position).select("ul.cine-seances li");
		for (Element seance : seances) {
			LinearLayout layoutSeances = new LinearLayout(context);            			
			layoutSeances.setOrientation(LinearLayout.HORIZONTAL);
			layoutSeances.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			layoutSeances.setBackgroundColor(Color.BLACK);
			
			// Version du film
			Element version = seance.select("span.cine-version").first();
			TextView twVersion = new TextView(context);
			twVersion.setLayoutParams(new LinearLayout.LayoutParams(100, LayoutParams.MATCH_PARENT, 0f));
			twVersion.setTextSize(subtitleTextSize);
			twVersion.setWidth(100);
			twVersion.setTypeface(null, Typeface.BOLD);
			twVersion.setBackgroundColor(subtitleBackgroundColor);
			twVersion.setTextColor(subtitleTextColor);
			twVersion.setText(version.text() + " : ");
            layoutSeances.addView(twVersion);
            
            // Horaire du film
			Element horaire = seance.select("span.cine-horaires").first();
            TextView twHoraire = new TextView(context);
			twHoraire.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
			twHoraire.setTextSize(simpleTextSize);
            twHoraire.setTextColor(simpleTextColor);
            twHoraire.setBackgroundColor(simpleBackgroundColor);
            twHoraire.setGravity(Gravity.CENTER_VERTICAL);
            twHoraire.setText(horaire.text().replace("Séances à ", "").replace("Film à ", ""));
            layoutSeances.addView(twHoraire);
            layoutBloc.addView(layoutSeances);
		}

		
		// Notes du film
		Elements notes = values.get(position).select("li.infos_notes span"); 
		LinearLayout layoutNotes = new LinearLayout(context);            			
		layoutNotes.setOrientation(LinearLayout.HORIZONTAL);
		layoutNotes.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layoutNotes.setBackgroundColor(Color.BLACK);
		
		// TextView notePresse
        TextView twNotePresse = new TextView(context);
        twNotePresse.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        twNotePresse.setTypeface(null, Typeface.BOLD);
        twNotePresse.setBackgroundColor(subtitleBackgroundColor);
        twNotePresse.setTextSize(subtitleTextSize);
        twNotePresse.setTextColor(simpleTextColor);
        twNotePresse.setText("Presse ");
        
        // ImageView notePresse
        ImageView imNotePresse = new ImageView(context);
        imNotePresse.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        imNotePresse.setBackgroundColor(simpleBackgroundColor);
        
        // TextView noteSpectateurs
        TextView twNoteSpectateurs = new TextView(context);
        twNoteSpectateurs.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        twNoteSpectateurs.setTypeface(null, Typeface.BOLD);
        twNoteSpectateurs.setBackgroundColor(subtitleBackgroundColor);
        twNoteSpectateurs.setTextSize(subtitleTextSize);
        twNoteSpectateurs.setTextColor(simpleTextColor);
        twNoteSpectateurs.setText("Spectateurs ");
        
        // ImageView noteSectateurs
        ImageView imNoteSpectateurs = new ImageView(context);
        imNoteSpectateurs.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        imNoteSpectateurs.setBackgroundColor(simpleBackgroundColor);
        
		for (Element note : notes) {
            
			// Note Presse
			if (note.text().contains("Presse 1")) {
                imNotePresse.setImageResource(R.drawable.rating_m1);
			} 
			else if (note.text().contains("Presse 2")) {
                imNotePresse.setImageResource(R.drawable.rating_m2);
			}
			else if (note.text().contains("Presse 3")) {
                imNotePresse.setImageResource(R.drawable.rating_m3);
			}
			else if (note.text().contains("Presse 4")) {
                imNotePresse.setImageResource(R.drawable.rating_m4);
			}
			// Note spectateurs
			else if (note.text().contains("Spectateurs 1")) {
                imNoteSpectateurs.setImageResource(R.drawable.rating_m1);
			}
			else if (note.text().contains("Spectateurs 2")) {
                imNoteSpectateurs.setImageResource(R.drawable.rating_m2);
			}
			else if (note.text().contains("Spectateurs 3")) {
                imNoteSpectateurs.setImageResource(R.drawable.rating_m3);
			}
			else if (note.text().contains("Spectateurs 4")) {
                imNoteSpectateurs.setImageResource(R.drawable.rating_m4);
			}
		}
        layoutNotes.addView(twNotePresse);
        layoutNotes.addView(imNotePresse);
        
        layoutNotes.addView(twNoteSpectateurs);
        layoutNotes.addView(imNoteSpectateurs);
        
        layoutBloc.addView(layoutNotes);
        
		rowView.setTag(values.get(position).select("div.titre a[href]").first().attr("href"));
		return rowView;
	}
	/*static class ViewHolder {
		public LinearLayout layoutBloc;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = layoutInflater.inflate(R.layout.row_layout, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.layoutBloc = (LinearLayout) rowView.findViewById(R.id.layout_bloc);
			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		// Titre du film
		Element titre = values.get(position).select("div.titre").first(); 
        TextView twTitre = new TextView(context);
        twTitre.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        twTitre.setTextSize(titleTextSize);
        twTitre.setTypeface(null, Typeface.BOLD);
        twTitre.setBackgroundColor(titleBackgroundColor);
        twTitre.setTextColor(titleTextColor);
        twTitre.setText(titre.text());
        holder.layoutBloc.addView(twTitre);
		
		// Seances du film
		Elements seances = values.get(position).select("ul.cine-seances li");
		for (Element seance : seances) {
			LinearLayout layoutSeances = new LinearLayout(context);            			
			layoutSeances.setOrientation(LinearLayout.HORIZONTAL);
			layoutSeances.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			layoutSeances.setBackgroundColor(Color.BLACK);
			
			// Version du film
			Element version = seance.select("span.cine-version").first();
			TextView twVersion = new TextView(context);
			twVersion.setLayoutParams(new LinearLayout.LayoutParams(100, LayoutParams.MATCH_PARENT, 0f));
			twVersion.setTextSize(subtitleTextSize);
			twVersion.setWidth(100);
			twVersion.setTypeface(null, Typeface.BOLD);
			twVersion.setBackgroundColor(subtitleBackgroundColor);
			twVersion.setTextColor(subtitleTextColor);
			twVersion.setText(version.text() + " : ");
            layoutSeances.addView(twVersion);
            
            // Horaire du film
			Element horaire = seance.select("span.cine-horaires").first();
            TextView twHoraire = new TextView(context);
			twHoraire.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
			twHoraire.setTextSize(simpleTextSize);
            twHoraire.setTextColor(simpleTextColor);
            twHoraire.setBackgroundColor(simpleBackgroundColor);
            twHoraire.setGravity(Gravity.CENTER_VERTICAL);
            twHoraire.setText(horaire.text().replace("Séances à ", "").replace("Film à ", ""));
            layoutSeances.addView(twHoraire);
            holder.layoutBloc.addView(layoutSeances);
		}

		
		// Notes du film
		Elements notes = values.get(position).select("li.infos_notes span"); 
		LinearLayout layoutNotes = new LinearLayout(context);            			
		layoutNotes.setOrientation(LinearLayout.HORIZONTAL);
		layoutNotes.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layoutNotes.setBackgroundColor(Color.BLACK);
		
		// TextView notePresse
        TextView twNotePresse = new TextView(context);
        twNotePresse.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        twNotePresse.setTypeface(null, Typeface.BOLD);
        twNotePresse.setBackgroundColor(subtitleBackgroundColor);
        twNotePresse.setTextSize(subtitleTextSize);
        twNotePresse.setTextColor(simpleTextColor);
        twNotePresse.setText("Presse ");
        
        // ImageView notePresse
        ImageView imNotePresse = new ImageView(context);
        imNotePresse.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        imNotePresse.setBackgroundColor(simpleBackgroundColor);
        
        // TextView noteSpectateurs
        TextView twNoteSpectateurs = new TextView(context);
        twNoteSpectateurs.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        twNoteSpectateurs.setTypeface(null, Typeface.BOLD);
        twNoteSpectateurs.setBackgroundColor(subtitleBackgroundColor);
        twNoteSpectateurs.setTextSize(subtitleTextSize);
        twNoteSpectateurs.setTextColor(simpleTextColor);
        twNoteSpectateurs.setText("Spectateurs ");
        
        // ImageView noteSectateurs
        ImageView imNoteSpectateurs = new ImageView(context);
        imNoteSpectateurs.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        imNoteSpectateurs.setBackgroundColor(simpleBackgroundColor);
        
		for (Element note : notes) {
            
			// Note Presse
			if (note.text().contains("Presse 1")) {
                imNotePresse.setImageResource(R.drawable.rating_m1);
			} 
			else if (note.text().contains("Presse 2")) {
                imNotePresse.setImageResource(R.drawable.rating_m2);
			}
			else if (note.text().contains("Presse 3")) {
                imNotePresse.setImageResource(R.drawable.rating_m3);
			}
			else if (note.text().contains("Presse 4")) {
                imNotePresse.setImageResource(R.drawable.rating_m4);
			}
			// Note spectateurs
			else if (note.text().contains("Spectateurs 1")) {
                imNoteSpectateurs.setImageResource(R.drawable.rating_m1);
			}
			else if (note.text().contains("Spectateurs 2")) {
                imNoteSpectateurs.setImageResource(R.drawable.rating_m2);
			}
			else if (note.text().contains("Spectateurs 3")) {
                imNoteSpectateurs.setImageResource(R.drawable.rating_m3);
			}
			else if (note.text().contains("Spectateurs 4")) {
                imNoteSpectateurs.setImageResource(R.drawable.rating_m4);
			}
		}
        layoutNotes.addView(twNotePresse);
        layoutNotes.addView(imNotePresse);
        
        layoutNotes.addView(twNoteSpectateurs);
        layoutNotes.addView(imNoteSpectateurs);
        
        holder.layoutBloc.addView(layoutNotes);
        
		//rowView.setTag(values.get(position).select("div.titre a[href]").first().attr("href"));
		return rowView;
	}*/
	
}
