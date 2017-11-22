package com.project.newmusicplayer;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
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

import com.project.newmusicplayer.Dmanager.DataManager;
import com.project.newmusicplayer.MusicAndPlay.GetMusicInfo;
import com.project.newmusicplayer.PermissionActivity.BaseActivity;
import com.project.newmusicplayer.PlayList.PlayListViewFrameLayout;
import com.project.newmusicplayer.PlayList.RecyclerViewAdapter;

public class HomeActivity extends BaseActivity implements RecyclerViewAdapter.SetPlayListFrameLayout {

    PlayListViewFrameLayout playListViewFrameLayout;
    ConstraintLayout layout, constraintPlay, constraintTop;
    ConstraintSet constraintSet;
    Button btnList, btnFirstList;
    ImageButton btnPlay, btnFoward, btnBackward;
    Button btnToList;
    TextView tvArtistTitle, tvStrTime, tvMusicTime;
    RelativeLayout relativeFirst, relativeLayout;
    ImageView imageViewAlbumArt;
    DataManager dataManager;
    int time=0;


    @Override
    public void init() {
        setContentView(R.layout.activity_home);
        initView();
        setListener();
        dataManager = DataManager.getInstance();
    }

    @Override
    public void onBackPressed() {
        if (playListViewFrameLayout.getVisibility() == View.VISIBLE) {
            playListViewFrameLayout.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }


    public void initView() {
        btnFirstList = findViewById(R.id.btnFirstList);
        btnList = findViewById(R.id.btnList);
        layout = findViewById(R.id.layout);
        constraintPlay = findViewById(R.id.constraintPlay);
        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setTag("Pause");
        btnFoward = findViewById(R.id.btnFoward);
        btnBackward = findViewById(R.id.btnBackward);
        btnToList = findViewById(R.id.btnToList);
        tvArtistTitle = findViewById(R.id.tvArtistTitle);
        tvStrTime = findViewById(R.id.tvStrTime);
        tvMusicTime = findViewById(R.id.tvMusicTime);
        relativeLayout = findViewById(R.id.relative);
        relativeFirst = findViewById(R.id.relativeFirst);
        imageViewAlbumArt = findViewById(R.id.imageViewAlbumArt);
        playListViewFrameLayout = new PlayListViewFrameLayout(this, this);
        playListViewFrameLayout.setBackgroundColor(Color.CYAN);
        playListViewFrameLayout.setVisibility(View.INVISIBLE);

        ViewTreeObserver viewTreeObserver = relativeLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relativeLayout.setY(constraintPlay.getY() - tvArtistTitle.getHeight());
                relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                relativeFirst.addView(playListViewFrameLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, relativeFirst.getHeight() - tvArtistTitle.getHeight()));
            }
        });
    }

    private void setAnimator(int index) {

        float value = 0;

        if (index == 0) {
            value = 0;
        } else if (index == 1) {
            value = constraintPlay.getY() - tvArtistTitle.getHeight();
        }
        ObjectAnimator animForRelativeLayout = ObjectAnimator.ofFloat(relativeLayout, "translationY", value);
        animForRelativeLayout.setDuration(500);
        animForRelativeLayout.start();
    }

    private String miliToSec(int mili) {
        int sec = mili / 1000;
        int min = sec / 60;
        sec = sec % 60;

        return String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

    @SuppressLint("StaticFieldLeak")
    private void setASync(){
        //time=0;
        new AsyncTask<Void, Integer, Integer>(){
            @Override
            protected Integer doInBackground(Void... voids) {
                while(time<30){
                    time++;
                    publishProgress(time);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                return time;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                tvStrTime.setText(miliToSec(time));
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                tvStrTime.setText(miliToSec(time));
            }
        }.execute();
    }

    public void setListener() {

        tvArtistTitle.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (relativeLayout.getY() <= 0) {
                            relativeLayout.setY(1);
                        }
                        dY = relativeLayout.getY() - event.getRawY();
                        Log.d("getY()", relativeLayout.getY() + "");
                        Log.d("getRawY()", event.getRawY() + "");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (relativeLayout.getY() > 0) {
                            relativeLayout.animate()
                                    .y(event.getRawY() + dY)
                                    .setDuration(0)
                                    .start();
                        }
                        //v.setY(event.getRawY()+dY);
                        break;

                    case MotionEvent.ACTION_UP:
                        if (relativeLayout.getY() <= relativeLayout.getHeight() / 2) {
                            setAnimator(0);
                        } else {
                            setAnimator(1);
                        }
                        break;
                }
                return true;
            }


        });
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
                switch(v.getTag().toString()){
                    case "Pause" :
                        ((ImageButton) v).setImageResource(android.R.drawable.ic_media_pause);
                        v.setTag("Playing");
                        Intent intent = new Intent(HomeActivity.this, PlayService.class);
                        intent.setAction("From_PlayButton");
                        startService(intent);
                        break;

                    case "Playing" :
                        ((ImageButton) v).setImageResource(android.R.drawable.ic_media_play);
                        v.setTag("Pause");
                        Intent intent2 = new Intent(HomeActivity.this, PlayService.class);
                        intent2.setAction("From_PauseButton");
                        startService(intent2);

                        break;


                }

            }
        });

        btnFoward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManager.setPosition(dataManager.getPosition()+1);
                setAlbumArt(dataManager.getAlbumUri());
                setTitleArtist(dataManager.getSonginfo());
                dataManager.sendIntentToService(HomeActivity.this);
                setASync();
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManager.setPosition(dataManager.getPosition()-1);
                setAlbumArt(dataManager.getAlbumUri());
                setTitleArtist(dataManager.getSonginfo());
                dataManager.sendIntentToService(HomeActivity.this);
            }
        });



        btnToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void setAlbumArt(Uri uri) {
        if ("".equals(uri.toString())) {
            imageViewAlbumArt.setImageResource(R.mipmap.ic_launcher);
        }else {
            imageViewAlbumArt.setImageURI(uri);
        }
    }

    @Override
    public void setTitleArtist(String str) {
        tvArtistTitle.setText(str);
        btnPlay.setImageResource(android.R.drawable.ic_media_pause);
        btnPlay.setTag("Playing");
    }
}
