/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game.sprite

import android.graphics.Bitmap
import cn.lblbc.game.SoundManager
import cn.lblbc.game.SpriteManager

/**
 * 敌机
 */
class EnemyPlane(bitmap: Bitmap) : AutoSprite(bitmap) {
    override fun hide() {
        explode()
        super.hide()
    }

    //创建爆炸效果
    private fun explode() {
        val centerX = x + width / 2
        val centerY = y + height / 2
        SoundManager.getInstance().playBomb()
        SpriteManager.addExplosion(centerX, centerY)
    }
}