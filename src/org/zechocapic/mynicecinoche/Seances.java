package org.zechocapic.mynicecinoche;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Seances extends ListFragment {
	OnSeanceSelectedListener mCallback;
	static String[] Seances = { "Seance un", "Seance deux", "Seance trois" };
	
	public interface OnSeanceSelectedListener {
		public void onFilmSelected(int position);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new SeanceArrayAdapter(getActivity(), Seances));
		//setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Seances));
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
		mCallback.onFilmSelected(position);
		getListView().setItemChecked(position, true);
	}

}
