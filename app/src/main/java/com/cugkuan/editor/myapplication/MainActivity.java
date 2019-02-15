package com.cugkuan.editor.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cugkuan.editor.myapplication.tab.TabDialogFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    private TabLayout tabs;

    private ViewPager viewPager;

    private CurriculumIndexAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = findViewById(android.R.id.tabs);
        viewPager = findViewById(R.id.viewPager);

        mAdapter = new CurriculumIndexAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabs.setupWithViewPager(viewPager);

        findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabDialogFragment fragment = new TabDialogFragment();
                fragment.show(getSupportFragmentManager(),"xxx");

            }
        });

        ViewModelProviders.of(this).get(TabsViewModel.class)
                .getTabsLiveData().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(@Nullable List<Subject> subjects) {
                mAdapter.setData(subjects);

            }
        });
    }
}
