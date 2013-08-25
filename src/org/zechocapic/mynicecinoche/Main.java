package org.zechocapic.mynicecinoche;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class Main extends Activity implements SeancesFragment.OnFilmSelectedListener {
	private final static boolean LANDSCAPE = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// choice of orientation
		if (LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.main_activity_landscape);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.main_activity_portrait);
		}
		
		// check activity layout and act accordingly
		if (findViewById(R.id.fragment_container) != null) {
			SeancesFragment seancesFragment = new SeancesFragment();
			seancesFragment.setArguments(getIntent().getExtras());
			
			getFragmentManager().beginTransaction().add(R.id.fragment_container, seancesFragment).commit();
		}
		
	}

	@Override
	public void onFilmSelected(String link) {
		//check if film_fragment exists
		FilmFragment filmFragment = (FilmFragment) getFragmentManager().findFragmentById(R.id.film_fragment);
		if (filmFragment != null) {
			// if it exists we re in landscape, so we update film fragment
			filmFragment.updateFilmView(link);
		}
		// if it does not exist we re in portrait mode, so we replace the seances_listfragment by a film_fragment
		else {
			FilmFragment newFilm = new FilmFragment();
			Bundle args = new Bundle();
			args.putString(FilmFragment.ARG_URL, link);
			newFilm.setArguments(args);
			
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right, R.animator.slide_out_to_left);
			fragmentTransaction.replace(R.id.fragment_container, newFilm);
			fragmentTransaction.addToBackStack(null);
			
			fragmentTransaction.commit();
		}
		
	}

}
