package org.zechocapic.mynicecinoche;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SeancesListFragment extends ListFragment {
	OnSeanceSelectedListener mCallback;
	static String[] Seances = { "Seance un", "Seance deux", "Seance trois" };
	
	public interface OnSeanceSelectedListener {
		public void onFilmSelected(int position, String tag);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new RecupSeancesAsyncTask(this).execute("http://www.premiere.fr/horaire/cine/32619");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		if (getFragmentManager().findFragmentById(R.id.film_fragment) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			mCallback = (OnSeanceSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnSeanceSelectedListened");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCallback.onFilmSelected(position, (String) v.getTag());
		getListView().setItemChecked(position, true);
	}

}
