<template>
  <the-aside></the-aside>
  <el-container style="display: flex; flex-direction: column">
    <the-header></the-header>
    <el-main>
      <el-scrollbar>
        <router-view></router-view>
      </el-scrollbar>
    </el-main>
    <the-footer></the-footer>
  </el-container>
</template>

<script setup>
import TheAside from "@/layouts/TheAside.vue";
import TheHeader from "@/layouts/TheHeader.vue";
import TheFooter from "@/layouts/TheFooter.vue";
</script>

<style lang="scss" scoped></style>
