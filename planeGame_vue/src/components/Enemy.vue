<template>
  <container>
    <sprite :texture="enemyImg"></sprite>
  </container>
</template>
<script>
import enemyImg from "../assets/images/enemy_plane.png";
import { reactive, ref, onUnmounted, onMounted } from "vue";
import { game } from "../game";
import config from "../config";
let _emit;
export default {
  setup(props, { emit }) {
    _emit = emit;
    return {
      enemyImg,
      props
    };
  }
};
export function useEnemy() {
  // 敌机数据
  const enemys = reactive([]);
  // 敌机创建
  const createEnemy = () => {
    let x = Math.floor(
      Math.random() * config.container.width - config.enemy.width
    );
    x = x < 0 ? 0 : x;
    const width = config.enemy.width;
    const height = config.enemy.height;
    return {
      x,
      y: 0,
      width,
      height
    };
  };
  // 新增敌机
  const addEnemy = () => {
    enemys.push(createEnemy());
  };
  // 敌机移动
  const move = () => {
    enemys.forEach((enemy, index) => {
      enemy.y += config.enemy.speed;
      if (move.y >= config.container.height) {
        removeEnemy(index);
      }
    });
  };
  // 敌机受到攻击
  const hurtEnemy = (index) => {
    if (!enemys[index]) return;
    _emit("addScore", 1);
    removeEnemy(index);
  };
  // 敌机删除
  const removeEnemy = index => {
    enemys.splice(index, 1);
  };

  // 添加敌机interval
  const addEnemyTimer = ref();
  onMounted(() => {
    game.ticker.add(move);
    addEnemyTimer.value = setInterval(() => {
      addEnemy();
    }, 2000);
  });
  onUnmounted(() => {
    game.ticker.remove(move);
    clearInterval(addEnemyTimer.value);
  });
  return {
    enemys,
    hurtEnemy
  };
}
</script>
