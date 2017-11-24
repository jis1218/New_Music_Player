package com.project.newmusicplayer.MusicAndPlay;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by 정인섭 on 2017-11-16.
 */

public class PlayMusic {

    private static PlayMusic playMusic;
    private Example example;

    public static PlayMusic getInstance(){
        if(playMusic==null){
            playMusic = new PlayMusic();
        }
        return playMusic;
    }

    public MediaPlayer mediaPlayer = null;

    public void setPlayer(Context context, Uri uri){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }

        mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                example.goFoward();
            }
        });
    }

    public void getExample(Example example){
        this.example = example;
    }

    public void startPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }
    }

    public void pausePlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }

    public interface Example{

        void goFoward();
    }


}
