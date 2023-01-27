/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game.sprite

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import cn.lblbc.game.GameView

/**
 * 自动移动的Sprite，子弹和敌机属于这一类
 */
open class AutoSprite(bitmap: Bitmap) : Sprite(bitmap) {
    private var speed = 2f //每帧移动的像素数,正数表示向下运动，负数向上运动
    fun setSpeed(speed: Float) {
        this.speed = speed
    }

    override fun draw(canvas: Canvas, paint: Paint?, gameView: GameView) {
        move(0f, speed * gameView.density)
        super.draw(canvas, paint, gameView)
        checkOffScreen(canvas)
    }

    /**
     * 检查Sprite是否超出了屏幕范围，如果超出，则隐藏Sprite
     */
    private fun checkOffScreen(canvas: Canvas) {
        val canvasRecF = RectF(0F, 0F, canvas.width.toFloat(), canvas.height.toFloat())
        val spriteRecF = rectF
        if (!RectF.intersects(canvasRecF, spriteRecF!!)) {
            hide()
        }
    }
}