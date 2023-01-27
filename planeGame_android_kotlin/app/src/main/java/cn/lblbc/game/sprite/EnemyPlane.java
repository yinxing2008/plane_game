/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game.sprite;

import android.graphics.Bitmap;

import cn.lblbc.game.SpriteManager;

/**
 * 敌机
 */
public class EnemyPlane extends AutoSprite {

    public EnemyPlane(Bitmap bitmap) {
        super(bitmap);
    }

    public void hide() {
        explode();
        super.hide();
    }

    //创建爆炸效果
    private void explode() {
        float centerX = getX() + getWidth() / 2;
        float centerY = getY() + getHeight() / 2;
        SpriteManager.getInstance().addExplosion(centerX, centerY);
    }
}