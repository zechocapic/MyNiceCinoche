package org.zechocapic.mynicecinoche;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Main extends Activity implements Seances.OnSeanceSelectedListener {
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
			if (savedInstanceState != null) {
				return;
			}
			
			Seances seances = new Seances();
			seances.setArguments(getIntent().getExtras());
			
			getFragmentManager().beginTransaction().add(R.id.fragment_container, seances).commit();
		}
		
	}

	@Override
	public void onFilmSelected(int position) {
		Film film = (Film) getFragmentManager().findFragmentById(R.id.film_fragment);
		
		if (film != null) {
			film.updateFilmView(position);
		} else {
			Film newFilm = new Film();
			Bundle args = new Bundle();
			args.putInt(Film.ARG_POSITION, position);
			newFilm.setArguments(args);
			
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.replace(R.id.fragment_container, newFilm);
			fragmentTransaction.addToBackStack(null);
			
			fragmentTransaction.commit();
		}
		
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

}
