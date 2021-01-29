package com.amst.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONArray;

public class Perfil extends AppCompatActivity {
    public BarChart graficosBarras;
    private RequestQueue ListaRequest = null;
    private String token = "";
    private String busqueda;
    private TextView nombre_heroe;
    private TextView nombre_real;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent intent= getIntent();

        iniciarGrafico();
        estadisticasHeroe();
    }


    public void iniciarGrafico(){
        //graficosBarras = findViewById();

    }

    public void estadisticasHeroe(){
        String url_heroes = "https://www.superheroapi.com/api.php/4167113613299027/";
        url_heroes = url_heroes+busqueda;

    }

}