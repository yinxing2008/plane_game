<template>
  <container>
    <sprite :x="0" :y="0" :texture="mapImg"></sprite>
  </container>
</template>
<script>
import mapImg from '../assets/images/map.jpg'
export default {
  setup() {
    return {
      mapImg
    }
  }
}
</script>
