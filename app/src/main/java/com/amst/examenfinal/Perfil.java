package com.amst.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Perfil extends AppCompatActivity {
    public BarChart graficosBarras;
    private RequestQueue ListaRequest = null;
    private String token = "4167113613299027";
    private String busqueda;
    private TextView nombre_heroe;
    private TextView nombre_completo;
    private String id_real;
    private String id = "63";
    private ArrayList<String> powers = new ArrayList<String> ();
// Obtener el id
    // intent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        //set Powers
        powers.add("intelligence"); powers.add("strength"); powers.add("speed");
        powers.add("durability"); powers.add("power"); powers.add("combat");
        //Obtener el id del personaje.
        Intent intent = getIntent();
        id_real = intent.getStringExtra("id");

        iniciarGrafico();
        estadisticasHeroe();
    }


    public void iniciarGrafico(){
        //graficosBarras = findViewById();
        graficosBarras.getDescription().setEnabled(false);
        graficosBarras.setMaxVisibleValueCount(150);
        graficosBarras.setPinchZoom(false);
        graficosBarras.setDrawBarShadow(false);
        graficosBarras.setDrawGridBackground(false);

        XAxis xaxis = graficosBarras.getXAxis();
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setDrawAxisLine(false);


        graficosBarras.getAxisLeft().setDrawGridLines(false);
        graficosBarras.animateY(1500);
        graficosBarras.getLegend().setEnabled(false);
    }

    public void estadisticasHeroe(){
        String url_heroes = "https://www.superher|oapi.com/api.php/"+token+"/"+id+"/powerstats";
        JsonArrayRequest requestEstadisticas = new JsonArrayRequest(
                Request.Method.GET, url_heroes,null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response){
                //MostrarEstadisticas(response);
                actualizarGrafico(response);
            }}, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println(error);
                    }
        })
        {
            @Override
            public Map<String,String> getHeaders(){
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","JWT"+token);
                return params;
            }};
        ListaRequest.add(requestEstadisticas);
    }

    public void actualizarGrafico(JSONArray estadisticas){
        float power_value;
        ArrayList<BarEntry> graph_powers = new ArrayList<>();
        try {
            JSONObject registro_poderes = (JSONObject) estadisticas.get(0);
            for (int i = 0; powers.size() < i; i++) {
                String power = powers.get(i);
                String value = registro_poderes.getString(power);
                power_value = Float.parseFloat(value);
                graph_powers.add(new BarEntry(i,power_value));
            }
        } catch(JSONException e){
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    public void llenarGrafico(ArrayList<BarEntry> graph_powers){
        BarDataSet powersDataSet;
        if(graficosBarras.getData()!= null && graficosBarras.getData().getDataSetCount()>0){

        }
    }
}