package com.example.work_weixin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class activity_music extends AppCompatActivity {

    Button button1,button2,button3,button4;
    MyService2.MyBinder binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        button1=findViewById(R.id.buttonM1);
        button2=findViewById(R.id.buttonM2);
        button3=findViewById(R.id.buttonM3);
        button4=findViewById(R.id.buttonM4);
        Intent intent = new Intent(activity_music.this,MyService1.class);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startService(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
            }
        });

        Intent intent2 = new Intent(activity_music.this,MyService2.class);

        //2.
        ServiceConnection connection = new ServiceConnection() {
            @Override
            //开始
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                binder = (MyService2.MyBinder) iBinder;//3.接住
                binder.Todo();//4.调用
            }
            //结束,解绑
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                binder=null;
            }
        };
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bindService(intent2,connection,BIND_AUTO_CREATE);//1-->服务-->绑定-->返回实例
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(connection);
            }
        });
    }
}