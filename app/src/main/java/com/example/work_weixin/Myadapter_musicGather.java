package com.example.work_weixin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Myadapter_musicGather extends RecyclerView.Adapter<Myadapter_musicGather.Myholder>{
    Context context1;
    List<Integer> image_music1;
    View view;


    //传参
    public Myadapter_musicGather(Context context, List<Integer> image_music) {
        context1 = context;
       image_music1=image_music;
    }

    @NonNull
    @Override
    //为每个item设置一个点击事件监听器
    //返回每一项布局
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context1).inflate(R.layout.item1, parent, false);
        Myholder myholder = new Myholder(view);
        return myholder;
    }

    @Override

    //根据位置获取图片资源ID，并将其设置给ImageView
    public void onBindViewHolder(@NonNull Myadapter_musicGather.Myholder holder, int position) {
       int imageId =image_music1.get(position);
       holder.imageView.setImageResource(imageId);

    }

    @Override
    //返回列表中的元素数量
    public int getItemCount() {
        return image_music1.size();
    }



    //为列表中的每一行数据设置固定格式(控件),并指定变化的内容
    public class Myholder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gedan_imag);
        }

    }




}


