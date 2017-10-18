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
            //String imgB64 = (String) my_subcat.get(1);
            String imgB64 = "data:image/jpeg;base64,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";

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
