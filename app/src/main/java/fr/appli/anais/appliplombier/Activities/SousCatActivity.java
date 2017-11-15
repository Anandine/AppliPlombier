package fr.appli.anais.appliplombier.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.appli.anais.appliplombier.R;
import fr.appli.anais.appliplombier.utilities.Json;

public class SousCatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sous_cat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            String monJson = Json.recupJSON(getApplicationContext());
            JSONObject jsonObject = new JSONObject(monJson);
            JSONArray cat = jsonObject.getJSONArray("contenu");
            int num_cat = getIntent().getIntExtra("Num cat", 0);
            int num_sous_cat = getIntent().getIntExtra("Num sous cat", 0);
            JSONArray subcats = ((JSONObject) cat.get(num_cat)).getJSONArray("contenu");
            JSONObject subcat = (JSONObject) subcats.get(num_sous_cat);

            String title = subcat.getString("titre");
            String presentation = subcat.getString("presentation");
            String description = subcat.getString("description");
            String imgB64 = subcat.getString("image");

            TextView cat_title = (TextView) this.findViewById(R.id.sous_cat_activity_title);
            if (title != null) {
                cat_title.setText(title);
            }
            // on affiche le premier texte de chaque sous-catégorie
            TextView cat_summary = (TextView) this.findViewById(R.id.summary);
            cat_summary.setMovementMethod(new ScrollingMovementMethod());
            if (presentation != null) {
                cat_summary.setText(presentation);
            }
            TextView cat_details = (TextView) this.findViewById(R.id.details);
            cat_details.setMovementMethod(new ScrollingMovementMethod());
            if (description != null) {
                cat_details.setText(description);
            }

            byte[] monImage = Base64.decode(imgB64, Base64.DEFAULT);
            Bitmap monBitmap = BitmapFactory.decodeByteArray(monImage, 0, monImage.length);
            ImageView image = (ImageView) findViewById(R.id.image);
            image.setImageBitmap(monBitmap);
        } catch (JSONException je) {
            //je.printStackTrace();
            Log.d("STATE", je.toString());

            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "erreur de récupération des données", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
