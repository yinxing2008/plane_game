/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Rect
import android.text.TextPaint

class GameOverView(density: Float) {
    private var textFontSize = 20f //用于在Game Over的时候绘制Dialog中的文本
    private var borderSize = 2f //Game Over的Dialog的边框
    private var continueRect = Rect() //"继续"、"重新开始"按钮的Rect

    init {
        textFontSize *= density
        borderSize *= density
    }

    fun draw(canvas: Canvas, mPaint: Paint, density: Float, score: Long) {
        //设置textPaint，设置为抗锯齿，且是粗体
        val textPaint: Paint = TextPaint(Paint.ANTI_ALIAS_FLAG or Paint.FAKE_BOLD_TEXT_FLAG)
        textPaint.color = -0x1000000
        var fontSize = textPaint.textSize
        fontSize *= density
        textPaint.textSize = fontSize
        val operation = "重新开始"
        val canvasWidth = canvas.width
        val canvasHeight = canvas.height
        //存储原始值
        val originalFontSize = textPaint.textSize
        val originalFontAlign = textPaint.textAlign
        val originalColor = mPaint.color
        val originalStyle = mPaint.style
        val w1 = (20.0 / 360.0 * canvasWidth).toInt()
        val w2 = canvasWidth - 2 * w1
        val buttonWidth = (140.0 / 360.0 * canvasWidth).toInt()
        val h1 = (150.0 / 558.0 * canvasHeight).toInt()
        val h2 = (60.0 / 558.0 * canvasHeight).toInt()
        val h3 = (124.0 / 558.0 * canvasHeight).toInt()
        val h4 = (76.0 / 558.0 * canvasHeight).toInt()
        val buttonHeight = (42.0 / 558.0 * canvasHeight).toInt()
        canvas.translate(w1.toFloat(), h1.toFloat())
        //绘制背景色
        mPaint.style = Paint.Style.FILL
        mPaint.color = -0x282222
        val rect1 = Rect(0, 0, w2, canvasHeight - 2 * h1)
        canvas.drawRect(rect1, mPaint)
        //绘制边框
        mPaint.style = Paint.Style.STROKE
        mPaint.color = -0xaeaeaf
        mPaint.strokeWidth = borderSize
        //paint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.strokeJoin = Paint.Join.ROUND
        canvas.drawRect(rect1, mPaint)
        //绘制文本"大战成绩"
        textPaint.textSize = textFontSize
        textPaint.textAlign = Align.CENTER
        canvas.drawText("大战成绩", (w2 / 2).toFloat(), (h2 - textFontSize) / 2 + textFontSize, textPaint)
        //绘制"大战成绩"下面的横线
        canvas.translate(0f, h2.toFloat())
        canvas.drawLine(0f, 0f, w2.toFloat(), 0f, mPaint)
        //绘制实际的分数
        val allScore = score.toString()
        canvas.drawText(allScore, (w2 / 2).toFloat(), (h3 - textFontSize) / 2 + textFontSize, textPaint)
        //绘制分数下面的横线
        canvas.translate(0f, h3.toFloat())
        canvas.drawLine(0f, 0f, w2.toFloat(), 0f, mPaint)
        //绘制按钮边框
        val rect2 = Rect()
        rect2.left = (w2 - buttonWidth) / 2
        rect2.right = w2 - rect2.left
        rect2.top = (h4 - buttonHeight) / 2
        rect2.bottom = h4 - rect2.top
        canvas.drawRect(rect2, mPaint)
        //绘制文本"继续"或"重新开始"
        canvas.translate(0f, rect2.top.toFloat())
        canvas.drawText(operation, (w2 / 2).toFloat(), (buttonHeight - textFontSize) / 2 + textFontSize, textPaint)
        continueRect = Rect(rect2)
        continueRect.left = w1 + rect2.left
        continueRect.right = continueRect.left + buttonWidth
        continueRect.top = h1 + h2 + h3 + rect2.top
        continueRect.bottom = continueRect.top + buttonHeight

        //重置
        textPaint.textSize = originalFontSize
        textPaint.textAlign = originalFontAlign
        mPaint.color = originalColor
        mPaint.style = originalStyle
    }

    //是否单击了GAME OVER状态下的“重新开始”按钮
    fun isRestartButtonClicked(x: Float, y: Float): Boolean {
        return continueRect.contains(x.toInt(), y.toInt())
    }
}