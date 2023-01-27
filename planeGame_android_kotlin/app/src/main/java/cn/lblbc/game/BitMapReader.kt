package cn.lblbc.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
object BitMapReader {
    lateinit var enemyPlaneBitmap: Bitmap
    lateinit var myPlaneBitmap: Bitmap
    lateinit var bulletBitmap: Bitmap
    lateinit var explosionBitmap: Bitmap

    fun init(context: Context) {
        myPlaneBitmap = getBitmap(context, R.drawable.my_plane)
        enemyPlaneBitmap = getBitmap(context, R.drawable.enemy_plane)
        bulletBitmap = getBitmap(context, R.drawable.bullet)
        explosionBitmap = getBitmap(context, R.drawable.explosion)
    }

    private fun getBitmap(context: Context, bullet: Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, bullet)
    }
}