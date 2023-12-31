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

import java.util.ArrayList;


public class Myadapter_musicList extends RecyclerView.Adapter<Myadapter_musicList.Myholder>{
    Context context;
    ArrayList<Music> list;
    View view;


    //传参
    public Myadapter_musicList(Context context, ArrayList<Music> list) {
        this.context = context;
        this.list=list;
    }

    @NonNull
    @Override
    //返回每一项布局
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        Myholder myholder = new Myholder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter_musicList.Myholder holder, int position) {
        Music currentMusic = list.get(position);
        holder.textViewName.setText(currentMusic.getName());
        holder.textViewAuthor.setText(currentMusic.getAuthor());
        // 在点击事件发生时,使用intent跳转到详情页面,并传递对应数据:一个Music实例
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, musicDetails.class);
                intent.putExtra("music", currentMusic);
                //intent.putExtra("musicList", currentMusic.musicList);
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
        TextView textViewAuthor;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewMusicName);
            textViewAuthor = itemView.findViewById(R.id.textViewMusicAuthor);
        }

    }


//    //定义接口实现删除的具体操作
//    public interface OnItemDeleteListener {
//        void onDelete(int position);
//    }
//    //声明
//    private  OnItemDeleteListener onItemDeleteListener;
//    //设置接口的回调方法:
//    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
//        this.onItemDeleteListener = onItemDeleteListener;
//    }
//
//    //在配适器中实现滑动删除逻辑:
//    //ItemTouchHelper.SimpleCallback来监听滑动事件，并在滑动结束时删除相应的列表项。
//    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//        //DragDirs – 用于指定某个列表项是否可以通过拖拽操作进行移动,用二进制或LEFT、RIGHT、START、END、UP 和 DOWN 组成。
//        //swipeDirs – 用于指定某个列表项是否可以通过滑动操作进行删除,,用二进制或LEFT、RIGHT、START、END、UP 和 DOWN 组成。
//        //public SimpleCallback(int dragDirs, int swipeDirs)--->滑动方向设定
//        //此处我们设定向左滑动删除,DragDirs参数设置为0，即不指定任何方向。
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


}


