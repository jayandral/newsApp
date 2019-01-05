package com.example.jayand_zuch569.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TopicSelection extends AppCompatActivity {

    Button business, politics, sports, startup, entertainment, hatke, international, automobile,technology, science, fashion, miscellaneous, india, done, travel;
    List<String> topic_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        business = findViewById(R.id.business);
        politics = findViewById(R.id.politics);
        sports = findViewById(R.id.sports);
        startup = findViewById(R.id.startup);
        entertainment = findViewById(R.id.entertainment);
        hatke = findViewById(R.id.hatke);
        international = findViewById(R.id.international);
        automobile = findViewById(R.id.automobile);
        technology = findViewById(R.id.technology);
        science = findViewById(R.id.science);
        fashion = findViewById(R.id.fashion);
        miscellaneous = findViewById(R.id.miscellaneous);
        india = findViewById(R.id.india);
        travel = findViewById(R.id.travel);
        done = findViewById(R.id.done);

        topic_list = new ArrayList<>();

        final SharedPreferences sharedPreferences = getSharedPreferences("data",Context.MODE_PRIVATE);
        if(!sharedPreferences.getString("topic","DEFAULT").equalsIgnoreCase("DEFAULT")){
            String data = sharedPreferences.getString("topic","DEFAULT");
            topic_list = new LinkedList<>(Arrays.asList(data.substring(1,data.length()-1).replaceAll("[\\s]+","").split(",")));
        }

        for (String topic:topic_list) {
            switch (topic.replaceAll("[\\s]+","")) {
                case "business": {
                    Log.d("coming","coming");
                    GradientDrawable gradientDrawable = (GradientDrawable) business.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "politics": {
                    Log.d("coming","coming");
                    GradientDrawable gradientDrawable = (GradientDrawable) politics.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "sports": {
                    Log.d("coming","coming");
                    GradientDrawable gradientDrawable = (GradientDrawable) sports.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "startup": {
                    Log.d("coming","coming");
                    GradientDrawable gradientDrawable = (GradientDrawable) startup.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "entertainment": {
                    Log.d("coming","coming");
                    GradientDrawable gradientDrawable = (GradientDrawable) entertainment.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "hatke": {
                    Log.d("coming","coming");
                    GradientDrawable gradientDrawable = (GradientDrawable) hatke.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "international": {
                    Log.d("coming","coming");
                    GradientDrawable gradientDrawable = (GradientDrawable) international.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "automobile": {
                    GradientDrawable gradientDrawable = (GradientDrawable) automobile.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "technology": {
                    GradientDrawable gradientDrawable = (GradientDrawable) technology.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "science": {
                    GradientDrawable gradientDrawable = (GradientDrawable) science.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "fashion": {
                    GradientDrawable gradientDrawable = (GradientDrawable) fashion.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "miscellaneous": {
                    GradientDrawable gradientDrawable = (GradientDrawable) miscellaneous.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "india": {
                    GradientDrawable gradientDrawable = (GradientDrawable) india.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
                case "travel": {
                    GradientDrawable gradientDrawable = (GradientDrawable) travel.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    break;
                }
            }
        }

        onClick(business,"business");
        onClick(politics,"politics");
        onClick(startup,"startup");
        onClick(sports,"sports");
        onClick(entertainment,"entertainment");
        onClick(hatke,"hatke");
        onClick(international,"international");
        onClick(automobile,"automobile");
        onClick(technology,"technology");
        onClick(science,"science");
        onClick(fashion,"fashion");
        onClick(miscellaneous,"miscellaneous");
        onClick(india,"india");
        onClick(travel,"travel");

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                if(topic_list.size()!=0) {
                    sharedPreferences.edit().putString("topic", topic_list.toString().replaceAll("[\\s]+", "")).apply();
                }
                Log.d("coming__",sharedPreferences.getString("topic","DEFAULT"));
                finish();
            }
        });
    }

    public void onClick(View v , final String topic) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("coming",topic);
                if(topic_list.contains(topic.replaceAll("[\\s]+",""))){
                    GradientDrawable gradientDrawable = (GradientDrawable) v.getBackground();
                    gradientDrawable.setStroke(0, Color.parseColor("#FFA500"));
                    topic_list.remove(topic.replaceAll("[\\s]+",""));
                }else{
                    GradientDrawable gradientDrawable = (GradientDrawable) v.getBackground();
                    gradientDrawable.setStroke(4, Color.parseColor("#FFA500"));
                    topic_list.add(topic.replaceAll("[\\s]+",""));
                }
            }
        });
    }
}
