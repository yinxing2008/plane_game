/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import cn.lblbc.game.GameView;

/**
 * 爆炸效果类，位置不变，但是可以显示动态的爆炸效果
 */
public class Explosion extends Sprite {
    private final int segment = 14;//爆炸效果由14个片段组成
    private int level = 0;//最开始处于爆炸的第0片段

    public Explosion(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public float getWidth() {
        return getBitmap().getWidth() / segment;
    }

    @Override
    public Rect getBitmapSrcRect() {
        Rect rect = super.getBitmapSrcRect();
        int left = (int) (level * getWidth());
        rect.offsetTo(left, 0);
        return rect;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, GameView gameView) {
        super.draw(canvas, paint, gameView);
        //每个爆炸片段绘制2帧
        int explodeFrequency = 2;
        if (getFrame() % explodeFrequency == 0) {
            level++;//level自加1，用于绘制下个爆炸片段
            if (level >= segment) {
                hide();//当绘制完所有的爆炸片段后，隐藏
            }
        }
    }
}