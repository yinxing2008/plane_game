import AnimateSprite from './AnimateSprite'
import DataBus from '../utils/DataBus'

const PLANE_IMG_SRC = 'images/enemy_plane.png'
const PLANE_WIDTH = 40
const PLANE_HEIGHT = 40
var speed = 0

const databus = new DataBus()

function rnd(start, end) {
  return Math.floor(Math.random() * (end - start) + start)
}

export default class EnemyPlane extends AnimateSprite {
  constructor() {
    super(PLANE_IMG_SRC, PLANE_WIDTH, PLANE_HEIGHT)

    this.initExplosionAnimation()
  }

  init(speed) {
    this.x = rnd(0, window.innerWidth - PLANE_WIDTH)
    this.y = -this.height

    this.speed = speed

    this.visible = true
  }

  // 预定义爆炸的帧动画
  initExplosionAnimation() {
    const frames = []

    const EXPLO_IMG_PREFIX = 'images/explosion'
    const EXPLO_FRAME_COUNT = 19

    for (let i = 0; i < EXPLO_FRAME_COUNT; i++) {
      frames.push(`${EXPLO_IMG_PREFIX + (i + 1)}.png`)
    }

    this.initFrames(frames)
  }

  //更新敌机位置
  update() {
    this.y += this.speed

    // 超出屏幕,回收对象
    if (this.y > window.innerHeight + this.height) {
      databus.removeEnemey(this)
    }
  }
}
