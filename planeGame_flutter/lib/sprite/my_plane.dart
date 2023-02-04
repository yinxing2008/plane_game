/// 厦门大学计算机专业 | 前华为工程师
///  专注《零基础学编程系列》  http://lblbc.cn/blog
///  包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
///  公众号：蓝不蓝编程
import 'package:flutter/cupertino.dart';

import 'bullet.dart';
import 'sprite.dart';

class MyPlane extends Sprite {
  var bullets = <Bullet>[];

  MyPlane() {
    this.size = Size(100, 124);
    collideOffset = 20;
  }

  @override
  void update() {
    super.update();
    if (visible && frame % 7 == 0) {
      var bullet = Bullet('assets/images/bullet.png');
      bullet.centerTo(this.x + this.size.width / 2, this.y);
      bullets.add(bullet);
    }
  }

  @override
  Widget getRenderWidget() {
    return Positioned(
      top: this.y,
      left: this.x,
      child: Container(
          child: Image.asset(
        'assets/images/my_plane.png',
        height: this.size.height,
        width: this.size.width,
      )),
    );
  }

  List<Bullet> getBullets() {
    var list = bullets;
    bullets = <Bullet>[];
    return list;
  }
}
