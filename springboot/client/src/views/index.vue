<template>
  <div id="app-container">
    <the-header></the-header>
    <the-navs></the-navs>
    <the-carousel height="600px" type="card"></the-carousel>
    <router-view></router-view>
    <the-footer></the-footer>
  </div>
</template>

<script setup>
import TheHeader from "@/layouts/the-header.vue";
import TheCarousel from "@/layouts/the-carousel.vue";
import TheNavs from "@/layouts/the-navs.vue";
import TheFooter from "@/layouts/the-footer.vue";
</script>

<style lang="scss" scoped>
#app-container {
  height: 100vh;
  width: 100vw;
  overflow-y: scroll;
}
</style>
