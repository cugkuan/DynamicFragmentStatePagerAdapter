/*
 * Copyright (c) 2017.
 * author:kuan
 * SunStar
 *
 */

package com.cugkuan.editor.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kuan on 2017/11/28.
 *
 * @Author Kuan
 * @Date 2017/11/28
 * 小鸟校园
 * 科目
 * 语文，数学等科目
 */

public class Subject implements Parcelable {

    /**
     * id : 1
     * name : 数学
     * ename : Math
     * icon : http://img0.bdstatic.com/static/searchresult/img/logo-2X_b99594a.png
     */
    private int id;
    //名字
    private String name;
    /**
     * 英语名字
     */
    private String ename;
    /**
     * 图标
     */
    private String icon;


    /**
     * 排序，这是一个辅助字段
     */
    private int sort;

    /**
     * 可视性，辅助字段
     */
    private boolean visible = true;


    /**
     * 年级的Id，这是一个辅助字段,不属于实体本身
     */
    private int gradeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    //辅助变量，不是实体的属性
    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    @Override
    protected Subject clone()  {

        Subject subject = new Subject();

        subject.setEname(ename);
        subject.setName(name);
        subject.setIcon(icon);
        subject.setId(id);
        subject.setSelect(isSelect);
        subject.setSort(sort);
        subject.setVisible(visible);
        return subject;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj){
            return true;
        }else {
            if (obj instanceof Subject){

                Subject value = (Subject)obj;

                if (value.getId() == id){
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }
    }

    public Subject() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.ename);
        dest.writeString(this.icon);
        dest.writeInt(this.sort);
        dest.writeByte(this.visible ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    protected Subject(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ename = in.readString();
        this.icon = in.readString();
        this.sort = in.readInt();
        this.visible = in.readByte() != 0;
        this.isSelect = in.readByte() != 0;
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel source) {
            return new Subject(source);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
}
