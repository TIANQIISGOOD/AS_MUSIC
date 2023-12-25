package com.example.work_weixin;

import java.io.Serializable;
import java.net.URL;

public class Music implements Serializable {
    String name;
    String author;
    String url;
    MusicList musicList;
    Integer id;
    Music(String name,String author,String url,Integer id,MusicList musicList){
        this.name=name;
        this.author=author;
        this.url=url;
        this.id=id;
        this.musicList=musicList;
    }
    public String getName(){
        return name;
    }
    public String getAuthor(){
        return author;
    }
    public String getUrl(){
        return url;
    }
    public Integer getId(){
        return id;
    }
}
