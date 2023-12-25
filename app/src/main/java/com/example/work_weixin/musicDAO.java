package com.example.work_weixin;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class musicDAO {
    musicDbHelper dbHelper;
    SQLiteDatabase database;
    Context context;

    //传入实例的context
    public musicDAO(Context context1){
        context=context1;
        //连接数据库
        dbHelper =new musicDbHelper(context);//连接
        database = dbHelper.getWritableDatabase();//打开一个可写的数据库
    }
    //歌单:
    //增加歌单
    public void insertmusiclist(ContentValues values){
        //每向歌单表中插入一行数据,就会创建一个对应的歌表musictable,歌单表每列的主键值,对应歌表的表名编号
        //返回插入的行的主键值
        int musictableNO=(int)database.insert("MUSICLIST",null,values);
        createMusicTable(musictableNO);
    }
    //删除歌单
    public void deletemusiclist(String name){
        int musictableNO=database.delete("MUSICLIST","listName=?",new String[]{name});
        //喜爱的歌单不可以:
        if (musictableNO==0){
            //后续添加:提示框
        }
        else deletemusictable(musictableNO);
        //删除对应歌表


    }
    //改:修改歌单的名字:未完成
    public void changeMusicListName(){

    }
    //改:修改歌单的图片:未完成
    public void changeMusicListPicture(){

    }
    //查:直接查询歌单信息
    @SuppressLint("Range")
    public HashMap<String, Object> queryMusicList(String name){
        HashMap<String, Object> musicTable = new HashMap<>();
        Cursor cursor = database.rawQuery("SELECT * FROM MUSICLIST WHERE listName = ?", new String[]{name});
        if (cursor != null && cursor.moveToFirst()) {
            musicTable.put("ID", cursor.getString(cursor.getColumnIndex("_id")));
            musicTable.put("listName", cursor.getString(cursor.getColumnIndex("listName")));
            musicTable.put("listPicture", cursor.getString(cursor.getColumnIndex("listPicture")));
            cursor.close();
        }
        return musicTable;
    }

    //查:查询所有歌单信息
    public void queryAllMusicList(String name) {

    }
    //音乐表:
    //创建
    public void createMusicTable(int musictableID){
        String createTableMusic = "CREATE TABLE IF NOT EXISTS MUSIC_" + musictableID + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "author TEXT," +
                "URL TEXT," +
                "UNIQUE(name,author)"+
                ")";
        database.execSQL(createTableMusic);

    }
    public void insertMusic(int musictableID, ContentValues values) {

        // 检查是否存在相同的音乐记录
        if (isMusicExist(musictableID, values)) {
            // 存在冲突，更新数据
            updateMusic(musictableID, values);
        } else {
            // 不存在冲突，插入新数据
            database.insertWithOnConflict("MUSIC_" + musictableID, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    // 检查是否存在相同的音乐记录
    private boolean isMusicExist(int musictableID, ContentValues values) {
        String name = values.getAsString("name");
        String author = values.getAsString("author");
        Cursor cursor = database.query(
                "MUSIC_" + musictableID,
                new String[]{"name", "author"},
                "name = ? AND author = ?",
                new String[]{name, author},
                null, null, null
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // 更新音乐数据url
    private void updateMusic(int musictableID, ContentValues values) {
        String name = values.getAsString("name");
        String author = values.getAsString("author");
        database.update(
                "MUSIC_" + musictableID,
                values,
                "name = ? AND author = ?",
                new String[]{name, author}
        );
    }

    //删:
    // 1:根据歌曲名和作者删除歌曲
    public void deleteMusic(int musictableID,String name,String author){
        database.delete("MUSIC_"+musictableID,"name=? AND author=? ",new String[]{name,author});

    };
    // 2:删除该歌单
    public void deletemusictable(int musictableID){
        String tablename="MUSIC_"+musictableID;
        database.execSQL("DROP TABLE IF EXISTS " + tablename);

    }
    //查询全部歌曲信息
    @SuppressLint("Range")
    public ArrayList<HashMap<String, Object>> queryAllMusic(int musictableID) {
        ArrayList<HashMap<String, Object>> musicList = new ArrayList<>();
        Cursor cursor = database.query("MUSIC_" + musictableID,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            HashMap<String, Object> music = new HashMap<>();
            music.put("_id",cursor.getString(cursor.getColumnIndex("_id")));
            music.put("name", cursor.getString(cursor.getColumnIndex("name")));
            music.put("author", cursor.getString(cursor.getColumnIndex("author")));
            music.put("URL", cursor.getString(cursor.getColumnIndex("URL")));
            musicList.add(music);
        }
        cursor.close();
        return musicList;
    }
    public void closemusicDB(){
        dbHelper.close();
    }
}
