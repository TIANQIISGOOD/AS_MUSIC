package com.example.work_weixin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {
    TextView textView_1;
    TextView textView_2;
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("kk","2:onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("kk","2:onRestart");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("kk","2:onPostResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("kk","2:onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("kk","2:onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("kk","2:onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("kk","2:onStop");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Log.d("kk","2:onCreate");
        textView_1=findViewById(R.id.textView22);
        //textView_2=findViewById(R.id.textView33);
        //获取跳转信息
        Intent intent = getIntent();
        //String str_1 = intent.getStringExtra("name")+"-"+intent.getIntExtra("age",1);
        String str_2 = intent.getExtras().getString("name")+"-"+intent.getExtras().getInt("age");
        //根据跳转包含的值设置控件信息
        //textView_2.setText((str_1));
        textView_1.setText((str_2));
    }
}