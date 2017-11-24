package com.project.newmusicplayer;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.project.newmusicplayer.Dmanager.DataManager;
import com.project.newmusicplayer.MusicAndPlay.GetMusicInfo;
import com.project.newmusicplayer.MusicAndPlay.Music;
import com.project.newmusicplayer.MusicAndPlay.PlayMusic;
import com.project.newmusicplayer.PermissionActivity.BaseActivity;
import com.project.newmusicplayer.PlayList.PlayListViewFrameLayout;
import com.project.newmusicplayer.PlayList.RecyclerViewAdapter;
import com.project.newmusicplayer.Util.Const;
import com.project.newmusicplayer.Util.TimeConverter;

public class HomeActivity extends BaseActivity implements RecyclerViewAdapter.SetPlayListFrameLayout, PlayMusic.Example {

    PlayListViewFrameLayout playListViewFrameLayout;
    ConstraintLayout layout, constraintPlay;
    Button btnList, btnFirstList;
    ImageButton btnPlay, btnFoward, btnBackward;
    Button btnToList;
    TextView tvArtistTitle, tvStrTime, tvMusicTime;
    RelativeLayout relativeFirst, relativeLayout;
    ImageView imageViewAlbumArt;
    DataManager dataManager;
    SeekBar seekBar;
    int time;

    @Override
    public void init() {
        setContentView(R.layout.activity_home);
        dataManager = DataManager.getInstance();
        PlayMusic.getInstance().getExample(this);
        initView();
        setListener();
        Const.setFlagTrue();
        setAsyncTask();
        setinitViewIfPlaying();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Const.setFlagFalse();
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
        seekBar = findViewById(R.id.seekBar);

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

    private void setinitViewIfPlaying(){
        if(PlayMusic.getInstance().mediaPlayer!=null){
            setAlbumArt(dataManager.getAlbumUri());
            setSongInfo(dataManager.getSonginfo(), dataManager.getSongLength());
            time = PlayMusic.getInstance().mediaPlayer.getCurrentPosition()/1000;
            tvStrTime.setText(TimeConverter.getSec(time));
            seekBar.setProgress(time);

        }
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


    @SuppressLint("StaticFieldLeak")
    public void setAsyncTask() {
        new AsyncTask<Object, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Object... objects) {

                    while (Const.flag) {
                        if(PlayMusic.getInstance().mediaPlayer!=null){
                            if(PlayMusic.getInstance().mediaPlayer.isPlaying()){
                                time++;
                                publishProgress(time);

                                Log.d("time은", time + "");
                            }
                        }


                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                return 0;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                tvStrTime.setText(TimeConverter.getSec(time));
                seekBar.setProgress(time);

            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                tvStrTime.setText(TimeConverter.getSec(time));
            }
        }.execute();

    }

    public void setListener() {

        tvArtistTitle.setOnTouchListener(new View.OnTouchListener() {
            float dY;

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
                switch (v.getTag().toString()) {
                    //누르면 플레이됨
                    case "Pause":
                        Const.setFlagTrue();
                        ((ImageButton) v).setImageResource(android.R.drawable.ic_media_pause);
                        v.setTag("Playing");
                        Intent intent = new Intent(HomeActivity.this, PlayService.class);
                        intent.setAction("From_PlayButton");
                        startService(intent);
                        break;
                    //누르면 멈춤
                    case "Playing":
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
                dataManager.setPosition(dataManager.getPosition() + 1);
                dataManager.sendIntentToService(HomeActivity.this);
                setAlbumArt(dataManager.getAlbumUri());
                setSongInfo(dataManager.getSonginfo(), dataManager.getSongLength());
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManager.setPosition(dataManager.getPosition() - 1);
                dataManager.sendIntentToService(HomeActivity.this);
                setAlbumArt(dataManager.getAlbumUri());
                setSongInfo(dataManager.getSonginfo(), dataManager.getSongLength());


            }
        });

//        PlayMusic.getInstance().mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                dataManager.setPosition(dataManager.getPosition() + 1);
//                setAlbumArt(dataManager.getAlbumUri());
//                setSongInfo(dataManager.getSonginfo(), dataManager.getSongLength());
//                dataManager.sendIntentToService(HomeActivity.this);
//            }
//        });


        btnToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                time = seekBar.getProgress();
                PlayMusic.getInstance().mediaPlayer.seekTo(time*1000);

                tvStrTime.setText(TimeConverter.getSec(time));
            }
        });
    }
    @Override
    public void goFoward(){
        dataManager.setPosition(dataManager.getPosition() + 1);
        dataManager.sendIntentToService(HomeActivity.this);
        setAlbumArt(dataManager.getAlbumUri());
        setSongInfo(dataManager.getSonginfo(), dataManager.getSongLength());
    }

    @Override
    public void setAlbumArt(Uri uri) {
        if ("".equals(uri.toString())) {
            imageViewAlbumArt.setImageResource(R.mipmap.ic_launcher);
        } else {
            imageViewAlbumArt.setImageURI(uri);
        }
    }

    @Override
    public void setSongInfo(String str, String length) {
        tvArtistTitle.setText(str);
        btnPlay.setImageResource(android.R.drawable.ic_media_pause);
        btnPlay.setTag("Playing");
        tvMusicTime.setText(length);
        seekBar.setMax(dataManager.getsongLengthInt());
        time = 0;
    }
}
