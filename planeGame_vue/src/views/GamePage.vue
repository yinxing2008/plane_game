<template>
  <container>
    <Map />
    <Plane
      @changePage="changePage"
      :x="planeInfo.x"
      :y="planeInfo.y"
    />
    <PlaneBullet v-for="(bullet,index) in planeBullets" :key="index" :x="bullet.x" :y="bullet.y" />
    <Enemy
      v-for="(enemy,index) in enemys"
      :key="index"
      :x="enemy.x"
      :y="enemy.y"
      @addScore="addScore"
    ></Enemy>
    <text :text="`得分：${score}`" :x="10" :y="10"></text>
  </container>
</template>
<script>
import Map from "../components/Map.vue";
import Plane, { usePlane } from "../components/Plane.vue";
import PlaneBullet, { usePlaneBullet } from "../components/PlaneBullet.vue";
import Enemy, { useEnemy } from "../components/Enemy.vue";
import { ref } from "vue";
import { fighting } from "../game/fighting.js";
export default {
  components: { Map, Plane, PlaneBullet, Enemy },
  setup(props, { emit }) {
    const score = ref(0);
    const addScore = val => {
      score.value += val;
    };
    const changePage = pageName => {
      emit("changePage", pageName);
    };
    const {
      bullets: planeBullets,
      addBullet: addPlaneBullet,
      removeBullet: removePlaneBullet
    } = usePlaneBullet();
    const { planeInfo, hurtPlane } = usePlane(addPlaneBullet);
    const { enemys, hurtEnemy } = useEnemy();

    fighting({
      enemys,
      planeInfo,
      hurtPlane,
      planeBullets,
      removePlaneBullet,
      hurtEnemy
    });

    return {
      score,
      addScore,
      planeBullets,
      planeInfo,
      enemys,
      changePage
    };
  }
};
</script>
