package fr.appli.anais.appliplombier;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
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

        //récupérer le json test
        InputStream inputStream = getResources().openRawResource(R.raw.test);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        //on affiche le json
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.v("Text Data", byteArrayOutputStream.toString());

        try{
            String monJson = byteArrayOutputStream.toString();
            JSONObject jsonObject = new JSONObject(monJson);
            JSONArray subcats = jsonObject.getJSONArray(getIntent().getStringExtra("Cat"));
            int num_sous_cat = getIntent().getIntExtra("Num sous cat", 0);
            JSONArray my_subcat = (JSONArray) subcats.get(num_sous_cat);

            String text1 = (String) my_subcat.get(2);
            String text2 = (String) my_subcat.get(3);
            String imgfile = (String) my_subcat.get(1);
            // imgfile = imgfile.substring(0, imgfile.length() - 4);  // pour enlever l'extension
            Log.d("STATE", imgfile);

            int imgID = getResources().getIdentifier(imgfile, "drawable", getPackageName());
            ImageView image = (ImageView) findViewById(R.id.image);
            image.setImageResource(imgID);
            /*ImageView img = (ImageView) findViewById(R.id.image);
            //img.setImageResource(R.drawable.schema1);
            img.setImageResource(getResources().getIdentifier(imgfile, "drawable", getPackageName()));*/

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
