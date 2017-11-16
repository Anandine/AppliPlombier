package fr.appli.anais.appliplombier.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final String title = getIntent().getStringExtra("Title");
        final int num_cat = getIntent().getIntExtra("Num cat", 0);

        TextView cat_title = this.findViewById(R.id.cat_activity_title);
        if (title != null) {
            cat_title.setText(title);
        }

        //on récupère le json
        String monJson = Json.recupJSON(getApplicationContext());
        ArrayList<String> mesSubCatsTexts = new ArrayList<>();
        ArrayList<String> mesSubCatsImages = new ArrayList<>();

        //on ajoute dynamiquement autant d'entrée dans la collection qu'il y a de sous-catégories
        try {
            JSONObject jsonObject = new JSONObject(monJson);
            JSONArray cats = jsonObject.getJSONArray("contenu");
            JSONArray subcats = ((JSONObject) cats.get(num_cat)).getJSONArray("contenu");
            for (int i = 0; i <= subcats.length(); i++) {
                mesSubCatsTexts.add(((JSONObject) subcats.get(i)).getString("titre"));
                mesSubCatsImages.add(((JSONObject) subcats.get(i)).getString("image"));
            }
        } catch (JSONException je) {
            //je.printStackTrace();
            Log.d("STATE", je.toString());
        }

        ListView maListView = findViewById(R.id.catListView);
        MyAdapter adapter = new MyAdapter(getApplicationContext(), mesSubCatsTexts, mesSubCatsImages);
        maListView.setAdapter(adapter);

        maListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent I = new Intent(CatActivity.this, SousCatActivity.class);
                I.putExtra("Cat", title);
                I.putExtra("Num cat", num_cat);
                I.putExtra("Num sous cat", position);
                startActivity(I);
            }
        });
    }
}
