package com.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements LocationListener {
    static final String URL = "https://api.openweathermap.org/data/2.5/weather?q=cairo,eg,eg&appid=5591cb3fcc2d6c4445455fd59b39b5be&units=imperial/";
    String apiKey="5591cb3fcc2d6c4445455fd59b39b5be";
    String units="imperial";

    LocationManager locationManager;
    String provider;

    int temp;
    int humidity;
    int pressure;
    String city="";
    TextView date_time ;
    TextView weatherTxt ;
    TextView humidityTxt;
    TextView pressureTxt ;
    TextView degreeTxt ;
    ImageView weatherImg ;
    TextView countryTxt ;
    double longitude,latitude;
    Weather weather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;
         date_time = (TextView) findViewById(R.id.dateTime_txt);
         weatherTxt = (TextView) findViewById(R.id.weather_txt);
         humidityTxt = (TextView) findViewById(R.id.humidity_txt);
         pressureTxt = (TextView) findViewById(R.id.pressure_txt);
         degreeTxt = (TextView) findViewById(R.id.temprature_txt);
         weatherImg = (ImageView) findViewById(R.id.weather_img);
         countryTxt = (TextView) findViewById(R.id.country_txt);
        Random rand = new Random();
        SharedPreferences pref = MainActivity.this.getSharedPreferences("pref",0);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = LocationManager.GPS_PROVIDER;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider,0,0,this);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            city= extras.getString("city");

        }
/*
       Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        System.out.println("start");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIInterface gerritAPI = retrofit.create(APIInterface.class);
        Call<APIModel> call = gerritAPI.getTemp(city,apiKey,units);
        call.enqueue(new Callback<APIModel>() {
            @Override
            public void onResponse(Call<APIModel> call, Response<APIModel> response) {
                if(response.isSuccessful())
                {
                    APIModel model = response.body();
                    temp=(int)model.main.temp;
                    humidity=model.main.humidity;
                    pressure=model.main.pressure;
                    System.out.println(temp);
                    degreeTxt.setText(temp+".00 *C");
                    countryTxt.setText(city);
                    pressureTxt.setText("Pressure: "+pressure);
                    humidityTxt.setText("Humidity: "+humidity);

                    SharedPreferences.Editor editor = pref.edit();
                    weather.temp=temp;
                    weather.humidity=humidity;
                    weather.pressure=pressure;
                    editor.putInt("temp",weather.temp);
                    editor.putInt("pressure",weather.pressure);
                    editor.putInt("humidity",weather.humidity);
                    editor.commit();

                }
            }

            @Override
            public void onFailure(Call<APIModel> call, Throwable t) {
                System.out.println("failure"+ t.getMessage());
                degreeTxt.setText(pref.getInt("temp",30));
                pressureTxt.setText(pref.getInt("pressure",1005));
                humidityTxt.setText(pref.getInt("humidity",9));
            }
        } );

*/
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIInterface gerritAPI = retrofit.create(APIInterface.class);
        Call<APIModel> call = gerritAPI.getTempByLoc(longitude,latitude,apiKey,units);
        call.enqueue(new Callback<APIModel>() {
            @Override
            public void onResponse(Call<APIModel> call, Response<APIModel> response) {
                if (response.isSuccessful()) {
                    APIModel model = response.body();
                    temp = (int) model.main.temp;
                    humidity = model.main.humidity;
                    pressure = model.main.pressure;
                    System.out.println(temp);
                    degreeTxt.setText(temp + ".00 *C");
                    countryTxt.setText(city);
                    pressureTxt.setText("Pressure: " + pressure);
                    humidityTxt.setText("Humidity: " + humidity);
                    //countryTxt.setText(model.name);
                }
            }

            @Override
            public void onFailure(Call<APIModel> call, Throwable t) {
                System.out.println("failure"+ t.getMessage());
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        } );
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm:ss ");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date_time.setText("Last updated: "+dateTime);

        //change image, weather details based on random
        setImage(temp,weatherImg,weatherTxt);
    }


    public void setImage(int random, ImageView imageView,TextView textView1)
    {
        if(random<=10)
        {
            imageView.setImageResource(R.drawable.spring);
            textView1.setText("Sunny");

        }
        else if (random>10 && random<=70)
        {

            imageView.setImageResource(R.drawable.snow_icon);
            textView1.setText("FOG");
        }
        else
        {
            imageView.setImageResource(R.drawable.summer);
            textView1.setText("Hot");

        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        longitude=location.getLongitude();
        latitude=location.getLatitude();
    }


    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Log.d("Latitude","enabled");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Log.d("Latitude","disabled");

    }



}