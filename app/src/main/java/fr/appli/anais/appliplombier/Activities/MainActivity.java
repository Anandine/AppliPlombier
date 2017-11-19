package fr.appli.anais.appliplombier.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

            // on previent l'utilisateur d'une fonctionnalité, la première fois qu'il est connecté
            SharedPreferences settings = getPreferences(MODE_PRIVATE);
            boolean tip = settings.getBoolean("tip", false);
            if(!tip) {
                Toast.makeText(getApplicationContext(),"vous pouvez mettre à jour les catégories en cliquant sur le titre",
                        Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("tip", true);  // on se rappelle que le tip a déjà été affiché
                editor.apply();  // sauvegarde en arrière plan
            }
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
                LinearLayout linear = findViewById(R.id.main_layout);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(30, 30, 30, 0);
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

        // on permet au titre d'être clickable pour mettre à jour les catégories
        TextView title = findViewById(R.id.main_title);
        title.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // permet à l'appli d'afficher les derniers éléments téléchargés
                Intent I = new Intent(MainActivity.this, SplashScreen.class);
                startActivity(I);
                MainActivity.this.finish();
            }
        });
    }
}
