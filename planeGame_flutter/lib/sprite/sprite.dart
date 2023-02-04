/// 厦门大学计算机专业 | 前华为工程师
///  专注《零基础学编程系列》  http://lblbc.cn/blog
///  包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
///  公众号：蓝不蓝编程
import 'package:flutter/material.dart';

class Sprite {
  double x = 0;
  double y = 0;
  bool visible = true;
  late Size size;
  int frame = 0;
  late String image;
  double collideOffset = 0;

  void beforeDraw() {}

  void update() {
    frame++;
  }

  Widget getRenderWidget() {
    return Container();
  }

  void afterDraw() {}

  void move(double offsetX, double offsetY) {
    x += offsetX;
    y += offsetY;
  }

  void moveTo(double x, double y) {
    this.x = x;
    this.y = y;
  }

  get centerX => x + size.width / 2;

  get centerY => y + size.height / 2;

  void centerTo(double centerX, double centerY) {
    if (size != null) {
      x = centerX - size.width / 2;
      y = centerY - size.height / 2;
    }
  }

  Rect getRect() {
    return Rect.fromLTWH(
      this.x,
      this.y,
      this.size.width,
      this.size.height,
    );
  }

  /// 用于碰撞检测的Rect
  Rect getCollideRect() {
    Rect rect = getRect();
    return Rect.fromLTRB(
      rect.left + collideOffset,
      rect.top + collideOffset,
      rect.right - collideOffset,
      rect.bottom - collideOffset,
    );
  }

  /// 是否相交
  bool isIntersect(Sprite sprite) {
    Rect rect1 = getRect();
    Rect rect2 = sprite.getRect();
    Rect rect = rect1.intersect(rect2);
    return rect.width >= 0 && rect.height >= 0;
  }

  /// 是否碰撞
  bool isCollide(Sprite sprite) {
    Rect rect1 = getCollideRect();
    Rect rect2 = sprite.getCollideRect();
    Rect rect = rect1.intersect(rect2);
    return rect.width >= 0 && rect.height >= 0;
  }
}
