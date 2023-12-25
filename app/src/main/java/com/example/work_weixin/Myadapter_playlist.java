package com.example.work_weixin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;


public class Myadapter_playlist extends RecyclerView.Adapter<Myadapter_playlist.Myholder>{
    Context context;
    ArrayList<Music> list;
    View view;


    //传参
    public Myadapter_playlist(Context context, ArrayList<Music> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    //为每个item设置一个点击事件监听器
    //返回每一项布局
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item3, parent, false);
        Myholder myholder = new Myholder(view);
        return myholder;
    }

    @Override

    public void onBindViewHolder(@NonNull Myadapter_playlist.Myholder holder, int position) {
        Music currentMusic = list.get(position);
        holder.textViewName.setText(currentMusic.getName());
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, musicDetails.class);
                intent.putExtra("music", currentMusic);
               // intent.putExtra("musicList", currentMusic.musicList);
                context.startActivity(intent);
            }

        });
    }

    @Override
    //返回列表中的元素数量
    public int getItemCount() {
        return list.size();
    }



    //为列表中的每一行数据设置固定格式(控件),并指定变化的内容
    public class Myholder extends RecyclerView.ViewHolder {
        TextView textViewName;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewPlayList);
        }

    }


    //定义接口实现删除的具体操作
//    public interface OnItemDeleteListener {
//        void onDelete(int position);
//    }
//    //声明
//    private Myadapter_musicList.OnItemDeleteListener onItemDeleteListener;
//    //设置接口的回调方法:
//    public void setOnItemDeleteListener(Myadapter_musicList.OnItemDeleteListener onItemDeleteListener) {
//        this.onItemDeleteListener = onItemDeleteListener;
//    }
//    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//        @Override
//        //处理列表项的拖拽移动逻辑
//        public boolean onMove(@NonNull RecyclerView recyclerView,
//                              @NonNull RecyclerView.ViewHolder viewHolder,
//                              @NonNull RecyclerView.ViewHolder target) {
//            return false;
//        }
//        @Override
//        //onSwiped是RecyclerView的ItemTouchHelper.SimpleCallback类中的一个回调方法。
//        //在用户滑动某个列表项并松开手指后被触发。
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            //获得删除项位置
//            int position = viewHolder.getAdapterPosition();
//            // 调用回调接口通知删除列表项
//            if (onItemDeleteListener != null) {
//                onItemDeleteListener.onDelete(position);
//            }
//        }
//    };
//



}


