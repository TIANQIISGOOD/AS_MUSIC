package com.example.work_weixin;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicList implements Serializable {
    private transient musicDAO musicdao;
    public String name;
    public String pictureLocation;
    int ID;
    public ArrayList<Music> musicList;
    //使用DAO连接数据库
    //将每个歌单初始化为一个实例
    MusicList(Context context,String name){
        musicdao=new musicDAO(context);
        this.name=name;
        //从歌单数据库中找到该表详细信息
        HashMap<String,Object> musicTable=musicdao.queryMusicList(name);
        pictureLocation=(String) musicTable.get("listPicture");
        ID=Integer.parseInt((String) musicTable.get("ID"));
        //从歌单数据库中找到该表的图片位置
        //歌单所有歌
        musicList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> list = musicdao.queryAllMusic(ID);
        for (HashMap<String,Object> temp:list) {
            Music music=new Music((String) temp.get("name"),
                    (String) temp.get("author"),
                    (String) temp.get("URL"),
                    Integer.valueOf(String.valueOf(temp.get("_id"))),
                    this);
            Log.d("aa",music.getName());
            musicList.add(music);
        }
    }

    //删除歌单
    public void deleteMusicList(){
        musicdao.deletemusiclist(name);
    }
    //修改歌单_名字
    public void changeMusicListName(){
        //未完待续
    }
    //修改歌单_图片
    public void changeMusicListPicture(){
        //未完待续
    }

    //歌单中歌曲的添加功能
    public void addMusic(Music music){
        musicList.add(music);
        //未完待续
    }
    //歌单中歌曲的删除功能
    public void removeMusic(Music music){
        musicList.remove(music);
        //未完待续
    }

}
