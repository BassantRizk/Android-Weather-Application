package com.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CitiyActivity extends AppCompatActivity {
    private CityListAdapter.RecyclerlickListener listener ;
    int min1 = 0;
    int max1 = 15;
    double random1= new Random().nextInt((max1 - min1) + 1) + min1;
    int min2 = 15;
    int max2 = 25;
    int random2= new Random().nextInt((max2 - min2) + 1) + min2;
    int min3 = 25;
    int max3 = 30;
    int random3 = new Random().nextInt((max3 - min3) + 1) + min3;


    ArrayList<CityInfo> cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citiy);


        RecyclerView city_rcyclr = (RecyclerView)findViewById(R.id.city_list);
        city_rcyclr.setLayoutManager(new LinearLayoutManager(this));
        cities=new ArrayList<>();
        cities.add(new CityInfo("Cairo",""));
        cities.add(new CityInfo("Alex",""));
        cities.add(new CityInfo("Luxor",""));


        setOnClickListener();
        CityListAdapter cityListAdapter = new CityListAdapter(cities,this,listener);
        city_rcyclr.setAdapter(cityListAdapter);
        cityListAdapter.notifyDataSetChanged();


    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("city",cities.get(position).city);
          //  intent.putExtra("rand",cities.get(position).random);
            startActivity(intent);
        };
    }
}