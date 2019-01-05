package com.example.jayand_zuch569.news;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;


public class Settings extends Fragment {

    TextView topicTextView, bookMarks, reportBug;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        topicTextView = view.findViewById(R.id.topics);
        bookMarks = view.findViewById(R.id.bookmarks);
        reportBug = view.findViewById(R.id.reportBug);

        topicTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),TopicSelection.class);
                startActivity(intent);
            }
        });

        bookMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Bookmark.class);
                startActivity(intent);
            }
        });

        reportBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:JFactorywrks@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Bug Report");
                if (intent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
