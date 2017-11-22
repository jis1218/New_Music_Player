package com.project.newmusicplayer.Dmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.project.newmusicplayer.MusicAndPlay.GetMusicInfo;
import com.project.newmusicplayer.MusicAndPlay.Music;
import com.project.newmusicplayer.PlayService;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-11-22.
 */

public class DataManager {

    private static DataManager dataManager;
    ArrayList<Music> list = GetMusicInfo.getInstance().getMusicList();
    int position;


    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public void setPosition(int position) {
        if(position==-1){
            this.position = list.size()-1;
        }else if(position==list.size()){
            this.position = 0;
        }else{
            this.position = position;
        }

    }

    public int getPosition() {
        return position;
    }


    public void sendIntentToService(Context context) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction("From_RecyclerView");
        intent.putExtra("music_uri", list.get(position).music_uri);
        context.startService(intent);

    }

    public Uri getAlbumUri(){
        return list.get(position).album_uri;
    }

    public String getSonginfo(){
        return list.get(position).artist + " - " + list.get(position).title;
    }


    public int getCurrentPosition() {
        return position;
    }

    public interface ISongData{
        void setSongData(Uri album_uri, String song_info);
    }

}
