/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import cn.lblbc.game.sprite.Sprite;

public final class GameView extends View {
    private final Context mContext;
    private final Paint mPaint;
    private Sprite myPlane;
    private final SpriteManager spriteManager;
    private final SoundManager soundManager = SoundManager.getInstance();
    private final GameOverView gameOverView;
    private TextPaint textPaint;
    private final float density = getResources().getDisplayMetrics().density;
    public static final int STATUS_GAME_NOT_STARTED = 0;//游戏未开始
    public static final int STATUS_GAME_STARTED = 1;//游戏开始
    public static final int STATUS_GAME_OVER = 2;//游戏结束
    private int status = STATUS_GAME_NOT_STARTED;
    private float touchX = -1;
    private float touchY = -1;
    private long frame = 0;//总共绘制的帧数
    private long score = 0;//总得分

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;
        mPaint = new Paint();
        spriteManager = SpriteManager.getInstance();
        gameOverView = new GameOverView(density);
        init();
    }

    private void init() {
        frame = 0;
        score = 0;
        initTextPaint();
        soundManager.init(mContext);
        spriteManager.init(mContext);
        myPlane = spriteManager.getMyPlane();
        status = STATUS_GAME_STARTED;  //将游戏设置为开始状态
        postInvalidate();
    }

    private void initTextPaint() {
        //设置textPaint，设置为抗锯齿，且是粗体
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
        textPaint.setColor(-0x1000000);
        float fontSize = 30F;
        fontSize *= density;
        textPaint.setTextSize(fontSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (status == STATUS_GAME_STARTED) {
            drawGame(canvas);
        } else if (status == STATUS_GAME_OVER) {
            drawGameOver(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        int action = event.getAction();
        if (status == STATUS_GAME_STARTED) {
            if (action == MotionEvent.ACTION_MOVE) {
                myPlane.moveToByCenter(touchX, touchY);
            }
        } else if (status == STATUS_GAME_OVER) {
            if (gameOverView.isRestartButtonClicked(touchX, touchY)) { //点击“重新开始”按钮
                init();
            }
        }
        return true;
    }

    //绘制游戏运行状态
    private void drawGame(Canvas canvas) {
        //第一次绘制时，将战斗机移到Canvas最下方，在水平方向的中心
        if (frame == 0) {
            float centerX = canvas.getWidth() / 2;
            float centerY = canvas.getHeight() - myPlane.getHeight() / 2;
            myPlane.moveToByCenter(centerX, centerY);
        }

        //在绘制之前先移除掉已经被destroyed的Sprite
        spriteManager.recycleHiddenSprites();

        //每隔30帧随机添加一架敌机
        if (frame % 30 == 0) {
            spriteManager.createEnemyPlane(canvas.getWidth());
        }

        //遍历sprites，绘制战机、子弹、爆炸效果
        List<Sprite> sprites = spriteManager.getSprites();
        for (Sprite sprite : sprites) {
            sprite.draw(canvas, mPaint, this);
        }

        //碰撞检测
        checkCollision();

        //绘制主界面得分
        drawScore(canvas);

        //如果我方战机被击中，游戏结束
        if (!myPlane.isVisible()) {
            status = STATUS_GAME_OVER;
        }

        //通过调用postInvalidate()方法使得View持续渲染，实现动态效果
        postInvalidate();

        frame++;
    }

    /**
     * 碰撞检测
     */
    private void checkCollision() {
        //敌机在绘制完成后要判断是否被子弹打中
        List<Sprite> bullets = SpriteManager.getInstance().getVisibleBullets();
        List<Sprite> enemies = SpriteManager.getInstance().getVisibleEnemyPlanes();

        for (Sprite enemyPlane : enemies) {
            for (Sprite bullet : bullets) {
                if (enemyPlane.isCollidePointWithOther(bullet)) {
                    bullet.hide();
                    enemyPlane.hide();
                    addScore(1);//打一架敌机得1分
                    break;
                }
            }
        }

        for (Sprite enemyPlane : enemies) {
            if (enemyPlane.isVisible() && myPlane.isCollidePointWithOther(enemyPlane)) {
                myPlane.hide();
                break;
            }
        }
    }

    //绘制游戏结束状态
    private void drawGameOver(Canvas canvas) {
        gameOverView.draw(canvas, mPaint, density, score);
        postInvalidate();
    }

    //添加得分
    public void addScore(int value) {
        score += value;
    }

    //绘制主界面得分
    private void drawScore(Canvas canvas) {
        canvas.drawText("得分: " + score, 30F, 90F, textPaint);
    }

    public float getDensity() {
        return density;
    }
}