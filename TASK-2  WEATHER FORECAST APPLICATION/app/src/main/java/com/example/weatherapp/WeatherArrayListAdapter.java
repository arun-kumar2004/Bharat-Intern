package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class WeatherArrayListAdapter extends RecyclerView.Adapter<WeatherArrayListAdapter.ViewHolder>{
    Context context;

    ArrayList<WeatherCurrentData> weatherCurrentDataArrayList;
    public WeatherArrayListAdapter(Context context, ArrayList<WeatherCurrentData> weatherCurrentDataArrayList){
        this.context = context;
        this.weatherCurrentDataArrayList = weatherCurrentDataArrayList;
    }

    @NonNull
    @Override
    public WeatherArrayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_hours_format,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherArrayListAdapter.ViewHolder holder, int position) {
//        holder.lottieAnimationView.setAnimation(hourlyWeatherArrayList.get(position).getAnimation());
        String timeString = weatherCurrentDataArrayList.get(position).getTime();
        String timeS = timeString.substring(11,13);
        String dayNight = weatherCurrentDataArrayList.get(position).getPod();
        setDayNight(holder.backgroundLayout,dayNight);
        holder.timeTextView.setText(timeS +":00");
        setWeatherAnimation(holder.lottieAnimationView2,holder.weatherCondition,weatherCurrentDataArrayList.get(position).getIcon()+"");
//        holder.weatherCondition.setText( +"l");
        holder.windSpeedTextView.setText(weatherCurrentDataArrayList.get(position).getSpeed() +" Km/h");
        holder.tempTextView.setText(weatherCurrentDataArrayList.get(position).getTemperature()+"°C");
    }

    @Override
    public int getItemCount() {
        return weatherCurrentDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        LottieAnimationView lottieAnimationView2;
        ConstraintLayout backgroundLayout;
        TextView timeTextView,tempTextView,windSpeedTextView,weatherCondition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            lottieAnimationView = itemView.findViewById(R.id.lottieAnimationView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            tempTextView = itemView.findViewById(R.id.tempTextView);
            weatherCondition = itemView.findViewById(R.id.weatherCondition);
            windSpeedTextView = itemView.findViewById(R.id.windSpeedTextView);
            cardView = itemView.findViewById(R.id.cardView);
            lottieAnimationView2 = itemView.findViewById(R.id.lottieAnimationView2);
            backgroundLayout = itemView.findViewById(R.id.backgroundLayout);
        }
    }
    public void setDayNight(ConstraintLayout constraintLayout,String pod){
        if (pod.equalsIgnoreCase("n"))
            constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.cloudy_sky));
        else
            constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.sunny_sky));
    }

    private void setWeatherAnimation(LottieAnimationView lottieAnimationView2, TextView weatherCondition, String animation)
    {
        if (animation.equalsIgnoreCase("01d")) {
            lottieAnimationView2.setAnimation(R.raw.sunny);
            weatherCondition.setText("Clear");
        }
        else if (animation.equalsIgnoreCase("01n")){
            lottieAnimationView2.setAnimation(R.raw.nightmoon);
            weatherCondition.setText("Clear");
        }
        else if (animation.equalsIgnoreCase("50n") || animation.equalsIgnoreCase("50d")) {
            lottieAnimationView2.setAnimation(R.raw.mist);
            weatherCondition.setText("Haze");
        }
        else if (animation.equalsIgnoreCase("09n") || animation.equalsIgnoreCase("09d")
                || animation.equalsIgnoreCase("10n") || animation.equalsIgnoreCase("10d")) {
            lottieAnimationView2.setAnimation(R.raw.rainstorm);
            weatherCondition.setText("Rain");
        }
        else if (animation.equalsIgnoreCase("02d")) {
            lottieAnimationView2.setAnimation(R.raw.cloudy_sun);
            weatherCondition.setText("Clouds");
        }
        else if (animation.equalsIgnoreCase("02n")){
            lottieAnimationView2.setAnimation(R.raw.cloudynight);
            weatherCondition.setText("Clouds");
        }
        else if (animation.equalsIgnoreCase("03d") || animation.equalsIgnoreCase("03n")
                || animation.equalsIgnoreCase("04d") || animation.equalsIgnoreCase("04n")){
            lottieAnimationView2.setAnimation(R.raw.cloudy_weather);
            weatherCondition.setText("Clouds");
        }
        else if (animation.equalsIgnoreCase("13d") || animation.equalsIgnoreCase("13n")) {
            lottieAnimationView2.setAnimation(R.raw.snow);
            weatherCondition.setText("Snow");
        }
        else if (animation.equalsIgnoreCase("11d") || animation.equalsIgnoreCase("11n")) {
            lottieAnimationView2.setAnimation(R.raw.thunderstorm);
            weatherCondition.setText("Thunderstorm");
        }
    }
}
