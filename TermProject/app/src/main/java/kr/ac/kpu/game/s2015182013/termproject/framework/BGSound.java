package kr.ac.kpu.game.s2015182013.termproject.framework;

import android.content.Context;
import android.media.MediaPlayer;

import kr.ac.kpu.game.s2015182013.termproject.R;

public class BGSound {
    private static final String TAG = BGSound.class.getSimpleName();
    private Context context;
    private MediaPlayer mp;

    public static BGSound get(){
        if(singleton == null){
            singleton = new BGSound();
        }
        return singleton;
    }
    private static BGSound singleton;
    public void init(Context context) {
        this.context = context;
        mp = MediaPlayer.create(this.context, R.raw.bg_music);
        mp.setVolume(1.f, 1.f);
    }

    public void playBGM() {
        mp.setLooping(true);
        mp.start();
    }

    public void stop() {
        mp.stop();
    }

    public void pause() {
        mp.pause();
    }


    public boolean isPlaying() {
        return mp.isPlaying();
    }
}
