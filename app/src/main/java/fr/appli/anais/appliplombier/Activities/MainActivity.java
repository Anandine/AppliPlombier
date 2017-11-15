package fr.appli.anais.appliplombier.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.appli.anais.appliplombier.R;
import fr.appli.anais.appliplombier.utilities.Json;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check si l'appareil est connecté
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Json.isConnected(cm)) {
            Log.d("STATE", "You are connected");

        } else {
            Log.d("STATE", "Not connected");
        }

        //on récupère le json
        String monJson = Json.recupJSON(getApplicationContext());

        //on ajoute dynamiquement autant de boutons que de catégories
        try {
            JSONObject jsonObject = new JSONObject(monJson);
            JSONArray cats = jsonObject.getJSONArray("contenu");
            for (int i = 0; i <= cats.length(); i++) {
                String btn_txt = ((JSONObject) cats.get(i)).getString("titreCat");
                LinearLayout linear = (LinearLayout) findViewById(R.id.main_layout);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                Button btn = new Button(this);
                btn.setId(i);
                btn.setText(btn_txt);
                linear.addView(btn, params);
                final int finalI = i;
                Log.d("WOW", btn_txt);
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent I = new Intent(MainActivity.this, CatActivity.class);
                        I.putExtra("Title", ((Button) view).getText().toString());
                        I.putExtra("Num cat", finalI);
                        startActivity(I);
                    }
                });
            }
        } catch (JSONException je) {
            //je.printStackTrace();
            Log.d("STATE", je.toString());
        } catch (Exception e) {
            Log.d("EXCEPTION", "Problème lors de l'affichage des boutons : " + e.toString());
        }

        // call AsynTask to perform network operation on separate thread
        new Json.HttpAsyncTask().execute("https://hyppo.neocities.org/bd.json");
    }
}
