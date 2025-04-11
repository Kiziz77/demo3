<template>
  <el-aside class="the-aside">
    <el-scrollbar>
      <el-menu :default-openeds="[]" :unique-opened="true" :default-active="0 + ''" class="el-menu-vertical">
        <div class="title">
          <h1>后台</h1>
        </div>
        <el-sub-menu index="0" class="menu-item-home" @click="menuClickHandler('')">
          <template #title>
            <el-icon v-if="config.showInconFlag"><House /></el-icon>
            <span class="item-title">系统首页</span>
          </template>
        </el-sub-menu>

        <el-sub-menu v-for="(item, index) in menuList.backMenu" :key="index" :index="index + 1 + ''">
          <template #title>
            <el-icon v-if="config.showInconFlag"><component :is="config.icons[index]" /></el-icon>
            <span class="item-title">{{ item.menu }}</span>
          </template>
          <el-menu-item v-for="(child, sort) in item.child" :key="sort" :index="index + 1 + '-' + sort" @click="menuClickHandler(child.tableName)">
            <span class="item-title">{{ child.menu }}</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-scrollbar>
  </el-aside>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import storage from "@/utils/storage.js";
import config from "@/config/config.js";
import menu from "@/utils/menu.js";
const menuList = ref([]);
const roleName = ref(null);
/**
 * 加载菜单
 */
const loadMenu = () => {
  const menus = menu.list();
  if (menus) {
    menuList.value = menus;
  }
  menuList.value = menuList.value.filter((item) => {
    return item.roleName === roleName.value;
  })[0];
};

/**
 * 菜单点击事件
 * @param {*}
 */
let router = useRouter();
const menuClickHandler = (name) => {
  //   console.log("🚀 ~ menuClickHandler ~ name:", name);
  router.push("/" + name);
};
onMounted(() => {
  roleName.value = storage.get(config.CURRENT_SESSION_ROLE_NAME_KEY);
  loadMenu();
});
</script>

<style lang="scss" scoped>
.the-aside {
  width: 220px;
  background-color: #909399;
  .el-scrollbar {
    .el-menu-vertical {
      background-color: #909399;
      .menu-item-home:deep(.el-sub-menu__icon-arrow) {
        display: none;
      }
      .el-sub-menu {
        :deep(.el-sub-menu__title) {
          cursor: pointer;
          border-radius: 10px;
          padding: 0 20px;
          box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.302);
          color: #333;
          white-space: nowrap;
          background: #ebebeb;
          display: flex;
          justify-content: center;
          align-items: center;
          margin: 10px;
          position: relative;
          border: 8px double #77b1be;
        }
        :deep(.el-sub-menu__title):hover {
          background: #bad1e7;
        }
        :deep(.el-menu) {
          background-color: #dceeff;
          display: flex;
          flex-direction: column;
        }
        :deep(.el-menu-item) {
          cursor: pointer;
          border-radius: 10px;
          padding: 0 20px;
          box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.302);
          color: #333;
          white-space: nowrap;
          background: #ebebeb;
          display: flex;
          justify-content: center;
          align-items: center;
          margin: 5px 10px;
          position: relative;
          line-height: 56px;
          height: 56px;
          border: 8px double #77b1be;
        }
        :deep(.el-menu-item):hover {
          background: #bad1e7;
        }
        :deep(.is-active) {
          background: #bad1e7;
        }
      }
    }
  }
}

// 大标题
.title {
  display: flex;
  justify-content: center;
  margin: 20px;
  font-family: 微软雅黑;
  font-weight: 588;
  font-size: 20px;
  color: #fff;
}
</style>
