package com.amst.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class Sugerencias extends AppCompatActivity {
    private String heroe;
    private RequestQueue ListaRequest ;
    private String url="https://superheroapi.com/api/4167113613299027";
    private LinearLayout ly;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencias);
        Intent intent= getIntent();
        heroe= intent.getStringExtra("sugerencia");
        ly= findViewById(R.id.ly);
        ListaRequest = Volley.newRequestQueue(this);
        buscarHeroes(heroe);
    }

    private void buscarHeroes(String heroe){
        JsonObjectRequest requestHeroes =  new JsonObjectRequest( Request.Method.GET,
                url+"/search/"+heroe, null,  new Response.Listener<JSONObject>()
        { @Override public void onResponse(JSONObject response) {
            llenarLayout(response);
        }
        }, error -> System.out.println(error));
        ListaRequest.add(requestHeroes);
    }

    private void llenarLayout(JSONObject response){

        try{

            JSONArray heroes = response.getJSONArray("results");
            pb= findViewById(R.id.progressBar);
            pb.setVisibility(View.GONE);
            TextView totalView= new TextView(getApplicationContext());
            Integer total= heroes.length();
            totalView.setText("Total de resultados: "+ total.toString());
            totalView.setTextSize(15);
            ly.addView(totalView);
            for(int i=0;i<heroes.length();i++){
                JSONObject heroe = (JSONObject) heroes.get(i);
                TextView tx= new TextView(getApplicationContext());
                tx.setText(heroe.getString("name"));
                String id= heroe.getString("id");
                tx.setTextSize(20);
                tx.setTextColor(Color.BLACK);
                tx.setOnClickListener(
                        v -> {
                            Intent intent= new Intent(getApplicationContext(),Perfil.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }
                );
                ly.addView(tx);
            }
        }catch (JSONException e){
            System.out.println(e);
        }
    }
}