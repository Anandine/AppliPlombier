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

        Button b1 = (Button) this.findViewById(R.id.cat1);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, CatActivity.class);
                I.putExtra("Title", ((Button) v).getText().toString());
                startActivity(I);
            }
        });
        Button b2 = (Button) this.findViewById(R.id.cat2);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, CatActivity.class);
                I.putExtra("Title", ((Button) v).getText().toString());
                startActivity(I);
            }
        });
    }
}
