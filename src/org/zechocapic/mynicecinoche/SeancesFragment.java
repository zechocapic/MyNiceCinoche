package org.zechocapic.mynicecinoche;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SeancesFragment extends Fragment {
	private OnFilmSelectedListener onFilmSelectedListener;
	
	public interface OnFilmSelectedListener {
		public void onSeancesFilmSelected(String link);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.seances_view, container, false);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnFilmSelectedListener) {
			onFilmSelectedListener = (OnFilmSelectedListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + " must implement SeancesFragment.OnFilmSelectedListener");
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		onFilmSelectedListener = null;
	}

	@Override
	public void onStart() {
		super.onStart();
		new RecupSeancesAsyncTask(this).execute("http://www.premiere.fr/horaire/cine/32619");
	}
	
	public void updateFilm(String url) {
		onFilmSelectedListener.onSeancesFilmSelected(url);
	}

}
