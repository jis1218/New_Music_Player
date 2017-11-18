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
        Uri uri = intent.getParcelableExtra("music_uri");
        PlayMusic.getInstance().setPlayer(this, uri);
        PlayMusic.getInstance().startPlayer();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
