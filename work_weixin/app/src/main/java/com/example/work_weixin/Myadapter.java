package com.example.work_weixin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myadapter.Myholder> {

    Context context1;
    List<String> list1;
    View view;
    //传参
    public  Myadapter(Context context,List<String> list)
    {
        context1=context;
        list1=list;
    }

    @NonNull
    @Override
    //为每个item设置一个点击事件监听器
    //在点击事件发生时,使用intent跳转到详情页面,并传递对应数据
    //返回每一项布局
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view=LayoutInflater.from(context1).inflate(R.layout.item,parent,false);
        Myholder myholder = new Myholder(view);
        return myholder;
    }

    @Override
    //将数据提取出来
    public void onBindViewHolder(@NonNull Myadapter.Myholder holder, int position) {
        holder.textView.setText(list1.get(position));
    }

    @Override
    //返回列表中的元素数量
    public int getItemCount() {
        return list1.size();
    }

    //为列表中的每一行数据设置固定格式(控件),并指定变化的内容
    public class Myholder extends RecyclerView.ViewHolder
    {
        TextView textView;
        public  Myholder(@NonNull View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.textView6);
        }

    }
}
