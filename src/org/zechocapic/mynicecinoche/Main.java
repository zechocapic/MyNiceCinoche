package org.zechocapic.mynicecinoche;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Main extends Activity implements SeancesFragment.OnFilmSelectedListener {
	private final static boolean LANDSCAPE = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// choice of orientation
		if (LANDSCAPE) {
			setContentView(R.layout.seances_films_landscape);
		} else {
			setContentView(R.layout.seances_films_portrait);
		}
		
		// check activity layout and act accordingly
		if (findViewById(R.id.fragment_container) != null) {
			SeancesFragment seancesFragment = new SeancesFragment();
			seancesFragment.setArguments(getIntent().getExtras());
			
			getFragmentManager().beginTransaction().add(R.id.fragment_container, seancesFragment).commit();
		}
		
	}

	@Override
	public void onSeancesFilmSelected(String link) {
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
			fragmentTransaction.replace(R.id.fragment_container, newFilm);
			fragmentTransaction.addToBackStack(null);
			
			fragmentTransaction.commit();
		}
		
	}

	/*@Override
	public void onFilmSelected(int position, String tag) {
		//check if film_fragment exists
		FilmFragment filmFragment = (FilmFragment) getFragmentManager().findFragmentById(R.id.film_fragment);
		if (filmFragment != null) {
			// if it exists we re in landscape, so we update film fragment
			filmFragment.updateFilmView(position, tag);
		}
		// if it does not exist we re in portrait mode, so we replace the seances_listfragment by a film_fragment
		else {
			FilmFragment newFilm = new FilmFragment();
			Bundle args = new Bundle();
			args.putInt(FilmFragment.ARG_POSITION, position);
			args.putString(FilmFragment.ARG_URL, tag);
			newFilm.setArguments(args);
			
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.replace(R.id.fragment_container, newFilm);
			fragmentTransaction.addToBackStack(null);
			
			fragmentTransaction.commit();
		}
		
	}*/
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

}
