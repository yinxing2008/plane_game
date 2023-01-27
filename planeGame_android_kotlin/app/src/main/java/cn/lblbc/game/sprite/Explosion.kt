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
 * 爆炸效果类，位置不变，但是可以显示动态的爆炸效果
 */
class Explosion(bitmap: Bitmap) : Sprite(bitmap) {
    private val segment = 14 //爆炸效果由14个片段组成
    private var level = 0 //最开始处于爆炸的第0片段
    override val width: Float
        get() = (bitmap.width / segment).toFloat()
    override val bitmapSrcRect: Rect
        get() {
            val rect = super.bitmapSrcRect
            val left = (level * width).toInt()
            rect.offsetTo(left, 0)
            return rect
        }

    override fun draw(canvas: Canvas, paint: Paint?, gameView: GameView) {
        super.draw(canvas, paint, gameView)
        //每个爆炸片段绘制2帧
        val explodeFrequency = 2
        if (frame % explodeFrequency == 0) {
            level++ //level自加1，用于绘制下个爆炸片段
            if (level >= segment) {
                hide() //当绘制完所有的爆炸片段后，隐藏
            }
        }
    }
}