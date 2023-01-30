import Sprite from './Sprite'
import DataBus from '../utils/DataBus'

const BULLET_IMG_SRC = 'images/bullet.png'
const BULLET_WIDTH = 8
const BULLET_HEIGHT = 15
var speed = 0

const databus = new DataBus()

export default class Bullet extends Sprite {
  constructor() {
    super(BULLET_IMG_SRC, BULLET_WIDTH, BULLET_HEIGHT)
  }

  init(x, y, speed) {
    this.x = x
    this.y = y
    this.speed = speed
    this.visible = true
  }

  // 更新子弹位置
  update() {
    this.y -= this.speed

    // 超出屏幕,回收对象
    if (this.y < -this.height) {
      databus.removeBullets(this)
    }
  }
}
