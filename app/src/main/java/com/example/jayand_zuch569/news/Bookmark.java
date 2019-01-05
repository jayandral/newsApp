package com.example.jayand_zuch569.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Bookmark extends AppCompatActivity implements Refresh {

    Adapter_delete adapter;
    ViewPager viewPager;
    ArrayList<PageModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        create();
    }

    public void create(){
        final SharedPreferences sharedPreferences = getApplication().getSharedPreferences("bookmarks",Context.MODE_PRIVATE);
        try {
            JSONArray jsonArray = new JSONArray(sharedPreferences.getString("book","DEFAULT"));
            if(jsonArray.length()!=0 || !sharedPreferences.getString("book","DEFAULT").equalsIgnoreCase("DEFAULT")){
                setContentView(R.layout.activity_bookmark);
                viewPager = findViewById(R.id.viewpager);
                arrayList = new ArrayList<>();
                adapter = new Adapter_delete(this,arrayList);
                viewPager.setAdapter(adapter);
                for(int i=0;i<jsonArray.length();i++){
                    JSONArray main = jsonArray.getJSONArray(i);
                    String image = (String) main.get(0);
                    String title = (String) main.get(1);
                    String article = (String) main.get(2);
                    String readMore = (String) main.get(3);
                    if (image.length() != 0 || title.length() != 0 || article.length() != 0 || readMore.length() != 0) {
                        PageModel pageModel = new PageModel(image, title, article, readMore);
                        arrayList.add(pageModel);
                    }
                    adapter.notifyDataSetChanged();
                }
            }else{
                setContentView(R.layout.no_bookmark);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            setContentView(R.layout.no_bookmark);
        }
    }

    @Override
    public void refreshView() {
        create();
    }
}
