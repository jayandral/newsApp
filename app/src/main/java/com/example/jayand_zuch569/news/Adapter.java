package com.example.jayand_zuch569.news;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends PagerAdapter {

    private Context context;
    private ArrayList<PageModel> pageModels;
    private CardView linearLayout;

    Adapter(Context context, ArrayList<PageModel> itemsArrayList){
        this.context = context;
        this.pageModels = itemsArrayList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        final View view = layoutInflater.inflate(R.layout.single_page,container,false);

        final PageModel currentItem = (PageModel) pageModels.get(position);

        ImageView imageUrl = (ImageView) view.findViewById(R.id.imageUrl);
        TextView title = (TextView) view.findViewById(R.id.titlemain);
        TextView _60words = (TextView) view.findViewById(R.id._60words);
        Picasso.get().load(currentItem.getImageUrl()).into(imageUrl);
        title.setText(currentItem.getTitleString());
        _60words.setText(currentItem.get_60WordsContent());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),Browser.class);
                intent.putExtra("url",currentItem.getReadMoreUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((linearLayout));
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
