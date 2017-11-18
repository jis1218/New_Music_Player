package com.project.newmusicplayer;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.project.newmusicplayer.PermissionActivity.BaseActivity;
import com.project.newmusicplayer.PlayList.PlayListViewFrameLayout;
import com.project.newmusicplayer.PlayList.RecyclerViewAdapter;

public class HomeActivity extends BaseActivity {

    PlayListViewFrameLayout playListViewFrameLayout;
    FrameLayout container;
    Button btnList, btnFirstList;

    @Override
    public void init() {
        setContentView(R.layout.activity_home);
        initView();
        setListener();
    }

    public void initView(){
        btnFirstList = findViewById(R.id.btnFirstList);
        btnList = findViewById(R.id.btnList);
        playListViewFrameLayout = new PlayListViewFrameLayout(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this, null);
        play
        addContentView(playListViewFrameLayout,
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        container = findViewById(R.id.container);
//        container.addView(playListViewFrameLayout, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public void setListener(){
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                v.setVisibility(View.GONE);

                playListViewFrameLayout.setVisibility(View.VISIBLE);

            }
        });
    }

}
