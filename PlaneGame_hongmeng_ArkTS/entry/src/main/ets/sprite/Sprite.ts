/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
export class Sprite {
    public x: number = 0;
    public y: number = 0;
    public width: number = 0;
    public height: number = 0;
    public frame: number = 0;

    update() {
    }

    move(offsetX: number, offsetY: number) {
        this.x += offsetX
        this.y += offsetY
    }

    moveTo(x: number, y: number) {
        this.x = x
        this.y = y
    }

    getCenterX() {
        return this.x + this.width / 2
    }

    getCenterY() {
        return this.y + this.height / 2
    }

    isCollisionWith(sprite:Sprite){
        return !(
            this.x + this.width < sprite.x ||
            sprite.x + sprite.width < this.x ||
            this.y + this.height < sprite.y ||
            sprite.y + sprite.height < this.y
        );
    }
    isOffScreen(screenWidth: number, screenHeight: number): Boolean {
        var result = true;
        if (this.x > -this.width && this.x < screenWidth && this.y > -this.height && this.y < screenHeight) {
            result = false;
        }
        return result;
    }
}