package com.example.jayand_zuch569.news;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feeds extends Fragment {

    ViewPager viewPager;
    List<Map<String, String>> maps;
    int count = 0;
    ProgressDialog progressDialog;

    public Feeds() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feeds, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && !netInfo.isConnectedOrConnecting()) {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        maps = new ArrayList<Map<String, String>>();

        /*
        business
        politics
        sports
        startup
        entertainment
        hatke
        international
        automobile
        technology
        science
        fashion
        miscellaneous
        india
         */

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching....");
        progressDialog.setMessage("Making Your Feeds Ready");
        progressDialog.show();

        new MakeCall().execute("https://www.inshorts.com/en/read/india");
        new MakeCall().execute("https://www.inshorts.com/en/read/science");
        new MakeCall().execute("https://www.inshorts.com/en/read/technology");
        new MakeCall().execute("https://www.inshorts.com/en/read/business");
        new MakeCall().execute("https://www.inshorts.com/en/read/politics");
        new MakeCall().execute("https://www.inshorts.com/en/read/sports");
        new MakeCall().execute("https://www.inshorts.com/en/read/startups");
        new MakeCall().execute("https://www.inshorts.com/en/read/entertainment");
        new MakeCall().execute("https://www.inshorts.com/en/read/hatke");
        new MakeCall().execute("https://www.inshorts.com/en/read/international");
        new MakeCall().execute("https://www.inshorts.com/en/read/automobile");
        new MakeCall().execute("https://www.inshorts.com/en/read/miscellaneous");
        new MakeCall().execute("https://www.inshorts.com/en/read/fashion");
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class MakeCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                Elements elements = Jsoup.connect(strings[0]).get().getElementsByClass("card-stack").get(0).getElementsByClass("news-card");
                for (int i = 0; i < elements.size(); i++) {
                    Map<String, String> mainMap = new HashMap<String, String>();
                    mainMap.put("image-url", elements.get(i).getElementsByClass("news-card-image").attr("style").replace("background-image: url('", "").replace("')", ""));
                    mainMap.put("headline", elements.get(i).getElementsByClass("news-card-title news-right-box").select("a").select("span").text());
                    mainMap.put("article", elements.get(i).getElementsByClass("news-card-content news-right-box").text());
                    mainMap.put("read-more link", elements.get(i).getElementsByClass("read-more").select("a").attr("href"));
                    maps.add(mainMap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (count == 10) {
                makeChanges();
                progressDialog.cancel();
            } else if (count == 7) {
                progressDialog.setMessage("Hang On A Second");
                count += 1;
            } else if (count == 4) {
                progressDialog.setMessage("Making It Perfect");
                count += 1;
            } else {
                count += 1;
            }
        }
    }

    private void makeChanges() {
        ArrayList<PageModel> arrayList = new ArrayList<PageModel>();
        for (Map<String, String> map : maps) {
            String image = map.get("image-url");
            String title = map.get("headline");
            String article = map.get("article");
            String readMore = map.get("read-more link");
            if (image.length() != 0 || title.length() != 0 || article.length() != 0 || readMore.length() != 0) {
                PageModel pageModel = new PageModel(image, title, article, readMore);
                arrayList.add(pageModel);
            }
            Adapter adapter = new Adapter(getContext(), arrayList);
            viewPager.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
