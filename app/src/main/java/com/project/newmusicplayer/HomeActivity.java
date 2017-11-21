package com.project.newmusicplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.newmusicplayer.PermissionActivity.BaseActivity;
import com.project.newmusicplayer.PlayList.PlayListViewFrameLayout;
import com.project.newmusicplayer.PlayList.RecyclerViewAdapter;

public class HomeActivity extends BaseActivity {

    PlayListViewFrameLayout playListViewFrameLayout;
    ConstraintLayout layout, constraintPlay, constraintTop;
    ConstraintSet constraintSet;
    Button btnList, btnFirstList;
    ImageButton btnPlay;
    Button btnToList;
    TextView tvArtistTitle;
    RelativeLayout relativeFirst, relativeLayout;


    @Override
    public void init() {
        setContentView(R.layout.activity_home);
        initView();
        setListener();
    }

    @Override
    public void onBackPressed() {
        if (playListViewFrameLayout.getVisibility() == View.VISIBLE) {
            playListViewFrameLayout.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    public void initView() {
        btnFirstList = findViewById(R.id.btnFirstList);
        btnList = findViewById(R.id.btnList);
        layout = findViewById(R.id.layout);
        constraintPlay = findViewById(R.id.constraintPlay);
        btnPlay = findViewById(R.id.btnPlay);
        btnToList = findViewById(R.id.btnToList);
        tvArtistTitle = findViewById(R.id.tvArtistTitle);
        relativeLayout = findViewById(R.id.relative);
        relativeFirst = findViewById(R.id.relativeFirst);

        playListViewFrameLayout = new PlayListViewFrameLayout(this);

        playListViewFrameLayout.setBackgroundColor(Color.CYAN);
        playListViewFrameLayout.setVisibility(View.INVISIBLE);

        Log.d("generateViewId()", playListViewFrameLayout.getId() + "");

        ViewTreeObserver viewTreeObserver = relativeLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                relativeLayout.setY(constraintPlay.getY() - tvArtistTitle.getHeight());
                relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                relativeFirst.addView(playListViewFrameLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, relativeFirst.getHeight() - tvArtistTitle.getHeight()));

            }
        });


        tvArtistTitle.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = relativeLayout.getX() - event.getRawX();
                        dY = relativeLayout.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        relativeLayout.animate()
                                .y(event.getRawY()+dY)
                                .setDuration(0)
                                .start();
                        //v.setY(event.getRawY()+dY);
                        break;

                    case  MotionEvent.ACTION_UP :
                        break;


                }
                return true;
            }
        });


    }

    @SuppressLint("ClickableViewAccessibility")
    public void setListener() {
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
                Log.d("height", (constraintPlay.getY() + tvArtistTitle.getHeight() + ""));
                relativeLayout.setY(constraintPlay.getY() - tvArtistTitle.getHeight());
            }
        });


    }

}
