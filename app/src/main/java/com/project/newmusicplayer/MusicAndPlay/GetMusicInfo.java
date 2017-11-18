package com.project.newmusicplayer.MusicAndPlay;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.project.newmusicplayer.Util.TimeConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 정인섭 on 2017-11-16.
 */

public class GetMusicInfo {

    List<Music> list = new ArrayList<>();
    private static GetMusicInfo getMusicInfo;
    public static GetMusicInfo getInstance(){
        if(getMusicInfo==null){
            getMusicInfo = new GetMusicInfo();
        }

        return getMusicInfo;
    }


    public void loadMusicInfo(Context context){
        ContentResolver contentResolver = context.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String projection[] = {
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION
        };

        Cursor cursor = contentResolver.query(uri, projection, null, null, projection[1] + " ASC");

        if(cursor!=null){
            list.clear();
            while(cursor.moveToNext()){
                Music music = new Music();
                music.album = cursor.getString(cursor.getColumnIndex(projection[2]));
                music.title = cursor.getString(cursor.getColumnIndex(projection[1]));
                music.artist = cursor.getString(cursor.getColumnIndex(projection[0]));
                music._id = cursor.getString(cursor.getColumnIndex(projection[3]));
                music.album_id = cursor.getString(cursor.getColumnIndex(projection[4]));
                music.album_uri = getAlbumUri(music.album_id);
                music.music_uri = getMusicUri(music._id);
                music.length = cursor.getString(cursor.getColumnIndex(projection[5]));
                list.add(music);
            }
            cursor.close();
        }
    }

    public ArrayList<Music> getMusicList(){
        return (ArrayList) list;
    }

    private Uri getMusicUri(String _id){
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(contentUri, _id);
    }

    private Uri getAlbumUri(String album_id){
        String contentUri = "content://media/external/audio/albumart/";
        return Uri.parse(contentUri + album_id);
    }

}
