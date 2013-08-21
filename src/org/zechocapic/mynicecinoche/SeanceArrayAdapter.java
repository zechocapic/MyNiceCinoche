package org.zechocapic.mynicecinoche;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SeanceArrayAdapter extends ArrayAdapter<String>{
	private Context context;
	private String[] values;

	public SeanceArrayAdapter(Context context, String[] values) {
		super(context, R.layout.row_layout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = layoutInflater.inflate(R.layout.row_layout, parent, false);
		TextView twFilm = (TextView) rowView.findViewById(R.id.text1);
		TextView twVersion = (TextView) rowView.findViewById(R.id.text2);
		String s = values[position];
		twFilm.setText(s);
		twVersion.setText("VO");
		return rowView;
	}

}
