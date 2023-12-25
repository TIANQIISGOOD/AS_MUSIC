package com.example.work_weixin;

import android.content.ContentValues;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.os.Handler;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CrawlMusic {
    //网络爬取:
    private OkHttpClient okHttpClient;
    private  musicDAO dao;
    private musicDbHelper dbHelper;
    private  ArrayList<HashMap<String,Object>> musicList;
    Context context;

    String url;

    public CrawlMusic(Context context, String url){
        this.context=context;
        this.url=url;
        //初始化数据库
        dbHelper=new musicDbHelper(context);
        //初始化DAO
        dao=new musicDAO(context);
        //初始化OkHttpClient实例用于发送网络请求。
        okHttpClient=new OkHttpClient();
        // 初始化musicList
        musicList = new ArrayList<>();

    }

    public void getMusicData() {
        //网络请求
        Request request = new Request.Builder()
                .url(url)
                .build();
        //处理网络请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //异常处理:网络请求失败
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //处理响应数据
                if (response.isSuccessful()){
                    String musicData = response.body().string();
                    //Jsoup解析HTML
                    Document document = Jsoup.parse(musicData);
                    //存储获得相关数据-->数据库处理
                    //定位获取音乐资源的URL
                    for (Element element: document.select("div.items")){
                        //每一条音乐数据的存储
                        HashMap<String,Object> music=new HashMap<>();
                        //获取音乐名
                        String musicName=element.select("span.value").text();
                        //获取音频的链接
                        String musicUrl="https:"+element.select("audio").attr("src");
                        //存入数据
                        music.put("name",musicName);
                        music.put("URL",musicUrl);
                        musicList.add(music);
                        Log.d("music URL",musicUrl);
                    }
                    saveMusicToDatabase();
                }else {
                    //异常处理:响应失败
                    throw  new IOException("!"+response.code());
                }
            }
        });
    }

    public void saveMusicToDatabase(){
        //将爬取到的相关内容通过DAO存取到数据库中
        //0号音乐表:我喜爱的歌单
        //我喜爱的歌单展示在我的页面
        Log.d("ll","55");
        for (HashMap<String,Object> music:musicList) {
            ContentValues values=new ContentValues();
            values.put("name",(String) music.get("name"));
            values.put("author",music.get("name")+"作者");
            values.put("URL",(String) music.get("URL"));
            dao.insertMusic(1,values);
        }
    }


}
