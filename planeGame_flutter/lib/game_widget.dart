/// 厦门大学计算机专业 | 前华为工程师
///  专注《零基础学编程系列》  http://lblbc.cn/blog
///  包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
///  公众号：蓝不蓝编程
import 'dart:async';

import 'package:flutter/material.dart';

import 'sprite/game.dart';

class GameWidget extends StatefulWidget {
  @override
  _GameWidgetState createState() => _GameWidgetState();
}

class _GameWidgetState extends State<GameWidget> with SingleTickerProviderStateMixin, WidgetsBindingObserver {
  double top = 0;
  double left = 0;

  Timer? _timer;

  Game? _game;

  @override
  void initState() {
    startLoop();
    WidgetsBinding.instance!.addObserver(this);
    super.initState();
  }

  @override
  void dispose() {
    endLoop();
    WidgetsBinding.instance!.removeObserver(this);
    super.dispose();
  }

  startLoop() {
    if (_timer == null) {
      _timer = Timer.periodic(Duration(milliseconds: 16), (timer) {
        _game?.update();
        setState(() {});
      });
    }
  }

  endLoop() {
    _timer?.cancel();
    _timer = null;
  }

  @override
  Widget build(BuildContext context) {
    if (_game == null) {
      _game = Game();
      _game?.size = MediaQuery.of(context).size;
      _game?.startGame();
    }
    return GestureDetector(
      behavior: HitTestBehavior.translucent,
      onPanUpdate: (details) {
        setState(() {
          if (details is DragUpdateDetails) {
            _game?.move(details.delta.dx, details.delta.dy);
          }
        });
      },
      child: SizedBox(
        child: _game?.getRenderWidget(),
      ),
    );
  }
}
