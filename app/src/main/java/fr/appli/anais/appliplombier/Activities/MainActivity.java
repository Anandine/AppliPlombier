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

        // check if you are connected or not
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Json.isConnected(cm)) {
            Log.d("STATE", "You are connected");
            /*Context context = getApplicationContext();
            CharSequence text = "You are connected";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();*/

        } else {
            Log.d("STATE", "Not connected");
        }

        //on récupère le json
        String monJson = Json.monJson;

        //on ajoute dynamiquement autant de boutons que de sous cat
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
                final int id_ = btn.getId();
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
        //on déclare le bouton d'id cat1 qu'on stoque dans la variable Button b1
        /*Button b1 = (Button) this.findViewById(R.id.cat1);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //on relie les deux activités
                Intent I = new Intent(MainActivity.this, CatActivity.class);
                //on change le texte du titre de l'écran suivant avec le nom du bouton b1
                I.putExtra("Title", ((Button) v).getText().toString());
                I.putExtra("Num cat", 1);
                //on change d'écran au clic du bouton après avoir changé le titre
                startActivity(I);
            }
        });
        Button b2 = (Button) this.findViewById(R.id.cat2);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, CatActivity.class);
                //ou
                //Intent I = new Intent();
                //intent.getClass(this, CatActivity.class);
                I.putExtra("Title", ((Button) v).getText().toString());
                I.putExtra("Num cat", 2);
                startActivity(I);
            }
        });
        Button b3 = (Button) this.findViewById(R.id.cat3);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, CatActivity.class);
                I.putExtra("Title", ((Button) v).getText().toString());
                I.putExtra("Num cat", 3);
                startActivity(I);
            }
        });
        Button b4 = (Button) this.findViewById(R.id.cat4);
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, CatActivity.class);
                I.putExtra("Title", ((Button) v).getText().toString());
                I.putExtra("Num cat", 4);
                startActivity(I);
            }
        });*/
        // call AsynTask to perform network operation on separate thread
        new Json.HttpAsyncTask().execute("https://hyppo.neocities.org/bd.json");
    }
}
