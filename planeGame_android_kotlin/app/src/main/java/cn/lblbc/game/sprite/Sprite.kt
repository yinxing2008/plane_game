/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game.sprite

import android.graphics.*
import cn.lblbc.game.GameView

open class Sprite(val bitmap: Bitmap) {
    var x = 0f
    var y = 0f
    var isVisible = true
        private set
    var frame = 0 //绘制的次数
        private set

    open fun draw(canvas: Canvas, paint: Paint?, gameView: GameView) {
        if (isVisible) {
            frame++
            val srcRef = bitmapSrcRect
            val dstRecF = rectF
            canvas.drawBitmap(bitmap, srcRef, dstRecF, paint)
        }
    }

    /**
     * 获取当前对象显示的矩形区域
     */
    val rectF: RectF
        get() {
            val left = x
            val top = y
            val right = left + width
            val bottom = top + height
            return RectF(left, top, right, bottom)
        }

    /**
     * 获取待绘制的图片矩形区域
     */
    open val bitmapSrcRect: Rect
        get() {
            val rect = Rect()
            rect.left = 0
            rect.top = 0
            rect.right = width.toInt()
            rect.bottom = height.toInt()
            return rect
        }

    /**
     * 碰撞检测
     */
    fun isCollidePointWithOther(s: Sprite): Boolean {
        var result = false
        val rectF1 = rectF
        val rectF2 = s.rectF
        val rectF = RectF()
        val isIntersect = rectF.setIntersect(rectF1, rectF2)
        if (isIntersect) {
            result = true
        }
        return result
    }

    fun move(offsetX: Float, offsetY: Float) {
        x += offsetX
        y += offsetY
    }

    fun moveTo(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    /**
     * 移动对象，并保持自身中心点和centerX、centerY一致。
     */
    fun moveToByCenter(centerX: Float, centerY: Float) {
        val w = width
        val h = height
        x = centerX - w / 2
        y = centerY - h / 2
    }

    open val width: Float
        get() = bitmap.width.toFloat()
    val height: Float
        get() = bitmap.height.toFloat()

    fun show() {
        isVisible = true
    }

    open fun hide() {
        isVisible = false
    }
}