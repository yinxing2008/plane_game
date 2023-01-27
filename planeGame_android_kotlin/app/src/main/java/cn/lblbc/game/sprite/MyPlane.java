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

import cn.lblbc.game.GameView;
import cn.lblbc.game.SpriteManager;

/**
 * 我方战机
 */
public class MyPlane extends Sprite {
    public MyPlane(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, GameView gameView) {
        //每5帧发射一颗子弹
        if (getFrame() % 5 == 0) {
            shoot();
        }
        super.draw(canvas, paint, gameView);
    }

    //发射子弹
    public void shoot() {
        float x = getX() + getWidth() / 2;
        float y = getY() - 5;
        SpriteManager.getInstance().addBullet(x, y);
    }
}