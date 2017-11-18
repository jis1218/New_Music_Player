package com.project.newmusicplayer.MusicAndPlay;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by 정인섭 on 2017-11-16.
 */

public class PlayMusic {

    private static PlayMusic playMusic;

    public static PlayMusic getInstance(){
        if(playMusic==null){
            playMusic = new PlayMusic();
        }
        return playMusic;
    }

    MediaPlayer mediaPlayer = null;

    public void setPlayer(Context context, Uri uri){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }

        mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.setLooping(false);
    }

    public void startPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }
    }

    public void stopPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }

    public void pausePlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }


}
