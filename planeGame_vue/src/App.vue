<template>
  <container>
    <GamePage @changePage="changePage" v-if="currentPage==='gamePage'" />
    <GameOverPage @changePage="changePage" v-else-if="currentPage==='gameOverPage'" />
  </container>
</template>

<script>
import { ref } from "vue";
import GamePage from "./pages/GamePage.vue";
import GameOverPage from "./pages/GameOverPage.vue";
export default {
  components: {
    GamePage,
    GameOverPage
  },
  setup() {
    const currentPage = ref("gamePage");
    const changePage = pageName => {
      currentPage.value = pageName;
    };
    return {
      currentPage,
      changePage
    };
  }
};
</script>
<style>
</style>
