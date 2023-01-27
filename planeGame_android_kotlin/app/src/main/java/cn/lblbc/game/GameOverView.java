/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

public class GameOverView {
    private float textFontSize = 20;//用于在Game Over的时候绘制Dialog中的文本
    private float borderSize = 2;//Game Over的Dialog的边框
    private Rect continueRect = new Rect();//"继续"、"重新开始"按钮的Rect

    public GameOverView(float density) {
        textFontSize *= density;
        borderSize *= density;
    }

    public void draw(Canvas canvas, Paint mPaint, float density, long score) {
        //设置textPaint，设置为抗锯齿，且是粗体
        Paint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
        textPaint.setColor(0xff000000);
        float fontSize = textPaint.getTextSize();
        fontSize *= density;
        textPaint.setTextSize(fontSize);

        String operation = "重新开始";
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        //存储原始值
        float originalFontSize = textPaint.getTextSize();
        Paint.Align originalFontAlign = textPaint.getTextAlign();
        int originalColor = mPaint.getColor();
        Paint.Style originalStyle = mPaint.getStyle();
        int w1 = (int) (20.0 / 360.0 * canvasWidth);
        int w2 = canvasWidth - 2 * w1;
        int buttonWidth = (int) (140.0 / 360.0 * canvasWidth);

        int h1 = (int) (150.0 / 558.0 * canvasHeight);
        int h2 = (int) (60.0 / 558.0 * canvasHeight);
        int h3 = (int) (124.0 / 558.0 * canvasHeight);
        int h4 = (int) (76.0 / 558.0 * canvasHeight);
        int buttonHeight = (int) (42.0 / 558.0 * canvasHeight);

        canvas.translate(w1, h1);
        //绘制背景色
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFD7DDDE);
        Rect rect1 = new Rect(0, 0, w2, canvasHeight - 2 * h1);
        canvas.drawRect(rect1, mPaint);
        //绘制边框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFF515151);
        mPaint.setStrokeWidth(borderSize);
        //paint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawRect(rect1, mPaint);
        //绘制文本"大战成绩"
        textPaint.setTextSize(textFontSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("大战成绩", w2 / 2, (h2 - textFontSize) / 2 + textFontSize, textPaint);
        //绘制"大战成绩"下面的横线
        canvas.translate(0, h2);
        canvas.drawLine(0, 0, w2, 0, mPaint);
        //绘制实际的分数
        String allScore = String.valueOf(score);
        canvas.drawText(allScore, w2 / 2, (h3 - textFontSize) / 2 + textFontSize, textPaint);
        //绘制分数下面的横线
        canvas.translate(0, h3);
        canvas.drawLine(0, 0, w2, 0, mPaint);
        //绘制按钮边框
        Rect rect2 = new Rect();
        rect2.left = (w2 - buttonWidth) / 2;
        rect2.right = w2 - rect2.left;
        rect2.top = (h4 - buttonHeight) / 2;
        rect2.bottom = h4 - rect2.top;
        canvas.drawRect(rect2, mPaint);
        //绘制文本"继续"或"重新开始"
        canvas.translate(0, rect2.top);
        canvas.drawText(operation, w2 / 2, (buttonHeight - textFontSize) / 2 + textFontSize, textPaint);
        continueRect = new Rect(rect2);
        continueRect.left = w1 + rect2.left;
        continueRect.right = continueRect.left + buttonWidth;
        continueRect.top = h1 + h2 + h3 + rect2.top;
        continueRect.bottom = continueRect.top + buttonHeight;

        //重置
        textPaint.setTextSize(originalFontSize);
        textPaint.setTextAlign(originalFontAlign);
        mPaint.setColor(originalColor);
        mPaint.setStyle(originalStyle);
    }

    //是否单击了GAME OVER状态下的“重新开始”按钮
    public boolean isRestartButtonClicked(float x, float y) {
        return continueRect.contains((int) x, (int) y);
    }


}
