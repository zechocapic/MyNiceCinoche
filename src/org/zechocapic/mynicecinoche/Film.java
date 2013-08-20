package org.zechocapic.mynicecinoche;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Film extends Fragment {
	static String Films[] = { "Film un", "Film deux"};
	final static String ARG_POSITION = "position";
	int mCurrentPosition = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}
		return inflater.inflate(R.layout.film_view, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		
		Bundle args = getArguments();
		if (args != null) {
			updateFilmView(args.getInt(ARG_POSITION));
		} else if (mCurrentPosition != -1) {
			updateFilmView(mCurrentPosition);
		}
	}

	public void updateFilmView(int position) {
		TextView filmTextView = (TextView) getActivity().findViewById(R.id.film_fragment);
		filmTextView.setText(Films[position]);
		mCurrentPosition = position;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putInt(ARG_POSITION, mCurrentPosition);
	}
	
	

}
