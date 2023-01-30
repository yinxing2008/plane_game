package cn.lblbc.game;

import android.content.Context;
import android.media.SoundPool;

public class SoundManager {
    private static final SoundManager instance = new SoundManager();
    private final SoundPool soundPool;

    public static int backgroundSound, shootSound, bombSound;

    private SoundManager() {
        soundPool = new SoundPool.Builder().build();
    }

    public static SoundManager getInstance() {
        return instance;
    }

    public void init(Context context) {
        backgroundSound = soundPool.load(context, R.raw.bg, 1);
        shootSound = soundPool.load(context, R.raw.shoot, 1);
        bombSound = soundPool.load(context, R.raw.boom, 1);
    }

    public void playBackgroundSound() {
        soundPool.play(backgroundSound, 1, 1, 0, 1, 1);
    }

    public void playShoot() {
        soundPool.play(shootSound, 1, 1, 0, 0, 1);
    }

    public void playBomb() {
        soundPool.play(bombSound, 1, 1, 0, 0, 1);
    }
}