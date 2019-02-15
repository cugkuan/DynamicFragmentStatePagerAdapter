package com.cugkuan.editor.myapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cugkuan.editor.mylibrary.DynamicFragmentStatePagerAdapter;

import java.util.List;

/**
 * 使用内部类，考虑到科目的变化
 */

class CurriculumIndexAdapter extends DynamicFragmentStatePagerAdapter<Subject> {

     private List<Subject> mSubjects;

    public CurriculumIndexAdapter(FragmentManager manager) {
        super(manager);
    }

    public void setData(List<Subject> subjects){
        mSubjects = subjects;
        notifyDataSetChanged();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mSubjects.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {

        Subject subject = getItemData(position);
        Fragment fragment = BlankFragment.newInstance(subject);
        return fragment;
    }

    @Override
    public int getCount() {
        int count = mSubjects == null ? 0 : mSubjects.size();
        return count;
    }

    @Override
    public Subject getItemData(int position) {

        if (position >= mSubjects.size()) {
            return null;
        }
        return mSubjects == null ? null : mSubjects.get(position);
    }

    @Override
    public boolean dataEquals(Subject oldData, Subject newData) {

        if (oldData == null || newData == null) {
            return false;
        }
        return oldData.getId() == newData.getId();
    }

    @Override
    public int getDataPosition(Subject data) {
        int index = -1;
        for (int i = 0;i < mSubjects.size();i++){
            Subject subject = mSubjects.get(i);
            if (dataEquals(subject,data)){
                index = i;
                break;
            }
        }
        return index;
    }
}
