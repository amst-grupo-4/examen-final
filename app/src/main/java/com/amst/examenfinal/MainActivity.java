package com.amst.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText heroeFiltro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        heroeFiltro= findViewById(R.id.heroeInsertar);
        setContentView(R.layout.activity_main);
    }


    public void irSugerencias(View view){
        String heroe= heroeFiltro.getText().toString();
        Intent intent= new Intent(this.getApplicationContext(),Sugerencias.class);
        intent.putExtra("sugerencia",heroe);
        startActivity(intent);
    }
}