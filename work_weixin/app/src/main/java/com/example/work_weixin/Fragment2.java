package com.example.work_weixin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    RecyclerView recyclerView;
    List<String> list;
    Myadapter adapter;
    Context context;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.tab2, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        list=new ArrayList<>();
        for (int i=0;i<9;i++)
        {
            list.add("联系人"+(i+1));
        }
        context=getContext();
        adapter=new Myadapter(context,list);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        return view;

    }
}