package org.zechocapic.mynicecinoche;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

public class Main extends Activity implements SeancesFragment.OnFilmSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// detect orientation and launch suitable layout
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			setContentView(R.layout.main_activity_landscape);
		} else {
			setContentView(R.layout.main_activity_portrait);
		}
		
		// if activity layout is portrait, launch seancesFragment manually
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
			// if it exists we are in landscape mode, so we update film fragment
			filmFragment.updateFilmView(link);
		}
		// if it does not exist we are in portrait mode, so we replace the seances_listfragment by a film_fragment
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
