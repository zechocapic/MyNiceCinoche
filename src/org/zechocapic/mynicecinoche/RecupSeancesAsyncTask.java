package org.zechocapic.mynicecinoche;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
//import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecupSeancesAsyncTask extends AsyncTask<String, Void, Document>{
	private SeancesFragment seancesFragment;
	private ProgressDialog progressDialog;
	private View inflatedView;
	private Context context;
	private LinearLayout.OnClickListener onClickListener = new LinearLayout.OnClickListener() {
		public void onClick(View v) {
			String url = (String) v.getTag();
			seancesFragment.updateFilm(url);
		}
	};

	public RecupSeancesAsyncTask(SeancesFragment seancesFragment, View inflatedView, Context context) {
		this.seancesFragment = seancesFragment;
		this.inflatedView = inflatedView;
		this.context = context;
	}
	
    // launching progress dialog
	@Override
	protected void onPreExecute() {
    	this.progressDialog = new ProgressDialog(context);
    	this.progressDialog.setMessage("Liste des séances en cours de récupération");
    	this.progressDialog.show();
	}
    
    // downloading web page
	@Override
	protected Document doInBackground(String... urls) {
		Document document = null;
		String url = urls[0];
		try {
			document = Jsoup.connect(url).get();
			return document;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

    // parsing web page and drawing the result
	@Override
    protected void onPostExecute(Document doc) {
		// closing progress dialog
		progressDialog.dismiss();
		
		// toast to manage failed connection
        if(doc == null){
        	Toast.makeText(context, "Erreur : le chargement de la page n'aboutit pas !", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // style variables
        int titleTextSize, subtitleTextSize, simpleTextSize;
        int titleBackgroundColor, subtitleBackgroundColor, simpleBackgroundColor;
        int titleTextColor, subtitleTextColor, simpleTextColor;
        
        // initializing styles
    	int appStyle = 1;
        if (appStyle == 1) {
        	titleTextSize = 20;
        	subtitleTextSize = 16;
        	simpleTextSize = 14;
        	titleBackgroundColor = Color.rgb(47, 55, 64);
        	subtitleBackgroundColor = Color.rgb(192, 192, 192);
        	simpleBackgroundColor = Color.rgb(224, 224, 224);
        	titleTextColor = Color.WHITE;
        	subtitleTextColor = Color.BLACK;
        	simpleTextColor = Color.BLACK;
        } else {
        	titleTextSize = 32;
        	subtitleTextSize = 24;
        	simpleTextSize = 24;
        	titleBackgroundColor = Color.BLACK;
        	subtitleBackgroundColor = Color.rgb(32, 32, 32);
        	simpleBackgroundColor = Color.rgb(64, 64, 64);
        	titleTextColor = Color.WHITE;
        	subtitleTextColor = Color.WHITE;
        	simpleTextColor = Color.WHITE;
        }

		// page layout
        LinearLayout layoutListeSeances = (LinearLayout) inflatedView.findViewById(R.id.layout_liste_seances);
        layoutListeSeances.setBackgroundColor(Color.WHITE);
        layoutListeSeances.removeAllViews();
		
        Elements blocs = doc.select("div.bloc_salles_bloc");
        
		for (Element bloc : blocs) {
			// layout for each show time
			LinearLayout layoutBloc = new LinearLayout(context);
			layoutBloc.setOrientation(LinearLayout.VERTICAL);
			layoutBloc.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			layoutBloc.setPadding(5, 5, 5, 5);
			layoutBloc.setOnClickListener(onClickListener);
			layoutBloc.setTag(bloc.select("div.titre").first().select("a[href]").first().attr("href"));
			
			// movie title
			Element titre = bloc.select("div.titre").first(); 
            TextView twTitre = new TextView(context);
            twTitre.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            twTitre.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize);
            twTitre.setTypeface(null, Typeface.BOLD);
            twTitre.setBackgroundColor(titleBackgroundColor);
            twTitre.setTextColor(titleTextColor);
            twTitre.setText(titre.text());
            layoutBloc.addView(twTitre);
			
			// movie show time
			Elements seances = bloc.select("ul.cine-seances li");
			for (Element seance : seances) {
    			LinearLayout layoutSeances = new LinearLayout(context);            			
    			layoutSeances.setOrientation(LinearLayout.HORIZONTAL);
    			layoutSeances.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    			layoutSeances.setBackgroundColor(Color.BLACK);
				
				// movie version
    			Element version = seance.select("span.cine-version").first();
				TextView twVersion = new TextView(context);
				twVersion.setLayoutParams(new LinearLayout.LayoutParams(100, LayoutParams.MATCH_PARENT, 0f));
				twVersion.setTextSize(TypedValue.COMPLEX_UNIT_SP, subtitleTextSize);
				twVersion.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics()));
				twVersion.setTypeface(null, Typeface.BOLD);
				twVersion.setBackgroundColor(subtitleBackgroundColor);
				twVersion.setTextColor(subtitleTextColor);
				twVersion.setText(version.text() + " : ");
                layoutSeances.addView(twVersion);
                
                // movie schedule
				Element horaire = seance.select("span.cine-horaires").first();
                TextView twHoraire = new TextView(context);
				twHoraire.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
				twHoraire.setTextSize(TypedValue.COMPLEX_UNIT_SP, simpleTextSize);
                twHoraire.setTextColor(simpleTextColor);
                twHoraire.setBackgroundColor(simpleBackgroundColor);
                twHoraire.setGravity(Gravity.CENTER_VERTICAL);
                twHoraire.setText(horaire.text().replace("Séances à ", "").replace("Film à ", ""));
                layoutSeances.addView(twHoraire);
                layoutBloc.addView(layoutSeances);
			}

			
			// movie rates
			Elements notes = bloc.select("li.infos_notes span"); 
			LinearLayout layoutNotes = new LinearLayout(context);            			
			layoutNotes.setOrientation(LinearLayout.HORIZONTAL);
			layoutNotes.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			layoutNotes.setBackgroundColor(Color.BLACK);
			
			// textView for press ratings
            TextView twNotePresse = new TextView(context);
            twNotePresse.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
            twNotePresse.setTypeface(null, Typeface.BOLD);
            twNotePresse.setBackgroundColor(subtitleBackgroundColor);
            twNotePresse.setTextSize(TypedValue.COMPLEX_UNIT_SP, subtitleTextSize);
            twNotePresse.setTextColor(simpleTextColor);
            twNotePresse.setText("Presse ");
            
            // imageView for press ratings
            ImageView imNotePresse = new ImageView(context);
            imNotePresse.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
            imNotePresse.setBackgroundColor(simpleBackgroundColor);
            
            // textView for viewers ratings
            TextView twNoteSpectateurs = new TextView(context);
            twNoteSpectateurs.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
            twNoteSpectateurs.setTypeface(null, Typeface.BOLD);
            twNoteSpectateurs.setBackgroundColor(subtitleBackgroundColor);
            twNoteSpectateurs.setTextSize(TypedValue.COMPLEX_UNIT_SP, subtitleTextSize);
            twNoteSpectateurs.setTextColor(simpleTextColor);
            twNoteSpectateurs.setText("Spectateurs ");
            
            // imageView for viewers ratings
            ImageView imNoteSpectateurs = new ImageView(context);
            imNoteSpectateurs.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
            imNoteSpectateurs.setBackgroundColor(simpleBackgroundColor);
            
			for (Element note : notes) {
                
				// press ratings
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
    			// viewers ratings
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
            layoutListeSeances.addView(layoutBloc);
		}

    }

}