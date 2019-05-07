/*
 * Copyright (c) 2017.
 * author:kuan
 * SunStar
 *
 */

package com.cugkuan.widget.myapplication.tab;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cugkuan.widget.myapplication.R;
import com.cugkuan.widget.myapplication.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kuan on 2017/8/28.
 *
 * @Author Kuan
 * @Date 2017/8/28
 * 小鸟校园
 * 这里面的代码比较多，因为处理的逻辑很多。
 */

public class EditTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Subject> mTabs = new ArrayList<>();

    private LayoutInflater mLayoutInflater;


    private ItemTouchHelper mItemTouchHelper;


    /**
     * 是否处于删除的状态
     */
    private boolean isDeleteState = false;

    public interface OnTabDeleteListener {

        void delete(Subject subject);

        void changeState(boolean isEdit);
    }


    private OnTabDeleteListener mOnTabDeleteListener;


    public EditTabAdapter(RecyclerView recyclerView) {
        mItemTouchHelper = new ItemTouchHelper(new MyCallBack());
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        mLayoutInflater = LayoutInflater.from(recyclerView.getContext());
    }

    public List<Subject> getData() {

        return mTabs;
    }

    public void setData(List<Subject> subjects) {

        if (subjects != null) {
            mTabs.clear();
            mTabs.addAll(subjects);
            notifyDataSetChanged();
        }
    }

    public void setDeleteState(boolean isDeleteState) {
        this.isDeleteState = isDeleteState;
        notifyDataSetChanged();
    }

    public void setOnTabDeleteListener(OnTabDeleteListener listener) {
        mOnTabDeleteListener = listener;
    }

    public boolean isEmpty() {
        return mTabs.size() <= 1;
    }

    public void addTab(Subject subject) {

        if (subject != null) {
            int position = mTabs.size();
            mTabs.add(position, subject);
            notifyItemInserted(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        View view = mLayoutInflater.inflate(R.layout.item_my_tab, parent, false);
        viewHolder = new TabItem(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


        TabItem item = (TabItem) holder;
        final Subject subject = mTabs.get(position);
        item.tvSubject.setText(subject.getName());

        if (isDeleteState) {
            item.ivDelete.setVisibility(View.VISIBLE);
        } else {
            item.ivDelete.setVisibility(View.GONE);
        }

        item.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTabDeleteListener != null && isDeleteState) {

                    if (mTabs.size() > 1) {
                        int p = mTabs.indexOf(subject);
                        mOnTabDeleteListener.delete(subject);
                        mTabs.remove(p);
                        notifyItemRemoved(p);
                    } else {
                        Toast.makeText(v.getContext(),
                                "至少有一个栏目",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
        item.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isDeleteState) {
                    isDeleteState = true;
                    notifyDataSetChanged();
                }
                if (mOnTabDeleteListener != null) {
                    mOnTabDeleteListener.changeState(true);
                }
                mItemTouchHelper.startDrag(holder);
                return false;
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

        ImageView ivDelete;
        TextView tvSubject;

        public TabItem(View itemView) {
            super(itemView);
            ivDelete = itemView.findViewById(R.id.iv_subject_delete);
            tvSubject = itemView.findViewById(R.id.tv_tab_nam);
        }
    }

    /**
     * 配合拖拽的回调。
     */
    class MyCallBack extends ItemTouchHelper.Callback {
        private int dragFlags;
        private int swipeFlags;

        /**
         * 设置item是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向
         *
         * @param recyclerView
         * @param viewHolder
         * @return
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //判断 recyclerView的布局管理器数据
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {//设置能拖拽的方向
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipeFlags = 0;//0则不响应事件
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        /**
         * 当用户从item原来的位置拖动可以拖动的item到新位置的过程中调用
         *
         * @param recyclerView
         * @param viewHolder
         * @param target
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {


            int fromPosition = viewHolder.getAdapterPosition();//得到item原来的position
            int toPosition = target.getAdapterPosition();//得到目标position
//            if (toPosition == mTabs.size() - 1 || mTabs.size() - 1 == fromPosition) {
//                return true;
//            }
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mTabs, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mTabs, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        /**
         * 设置是否支持长按拖拽
         *
         * @return
         */
        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        /**
         * @param viewHolder
         * @param direction
         */
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        /**
         * 当用户与item的交互结束并且item也完成了动画时调用
         *
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            notifyDataSetChanged();
        }
    }
}
