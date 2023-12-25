package com.example.work_weixin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
//存储音乐信息的数据库:
//存储过程将在最终展开,因为不确定是爬取网络数据还是获得其它音乐厂商的API,但是可以确定是通过URL播放,此时假设已经完成了存储任务id,歌曲名,作者,URL
public class musicDbHelper extends SQLiteOpenHelper {
    //定义数据库名称和版本号
    private static final String DATABASE_NAME = "music_database.db";
    private static final int DATABASE_VERSION = 1;
    //创建
    public musicDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表
        //歌单表
            String table_musiclist="CREATE TABLE IF NOT EXISTS MUSICLIST (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "listName TEXT UNIQUE," +
                    "listPicture TEXT"+
                    ")";
            sqLiteDatabase.execSQL(table_musiclist);

        //每向歌单表中插入一个数据,就会创建一个对应的歌表musictable,与musictableNO关联
        //其中喜爱的歌单原始生成
        String insertLikeMusic="INSERT INTO MUSICLIST (listName, listPicture) VALUES ('Love', null)";
        sqLiteDatabase.execSQL(insertLikeMusic);
        String createTableMusic = "CREATE TABLE IF NOT EXISTS MUSIC_1" + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "author TEXT," +
                "URL TEXT," +
                "UNIQUE(name,author)"+
                ")";
        sqLiteDatabase.execSQL(createTableMusic);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //处理数据库升级的逻辑
    }
}
