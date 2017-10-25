package fr.appli.anais.appliplombier.utilities;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.appli.anais.appliplombier.R;

public class MyAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;

    public MyAdapter(Context context, ArrayList<String> values) {
        super(context, 0, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }
        TextView itemText = (TextView) convertView.findViewById(R.id.itemText);
        itemText.setText(values.get(position));
        return convertView;

    }
}
