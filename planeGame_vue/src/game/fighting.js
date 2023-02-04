import { onMounted, onUnmounted } from "vue";
import { game } from "../game";
import config from "../config";
export function fighting({ enemys, hurtEnemy, planeInfo, planeBullets, removePlaneBullet, hurtPlane }) {
  // 敌机与飞机碰撞检测
  const enemyPlaneCollision = () => {
    enemys.forEach((enemy, eIndex) => {
      if (isCollision(enemy, planeInfo)) {
        hurtEnemy(eIndex, config.plane.hurt);
        hurtPlane(config.enemy.hurt);
      }
    });
  };

  // 敌机与飞机子弹碰撞检测
  const enemyPlaneBulletCollision = () => {
    enemys.forEach((enemy, eIndex) => {
      planeBullets.forEach((bullet, bIndex) => {
        if (isCollision(enemy, bullet)) {
          hurtEnemy(eIndex, config.plane.hurt);
          removePlaneBullet(bIndex);
        }
      });
    });
  };
  onMounted(() => {
    game.ticker.add(enemyPlaneCollision);
    game.ticker.add(enemyPlaneBulletCollision);
  });
  onUnmounted(() => {
    game.ticker.remove(enemyPlaneCollision);
    game.ticker.remove(enemyPlaneBulletCollision);
  });
}

// 碰撞检测
function isCollision(objA, objB) {
  return (
    objA.x + objA.width >= objB.x &&
    objB.x + objB.width >= objA.x &&
    objA.y + objA.height >= objB.y &&
    objB.y + objB.height >= objA.y
  );
}