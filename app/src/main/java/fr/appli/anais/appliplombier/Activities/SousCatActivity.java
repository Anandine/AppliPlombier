package fr.appli.anais.appliplombier.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import fr.appli.anais.appliplombier.R;
import fr.appli.anais.appliplombier.utilities.Json;

public class SousCatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sous_cat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //récupérer le nom de la sous catégorie
        String title = getIntent().getStringExtra("Title");
        TextView cat_title = (TextView) this.findViewById(R.id.sous_cat_activity_title);
        if (title != null){
            cat_title.setText(title);
        }

        try{
            String monJson = Json.monJson;
            JSONObject jsonObject = new JSONObject(monJson);
            JSONArray subcats = jsonObject.getJSONArray(getIntent().getStringExtra("Cat"));
            int num_sous_cat = getIntent().getIntExtra("Num sous cat", 0);
            JSONArray my_subcat = (JSONArray) subcats.get(num_sous_cat);

            String text1 = (String) my_subcat.get(2);
            String text2 = (String) my_subcat.get(3);
            String imgB64 = (String) my_subcat.get(1);

            Log.d("STATE_IMG64", imgB64);
            byte[] monImage = Base64.decode(imgB64, Base64.DEFAULT);
            Bitmap monBitmap = BitmapFactory.decodeByteArray(monImage, 0, monImage.length);
            ImageView image = (ImageView) findViewById(R.id.image);
            image.setImageBitmap(monBitmap);

            // on affiche le premier texte de chaque sous-catégorie
            TextView cat_summary = (TextView) this.findViewById(R.id.summary);
            cat_summary.setMovementMethod(new ScrollingMovementMethod());
            if (text1 != null){
                cat_summary.setText(text1);
            }
            TextView cat_details = (TextView) this.findViewById(R.id.details);
            cat_details.setMovementMethod(new ScrollingMovementMethod());
            if (text2 != null){
                cat_details.setText(text2);
            }
        } catch (JSONException je)
        {
            //je.printStackTrace();
            Log.d("STATE", je.toString());
        }

    }
}
