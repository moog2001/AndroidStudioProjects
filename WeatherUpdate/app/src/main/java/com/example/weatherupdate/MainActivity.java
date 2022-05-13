package com.example.weatherupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etCity, etCountry;
    TextView tvResult;
    WeatherListAdapter weatherListAdapter;
    Context context;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "136059e791e983fd386ffb62d796ea6a";
    DecimalFormat df = new DecimalFormat("#.##");
    RecyclerView rvOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        rvOne = findViewById(R.id.rvOne);

        String tempUrl = url + "?q=" + "Ulaanbaatar" + "," + "Mongolia" +  "&cnt="+ "7" +"&appid=" + appid;

//        "lon": 106.8832,
//                "lat": 47.9077

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
                Log.d("response", response.toString());
                weatherListAdapter = new WeatherListAdapter(context, response);
                rvOne.setAdapter(weatherListAdapter);
                rvOne.setLayoutManager(new LinearLayoutManager(context));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}