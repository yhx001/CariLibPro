package cn.com.cari.carilibrary.Util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

/**
 * @author zhouzhou
 * @Title: SoundUtil
 * @Description:
 * @Company: www.cari.com.cn
 * @date 2015/11/18
 */
public class SoundUtil {

    public static void playSound(final Context context, final String soundName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
                    soundPool.load(context.getAssets().openFd(soundName), 1);
                    Thread.sleep(1000); //预留音效加载时间
                    soundPool.play(1, 1, 1, 0, 0, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
