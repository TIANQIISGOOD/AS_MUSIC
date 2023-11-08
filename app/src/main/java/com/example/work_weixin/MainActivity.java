package com.example.work_weixin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm;
    private Fragment fragment1,fragment2,fragment3,fragment4;
    private LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //根据传入的code展示对应的fragment
        ActivityResultLauncher launcher=
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if(result.getResultCode()==666)
                                {
                                    showFragment(1);
                                }
                            }
                        });

        fragment1=new diantai();
        fragment2=new wode();
        fragment3=new faxian();
        fragment4=new sheqv();

        initLinearLayout();
        initBotton();
        initFragment();
        showFragment(0);
    }
    private void initLinearLayout(){
        linearLayout1=findViewById(R.id.LinearLayout1);
        linearLayout2=findViewById(R.id.LinearLayout2);
        linearLayout3=findViewById(R.id.LinearLayout3);
        linearLayout4=findViewById(R.id.LinearLayout4);
    }
    private void initFragment(){
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.content,fragment1);
        transaction.add(R.id.content,fragment2);
        transaction.add(R.id.content,fragment3);
        transaction.add(R.id.content,fragment4);
        transaction.commit();
    }//初始化,将所有的tab.xml加入到主界面
    public void initBotton(){
        linearLayout1.setOnClickListener(this::onClick);
        linearLayout2.setOnClickListener(this::onClick);
        linearLayout3.setOnClickListener(this::onClick);
        linearLayout4.setOnClickListener(this::onClick);
    }//初始化按钮,此处可以是imagebottom也可以是其他控件
    private void hideFragment(FragmentTransaction transaction){
        transaction.hide(fragment1);
        transaction.hide(fragment2);
        transaction.hide(fragment3);
        transaction.hide(fragment4);
    }//隐藏tab
    private void showFragment(int i){
        FragmentTransaction transaction=fm.beginTransaction();
        hideFragment(transaction);//先将所有的藏起来
        switch (i){
            case 0:
                transaction.show(fragment1);//展示对应内容
                break;
            case 1:
                transaction.show(fragment2);
                break;
            case 2:
                transaction.show(fragment3);
                break;
            case 3:
                transaction.show(fragment4);
                break;
            default:
                break;
        }
        transaction.commit();
    }//展示对应的tab
    private void onClick(View view){
        if(view.getId()==R.id.LinearLayout1)
        {
            showFragment(0);
        }
        else if (view.getId()==R.id.LinearLayout2)
        {
            showFragment(1);
        }
        else if (view.getId()==R.id.LinearLayout3)
        {
            showFragment(2);
        }
        else
        {
            showFragment(3);
        }

    }//点击按钮,响应事件



}