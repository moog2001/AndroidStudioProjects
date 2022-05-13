package com.example.weatherupdate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder> {

    private String mResponse;
    private LayoutInflater mInflater;
    private LinkedList<DayTempItem> mItemList = new LinkedList<>();

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemDay;
        public TextView tvItemTemp;
        final WeatherListAdapter mAdapter;


        public WeatherViewHolder(View itemView, WeatherListAdapter adapter) {
            super(itemView);
            tvItemDay = itemView.findViewById(R.id.tvDay);
            tvItemTemp = itemView.findViewById(R.id.tvTemp);
            this.mAdapter = adapter;
        }


    }

    public WeatherListAdapter(Context context,
                              String response) {
        mInflater = LayoutInflater.from(context);
        this.mResponse = response;
        DecimalFormat df = new DecimalFormat("#.##");
        String output = "";

        try {
            JSONObject jsonResponse = new JSONObject(response);

            int size = jsonResponse.length();

            for (int i = 0; i < size; i++) {

                JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                String description = jsonObjectWeather.getString("description");
                JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                double temp = jsonObjectMain.getDouble("temp") - 273.15;
                double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                float pressure = jsonObjectMain.getInt("pressure");
                int humidity = jsonObjectMain.getInt("humidity");
                JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                String wind = jsonObjectWind.getString("speed");
                JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                String clouds = jsonObjectClouds.getString("all");
                JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                String countryName = jsonObjectSys.getString("country");
                String cityName = jsonResponse.getString("name");
                String dateRaw = jsonResponse.getString("dt");
                output += "Current weather of " + cityName + " (" + countryName + ")"
                        + "\n Temp: " + df.format(temp) + " °C"
                        + "\n Feels Like: " + df.format(feelsLike) + " °C"
                        + "\n Humidity: " + humidity + "%"
                        + "\n Description: " + description
                        + "\n Wind Speed: " + wind + "m/s (meters per second)"
                        + "\n Cloudiness: " + clouds + "%"
                        + "\n Pressure: " + pressure + " hPa";
                Log.d("success", output);

                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
                try {
                    Date date = originalFormat.parse(dateRaw);
                    DayTempItem tempItem = new DayTempItem(String.valueOf(temp), date.toString());
                    mItemList.add(tempItem);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }


        } catch (JSONException e) {
            Log.d("error", e.toString());
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(
                R.layout.weather_item, parent, false);
        return new WeatherViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        DayTempItem item = mItemList.get(position);

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy/MM/dd");
        holder.tvItemDay.setText(item.getDay());
        holder.tvItemTemp.setText(item.getTemp());

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
