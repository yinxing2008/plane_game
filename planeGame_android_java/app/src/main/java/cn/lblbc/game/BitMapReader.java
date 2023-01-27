package cn.lblbc.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
public class BitMapReader {
    private final static BitMapReader instance = new BitMapReader();
    private Bitmap enemyPlaneBitmap;
    private Bitmap myPlaneBitmap;
    private Bitmap bulletBitmap;
    private Bitmap explosionBitmap;

    private BitMapReader() {
    }

    public static BitMapReader getInstance() {
        return instance;
    }

    public void init(Context context) {
        myPlaneBitmap = getBitmap(context, R.drawable.my_plane);
        enemyPlaneBitmap = getBitmap(context, R.drawable.enemy_plane);
        bulletBitmap = getBitmap(context, R.drawable.bullet);
        explosionBitmap = getBitmap(context, R.drawable.explosion);
    }

    private Bitmap getBitmap(Context context, int bullet) {
        return BitmapFactory.decodeResource(context.getResources(), bullet);
    }

    public Bitmap getEnemyPlaneBitmap() {
        return enemyPlaneBitmap;
    }

    public Bitmap getMyPlaneBitmap() {
        return myPlaneBitmap;
    }

    public Bitmap getBulletBitmap() {
        return bulletBitmap;
    }

    public Bitmap getExplosionBitmap() {
        return explosionBitmap;
    }
}
