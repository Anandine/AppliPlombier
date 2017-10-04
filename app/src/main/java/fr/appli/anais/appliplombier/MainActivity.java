package fr.appli.anais.appliplombier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //on déclare le bouton d'id cat1 qu'on stoque dans la variable Button b1
        Button b1 = (Button) this.findViewById(R.id.cat1);
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
        });
    }
}
