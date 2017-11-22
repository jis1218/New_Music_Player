package com.project.newmusicplayer.PlayList;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.project.newmusicplayer.MusicAndPlay.GetMusicInfo;
import com.project.newmusicplayer.MusicAndPlay.Music;
import com.project.newmusicplayer.R;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-11-16.
 */

public class PlayListViewFrameLayout extends FrameLayout {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ImageView album_art;
    RecyclerViewAdapter.SetPlayListFrameLayout isetPlay;

    ArrayList<Music> list = new ArrayList<>();

    public PlayListViewFrameLayout(@NonNull Context context, RecyclerViewAdapter.SetPlayListFrameLayout isetPlay) {
        super(context);
        this.isetPlay = isetPlay;
        init();


    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.play_list_view, this, false);
        setRecyclerView(view);
        addView(view);
    }

    private void setPlayList(){
        GetMusicInfo getMusicInfo = GetMusicInfo.getInstance();
        getMusicInfo.loadMusicInfo(getContext());
        list = getMusicInfo.getMusicList();
    }

    private void setRecyclerView(View view){
        setPlayList();
        recyclerView = view.findViewById(R.id.recyclerView);
        //album_art = view.findViewById(R.id.albumart);
        adapter = new RecyclerViewAdapter(list, isetPlay);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //album_art.setVisibility(INVISIBLE);
    }

//    @Override
//    public void setAlbumArt(Uri uri){
//        //album_art.setImageURI(uri);
//        //album_art.setVisibility(VISIBLE);
//    }
}
