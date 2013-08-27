package org.zechocapic.mynicecinoche;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SeancesFragment extends Fragment {
	private OnFilmSelectedListener onFilmSelectedListener;
	private View inflatedView;
	private Context context;
	
	public interface OnFilmSelectedListener {
		public void onFilmSelected(String link);
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		inflatedView = inflater.inflate(R.layout.seances_view, container, false);
		context = getActivity();
		return inflatedView;
	}

	@Override
	public void onStart() {
		super.onStart();
		new RecupSeancesAsyncTask(this, inflatedView, context).execute("http://www.premiere.fr/horaire/cine/32619");
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		onFilmSelectedListener = null;
	}
	
	public void updateFilm(String url) {
		onFilmSelectedListener.onFilmSelected(url);
	}

}
