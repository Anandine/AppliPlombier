package fr.appli.anais.appliplombier;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

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
        String monJson = recupJSON();

        try {
            //on ajoute dynamiquement autant de boutons que de sous cat
            JSONObject jsonObject = new JSONObject(monJson);
            JSONArray subcats = (JSONArray) jsonObject.getJSONArray(title);
            for (int i = 0; i <= subcats.length(); i++) {
                String btn_txt = ((JSONArray) subcats.get(i)).get(0).toString();
                LinearLayout linear = (LinearLayout) findViewById(R.id.ln_content_cat);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                Button btn = new Button(this);
                btn.setId(i);
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

    String recupJSON(){
        String res = "";
        try{
            File dcim = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "test.json");
            BufferedReader br = new BufferedReader(new FileReader(dcim));
            String line = br.readLine();
            while(line != null){
                res += line;
                line = br.readLine();
            }
        }catch(FileNotFoundException fe){
            Log.d("STATE", "recupJson failed");
        }catch(IOException ie){
            Log.d("STATE", "recupJson failed");
        }
        return res;
    }

}
