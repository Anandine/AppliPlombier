package fr.appli.anais.appliplombier.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.appli.anais.appliplombier.R;
import fr.appli.anais.appliplombier.utilities.Json;
import fr.appli.anais.appliplombier.utilities.MyAdapter;

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
        ArrayList<String> mesSubCats = new ArrayList<>();

        //on ajoute dynamiquement autant de boutons que de sous cat
        try {
            JSONObject jsonObject = new JSONObject(monJson);
            JSONArray cats = (JSONArray) jsonObject.getJSONArray("contenu");
            int num_cat = getIntent().getIntExtra("Num cat", 0);
            JSONArray subcats = ((JSONObject) cats.get(num_cat)).getJSONArray("contenu");
            for (int i = 0; i <= subcats.length(); i++) {;
                mesSubCats.add(((JSONObject) subcats.get(i)).getString("titre"));
            }
        } catch (JSONException je) {
            //je.printStackTrace();
            Log.d("STATE", je.toString());
        }

        ListView maListView = (ListView) findViewById(R.id.catListView);
        MyAdapter adapter = new MyAdapter(this, mesSubCats);
        maListView.setAdapter(adapter);

        maListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
