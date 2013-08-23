package org.zechocapic.mynicecinoche;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FilmFragment extends Fragment {
	final static String ARG_URL = "position";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.film_view, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		Bundle bundle = getArguments();
		if (bundle != null) {
			updateFilmView(bundle.getString(ARG_URL));
		}
	}

	public void updateFilmView(String tag) {
		new RecupInfosAsyncTask(this).execute(tag);
	}

}
