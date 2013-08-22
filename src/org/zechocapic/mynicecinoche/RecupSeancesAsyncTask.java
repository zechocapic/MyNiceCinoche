package org.zechocapic.mynicecinoche;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.widget.Toast;

public class RecupSeancesAsyncTask extends AsyncTask<String, Void, Document>{
	private SeancesListFragment seancesListFragment;
	
	public RecupSeancesAsyncTask(SeancesListFragment seancesListFragment) {
		this.seancesListFragment = seancesListFragment;
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
			Toast.makeText(seancesListFragment.getActivity(), "Erreur chargement de la page", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Elements blocs = document.select("div.bloc_salles_bloc");
		ArrayList<Element> blocsElement = new ArrayList<Element>();
		for (Element bloc : blocs) {
			blocsElement.add(bloc);
		}
		seancesListFragment.setListAdapter(new SeanceArrayAdapter(seancesListFragment.getActivity(), blocsElement));
	}

}