package fr.appli.anais.appliplombier;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*String in="";
        JSONObject reader = new JSONObject(in);
        try {
            JSONArray sys  = reader.getJSONArray("sous cat1");
            String text1_cat1 = sys.get(1).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        /*Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1){
               writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        String json = writer.toString();*/
        String json = null;
        try{
            InputStream is = getResources().openRawResource(R.raw.test);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            JSONObject jsonObject = new JSONObject(buffer.toString());
            JSONArray subcats = jsonObject.getJSONArray("sous cat");
            for (int i=0; i < subcats.length(); i++){
                JSONArray my_subcat = (JSONArray) subcats.get(i);

                // on affiche le premier texte de chaque sous-catégorie
                Context context = getApplicationContext();
                CharSequence text = (CharSequence) my_subcat.get(1);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        } catch (IOException ex){
            ex.printStackTrace();
        } catch (JSONException je) {
            je.printStackTrace();
        }
        //return json;
    }
}
