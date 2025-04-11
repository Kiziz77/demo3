<template>
  <div class="navs-container">
    <el-menu :default-active="activeIndex + ''" mode="horizontal" @select="handleSelect">
      <el-menu-item v-for="(menu, index) in menuList" :index="index + ''" :key="index" @click="handleClick(menu)">{{ menu.name }}</el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import config from "@/config/config";
const activeIndex = ref("0");
const menuList = ref([]);
const handleSelect = (key, keyPath) => {
  if (keyPath) {
    localStorage.setItem(config.NAV_KEY, keyPath[0]);
  }
};
const router = useRouter();
const handleClick = (item) => {
  router.push(item.url);
};

// 获取当前路由
const route = useRoute();

// 监听路由变化
watch(
  () => route.path, // 监听路由的 path 变化
  (newValue) => {
    // 根据路由的路径更新 activeIndex
    menuList.value.forEach((menu, index) => {
      if (newValue === menu.url) {
        activeIndex.value = index;
      }
    });
  },
  { immediate: true } // 如果需要在初始化时就执行一次，可以设置 immediate: true
);

onMounted(() => {
  // 从 localStorage 中获取保存的 activeIndex，如果没有则默认为 "0"
  activeIndex.value = localStorage.getItem(config.NAV_KEY) || "0";

  // 根据当前路由动态设置 activeIndex
  const currentRoute = route.path;
  const matchingIndex = menuList.value.findIndex((menu) => menu.url === currentRoute);
  // 如果找到匹配的路由，则使用匹配的 index，否则使用 activeIndex.value
  activeIndex.value = matchingIndex !== -1 ? matchingIndex.toString() : activeIndex.value;
  menuList.value = config.indexNav;
});
</script>

<style lang="scss" scoped></style>
