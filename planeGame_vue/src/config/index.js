export default {
  // 容器配置
  container: {
    width: 750,
    height: 900
  },
  // 我方飞机配置
  plane: {
    width: 70,
    height: 99,
    startX: 375,
    startY: 800,
    speed: 16,
    hurt: 50,
    bloodVolume: 100,
    bullet: {
      width: 30,
      height: 51,
      speed: 15
    }
  },
  // 敌机配置
  enemy: {
    width: 89,
    height: 60,
    speed: 4,
    hurt: 50,
    bloodVolume: 100
  }
}