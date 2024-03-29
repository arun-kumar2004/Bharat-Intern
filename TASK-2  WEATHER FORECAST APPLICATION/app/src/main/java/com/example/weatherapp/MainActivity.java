package com.example.weatherapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {

//    final String URL = "https://api.openweathermap.org/data/2.5/weather";
    final String APP_ID = "bd050af1b5bb0c9b541078eafd3994dd";
    private int PERMISSION_CODE = 1;
    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;
    String[] saveKey = {
            "CurrentWeatherData",
            "ForecastWeatherData"
    };
    double lat = 0, lon = 0;
    int count;
    String locationProvider = LocationManager.GPS_PROVIDER;
    String dayNight="";
    ProgressBar progressBar;
    LocationManager locationManager;
    Location location;
    LocationListener locationListener;
    EditText searchCityEditText;
    ImageButton searchButton,refreshButton;
    LottieAnimationView lottieAnimationView;
    ArrayList<WeatherCurrentData> weatherCurrentDataArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    WeatherArrayListAdapter weatherArrayListAdapter;
    ConstraintLayout constraintLayout;
    TextView temperatureTextView, weatherState, cityName, windSpeed, humidity, lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        weatherState = findViewById(R.id.weatherState);
        cityName = findViewById(R.id.cityName);
        searchCityEditText = findViewById(R.id.searchCityEditText);
        windSpeed = findViewById(R.id.windSpeed);
        humidity = findViewById(R.id.humidity);
        progressBar = findViewById(R.id.progressBar);
        lastUpdate = findViewById(R.id.lastUpdate);
        searchButton = findViewById(R.id.searchButton);
        refreshButton = findViewById(R.id.refreshButton);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        constraintLayout = findViewById(R.id.constraintLayout);
        recyclerView = findViewById(R.id.recyclerView);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,this);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            Toast.makeText(this,"Fetching Last known location",Toast.LENGTH_SHORT).show();
            lat = location.getLatitude();
            lon = location.getLongitude();
        }
        getCurrentWeather(lat, lon);
        getForecastWeather(lat, lon);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
//
//
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1, RecyclerView.HORIZONTAL, false));
        weatherArrayListAdapter = new WeatherArrayListAdapter(MainActivity.this, weatherCurrentDataArrayList);
        recyclerView.setAdapter(weatherArrayListAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInternetAvailable = CheckNetwork.isNetworkAvailable(getApplicationContext());
                if(isInternetAvailable){
                    String city = searchCityEditText.getText().toString();
                    if (city.equals("")) {
                        Toast.makeText(MainActivity.this, "Please enter city Name", Toast.LENGTH_SHORT).show();
                        searchCityEditText.requestFocus();
                        searchCityEditText.setError("Please Enter City Name");
                    }else{
                        updateWeather(city);
                    }
                }else {
                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
                refreshWeather(v);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInternetAvailable = CheckNetwork.isNetworkAvailable(getApplicationContext());
                if(isInternetAvailable){
                    Toast.makeText(MainActivity.this,"Refreshing...",Toast.LENGTH_SHORT).show();
                    getCurrentWeather(lat,lon);
                    getForecastWeather(lat,lon);
                }else {
                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
//        temperature.setText("40\u2103");


    }
//
    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        Log.d("Latitude", String.valueOf(lat));
        Log.d("Longitude", String.valueOf(lon));

        // Stop receiving location updates if needed
        locationManager.removeUpdates( this);
}

    private void updateWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" +city +"&appid=" +APP_ID +"&units=metric";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("cod").equals("404")){
                        Toast.makeText(MainActivity.this, "Please enter correct city name", Toast.LENGTH_LONG).show();
                    }else {
                        lastUpdate.setText("Last Update :\n" +getCurrentTime());
                        lon = response.getJSONObject("coord").getDouble("lon");
                        lat = response.getJSONObject("coord").getDouble("lat");
                        getCurrentWeather(lat, lon);
                        getForecastWeather(lat, lon);
                    }
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "Please enter correct city name", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Fetch Error","city not found");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void refreshWeather(View view) {
        boolean isInternetAvailable = CheckNetwork.isNetworkAvailable(getApplicationContext());
        if(isInternetAvailable){
            Toast.makeText(MainActivity.this,"Refreshing...",Toast.LENGTH_SHORT).show();
            getCurrentWeather(lat,lon);
            getForecastWeather(lat,lon);
        }else {
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }
    private String getCurrentTime() {
        LocalDateTime dateTime = null;
        String time ="";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.now();
        }

        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            time = dateTime.format(formatter);
        }
        return time;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permission Granted...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void getCurrentWeather(double lat, double lon) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        String urlCurrent = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + APP_ID + "&units=metric";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlCurrent, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                lastUpdate.setText("Last Update:\n" +getCurrentTime());
                saveLastResponse(response,0);
                progressBar.setVisibility(View.GONE);
                constraintLayout.setVisibility(View.VISIBLE);
                updateCurrentWeather(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Fetch Error",error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
//
    private void updateCurrentWeather(JSONObject response) {
        try{
            String city = response.getString("name");
            cityName.setText(city);

            String temperature = response.getJSONObject("main").getString("temp");
            float floatValue = Float.parseFloat(temperature);
            int intValue = Math.round(floatValue);
            String st = String.valueOf(intValue);
            temperatureTextView.setText(st+ "°C");

            String img = response.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("icon");
            setWeatherAnimation(img);
//            Picasso.get().load("https://openweathermap.org/img/w/" + img + ".png").into(imgWeather);

            String condition = response.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherState.setText("Weather State:\n" +condition);
            dayNight = lastUpdate.getText().toString().substring(24,26);
            setHumidity();
            double wSpeed = response.getJSONObject("wind")
                    .getDouble("speed");
            windSpeed.setText("Wind Speed :\n"+wSpeed+"Km/h");
        }catch (Exception e){
            Log.d("Update Res",e.getMessage());
        }

    }
//
    private void getForecastWeather(double lat, double lon) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String urlForecast = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + APP_ID + "&units=metric";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlForecast, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                lastUpdate.setText("Last Update:\n" +getCurrentTime());
                saveLastResponse(response,1);
                weatherCurrentDataArrayList.clear();
                updateForecastWeather(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Fetch Error",error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
//
    private void updateForecastWeather(JSONObject response) {
        try{
            int loop = response.getInt("cnt");
            JSONArray forecast = response.getJSONArray("list");

            for (int i = 0; i < loop; i += 1) {
                String time = forecast.getJSONObject(i)
                        .getString("dt_txt");
                double temp = forecast.getJSONObject(i)
                        .getJSONObject("main")
                        .getDouble("temp");
                String condition = forecast.getJSONObject(i)
                        .getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("icon");
                double wSpeed = forecast.getJSONObject(i)
                        .getJSONObject("wind")
                        .getDouble("speed");
                String pod = forecast.getJSONObject(i)
                        .getJSONObject("sys")
                        .getString("pod");
                weatherCurrentDataArrayList.add(new WeatherCurrentData(temp, condition, wSpeed,time,pod));
                if(i==0){
                    if(pod.equals("n")){
                        constraintLayout.setBackground(getResources().getDrawable(R.drawable.cloudy_sky));
                    }else {
                        constraintLayout.setBackground(getResources().getDrawable(R.drawable.sunny_sky));
                    }
                }
            }
            weatherArrayListAdapter.notifyDataSetChanged();
        }catch (Exception e){
            Log.d("Update Res",e.getMessage());
        }
    }
//    public void onLocationChanged(Location location) {
//        double lat = location.getLatitude();
//        double lon = location.getLongitude();
//
//        Log.d("Latitude", String.valueOf(lat));
//        Log.d("Longitude", String.valueOf(lon));
//
//        // Stop receiving location updates if needed
//        locationManager.removeUpdates((LocationListener) this);
//    }
//
    private void saveLastResponse(JSONObject res, int time) {

        SharedPreferences sharedPreferences = getSharedPreferences("WeatherData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try{
            String save = res.toString();
            editor.putString(saveKey[time], save);
            editor.putString("Time", getCurrentTime());
            editor.apply();
        }catch (Exception e){
            Log.d("Save Response",e.getMessage());
        }
    }
    private void retrieveLastResponse(int time) {
        SharedPreferences sharedPreferences = getSharedPreferences("WeatherData", Context.MODE_PRIVATE);
        String weatherData = sharedPreferences.getString(saveKey[time], "");
        String dateTime = sharedPreferences.getString("Time", "");
        lastUpdate.setText(dateTime);
        try {
            JSONObject response = new JSONObject(weatherData);
            switch (time){
                case 0:
                    updateCurrentWeather(response);
                    break;
                case 1:
                    updateForecastWeather(response);
                    break;
                default:
                    Log.d("retrieveLastResponse","Wrong time");
                    break;
            }
        }catch (Exception ex){
            Log.d("Load Res",ex.getMessage());
        }
    }
    private void setWeatherAnimation(String animation)
    {
//        if (constraintLayout.getBackground().getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.sunny_sky).getConstantState()))
//            dayNight = "d";
//        else
//            dayNight = "n";
        if (animation.equalsIgnoreCase("01d")) {
            temperatureTextView.setTextColor(getResources().getColor(R.color.white));
            lottieAnimationView.setAnimation(R.raw.sunny);
        }
        else if (animation.equalsIgnoreCase("01n")){
            lottieAnimationView.setAnimation(R.raw.nightmoon);
            temperatureTextView.setTextColor(getResources().getColor(R.color.white));
        }
        else if (animation.equalsIgnoreCase("50n") || animation.equalsIgnoreCase("50d")) {
            lottieAnimationView.setAnimation(R.raw.mist);
            temperatureTextView.setTextColor(getResources().getColor(R.color.dark_blue));
        }
        else if (animation.equalsIgnoreCase("09n") || animation.equalsIgnoreCase("09d")
                || animation.equalsIgnoreCase("10n") || animation.equalsIgnoreCase("10d")) {
            lottieAnimationView.setAnimation(R.raw.rainstorm);
            temperatureTextView.setTextColor(getResources().getColor(R.color.dark_blue));
        }
        else if (animation.equalsIgnoreCase("02d")) {
                lottieAnimationView.setAnimation(R.raw.cloudy_sun);
                temperatureTextView.setTextColor(getResources().getColor(R.color.dark_blue));
            }
        else if (animation.equalsIgnoreCase("02n")){
                lottieAnimationView.setAnimation(R.raw.cloudynight);
                temperatureTextView.setTextColor(getResources().getColor(R.color.white));
        }
        else if (animation.equalsIgnoreCase("03d") || animation.equalsIgnoreCase("03n")
                || animation.equalsIgnoreCase("04d") || animation.equalsIgnoreCase("04n")){
            lottieAnimationView.setAnimation(R.raw.cloudy_weather);
            temperatureTextView.setTextColor(getResources().getColor(R.color.dark_blue));
        }
        else if (animation.equalsIgnoreCase("13d") || animation.equalsIgnoreCase("13n")) {
            lottieAnimationView.setAnimation(R.raw.snow);
            temperatureTextView.setTextColor(getResources().getColor(R.color.dark_blue));
        }
        else if (animation.equalsIgnoreCase("11d") || animation.equalsIgnoreCase("11n")) {
            lottieAnimationView.setAnimation(R.raw.thunderstorm);
            temperatureTextView.setTextColor(getResources().getColor(R.color.dark_blue));
        }
        lottieAnimationView.playAnimation();
        lottieAnimationView.loop(true);
    }
    private void setHumidity()
    {
        int hum = 35;
        if (count == 0) {
            hum = 44;
            count++;
        } else if (count == 1) {
            hum = 52;
            count++;
        }else if (count == 2) {
            hum = 38;
            count++;
        }else if (count == 3) {
            hum = 49;
            count++;
        }else if (count == 4) {
            hum = 36;
            count = 0;
        }
        humidity.setText("Humidity:\n" +String.valueOf(hum) +"%");
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        getWeatherForCurrentLocation();
//    }
//
//    private void getWeatherForCurrentLocation() {
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(@NonNull Location location) {
//                String longitude = String.valueOf(location.getLongitude());
//                String latitude = String.valueOf(location.getLatitude());
//                RequestParams params = new RequestParams();
//                params.put("lat",latitude);
//                params.put("lon",longitude);
//                params.put("appid",APP_ID);
//                letsDoSomeNetworking(params);
//            }
////
////
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
////
//            @Override
//            public void onProviderEnabled(@NonNull String provider) {
//
//            }
////
//            @Override
//            public void onProviderDisabled(@NonNull String provider) {
//
//            }
//        };
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(locationProvider, MIN_TIME, MIN_DISTANCE, locationListener);
//    }
//
//    private void letsDoSomeNetworking(RequestParams params) {
//        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//        asyncHttpClient.get(URL, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
////                super.onSuccess(statusCode, headers, response);
//                Toast.makeText(MainActivity.this, "Get Data sucessfully", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
////                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == REQUEST_CODE){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                getWeatherForCurrentLocation();
//                Toast.makeText(this, "Location Granted Sucessfully", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
