const screenWidth = window.innerWidth
const screenHeight = window.innerHeight

export default class GameOverView {
  showGameOverView(ctx, score) {
    var img = new Image()
    img.src = 'images/gameOver.png'
    ctx.drawImage(img, screenWidth / 2 - 150, screenHeight / 2 - 200, 300, 300)

    ctx.fillStyle = '#ffffff'
    ctx.font = '20px Arial'


    ctx.fillText(
      `${score}`,
      screenWidth / 2 - 10,
      screenHeight / 2 - 180 + 130
    )

    /**
     * 重新开始按钮区域
     * 方便简易判断按钮点击
     */
    this.btnArea = {
      startX: screenWidth / 2 - 70,
      startY: screenHeight / 2 + 40,
      endX: screenWidth / 2 + 60,
      endY: screenHeight / 2 + 100
    }
  }
}
