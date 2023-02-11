/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
import { Sprite } from '../sprite/Sprite';
import { SpriteManager } from '../sprite/SpriteManager';
import { Bullet } from '../sprite/Bullet';
import { SpriteDataSource } from '../sprite/SpriteDataSource';
import { EnemyPlane } from '../sprite/EnemyPlane';
import { MyPlane } from '../sprite/MyPlane';

@Entry
@Component
struct Index {
  screenWidth: number = 300 //屏幕宽度
  screenHeight: number = 600 //屏幕高度
  pageRefreshInterval: number = 50; //页面刷新周期
  pageRefreshIntervalId: number = 0; //页面刷新定时器Id，记录下来在取消时用
  frames: number = 0; //已经刷新过的帧数
  score: number = 0;
  private spriteManager: SpriteManager = new SpriteManager();
  @State myPlane: MyPlane = new MyPlane();
  @State enemyPlanes: Sprite[] = [];
  @State bullets: Sprite[] = [];
  @State isGameOver: boolean = false;

  build() {
    Stack({ alignContent: Alignment.TopStart }) {
      Image($r('app.media.bg')).width('100%').height('100%')
      Text("得分: " + this.score).margin({ top: 10, left: 20 }).fontSize(20)
      if (this.isGameOver) {
        Column() {
          Button({ type: ButtonType.Capsule, stateEffect: true }) {
            Text('重新开始').fontSize(30).fontColor(0xffffff).padding({ left: 30, right: 30, top: 10, bottom: 10 })
          }
          .onClick((event: ClickEvent) => {
            this.init()
          })
        }.justifyContent(FlexAlign.Center).width('100%').height('100%')
      } else {
        Image($r('app.media.my_plane'))
          .width(this.myPlane.width).height(this.myPlane.height)
          .margin({ top: this.myPlane.y - this.myPlane.height / 2 + 10, left: this.myPlane.x - this.myPlane.width / 2 })

        LazyForEach(new SpriteDataSource(this.enemyPlanes), (item) => {
          Image($r('app.media.enemy_plane'))
            .width(item.width).height(item.height)
            .margin({ top: item.y, left: item.x })
        })

        LazyForEach(new SpriteDataSource(this.bullets), (item) => {
          Image($r('app.media.bullet'))
            .width(item.width).height(item.height)
            .margin({ top: item.y, left: item.x })
        })
      }
    }.width('100%').height('100%')
    .onTouch((event: TouchEvent) => {
      if (event.type === TouchType.Move) {
        this.myPlane.x = event.touches[0].x
        this.myPlane.y = event.touches[0].y
      }
    })
  }

  aboutToAppear() {
    this.init()
  }

  aboutToDisappear() {
    clearInterval(this.pageRefreshIntervalId)
  }

  init() {
    this.frames = 0
    this.score = 0
    this.enemyPlanes = []
    this.bullets = []
    this.isGameOver = false
    this.initMyPlane()
    this.spriteManager.init(this.screenWidth, this.screenHeight)
    var _this = this


    this.pageRefreshIntervalId = setInterval(function () {

      if (_this.frames % 50 == 0) {
        _this.createEnemyPlane()
      }

      if (_this.frames % 5 == 0) {
        _this.createBullet(_this.myPlane.x, _this.myPlane.y - _this.myPlane.height / 2)
      }

      _this.enemyPlanes.forEach((item) => {
        item.update()
      })

      _this.bullets.forEach((item) => {
        item.update()
      })
      _this.checkCollision()
      _this.checkMyPlaneCollision()
      _this.checkOffScreenSprites(_this.bullets)
      _this.checkOffScreenSprites(_this.enemyPlanes)

      _this.frames++
    }, this.pageRefreshInterval)

  }

  initMyPlane() {
    this.myPlane.width = 98
    this.myPlane.height = 122
    this.myPlane.x = this.screenWidth / 2
    this.myPlane.y = this.screenHeight - this.myPlane.height
  }

  createEnemyPlane() {
    var enemyPlane = this.spriteManager.getEnemyPlane()
    if (enemyPlane != undefined) {
      this.enemyPlanes.push(enemyPlane)
    }
  }

  createBullet(x: number, y: number) {
    var bullet = this.spriteManager.getBullet()
    if (bullet != undefined) {
      bullet.x = x
      bullet.y = y
      this.bullets.push(bullet)
    }
  }

  checkMyPlaneCollision() {
    for (var i = 0; i < this.enemyPlanes.length; i++) {
      if (this.myPlane.isCollisionWith(this.enemyPlanes[i])) {
        this.isGameOver = true
        clearInterval(this.pageRefreshIntervalId)
        break
      }
    }
  }

  /**
   * 碰撞检测
   */
  checkCollision() {
    var bulletIndexes: number[] = [];
    var enemyPlaneIndexes: number[] = [];
    for (var i = 0; i < this.bullets.length; i++) {
      var bullet = this.bullets[i]
      for (var j = 0; j < this.enemyPlanes.length; j++) {
        if (bullet.isCollisionWith(this.enemyPlanes[j])) {
          bulletIndexes.push(i)
          if (!enemyPlaneIndexes.includes(j)) {
            enemyPlaneIndexes.push(j)
            this.score++
          }
          break
        }
      }
      this.returnToPool(this.bullets, bulletIndexes)
      this.returnToPool(this.enemyPlanes, enemyPlaneIndexes)
    }
  }

  checkOffScreenSprites(sprites: Sprite[]) {
    var offScreenIndexes: number[] = [];
    for (var i = 0; i < sprites.length; i++) {
      var sprite = sprites[i]
      if (sprite.isOffScreen(this.screenWidth, this.screenHeight)) {
        offScreenIndexes.push(i)
      }
    }
    this.returnToPool(sprites, offScreenIndexes)
  }

  returnToPool(sprites: Sprite[], spriteIndexes: number[]) {
    if (spriteIndexes.length <= 0) {
      return
    }
    //对spriteIndexes数组按照顺序排序
    spriteIndexes.sort()
    //从sprites数组后面按照序号移除。
    for (var i = spriteIndexes.length - 1; i >= 0; i--) {
      var spriteIndex = spriteIndexes[i]
      var sprite = sprites[spriteIndex]
      sprites.splice(spriteIndex, 1)
      this.spriteManager.returnToPool(sprite)
    }
  }
}