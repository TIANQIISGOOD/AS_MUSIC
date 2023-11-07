package com.example.work_weixin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class Myadapter extends RecyclerView.Adapter<Myadapter.Myholder>{
    Context context1;
    List<String> list1;
    View view;


    //传参
    public Myadapter(Context context, List<String> list) {
        context1 = context;
        list1 = list;
    }

    @NonNull
    @Override
    //为每个item设置一个点击事件监听器
    //返回每一项布局
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context1).inflate(R.layout.item, parent, false);
        Myholder myholder = new Myholder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter.Myholder holder, int position) {
        holder.textView.setText(list1.get(position));
        // 在点击事件发生时,使用intent跳转到详情页面,并传递对应数据
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context1, returnActivity.class);
                //获取textview的值
                String textvalue = holder.textView.getText().toString();
                intent.putExtra("name", textvalue);
                context1.startActivity(intent);
            }


        });
    }

    @Override
    //返回列表中的元素数量
    public int getItemCount() {
        return list1.size();
    }



    //为列表中的每一行数据设置固定格式(控件),并指定变化的内容
    public class Myholder extends RecyclerView.ViewHolder {
        TextView textView;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView6);
        }

    }


    //定义接口实现删除的具体操作
    public interface OnItemDeleteListener {
        void onDelete(int position);
    }
    //声明
    private  OnItemDeleteListener onItemDeleteListener;
    //设置接口的回调方法:
    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    //在配适器中实现滑动删除逻辑:
    //ItemTouchHelper.SimpleCallback来监听滑动事件，并在滑动结束时删除相应的列表项。
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        //DragDirs – 用于指定某个列表项是否可以通过拖拽操作进行移动,用二进制或LEFT、RIGHT、START、END、UP 和 DOWN 组成。
        //swipeDirs – 用于指定某个列表项是否可以通过滑动操作进行删除,,用二进制或LEFT、RIGHT、START、END、UP 和 DOWN 组成。
        //public SimpleCallback(int dragDirs, int swipeDirs)--->滑动方向设定
        //此处我们设定向左滑动删除,DragDirs参数设置为0，即不指定任何方向。
        @Override
        //处理列表项的拖拽移动逻辑
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        //onSwiped是RecyclerView的ItemTouchHelper.SimpleCallback类中的一个回调方法。
        //在用户滑动某个列表项并松开手指后被触发。
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //获得删除项位置
            int position = viewHolder.getAdapterPosition();
            // 调用回调接口通知删除列表项
            if (onItemDeleteListener != null) {
                onItemDeleteListener.onDelete(position);
            }
        }
    };


}


