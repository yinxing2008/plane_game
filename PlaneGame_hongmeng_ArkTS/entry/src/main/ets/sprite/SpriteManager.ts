/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
import { Sprite } from './Sprite';
import { Bullet } from './Bullet';
import { MyPlane } from './MyPlane';
import { EnemyPlane } from './EnemyPlane';

export class SpriteManager {
    public myPlane: MyPlane;
    public enemyPlanes: Sprite[] = [];
    public bullets: Sprite[] = [];
    public enemyPlanePool: Sprite[] = [];
    public bulletPool: Sprite[] = [];
    private screenWidth: number;
    private screenHeight: number

    public init(screenWidth: number, screenHeight: number) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.clearAll()
        for (var i = 0; i < 4; i++) {
            this.createEnemyPlane()
        }
        for (var i = 0; i < 10; i++) {
            this.createBullet()
        }
    }

    createEnemyPlane() {
        var enemyPlane = new EnemyPlane();
        enemyPlane.width = 65
        enemyPlane.height = 50
        enemyPlane.y = 0
        enemyPlane.x = this.randomVal(this.screenWidth - enemyPlane.width / 2)
        this.enemyPlanePool.push(enemyPlane)
    }

    createBullet() {
        var bullet = new Bullet();
        bullet.width = 7
        bullet.height = 18
        this.bulletPool.push(bullet)
    }

    getEnemyPlane() {
        if (this.enemyPlanePool.length > 0) {
            return this.enemyPlanePool.pop()
        } else {
            return undefined
        }
    }

    getBullet() {
        if (this.bulletPool.length > 0) {
            return this.bulletPool.pop()
        } else {
            return undefined
        }
    }

    returnToPool(sprite: Sprite) {
        if (sprite instanceof EnemyPlane) {
            sprite.y = 0
            sprite.x = this.randomVal(this.screenWidth - sprite.width / 2)
            this.enemyPlanePool.push(sprite)
        } else if (sprite instanceof Bullet) {
            this.bulletPool.push(sprite)
        }
    }

    randomVal(max: number) {
        return Math.floor(Math.random() * max)
    }

    clearAll() {
        this.bulletPool = []
        this.enemyPlanePool = []
        this.bullets = []
        this.enemyPlanes = []
    }
}