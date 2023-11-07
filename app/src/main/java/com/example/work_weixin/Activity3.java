package com.example.work_weixin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity3 extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("kk","3:onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("kk","3:onRestart");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("kk","3:onPostResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("kk","3:onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("kk","3:onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("kk","3:onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("kk","3:onStop");
    }
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        button3=findViewById(R.id.button33);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("result","888");
                setResult(666,intent);
                finish();//强制结束该页面-->页面跳转不必指定,只要结束,直接跳转
            }
        });
        Log.d("kk","3:onCreate");
    }
}