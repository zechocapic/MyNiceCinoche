package org.zechocapic.mynicecinoche;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FilmFragment extends Fragment {
	static String Films[] = { "Film un", "Film deux", "Film trois" };
	final static String ARG_POSITION = "position";
	final static String ARG_URL = "position";
	int mCurrentPosition = -1;
	String mCurrenString = "kedalle";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
			mCurrenString = savedInstanceState.getString(ARG_URL);
		}
		return inflater.inflate(R.layout.film_view, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		
		Bundle args = getArguments();
		if (args != null) {
			updateFilmView(args.getInt(ARG_POSITION), args.getString(ARG_URL));
		} else if (mCurrentPosition != -1) {
			updateFilmView(mCurrentPosition, null);
		}
	}

	public void updateFilmView(int position, String tag) {
		new RecupInfosAsyncTask(this).execute(tag);
		mCurrentPosition = position;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putInt(ARG_POSITION, mCurrentPosition);
	}
	
	

}
