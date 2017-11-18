package com.project.newmusicplayer.PlayList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.project.newmusicplayer.MusicAndPlay.GetMusicInfo;
import com.project.newmusicplayer.MusicAndPlay.Music;
import com.project.newmusicplayer.R;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-11-16.
 */

public class PlayListViewFrameLayout extends FrameLayout implements RecyclerViewAdapter.HideMySelfListener {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    ArrayList<Music> list = new ArrayList<>();

    public PlayListViewFrameLayout(@NonNull Context context) {
        super(context);
        init();
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.play_list_view, null);
        setRecyclerView(view);
        addView(view);
    }

    private void setRecyclerView(View view){
        setPlayList();
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setPlayList(){
        GetMusicInfo getMusicInfo = GetMusicInfo.getInstance();
        getMusicInfo.loadMusicInfo(getContext());
        list = getMusicInfo.getMusicList();
    }

    @Override
    public void hideMySelf() {

    }
}
