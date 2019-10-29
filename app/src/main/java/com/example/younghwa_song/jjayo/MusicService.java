package com.example.younghwa_song.jjayo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {

    MediaPlayer mp;


    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        mp = MediaPlayer.create(this,R.raw.backgroundmusic);
        mp.setLooping(true);
    }


    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
    }
}
