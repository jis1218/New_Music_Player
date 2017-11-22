package com.project.newmusicplayer;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.project.newmusicplayer.MusicAndPlay.PlayMusic;

public class PlayService extends Service {
    public PlayService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        switch(intent.getAction()){
            case "From_RecyclerView" :
                Uri uri = intent.getParcelableExtra("music_uri");
                PlayMusic.getInstance().setPlayer(this, uri);
                PlayMusic.getInstance().startPlayer();
                break;

            case "From_PlayButton" :
                PlayMusic.getInstance().startPlayer();
                break;

            case "From_PauseButton" :
                PlayMusic.getInstance().pausePlayer();
                break;

            default :
                break;

        }



        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
