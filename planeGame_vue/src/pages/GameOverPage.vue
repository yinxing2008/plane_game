<template>
  <container>
    <sprite :texture="gameOverPageImg"></sprite>
    <sprite x="230" y="450" :interactive="true" @click="startGame" :texture="restartBtnImg"></sprite>
  </container>
</template>
<script>
import gameOverPageImg from "../assets/images/game_over.jpg";
import restartBtnImg from "../assets/images/restart_btn.png";
export default {
  setup(props, { emit }) {
    const startGame = () => {
      emit("changePage", "gamePage");
    };
    return {
      gameOverPageImg,
      restartBtnImg,
      startGame
    };
  }
};
</script>
