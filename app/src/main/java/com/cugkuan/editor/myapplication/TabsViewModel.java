package com.cugkuan.editor.myapplication;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cugkuan.editor.myapplication.Subject;

import java.util.List;

public class TabsViewModel extends ViewModel {



    private MutableLiveData<List<Subject>> mTabsLiveData;



    public MutableLiveData<List<Subject>> getTabsLiveData(){

        if (mTabsLiveData == null){
            mTabsLiveData = new MutableLiveData<>();
            mTabsLiveData.setValue(DataUitls.getVisible());

        }
        return mTabsLiveData;
    }
    public void setSubjects(List<Subject> subjects){

        if (mTabsLiveData == null){
            mTabsLiveData = new MutableLiveData<>();
        }
        mTabsLiveData.setValue(subjects);
    }

}
