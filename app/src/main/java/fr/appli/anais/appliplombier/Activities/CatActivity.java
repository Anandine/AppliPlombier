package fr.appli.anais.appliplombier.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.appli.anais.appliplombier.R;
import fr.appli.anais.appliplombier.utilities.Json;

public class CatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        final String title = getIntent().getStringExtra("Title");
        TextView cat_title = (TextView) this.findViewById(R.id.cat_activity_title);
        if (title != null) {
            cat_title.setText(title);
        }

        //on récupère le json
        String monJson = Json.monJson;

        //on ajoute dynamiquement autant de boutons que de sous cat
        try {
            JSONObject jsonObject = new JSONObject(monJson);
            JSONArray cats = (JSONArray) jsonObject.getJSONArray("contenu");
            int num_cat = getIntent().getIntExtra("Num cat", 0);
            JSONArray subcats = ((JSONObject) cats.get(num_cat)).getJSONArray("contenu");
            for (int i = 0; i <= subcats.length(); i++) {;
                String btn_txt = ((JSONObject) subcats.get(i)).getString("titre");
                LinearLayout linear = (LinearLayout) findViewById(R.id.ln_content_cat);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                Button btn = new Button(this);
                btn.setId(i);
                Log.d("STATE", btn_txt);
                final int id_ = btn.getId();
                btn.setText(btn_txt);
                linear.addView(btn, params);
                final int finalI = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent I = new Intent(CatActivity.this, SousCatActivity.class);
                        I.putExtra("Title", ((Button) view).getText().toString());
                        I.putExtra("Cat", title);
                        I.putExtra("Num sous cat", finalI);
                        startActivity(I);
                    }
                });
                // Button myButton = new Button(this);
                // String btn = ((JSONArray) subcats.get(i)).get(0).toString();
                //myButton.setText(btn);
                //Log.d("STATE", btn);
            }
        } catch (JSONException je) {
            //je.printStackTrace();
            Log.d("STATE", je.toString());
        }
    }
}
