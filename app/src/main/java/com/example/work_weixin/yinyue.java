package com.example.work_weixin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class yinyue extends Fragment {
    ImageButton button_likeList;
    RecyclerView recyclerView_likeList;
    boolean isView = false;
    boolean isplay=false;
    boolean isshoucang=false;
    Context context;
    List<String> list;
    Myadapter_playlist adapter;
    ImageView imageView_tuzi,imageView_shoucang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //这是一个fragment在该view中,使用地是该view的控件
        //布局填充器（inflater）将布局文件 diantai.xml 转换为视图，并返回该视图作为片段的根视图。
        context = getContext();
        View view = inflater.inflate(R.layout.music, container, false);
        //喜爱的FM列表
        recyclerView_likeList = view.findViewById(R.id.RecyclerViewMusicList);
        button_likeList = view.findViewById(R.id.imageButtonMusicList);

        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("FM55." + (i + 1));
        }
//        //将配适器设置给对应的 recyclerView
//        //adapter = new Myadapter_playlist(context, list);
//        recyclerView_likeList.setAdapter(adapter);
//        //布局管理
//        LinearLayoutManager manager = new LinearLayoutManager(context);
//        manager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView_likeList.setLayoutManager(manager);

        // 设置初始状态下列表为隐藏
        recyclerView_likeList.setVisibility(View.GONE);
        button_likeList.setOnClickListener(new View.OnClickListener() {
            @Override
            //点击按钮时，根据列表的可见性状态，显示或隐藏 RecyclerView 列表。
            public void onClick(View view) {
                if (!isView) {
                    recyclerView_likeList.setVisibility(View.VISIBLE);
                    // 将RecyclerView移动到最顶层
                    recyclerView_likeList.bringToFront();
                    isView = true;
                } else {
                    recyclerView_likeList.setVisibility(View.GONE);
                    isView = false;
                }
            }
        });
        //播放按钮(兔子)按下改变颜色-->播放
        imageView_tuzi = view.findViewById(R.id.imageTUZI);
        imageView_tuzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isplay) {
                    imageView_tuzi.setImageResource(R.drawable.tuzi);
                    isplay = true;
                } else {
                    imageView_tuzi.setImageResource(R.drawable.tuzih);
                    isplay = false;
                }
            }
        });
        //收藏按钮:按下星星填满
        imageView_shoucang = view.findViewById(R.id.shoucang);
        imageView_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isshoucang) {
                    imageView_shoucang.setImageResource(R.drawable.shoucang);
                    isshoucang = true;
                } else {
                    imageView_shoucang.setImageResource(R.drawable.shoucangy);
                    isshoucang = false;
                }
            }
        });

//        //将ItemTouchHelper与RecyclerView关联
//        //监听删除动作:适配器通过onItemDeleteListener来回调监听器的方法。
//        adapter.setOnItemDeleteListener(this::onDelete);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(adapter.simpleCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView_likeList);
        return view;
    }

    //实现接口回调,选择在此处是因为我们要删除的数据源于此处
    public void onDelete(int position) {
        //扩展:窗口弹出询问是否删除
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//在当前Activity中展示
        builder.setTitle("DELETE")//设置标题
                .setMessage("Are you sure?")//设置提示语
                .setPositiveButton("YES!", new DialogInterface.OnClickListener() {//当确认时,发生点击事件删除&更新
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除
                        list.remove(position);
                        //更新
                        adapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("NO~", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })//当后悔时,点击了什么都不做
                .show();//展示窗口


    }
}