/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import cn.lblbc.game.sprite.Sprite

class GameView(private val mContext: Context, attributeSet: AttributeSet?) : View(mContext, attributeSet) {
    private val mPaint = Paint()
    private lateinit var myPlane: Sprite
    private val soundManager = SoundManager.getInstance()
    private val gameOverView: GameOverView
    val density = resources.displayMetrics.density //屏幕密度
    private lateinit var textPaint: Paint
    private val STATUS_GAME_NOT_STARTED = 0 //游戏未开始
    private val STATUS_GAME_STARTED = 1 //游戏开始
    private val STATUS_GAME_OVER = 2 //游戏结束
    private var status = STATUS_GAME_NOT_STARTED
    private var touchX = -1f
    private var touchY = -1f
    private var frame: Long = 0
    private var score: Long = 0

    init {
        gameOverView = GameOverView(density)
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (status == STATUS_GAME_STARTED) {
            drawGame(canvas)
        } else if (status == STATUS_GAME_OVER) {
            drawGameOver(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        touchX = event.x
        touchY = event.y
        val action = event.action
        if (status == STATUS_GAME_STARTED) {
            if (action == MotionEvent.ACTION_MOVE) {
                myPlane.moveToByCenter(touchX, touchY)
            }
        } else if (status == STATUS_GAME_OVER) {
            if (gameOverView.isRestartButtonClicked(touchX, touchY)) {
                init()
            }
        }
        return true
    }

    //绘制游戏运行状态
    private fun drawGame(canvas: Canvas) {
        //第一次绘制时，将战斗机移到Canvas最下方，在水平方向的中心
        if (frame == 0L) {
            val centerX = (canvas.width / 2).toFloat()
            val centerY = canvas.height - myPlane.height / 2
            myPlane.moveToByCenter(centerX, centerY)
        }

        //在绘制之前先移除掉已经被destroyed的Sprite
        SpriteManager.recycleHiddenSprites()

        //每隔30帧随机添加一架敌机
        if (frame % 30 == 0L) {
            SpriteManager.createEnemyPlane(canvas.width)
        }

        //遍历sprites，绘制战机、子弹、爆炸效果
        val sprites = SpriteManager.getSprites()
        for (sprite in sprites) {
            sprite!!.draw(canvas, mPaint, this)
        }

        //碰撞检测
        checkCollision()

        //绘制主界面得分
        drawScore(canvas)

        //如果我方战机被击中，游戏结束
        if (!myPlane.isVisible) {
            status = STATUS_GAME_OVER
        }

        //通过调用postInvalidate()方法使得View持续渲染，实现动态效果
        postInvalidate()
        frame++
    }

    /**
     * 碰撞检测
     */
    private fun checkCollision() {
        //敌机在绘制完成后要判断是否被子弹打中
        val bullets: List<Sprite> = SpriteManager.visibleBullets
        val enemies: List<Sprite> = SpriteManager.visibleEnemyPlanes
        for (enemyPlane in enemies) {
            for (bullet in bullets) {
                if (enemyPlane.isCollidePointWithOther(bullet)) {
                    bullet.hide()
                    enemyPlane.hide()
                    addScore(1)//打一架敌机得1分
                    break
                }
            }
        }
        for (enemyPlane in enemies) {
            if (enemyPlane.isVisible && myPlane.isCollidePointWithOther(enemyPlane)) {
                myPlane.hide()
                break
            }
        }
    }

    //绘制游戏结束状态
    private fun drawGameOver(canvas: Canvas) {
        gameOverView.draw(canvas, mPaint, density, score)
        postInvalidate()
    }

    //绘制主界面得分
    private fun drawScore(canvas: Canvas) {
        canvas.drawText("得分: $score", 30F, 90F, textPaint)
    }

    //添加得分
    private fun addScore(value: Int) {
        score += value.toLong()
    }

    private fun init() {
        frame = 0
        score = 0
        initTextPaint()
        soundManager.init(mContext)
        SpriteManager.init(mContext)
        myPlane = SpriteManager.myPlane
        status = STATUS_GAME_STARTED //将游戏设置为开始状态
        postInvalidate()
    }

    private fun initTextPaint() {
        //设置textPaint，设置为抗锯齿，且是粗体
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG or Paint.FAKE_BOLD_TEXT_FLAG)
        textPaint.color = -0x1000000
        var fontSize = 30F
        fontSize *= density
        textPaint.textSize = fontSize
    }

}