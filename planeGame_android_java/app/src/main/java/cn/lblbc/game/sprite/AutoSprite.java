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
import android.graphics.RectF;

import cn.lblbc.game.GameView;

/**
 * 自动移动的Sprite，子弹和敌机属于这一类
 */
public class AutoSprite extends Sprite {
    private float speed = 2;

    public AutoSprite(Bitmap bitmap) {
        super(bitmap);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, GameView gameView) {
        move(0, speed * gameView.getDensity());
        super.draw(canvas, paint, gameView);
        checkOffScreen(canvas);
    }

    /**
     * 检查Sprite是否超出了屏幕范围，如果超出，则隐藏Sprite
     */
    private void checkOffScreen(Canvas canvas) {
        RectF canvasRecF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        RectF spriteRecF = getRectF();
        if (!RectF.intersects(canvasRecF, spriteRecF)) {
            hide();
        }
    }
}