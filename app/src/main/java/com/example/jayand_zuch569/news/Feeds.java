package com.example.jayand_zuch569.news;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Feeds extends Fragment {

    List<Map<String, String>> maps;
    int count = 0;
    ProgressDialog progressDialog;
    Adapter adapter;
    ViewPager viewPager;
    ArrayList<PageModel> arrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("data",Context.MODE_PRIVATE);

        List<String> topic_list = new ArrayList<>();

        if(!sharedPreferences.getString("topic","DEFAULT").equalsIgnoreCase("DEFAULT")){
            String data = sharedPreferences.getString("topic","DEFAULT");
            topic_list = new LinkedList<>(Arrays.asList(data.substring(1,data.length()-1).replaceAll("[\\s]+","").split(",")));
        }

        if(topic_list.size()>0) {
            view = inflater.inflate(R.layout.fragment_feeds, container, false);
            count=0;
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Fetching....");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Making Your Feeds Ready");
            progressDialog.show();
            viewPager = view.findViewById(R.id.viewpager);
            arrayList = new ArrayList<>();
            adapter = new Adapter(getContext(),arrayList);
            viewPager.setAdapter(adapter);
            maps = new ArrayList<>();
            for (String topic : topic_list) {
                new MakeCall().execute("https://www.inshorts.com/en/read/"+topic,topic);
            }
        }else{
            view = inflater.inflate(R.layout.no_feeds, container, false);
        }
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class MakeCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                List<String> a = new ArrayList<>();
                Document html_page = Jsoup.connect(strings[0]).get();
                for (Element tag : html_page.getElementsByTag("script")){
                    for (DataNode node : tag.dataNodes()) {
                        a.add(node.getWholeData());
                    }
                }
                a = Arrays.asList(a.get(a.size()-1).replaceAll("[\\s]+"," ").split(";"));
                String min_id = a.get(0).substring(a.get(0).indexOf("var min_news_id = ")+18).replaceAll("\"","");
                Elements elements = html_page.getElementsByClass("card-stack").get(0).getElementsByClass("news-card");
                int call_Count = 0;
                while(call_Count<3) {
                    for (int i = 0; i < elements.size(); i++) {
                        Map<String, String> mainMap = new HashMap<>();
                        mainMap.put("image-url", elements.get(i).getElementsByClass("news-card-image").attr("style").replace("background-image: url('", "").replace("')", ""));
                        mainMap.put("headline", elements.get(i).getElementsByClass("news-card-title news-right-box").select("a").select("span").text());
                        mainMap.put("article", elements.get(i).getElementsByClass("news-card-content news-right-box").text());
                        mainMap.put("read-more link", elements.get(i).getElementsByClass("read-more").select("a").attr("href"));
                        maps.add(mainMap);
                    }
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder().add("news_offset", min_id).add("category", strings[1]).build();
                    Request request = new Request.Builder().url("https://inshorts.com/en/ajax/more_news").post(formBody).addHeader("Content-Type", "application/x-www-form-urlencoded").build();

                    Response response = client.newCall(request).execute();

                    assert response.body() != null;
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    elements = Jsoup.parse(jsonObject.get("html").toString()).getElementsByClass("news-card z-depth-1");
                    min_id = jsonObject.get("min_news_id").toString();
                    call_Count+=1;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            makeChanges();
        }
    }

    private void makeChanges() {
        while (count < maps.size()) {
            String image = maps.get(count).get("image-url");
            String title = maps.get(count).get("headline");
            String article = maps.get(count).get("article");
            String readMore = maps.get(count).get("read-more link");
            assert readMore != null;
            assert image != null;
            assert title != null;
            assert article != null;
            if (image.length() != 0 || title.length() != 0 || article.length() != 0 || readMore.length() != 0) {
                PageModel pageModel = new PageModel(image, title, article, readMore);
                arrayList.add(pageModel);
            }
            adapter.notifyDataSetChanged();
            if (arrayList.size()>5) {
                progressDialog.cancel();
            }
            count+=1;
        }
    }
}
