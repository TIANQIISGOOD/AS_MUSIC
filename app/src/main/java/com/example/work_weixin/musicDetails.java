package com.example.work_weixin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class musicDetails extends AppCompatActivity {
    TextView textView;
    Button button;
    //Fragment2 fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);
        //接受列表传输的数据
        textView=findViewById(R.id.music_name);
        Intent intent=getIntent();
        //取出数据
        String str=intent.getExtras().getString("name");
        //将数据展示在对应控件中
        textView.setText(str);
        //返回
        button=findViewById(R.id.music_back);
        //监听点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //当点击按钮时，会创建一个新的Intent对象
            public void onClick(View view) {
                Intent intent1=new Intent();
                //设置结果码为666并将Intent对象传递回前一个Activity
                setResult(666,intent1);
                //强制结束该Activity返回到前一个Activity
                finish();
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //点击按钮从Activity跳转到Fragment中
//                FragmentManager fragmentManager =getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                fragment=new Fragment2();
//                fragmentTransaction.replace(,fragment);
//                fragmentTransaction.commit();
//
//            }
//
//
//        });

    }
}