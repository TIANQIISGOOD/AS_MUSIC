package com.example.work_weixin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wode extends Fragment {
    RecyclerView recyclerView,recyclerView1;
    //键值对：类型：map
    ArrayList<Music> list;
    List<Integer> image_music;
    Myadapter_musicList adapter;
    Myadapter_musicGather adapter1;
    Context context;
    View view;
    MusicList myLike;
    @Override
    //界面列表展示
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getContext();
        view = inflater.inflate(R.layout.wode, container, false);
        //横向歌单:
        recyclerView1 = view.findViewById(R.id.recyclerView_top);
        //歌单图片数据导入
        image_music = new ArrayList<>();
        image_music.add(R.drawable.background);
        image_music.add(R.drawable.background1);
        image_music.add(R.drawable.background2);

        //将配适器设置给对应的 recyclerView
        adapter1 = new Myadapter_musicGather(context,image_music);
        recyclerView1.setAdapter(adapter1);


        //布局管理:
        LinearLayoutManager manager1=new LinearLayoutManager(context);
        manager1.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView1.setLayoutManager(manager1);



        //纵向歌曲列表:此处展示的是"Love"歌单
        recyclerView = view.findViewById(R.id.recyclerview);
        //MusicList myLike;
        //通过指定的歌单名称,构造一个MusicList实例
        myLike=new MusicList(context,"Love");
        //调用实例中的变量得到一个元素为 Music的ArrayList变量,传给配适器
        list=myLike.musicList;
        //将配适器设置给对应的 recyclerView,并传入相关变量
        adapter=new Myadapter_musicList(context,list);
        recyclerView.setAdapter(adapter);
        //布局管理
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);


        //将ItemTouchHelper与RecyclerView关联
        //监听删除动作:适配器通过onItemDeleteListener来回调监听器的方法。
//        adapter.setOnItemDeleteListener(this);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(adapter.simpleCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;

    }

//    @Override
//    //实现接口回调,选择在此处是因为我们要删除的数据源于此处
//    public void onDelete(int position) {
//        //扩展:窗口弹出询问是否删除
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//在当前Activity中展示
//        builder.setTitle("DELETE")//设置标题
//                .setMessage("Are you sure?")//设置提示语
//                        .setPositiveButton("YES!", new DialogInterface.OnClickListener() {//当确认时,发生点击事件删除&更新
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //删除
//                                //myLike.removeMusic();
//                                //更新
//                                adapter.notifyItemRemoved(position);
//                            }
//                        })
//                .setNegativeButton("NO~", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                })//当后悔时,点击了什么都不做
//                .show();//展示窗口
//
//
//    }


}
