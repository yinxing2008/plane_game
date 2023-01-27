/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game

import android.content.Context
import cn.lblbc.game.sprite.*

object SpriteManager {
    private val sprites: MutableList<Sprite> = ArrayList() //存放所有战机、子弹、爆炸对象
    private val spritesToBeAdded: MutableList<Sprite> = ArrayList() //待加入sprites的临时队列
    private val enemyPlanePool: MutableList<Sprite> = ArrayList() //敌机对象池
    private val explosionPool: MutableList<Sprite> = ArrayList() //爆炸对象池
    private val bulletPool: MutableList<Sprite> = ArrayList() //子弹对象池
    lateinit var myPlane: Sprite //我方战机
        private set

    fun init(context: Context) {
        BitMapReader.init(context)
        //初始化我方战机
        myPlane = MyPlane(BitMapReader.myPlaneBitmap)

        //初始化30架敌机
        for (i in 0..29) {
            val sprite: Sprite = EnemyPlane(BitMapReader.enemyPlaneBitmap)
            enemyPlanePool.add(sprite)
        }

        //初始化30个爆炸对象
        for (i in 0..29) {
            val sprite: Sprite = Explosion(BitMapReader.explosionBitmap)
            explosionPool.add(sprite)
        }

        //初始化20颗子弹
        for (i in 0..19) {
            val sprite: Sprite = Bullet(BitMapReader.bulletBitmap)
            bulletPool.add(sprite)
        }
    }

    fun addMyPlane() {
        spritesToBeAdded.add(myPlane)
    }

    fun createEnemyPlane(canvasWidth: Int) {
        val sprite = getEnemyPlane(canvasWidth)
        if (sprite != null) {
            spritesToBeAdded.add(sprite)
        }
    }

    private fun getEnemyPlane(canvasWidth: Int): Sprite? {
        return if (enemyPlanePool.isNotEmpty()) {
            val sprite = enemyPlanePool.removeAt(0)
            val spriteWidth = sprite.width
            val spriteHeight = sprite.height
            val x = ((canvasWidth - spriteWidth) * Math.random()).toFloat()
            val y = -spriteHeight
            sprite.x = x
            sprite.y = y
            sprite
        } else {
            null
        }
    }

    fun addBullet(x: Float, y: Float) {
        val sprite = getBullet(x, y)
        if (sprite != null) {
            spritesToBeAdded.add(sprite)
        }
    }

    fun addExplosion(centerX: Float, centerY: Float) {
        val sprite = getExplosion(centerX, centerY)
        if (sprite != null) {
            spritesToBeAdded.add(sprite)
        }
    }

    private fun getExplosion(centerX: Float, centerY: Float): Sprite? {
        return if (explosionPool.isNotEmpty()) {
            val sprite = explosionPool.removeAt(0)
            sprite.moveToByCenter(centerX, centerY)
            sprite
        } else {
            null
        }
    }

    private fun getBullet(x: Float, y: Float): Sprite? {
        return if (bulletPool.isNotEmpty()) {
            val sprite = bulletPool.removeAt(0)
            sprite.moveTo(x, y)
            sprite
        } else {
            null
        }
    }

    fun getSprites(): List<Sprite?> {
        //将spritesNeedAdded添加到sprites中,界面渲染时都从sprites读取对象
        if (spritesToBeAdded.size > 0) {
            sprites.addAll(spritesToBeAdded)
            spritesToBeAdded.clear()
        }
        return sprites
    }

    val visibleBullets: List<Sprite>
        get() {
            val bullets: MutableList<Sprite> = ArrayList()
            for (s in sprites) {
                if (s!!.isVisible && s is Bullet) {
                    bullets.add(s)
                }
            }
            return bullets
        }
    val visibleEnemyPlanes: List<Sprite>
        get() {
            val enemyPlanes: MutableList<Sprite> = ArrayList()
            for (sprite in sprites) {
                if (sprite!!.isVisible && sprite is EnemyPlane) {
                    enemyPlanes.add(sprite)
                }
            }
            return enemyPlanes
        }

    /**
     * 回收已隐藏的Sprite
     */
    fun recycleHiddenSprites() {
        val iterator = sprites.iterator()
        while (iterator.hasNext()) {
            val sprite = iterator.next()
            if (!sprite.isVisible) {
                iterator.remove()
                sprite.show()
                when (sprite) {
                    is Bullet -> bulletPool.add(sprite)
                    is EnemyPlane -> enemyPlanePool.add(sprite)
                    is Explosion -> explosionPool.add(sprite)
                }
            }
        }
    }

    fun cleanUp() {
        //隐藏战机、子弹、爆炸
        for (sprite in sprites) {
            sprite.hide()
        }
        sprites.clear()
        spritesToBeAdded.clear()
        enemyPlanePool.clear()
        bulletPool.clear()
        explosionPool.clear()
    }
}