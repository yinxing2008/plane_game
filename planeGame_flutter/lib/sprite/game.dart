/// 厦门大学计算机专业 | 前华为工程师
///  专注《零基础学编程系列》  http://lblbc.cn/blog
///  包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
///  公众号：蓝不蓝编程
import 'dart:math';

import 'package:flutter/material.dart';

import 'bullet.dart';
import 'enemy_plane.dart';
import 'explosion.dart';
import 'my_plane.dart';
import 'sprite.dart';

class Game {
  var myPlane = MyPlane();
  var bullets = <Bullet>[];
  var enemyPlanes = <EnemyPlane>[];
  var explosions = <Explosion>[];
  var size = Size(0, 0);
  var random = Random();
  int frame = 0;

  /// 游戏是否结束
  var isGameOver = false;

  void startGame() {
    score = 0;
    frame = 0;
    isGameOver = false;
    myPlane.visible = true;
    myPlane.x = (this.size.width - myPlane.size.width) / 2;
    myPlane.y = this.size.height - myPlane.size.height;
    enemyPlanes.clear();
    explosions.clear();
    bullets.clear();
  }

  void stopGame() {
    enemyPlanes.clear();
    explosions.clear();
    bullets.clear();
  }

  void update() {
    _updateWorld();
  }

  void move(double dx, double dy) {
    var x = myPlane.centerX + dx;
    var y = myPlane.centerY + dy;
    if (x < 0) {
      x = 0.0;
    } else if (x > this.size.width) {
      x = this.size.width;
    }
    if (y < 0) {
      y = 0.0;
    } else if (y > this.size.height) {
      y = this.size.height;
    }
    myPlane.centerTo(x, y);
  }

  void _updateWorld() {
    frame++;
    myPlane.update();
    enemyPlanes.forEach((enemyPlane) {
      enemyPlane.update();
    });
    var list = myPlane.getBullets();
    if (list != null && list.isNotEmpty) {
      bullets.addAll(list);
    }
    for (int i = 0; i < enemyPlanes.length; i++) {
      EnemyPlane enemyPlane = enemyPlanes[i];
      if (!enemyPlane.visible || isOffscreen(enemyPlane)) {
        enemyPlanes.removeAt(i);
        i--;
        continue;
      }
      for (int j = 0; j < bullets.length; j++) {
        Bullet bullet = bullets[j];
        if (enemyPlane.isCollide(bullet)) {
          enemyPlane.getShot(1, this);
          bullets.removeAt(j);
          j--;
        }
      }
      if (myPlane.isCollide(enemyPlane)) {
        isGameOver = true;
        myPlane.visible = false;
        break;
      }
    }
    for (int i = 0; i < explosions.length; i++) {
      explosions[i].update();
      if (!explosions[i].visible) {
        explosions.removeAt(i);
        i--;
      }
    }

    if (frame % 30 == 0 && !isGameOver) {
      createEnemy();
    }

    for (int i = 0; i < bullets.length; i++) {
      Bullet bullet = bullets[i];
      bullet.update();
      if (isOffscreen(bullet)) {
        bullets.removeAt(i);
        i--;
      }
    }
  }

  createEnemy() {
    EnemyPlane enemy = EnemyPlane();
    enemy.y = -enemy.size.height;
    enemy.x = random.nextDouble() * size.width - enemy.size.width / 2;
    enemyPlanes.add(enemy);
  }

  bool isOffscreen(Sprite sprite) {
    Rect rect1 = Rect.fromLTWH(0, 0, size.width, size.height);
    Rect rect2 = sprite.getRect();
    Rect rect = rect1.intersect(rect2);
    if (rect.width < 0 || rect.height < 0) {
      return true;
    }
    return false;
  }

  void addExplosion(Explosion explosion) {
    explosions.add(explosion);
  }

  Widget getRenderWidget() {
    return SizedBox(
      width: size.width,
      height: size.height,
      child: Stack(
        children: getWidgets(),
      ),
    );
  }

  int score = 0;

  addScore(int score) {
    this.score += score;
  }

  List<Widget> getWidgets() {
    var list = <Widget>[];
    list.add(Image.asset(
      'assets/images/bg.png',
      fit: BoxFit.cover,
      width: size.width,
      height: size.height,
    ));
    enemyPlanes.forEach((enemyPlane) {
      list.add(enemyPlane.getRenderWidget());
    });
    explosions.forEach((explosion) {
      list.add(explosion.getRenderWidget());
    });
    bullets.forEach((bullet) {
      list.add(bullet.getRenderWidget());
    });
    if (myPlane.visible) {
      list.add(myPlane.getRenderWidget());
    }
    list.add(getScoreWidget());
    if (isGameOver) {
      list.add(getDialogWidget());
    }
    return list;
  }

  Positioned getScoreWidget() {
    return Positioned(
      top: 42,
      left: 30,
      child: Text(
        '得分: $score',
        style: TextStyle(fontSize: 22, color: Colors.black54, decoration: TextDecoration.none),
      ),
    );
  }

  Widget getDialogWidget() {
    var top = (size.height - 260) / 2;
    return Positioned(
      top: top,
      left: 30,
      right: 30,
      child: Container(
        decoration: BoxDecoration(
          color: Color(0xFFD7DDDE),
          border: Border.all(color: Colors.black54, width: 2),
        ),
        child: Column(
          children: [
            Container(
              height: 70,
              alignment: Alignment.center,
              child: Text(
                '大战成绩',
                style: TextStyle(fontSize: 24, color: Colors.black54, decoration: TextDecoration.none),
              ),
            ),
            Divider(color: Colors.black54, height: 2, thickness: 2),
            Container(
              height: 120,
              alignment: Alignment.center,
              child: Text(
                '$score',
                style: TextStyle(fontSize: 30, color: Colors.black54, decoration: TextDecoration.none),
              ),
            ),
            Divider(color: Colors.black54, height: 2, thickness: 2),
            Container(
              height: 70,
              alignment: Alignment.center,
              child: OutlineButton(
                padding: EdgeInsets.symmetric(horizontal: 40),
                borderSide: BorderSide(
                  color: Colors.black54,
                  width: 2,
                ),
                onPressed: () {
                  if (isGameOver) {
                    startGame();
                  }
                },
                child: Text(
                  isGameOver ? '重新开始' : '继续',
                  style: TextStyle(fontSize: 24, color: Colors.black54, decoration: TextDecoration.none),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
