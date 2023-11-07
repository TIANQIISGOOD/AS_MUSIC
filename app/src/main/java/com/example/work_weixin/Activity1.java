package com.example.work_weixin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity1 extends AppCompatActivity {
    Button button1;
    Button button2;
    TextView textView1;
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("kk","onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("kk","onRestart");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("kk","onPostResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("kk","onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("kk","onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("kk","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("kk","onStop");
    }

   // @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        textView1=findViewById(R.id.textView11);
        Log.d("kk","onCreate");
        //监听点击事件
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转目标设定
                Intent intent=new Intent(Activity1.this,Activity2.class);
                //传值
                //intent.putExtra("name","intent:kk_1");
                //intent.putExtra("age",20);
                Bundle bundle=new Bundle();
                bundle.putString("name","bundle:kk_2");
                bundle.putInt("age",88);
                intent.putExtras(bundle);
                //开始跳转
                startActivity(intent);

            }
        });
//        ActivityResultLauncher launcher= new ActivityResultLauncher() {
//            @Override
//            public void launch(Object input, @Nullable ActivityOptionsCompat options) {
//
//            }
//
//            @Override
//            public void unregister() {
//
//            }
//
//            @NonNull
//            @Override
//            public ActivityResultContract getContract() {
//                return null;
//            }
//        };
        ActivityResultLauncher launcher=
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==666)
                        {
                            String str =result.getData().getStringExtra("result");
                            textView1.setText(str);
                        }


                    }
                });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_3=new Intent(Activity1.this,Activity3.class);
                launcher.launch(intent_3);

            }
        });

    }

}