package com.project.newmusicplayer;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.newmusicplayer.PermissionActivity.BaseActivity;
import com.project.newmusicplayer.PlayList.PlayListViewFrameLayout;
import com.project.newmusicplayer.PlayList.RecyclerViewAdapter;

public class HomeActivity extends BaseActivity {

    PlayListViewFrameLayout playListViewFrameLayout;
    ConstraintLayout layout;
    ConstraintSet constraintSet;
    Button btnList, btnFirstList;
    ImageButton btnPlay;
    Button btnToList;


    @Override
    public void init() {
        setContentView(R.layout.activity_home);
        initView();
        setListener();
    }

    public void initView(){
        btnFirstList = findViewById(R.id.btnFirstList);
        btnList = findViewById(R.id.btnList);
        layout = findViewById(R.id.layout);
        btnPlay = findViewById(R.id.btnPlay);
        btnToList = findViewById(R.id.btnToList);

        //constraintSet = new ConstraintSet();
        //constraintSet.clone(layout);
        playListViewFrameLayout = new PlayListViewFrameLayout(this);
        //playListViewFrameLayout.setId(View.generateViewId());
        //playListViewFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //Log.d("playListView 아이디", ""+playListViewFrameLayout.getId());
        //playListViewFrameLayout.setId(View.generateViewId());
//        constraintSet.clear(playListViewFrameLayout.getId());
//        constraintSet.connect(playListViewFrameLayout.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT,0);
//        constraintSet.connect(playListViewFrameLayout.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
//        constraintSet.constrainWidth(playListViewFrameLayout.getId(), ConstraintSet.MATCH_CONSTRAINT);

        //playListViewFrameLayout.clearFocus();

        playListViewFrameLayout.setBackgroundColor(Color.WHITE);
        playListViewFrameLayout.setVisibility(View.GONE);
//        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
//        playListViewFrameLayout.setLayoutParams(params);

//        constraintSet.applyTo(layout);
//
//        layout.addView(playListViewFrameLayout);
        addContentView(playListViewFrameLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800));

    }

    public void setListener(){
        // List 클릭하면 숨어있던 리스트가 나오게 됨
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playListViewFrameLayout.setVisibility(View.VISIBLE);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "하하하", Toast.LENGTH_SHORT).show();
            }
        });

        btnToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
