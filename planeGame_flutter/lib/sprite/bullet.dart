/// 厦门大学计算机专业 | 前华为工程师
///  专注《零基础学编程系列》  http://lblbc.cn/blog
///  包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
///  公众号：蓝不蓝编程
import 'package:flutter/cupertino.dart';

import 'auto_sprite.dart';

class Bullet extends AutoSprite {
  Bullet(String image) {
    this.image = image;
    this.speed = -8;
    this.size = Size(9, 21);
  }

  @override
  void update() {
    super.update();
  }

  @override
  Widget getRenderWidget() {
    return Positioned(
      top: this.y,
      left: this.x,
      child: Image.asset(
        this.image,
        width: this.size.width,
        height: this.size.height,
      ),
    );
  }
}
