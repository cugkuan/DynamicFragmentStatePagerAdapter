/*
 * Copyright (c) 2017.
 * author:kuan
 * SunStar
 *
 */

package com.cugkuan.widget.myapplication.tab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cugkuan.widget.myapplication.R;
import com.cugkuan.widget.myapplication.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuan on 2017/8/28.
 *
 * @Author Kuan
 * @Date 2017/8/28
 * 小鸟校园

 */
public class HideTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<Subject> mTabs = new ArrayList<>();

    private LayoutInflater mLayoutInflater;

    public interface OnTabAddListener {

        void add(Subject subject);
    }


    private OnTabAddListener mOnTabAddListener;


    public HideTabAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnTabAddListener(OnTabAddListener listener) {
        mOnTabAddListener = listener;
    }

    public boolean isEmpty() {
        return mTabs.size() <= 1;
    }


    public List<Subject> getData(){
        return mTabs;
    }

    public void setData(List<Subject> subjects){

        if (subjects != null) {
            mTabs.clear();
            mTabs.addAll(subjects);
            notifyDataSetChanged();
        }
    }

    public void addTab(Subject subject){

        if (subject != null) {

            int position = mTabs.size();
            mTabs.add(position,subject);
            notifyItemInserted(position);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        View view = mLayoutInflater.inflate(R.layout.item_add_tab, parent, false);
        viewHolder = new TabItem(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


        TabItem item = (TabItem) holder;
        final Subject subject = mTabs.get(position);
        item.tvSubject.setText(subject.getName());
        item.tvSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTabAddListener != null){
                    int  p = mTabs.indexOf(subject);
                    mOnTabAddListener.add(subject);
                    mTabs.remove(p);
                    notifyItemRemoved(p);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
       return mTabs.size();
    }

    /**
     * 图片展示的item
     */
    static class TabItem extends RecyclerView.ViewHolder {

        TextView tvSubject;

        public TabItem(View itemView) {
            super(itemView);
            tvSubject =  itemView.findViewById(R.id.tv_tab_nam);
        }
    }
}
