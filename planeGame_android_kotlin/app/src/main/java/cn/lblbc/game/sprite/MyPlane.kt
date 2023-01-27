/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game.sprite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.lblbc.game.R
import android.content.Intent
import android.graphics.*
import cn.lblbc.game.activity.GameActivity
import cn.lblbc.game.sprite.AutoSprite
import cn.lblbc.game.GameView
import cn.lblbc.game.sprite.Sprite
import cn.lblbc.game.SpriteManager
import cn.lblbc.game.BitMapReader
import android.text.TextPaint
import android.graphics.Paint.Align
import cn.lblbc.game.GameOverView
import android.view.MotionEvent
import cn.lblbc.game.sprite.MyPlane
import cn.lblbc.game.sprite.EnemyPlane
import cn.lblbc.game.sprite.Explosion
import cn.lblbc.game.sprite.Bullet

/**
 * 我方战机
 */
class MyPlane(bitmap: Bitmap) : Sprite(bitmap) {
    override fun draw(canvas: Canvas, paint: Paint?, gameView: GameView) {
        //每5帧发射一颗子弹
        if (frame % 5 == 0) {
            shoot()
        }
        super.draw(canvas, paint, gameView)
    }

    //发射子弹
    fun shoot() {
        val x = x + width / 2
        val y = y - 5
        SpriteManager.addBullet(x, y)
    }
}