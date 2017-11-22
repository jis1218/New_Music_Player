package com.project.newmusicplayer.PlayList;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.newmusicplayer.Dmanager.DataManager;
import com.project.newmusicplayer.MusicAndPlay.Music;
import com.project.newmusicplayer.PlayService;
import com.project.newmusicplayer.R;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-11-16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder>{
    ArrayList<Music> list = new ArrayList<>();
    SetPlayListFrameLayout setPlayListFrameLayout;

    public RecyclerViewAdapter(ArrayList<Music> list, SetPlayListFrameLayout setPlayListFrameLayout) {
        this.list = list;
        this.setPlayListFrameLayout = setPlayListFrameLayout;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tvArtist.setText(list.get(position).artist);
        holder.tvTitle.setText(list.get(position).title);
        holder.tvTime.setText(list.get(position).length);
//        holder.music_uri = list.get(position).music_uri;
//        holder.album_uri = list.get(position).album_uri;
//        Log.d("getAdapterPosition()", holder.getAdapterPosition()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView tvArtist, tvTitle, tvTime;
        Uri music_uri;
        Uri album_uri;
        int position;
        public MyHolder(final View itemView) {
            super(itemView);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataManager dataManager = DataManager.getInstance();
                    dataManager.setPosition(getAdapterPosition());
                    dataManager.sendIntentToService(itemView.getContext());



//                    Intent intent = new Intent(itemView.getContext(), PlayService.class);
//                    intent.setAction("From_RecyclerView");
//                    intent.putExtra("music_uri", music_uri);
//                    itemView.getContext().startService(intent);
                    setPlayListFrameLayout.setAlbumArt(dataManager.getAlbumUri());
                    setPlayListFrameLayout.setTitleArtist(dataManager.getSonginfo());
                    //Log.d("album_uri", album_uri.toString());


                }
            });
        }
    }

    public interface SetPlayListFrameLayout {
        void setAlbumArt(Uri uri);
        void setTitleArtist(String str);
    }
}
