package fr.appli.anais.appliplombier;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.io.ByteArrayOutputStream;
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

        String title = getIntent().getStringExtra("Title");
        TextView cat_title = (TextView) this.findViewById(R.id.cat_activity_title);
        if (title != null){
            cat_title.setText(title);
        }

        //on récupère le json
        InputStream inputStream = getResources().openRawResource(R.raw.test);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
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
        }try{
            String monJson = byteArrayOutputStream.toString();
            JSONObject jsonObject = new JSONObject(monJson);
            JSONArray subcats = (JSONArray) jsonObject.getJSONArray(title);
            for (int i=0;i<subcats.length();i++){
                Button myButton = new Button(this);
                String btn = ((JSONArray) subcats.get(i)).get(0).toString();
                myButton.setText(btn);
                Log.d("STATE", btn);
            }

        } catch (JSONException je)
        {
            //je.printStackTrace();
            Log.d("STATE", je.toString());
        }
        

        Button sb1 = (Button) this.findViewById(R.id.sous_cat1);
        sb1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent I = new Intent(CatActivity.this, SousCatActivity.class);
                I.putExtra("Title", ((Button) v).getText().toString());
                I.putExtra("Num sous cat", 1);
                startActivity(I);
            }
        });
        Button sb2 = (Button) this.findViewById(R.id.sous_cat2);
        sb2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent I = new Intent(CatActivity.this, SousCatActivity.class);
                I.putExtra("Title", ((Button) v).getText().toString());
                I.putExtra("Num sous cat", 2);
                startActivity(I);
            }
        });
    }

}
