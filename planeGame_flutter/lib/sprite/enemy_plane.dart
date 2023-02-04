/// 厦门大学计算机专业 | 前华为工程师
///  专注《零基础学编程系列》  http://lblbc.cn/blog
///  包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
///  公众号：蓝不蓝编程
import 'dart:ui';

import 'package:flutter/material.dart';

import 'auto_sprite.dart';
import 'explosion.dart';
import 'game.dart';

class EnemyPlane extends AutoSprite {
  EnemyPlane() {
    this.speed = 5;
    this.size = Size(51, 39);
    collideOffset = 5;
  }

  void getShot(int damage, Game game) {
    this.speed = 0;
    this.visible = false;
    game.addScore(1);
    explode(game);
  }

  @override
  Widget getRenderWidget() {
    return Positioned(
      left: this.x,
      top: this.y,
      child: Image.asset(
        'assets/images/enemy_plane.png',
        width: this.size.width,
        height: this.size.height,
      ),
    );
  }

  void explode(Game game) {
    Explosion explosion = Explosion([
      'assets/images/explosion1.png',
      'assets/images/explosion2.png',
      'assets/images/explosion3.png',
      'assets/images/explosion4.png',
    ]);
    explosion.x = this.x;
    explosion.y = this.y;
    explosion.speed = 0.5;
    explosion.size = this.size;
    game.addExplosion(explosion);
  }
}
