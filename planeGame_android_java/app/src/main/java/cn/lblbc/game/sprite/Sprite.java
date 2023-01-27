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
import android.graphics.RectF;

import cn.lblbc.game.GameView;

public class Sprite {
    private float x = 0;
    private float y = 0;
    private Bitmap bitmap;
    private boolean visible = true;
    private int frame = 0;//绘制的次数

    public Sprite(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void draw(Canvas canvas, Paint paint, GameView gameView) {
        if (isVisible()) {
            frame++;
            Rect srcRef = getBitmapSrcRect();
            RectF dstRecF = getRectF();
            canvas.drawBitmap(bitmap, srcRef, dstRecF, paint);
        }
    }

    /**
     * 获取当前对象显示的矩形区域
     */
    public RectF getRectF() {
        float left = x;
        float top = y;
        float right = left + getWidth();
        float bottom = top + getHeight();
        return new RectF(left, top, right, bottom);
    }

    /**
     * 获取待绘制的图片矩形区域
     */
    public Rect getBitmapSrcRect() {
        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.right = (int) getWidth();
        rect.bottom = (int) getHeight();
        return rect;
    }

    /**
     * 碰撞检测
     */
    public boolean isCollidePointWithOther(Sprite s) {
        boolean result = false;
        RectF rectF1 = getRectF();
        RectF rectF2 = s.getRectF();
        RectF rectF = new RectF();
        boolean isIntersect = rectF.setIntersect(rectF1, rectF2);
        if (isIntersect) {
            result = true;
        }
        return result;
    }

    public void move(float offsetX, float offsetY) {
        x += offsetX;
        y += offsetY;
    }

    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 移动对象，并保持自身中心点和centerX、centerY一致。
     */
    public void moveToByCenter(float centerX, float centerY) {
        float w = getWidth();
        float h = getHeight();
        x = centerX - w / 2;
        y = centerY - h / 2;
    }

    public float getWidth() {
        return bitmap.getWidth();
    }

    public float getHeight() {
        return bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void show() {
        this.visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void hide() {
        visible = false;
    }

    public int getFrame() {
        return frame;
    }
}
