<template>
  <container>
    <sprite :texture="planeImg"></sprite>
  </container>
</template>
<script>
import planeImg from '../assets/images/my_plane.png'
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import config from '../config'
let _emit
export default {
  setup(props, { emit }) {
    _emit = emit
    return {
      planeImg,
      props
    }
  }
}
export function usePlane(onAttack) {
  // 飞机数据
  const planeInfo = reactive({
    x: config.plane.startX,
    y: config.plane.startY,
    width: config.plane.width,
    height: config.plane.height,
    speed: config.plane.speed,
  })
  const initPlanePositioned = ref(true)
  // 初始化飞机位置 -> 底部飞出
  const initPlanePosition = setInterval(() => {
    planeInfo.y -= planeInfo.speed
    if (planeInfo.y <= 900) {
      initPlanePositioned.value = true
      clearInterval(initPlanePosition)
    }
  }, 60)

  // 飞机移动
  const move = (e) => {
    if (initPlanePositioned.value) {
      if (e.code === 'ArrowUp' && planeInfo.y > 0) {
        planeInfo.y -= planeInfo.speed
      } else if (
        e.code === 'ArrowDown' &&
        planeInfo.y <= config.container.height - config.plane.height
      ) {
        planeInfo.y += planeInfo.speed
      } else if (e.code === 'ArrowLeft' && planeInfo.x > 0) {
        planeInfo.x -= planeInfo.speed
      } else if (
        e.code === 'ArrowRight' &&
        planeInfo.x < config.container.width - config.plane.width
      ) {
        planeInfo.x += planeInfo.speed
      }
    }
  }
  // game over
  const gameOver = () => {
    _emit('changePage', 'gameOverPage')
  }
  // 飞机受到攻击
  const hurtPlane = () => {
      gameOver()
  }
  const shootTimer = ref()
  const shoot = () => {
    onAttack && onAttack({ x: planeInfo.x + 20, y: planeInfo.y - 20 })
  }
  onMounted(() => {
    window.addEventListener('keydown', move)

    shootTimer.value = setInterval(() => {
      shoot()
    }, 200)
  })
  onUnmounted(() => {
    window.removeEventListener('keydown', move)
    clearInterval(shootTimer.value)
  })
  return {
    planeInfo,
    hurtPlane
  }
}
</script>
