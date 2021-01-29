package com.amst.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText heroeFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }


    public void irSugerencias(View view){

        heroeFiltro= findViewById(R.id.heroeInsertar);
        String heroe= heroeFiltro.getText().toString();
        if(heroe.length()>0) {
            Intent intent = new Intent(this.getApplicationContext(), Sugerencias.class);
            intent.putExtra("sugerencia", heroe);
            startActivity(intent);
        }
        else {
            CharSequence text = "Por favor ingrese al menos un caracter";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
    }

}