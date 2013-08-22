package org.zechocapic.mynicecinoche;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

public class RecupInfosAsyncTask extends AsyncTask<String, Void, Document>{
	private FilmFragment filmFragment;
	
	public RecupInfosAsyncTask(FilmFragment filmFragment) {
		this.filmFragment = filmFragment;
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

	@Override
	protected void onPostExecute(Document document) {
		if (document == null) {
			Toast.makeText(filmFragment.getActivity(), "Erreur chargement page", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// titre du film
		TextView titreTextView = (TextView) filmFragment.getActivity().findViewById(R.id.film_titre);
		titreTextView.setText(document.select("div.bloc_rub_titre").first().text());
		
		// infos diverses sur le film
		TextView infosTextView = (TextView) filmFragment.getActivity().findViewById(R.id.film_infos_diverses);
		infosTextView.setText(
				"Genre : " + document.select("div.infos [itemprop=genre]").first().text() + "\n" +
        		"Date de sortie : " + document.select("div.infos [itemprop=datePublished]").first().text() + "\n" +
        		"Réalisateur : " + document.select("div.infos [itemprop=director] [itemprop=name]").first().text() + "\n" +
        		"Acteurs : " + document.select("div.infos [itemprop=actors]").first().text().replace("Avec :", "").replace("...> Tout le casting", "") + "\n" +
        		"Durée : " + document.select("div.infos [itemprop=duration]").first().text());
		
		// synopsis du film
		TextView synopsisTextView = (TextView) filmFragment.getActivity().findViewById(R.id.film_synopsis);
		synopsisTextView.setText("Synopsis : " + document.select("div.description_cnt").first().text());
		
	}
	
	

}
