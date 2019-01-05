package com.example.jayand_zuch569.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Adapter_delete extends PagerAdapter {

    private Context context;
    private ArrayList<PageModel> pageModels;
    private CardView linearLayout;
    private Refresh refresh;

    Adapter_delete(Context context, ArrayList<PageModel> itemsArrayList){
        this.context = context;
        this.pageModels = itemsArrayList;
        refresh = (Refresh) context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert layoutInflater != null;
        final View view = layoutInflater.inflate(R.layout.single_page,container,false);

        final SharedPreferences sharedPreferences = Adapter_delete.this.context.getSharedPreferences("bookmarks",Context.MODE_PRIVATE);

        final PageModel currentItem = pageModels.get(position);

        final ImageView imageUrl = view.findViewById(R.id.imageUrl);
        TextView title = view.findViewById(R.id.titlemain);
        TextView _60words = view.findViewById(R.id._60words);
        Picasso.get().load(currentItem.getImageUrl()).into(imageUrl);
        title.setText(currentItem.getTitleString());
        _60words.setText(currentItem.get_60WordsContent());

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),Browser.class);
                intent.putExtra("url",currentItem.getReadMoreUrl());
                view.getContext().startActivity(intent);
            }
        });

        title.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onLongClick(View v) {
                try {
                    if (!sharedPreferences.getString("book", "DEFAULT").equalsIgnoreCase("DEFAULT")) {
                        String string = sharedPreferences.getString("book", "DEFAULT");
                        JSONArray main = new JSONArray(string);
                        int index = -1;
                        for(int i=0;i<main.length();i++){
                            JSONArray jsonArray = main.getJSONArray(i);
                            if(jsonArray.get(1).equals(currentItem.getTitleString())){
                                index = i;
                                break;
                            }
                        }
                        main.remove(index);
                        sharedPreferences.edit().clear().apply();
                        sharedPreferences.edit().putString("book", main.toString()).apply();
                        refresh.refreshView();
                    }
                    Toast.makeText(Adapter_delete.this.context,"Deleted",Toast.LENGTH_LONG).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return true;
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(linearLayout);
    }

    @Override
    public int getCount() {
        return pageModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }

}
