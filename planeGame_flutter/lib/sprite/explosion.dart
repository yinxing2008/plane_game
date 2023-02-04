/// 厦门大学计算机专业 | 前华为工程师
///  专注《零基础学编程系列》  http://lblbc.cn/blog
///  包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
///  公众号：蓝不蓝编程
import 'package:flutter/material.dart';

import 'auto_sprite.dart';

class Explosion extends AutoSprite {
  List<String> images;

  Explosion(this.images);

  @override
  void update() {
    super.update();
    if (getImgIndex() >= this.images.length) {
      visible = false;
    }
  }

  int getImgIndex() {
    return this.frame ~/ 5;
  }

  @override
  Widget getRenderWidget() {
    return Positioned(
      left: this.x,
      top: this.y,
      child: Image.asset(
        this.images[getImgIndex()],
        width: this.size.width,
        height: this.size.height,
      ),
    );
  }
}
