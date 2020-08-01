package com.example.tictactoe;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 2;

    private static SoundPool soundPool;
    private static int loseSound;
    private static int winSound;
    private static int drawSound;

    public SoundPlayer(Context context) {

        // SoundPool is deprecated in API level 21. (Lollipop)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();

        } else {
            //SoundPool (int maxStreams, int streamType, int srcQuality)
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

        loseSound = soundPool.load(context, R.raw.lose, 1);
        winSound = soundPool.load(context, R.raw.win, 1);
        drawSound= soundPool.load(context, R.raw.draw, 1);

    }

   public void playloseSound() {

        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(loseSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playwinSound() {
        soundPool.play(winSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playdrawSound() {

        // play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(drawSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
