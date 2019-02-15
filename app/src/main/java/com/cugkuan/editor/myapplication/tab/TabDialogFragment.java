package com.cugkuan.editor.myapplication.tab;


import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ToggleButton;

import com.cugkuan.editor.myapplication.DataUitls;
import com.cugkuan.editor.myapplication.R;
import com.cugkuan.editor.myapplication.Subject;
import com.cugkuan.editor.myapplication.TabsViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabDialogFragment extends DialogFragment {


    public static final String GRADE = "grade";


    ToggleButton btnEdit;
    RecyclerView recyclerViewShow;
    RecyclerView recyclerViewHide;


    private EditTabAdapter mEditTabAdapter;

    private HideTabAdapter mHideTabAdapter;

    private TabsViewModel mTabsViewModel;



    public TabDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_grade);
        mTabsViewModel = ViewModelProviders.of(getActivity())
                .get(TabsViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        Window window = dialog.getWindow();

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = DensityUtil.getScreenWidth(getActivity());
        layoutParams.height = (int) (DensityUtil.getScreenWidth(getActivity()) * 1.2);
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        dialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        editComplete();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        editComplete();
    }

    private void editComplete() {

        if (mEditTabAdapter.isEmpty() && mHideTabAdapter.isEmpty()) {
            return;
        }
        DataUitls.setInVisible(mHideTabAdapter.getData());
        DataUitls.setVisible(mEditTabAdapter.getData());
        mTabsViewModel.getTabsLiveData().postValue(mEditTabAdapter.getData());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_tab, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewHide = view.findViewById(R.id.recyclerView_hide);
        recyclerViewShow = view.findViewById(R.id.recyclerView_show);
        btnEdit = view.findViewById(R.id.btn_edit);

        mEditTabAdapter = new EditTabAdapter(recyclerViewShow);
        mHideTabAdapter = new HideTabAdapter(getActivity());

        mEditTabAdapter.setOnTabDeleteListener(new EditTabAdapter.OnTabDeleteListener() {
            @Override
            public void delete(Subject subject) {
                mHideTabAdapter.addTab(subject);
            }

            @Override
            public void changeState(boolean isEdit) {
                btnEdit.setChecked(false);
            }
        });

        mHideTabAdapter.setOnTabAddListener(new HideTabAdapter.OnTabAddListener() {
            @Override
            public void add(Subject subject) {
                mEditTabAdapter.addTab(subject);
            }
        });

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(),
                4, GridLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(),
                4, GridLayoutManager.VERTICAL, false);

        recyclerViewShow.setLayoutManager(gridLayoutManager1);
        recyclerViewHide.setLayoutManager(gridLayoutManager2);


        recyclerViewShow.setAdapter(mEditTabAdapter);
        recyclerViewHide.setAdapter(mHideTabAdapter);


        mEditTabAdapter.setData(DataUitls.getVisible());
        mHideTabAdapter.setData(DataUitls.getInvisible());


        view.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnEdit.isChecked()) {
                    mEditTabAdapter.setDeleteState(false);
                } else {
                    mEditTabAdapter.setDeleteState(true);
                }
            }
        });

    }
}
