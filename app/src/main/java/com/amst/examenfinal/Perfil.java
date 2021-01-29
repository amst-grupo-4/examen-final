package com.amst.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

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
    private ArrayList<String> powers = new ArrayList<String> ();
// Obtener el id
    // intent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        //setText
        nombre_heroe=(TextView) findViewById(R.id.name);
        nombre_completo=(TextView) findViewById(R.id.real_name);
        //set Powers
        powers.add("intelligence"); powers.add("strength"); powers.add("speed");
        powers.add("durability"); powers.add("power"); powers.add("combat");
        //Request
        ListaRequest= Volley.newRequestQueue(this);
        //Obtener el id del personaje.
        Intent intent = getIntent();
        id_real = intent.getStringExtra("id");
        Log.e("TAG",id_real);

        iniciarGrafico();
        obtenerNombre();
        estadisticasHeroe();
    }


    public void iniciarGrafico(){
        graficosBarras = findViewById(R.id.barChart);
        graficosBarras.getDescription().setEnabled(false);
        graficosBarras.setMaxVisibleValueCount(150);
        graficosBarras.setPinchZoom(false);
        graficosBarras.setDrawBarShadow(false);
        graficosBarras.setDrawGridBackground(false);

        XAxis xaxis = graficosBarras.getXAxis();
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setDrawAxisLine(false);
        xaxis.setLabelRotationAngle(-90);

        ArrayList<String> axis= new ArrayList<>();
        for(int i=0; i<powers.size(); i++) {
            axis.add(powers.get(i));
        }
        xaxis.setValueFormatter(new IndexAxisValueFormatter(axis));

        graficosBarras.getAxisLeft().setDrawGridLines(false);
        graficosBarras.animateY(1500);
        graficosBarras.getLegend().setEnabled(false);
    }

    public void obtenerNombre(){
        String url_heroes = "https://www.superheroapi.com/api.php/4167113613299027/"+id_real+"/biography";
        JsonObjectRequest requestNombre = new JsonObjectRequest(
                Request.Method.GET, url_heroes,null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response){
                actualizarNombres(response);
            }}, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                System.out.println(error);
            }
        });
        ListaRequest.add(requestNombre);
    }

    public void actualizarNombres(JSONObject nombres){
        try {
            nombre_heroe.setText(nombres.getString("name"));
            nombre_completo.setText(nombres.getString("full-name"));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void estadisticasHeroe(){
        String url_heroes = "https://www.superheroapi.com/api.php/4167113613299027/"+id_real+"/powerstats";
        JsonObjectRequest requestEstadisticas = new JsonObjectRequest(
                Request.Method.GET, url_heroes,null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response){
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

    public void actualizarGrafico(JSONObject estadisticas){
        float power_value;
        int i;
        ArrayList<BarEntry> graph_powers = new ArrayList<>();
        try {

            for(i=0; i<powers.size(); i++) {
                String power = powers.get(i);
                String value = estadisticas.getString(power);
                try{
                    power_value = Float.parseFloat(value);
                } catch (Exception e){
                    power_value = 0;
                }
                graph_powers.add(new BarEntry(i,power_value));
            }
        } catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        llenarGrafico(graph_powers);
    }

    public void llenarGrafico(ArrayList<BarEntry> graph_powers){
        BarDataSet powersDataSet;
        if(graficosBarras.getData() != null && graficosBarras.getData().getDataSetCount()>0){
            powersDataSet = (BarDataSet) graficosBarras.getData().getDataSetByIndex(0);
            powersDataSet.setValues(graph_powers);
            graficosBarras.getData().notifyDataChanged();
            graficosBarras.notifyDataSetChanged();
        } else {
            powersDataSet = new BarDataSet (graph_powers,"Data Set");
            powersDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            powersDataSet.setDrawValues(true);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(powersDataSet);

            BarData data = new BarData(dataSets);
            graficosBarras.setData(data);
            graficosBarras.setFitBars(true);
        }
        graficosBarras.invalidate();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                estadisticasHeroe();
            }
        };
        handler.postDelayed(runnable,3000);
    }
}