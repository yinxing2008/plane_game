import { onMounted, onUnmounted } from "vue";
import { game } from "../game";
import { hitObjectTest } from "../utils";
import config from "../config";
export function fighting({ enemys, hurtEnemy, planeInfo, planeBullets, removePlaneBullet, hurtPlane }) {
  // 敌机与飞机碰撞检测
  const enemyPlaneCollision = () => {
    enemys.forEach((enemy, eIndex) => {
      if (hitObjectTest(enemy, planeInfo)) {
        hurtEnemy(eIndex, config.plane.hurt);
        hurtPlane(config.enemy.hurt);
      }
    });
  };

  // 敌机与飞机子弹碰撞检测
  const enemyPlaneBulletCollision = () => {
    enemys.forEach((enemy, eIndex) => {
      planeBullets.forEach((bullet, bIndex) => {
        if (hitObjectTest(enemy, bullet)) {
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