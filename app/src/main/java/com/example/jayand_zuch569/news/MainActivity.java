package com.example.jayand_zuch569.news;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView imageView = findViewById(R.id.imageView);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this,
                R.anim.hyperspace_jump);
        imageView.startAnimation(hyperspaceJumpAnimation);
        Handler h =new Handler() ;

        h.postDelayed(new Runnable() {
                          public void run() {
                              Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                              startActivity(intent);
                              finish();
                          }}
                ,2000);

    }
}
