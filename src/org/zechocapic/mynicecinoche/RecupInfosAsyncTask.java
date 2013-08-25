package org.zechocapic.mynicecinoche;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecupInfosAsyncTask extends AsyncTask<String, Void, Document>{
	private FilmFragment filmFragment;
	private ProgressDialog progressDialog;
	
	public RecupInfosAsyncTask(FilmFragment filmFragment) {
		this.filmFragment = filmFragment;
	}

    @Override
	protected void onPreExecute() {
    	this.progressDialog = new ProgressDialog(filmFragment.getActivity());
    	this.progressDialog.setMessage("Infos du film en cours de récupération");
    	this.progressDialog.show();
	}
    
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

    // Parsing de la page web
	@Override
    protected void onPostExecute(Document doc) {
		// Fermeture de la fenetre de chargement
		progressDialog.dismiss();
		
		// Toast pour gerer les cas ou la connexion est en carton
        if(doc == null){
        	Toast.makeText(filmFragment.getActivity(), "Erreur : le chargement de la page n'aboutit pas !", Toast.LENGTH_SHORT).show();;
            return;
        }
        
        // Variables de style
        int titleTextSize, subtitleTextSize, simpleTextSize;
        int titleBackgroundColor, subtitleBackgroundColor, simpleBackgroundColor;
        int titleTextColor, subtitleTextColor, simpleTextColor;
        
        // Definition des differents styles
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
        
		// Bloc du film
        LinearLayout linearLayout = (LinearLayout) filmFragment.getActivity().findViewById(R.id.layout_film);
        linearLayout.setBackgroundColor(Color.WHITE);
        linearLayout.setPadding(5, 5, 5, 5);
        linearLayout.removeAllViews();
        
        // TextView du titre
		Element titre = doc.select("div.bloc_rub_titre").first();
        TextView twTitre = new TextView(filmFragment.getActivity());
        twTitre.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        twTitre.setBackgroundColor(titleBackgroundColor);
        twTitre.setTextSize(titleTextSize);
        twTitre.setTypeface(null, Typeface.BOLD);
        twTitre.setTextColor(titleTextColor);
        twTitre.setText(titre.text());
        linearLayout.addView(twTitre);
		
		// Layout horizontal contenant affiche et infos
        LinearLayout layoutAfficheInfos = new LinearLayout(filmFragment.getActivity());            			
        layoutAfficheInfos.setOrientation(LinearLayout.HORIZONTAL);
        layoutAfficheInfos.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        layoutAfficheInfos.setBackgroundColor(Color.BLACK);
		
		// ImageView affiche
		Element affiche = doc.select("div.visuel img[src]").first();
		ImageView imAffiche = new ImageView(filmFragment.getActivity());
		imAffiche.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0f));
		layoutAfficheInfos.addView(imAffiche);
        new RecupImageAsyncTask(imAffiche).execute(affiche.attr("src"));
        
        // Textview date de sortie
        Element genre = doc.select("div.infos [itemprop=genre]").first();
        Element dateSortie = doc.select("div.infos [itemprop=datePublished]").first();
        Element realisateur = doc.select("div.infos [itemprop=director] [itemprop=name]").first();
        Element acteur = doc.select("div.infos [itemprop=actors]").first();
        Element duree = doc.select("div.infos [itemprop=duration]").first();
        TextView twInfosDiverses = new TextView(filmFragment.getActivity());
        twInfosDiverses.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
        twInfosDiverses.setBackgroundColor(simpleBackgroundColor);
        twInfosDiverses.setTextSize(simpleTextSize);
        twInfosDiverses.setTextColor(simpleTextColor);
        twInfosDiverses.setText("Genre : " + genre.text() + "\n" +
        		"Date de sortie : " + dateSortie.text() + "\n" +
        		"Durée : " + duree.text() + "\n" +
        		"Réalisateur : " + realisateur.text() + "\n" +
        		"Acteurs : " + acteur.text().replace("Avec :", "").replace("...> Tout le casting", ""));
        layoutAfficheInfos.addView(twInfosDiverses);
        
        linearLayout.addView(layoutAfficheInfos);

        
        // Cadre Synopsis
		TextView twCadreSynopsis = new TextView(filmFragment.getActivity());
		twCadreSynopsis.setTypeface(null, Typeface.BOLD_ITALIC);
		twCadreSynopsis.setBackgroundColor(subtitleBackgroundColor);
		twCadreSynopsis.setTextSize(subtitleTextSize);
		twCadreSynopsis.setTextColor(subtitleTextColor);
		twCadreSynopsis.setText("Synopsis");
		linearLayout.addView(twCadreSynopsis);
		
		// TextView du synopsis
		Element description = doc.select("div.description_cnt").first();
        TextView twDesc = new TextView(filmFragment.getActivity());
        twDesc.setBackgroundColor(simpleBackgroundColor);
        twDesc.setTextSize(simpleTextSize);
        twDesc.setTextColor(simpleTextColor);
        twDesc.setText(description.text());
        linearLayout.addView(twDesc);
        
    }

}
