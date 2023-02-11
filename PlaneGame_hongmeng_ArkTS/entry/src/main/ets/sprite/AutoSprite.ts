/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
import { Sprite } from './Sprite'
export class AutoSprite extends Sprite {
    public speed: number = 2;

    update() {
        super.move(0, this.speed)
    }
}