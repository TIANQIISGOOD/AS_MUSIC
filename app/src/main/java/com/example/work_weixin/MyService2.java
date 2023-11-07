package com.example.work_weixin;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService2 extends Service {
    MediaPlayer player;
    int[] musics={
            R.raw.music1,
            R.raw.music2
    };
    public MyService2() {


    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("kk","MyService2_onCreate");
        player = MediaPlayer.create(this,musics[0]);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("kk","MyService2_onStartCommand");
       // player.start();
        return super.onStartCommand(intent, flags, startId);

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        super.onDestroy();
        Log.d("kk","MyService2_onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("kk","MyService2_onBind");
        //throw new UnsupportedOperationException("Not yet implemented");
        MyBinder myBinder=new MyBinder();
        return myBinder;

    }

    class  MyBinder extends Binder{
        public MyBinder()
        {

        }
        public void Todo()
        {
            player.start();
            Log.d("kk","MyService2_Todo");
        }
    }
}