<template>
  <div class="top-header">
    <div class="left">
      {{ config.projectTitle }}
    </div>
    <div class="right" v-if="config.isHeader">
      <el-button v-if="!userStore.token" @click="toLogin" class="login-btn">登录 / 注册</el-button>
      <div v-if="userStore.token" class="user-area">
        <div class="shopping-cart" @click="toShoppingCart">
          <img src="/src/assets/svg/shopingCart.svg">
          <p>购物车</p>
        </div>
        <div class="username" @click="toPersonalCenter">{{ loginName }}</div>
        <el-button class="logout-btn" @click="handleLogout">退出</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import storage from "@/utils/storage.js";
import config from "@/config/config.js";
import { useUserStore } from "@/stores/user.js";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import request from "@/utils/request.js";
const loginName = ref(null);
const userStore = useUserStore();
const router = useRouter();

const toLogin = () => {
  router.push({ path: "/login" });
};

const toPersonalCenter = () => {
  router.push({ path: "personal-center" });
};

const toShoppingCart = () => {
  router.push({ path: "shopping-cart" });
};
/**
 * 退出登录
 */
const handleLogout = () => {
  let tableName = storage.get(config.CURRENT_SESSION_TABLE_KEY);
  console.log("🚀 ~ handleLogout ~ tableName:", tableName);
  request.post(`/${tableName}/logout`).then((data) => {
    userStore.reset();
    ElMessage.success("退出登录成功");
    router.push("/");
  });
};

onMounted(() => {
  loginName.value = storage.get(config.CURRENT_LOGIN_NAME);
});
</script>
