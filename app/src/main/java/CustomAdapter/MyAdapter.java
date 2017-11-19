package CustomAdapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.appli.anais.appliplombier.R;

public class MyAdapter extends ArrayAdapter<String> {
    private final ArrayList<String> texts;
    private final ArrayList<String> images;

    public MyAdapter(Context context, ArrayList<String> texts, ArrayList<String> images) {
        super(context, 0, texts);
        this.texts = texts;
        this.images = images;
    }

    @Override
    public int getCount() {
        return texts.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }
        TextView itemText = convertView.findViewById(R.id.itemText);
        itemText.setText(texts.get(position));

        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        byte[] monImage = Base64.decode(images.get(position), Base64.DEFAULT);
        Bitmap monBitmap = BitmapFactory.decodeByteArray(monImage, 0, monImage.length);
        itemImage.setImageBitmap(monBitmap);
        return convertView;

    }
}
