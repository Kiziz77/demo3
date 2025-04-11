<template>
  <el-header class="top-header">
    <div class="left">
<!--      <h1 style="font-size: 18px;font-weight: 700">{{ titleName }}</h1>-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-for="(item, index) in breadcrumbItems" :key="index" :to="item.path">
          <a  @click="handleBreadcrumbClick(item)">{{ item.name }}</a>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="right">
      <!-- 用户信息下拉菜单 -->
      <el-dropdown placement="bottom-end" trigger="hover">
        <div class="user-info">
          <el-avatar :size="30" :src="avatarUrl" class="avatar" />
          <span class="nickname">{{ roleName }} {{ loginName }}</span>
          <el-icon><arrow-down /></el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="handlePersonalCenter">
              <el-icon><user /></el-icon>
              <span>个人中心</span>
            </el-dropdown-item>
            <el-dropdown-item @click="handleResetPassword">
                <el-icon><Edit /></el-icon>
              <span>修改密码</span>
            </el-dropdown-item>
            <el-dropdown-item divided @click="handleLogout">
              <el-icon><switch-button /></el-icon>
              <span>退出登录</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </el-header>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import {useRoute, useRouter} from "vue-router";
import config from "@/config/config";
import request from "@/utils/request.js";
import storage from "@/utils/storage";
import { ElMessage } from "element-plus";
import { onMounted, ref, watch } from "vue";
import { User, SwitchButton, ArrowDown } from "@element-plus/icons-vue";

const userStore = useUserStore();
const loginName = ref(null);
const roleName = ref(null);
const avatarUrl = ref(null); // 头像 URL
const router = useRouter();

const route = useRoute()// 获取当前路由对象
const titleName = ref('')
const breadcrumbItems = ref([]); // 面包屑数据

// 监听路由 根据路由路径设置当前功能名称

watch(route,() => {
  titleName.value = route.name;
  updateBreadcrumb()
})

// 更新面包屑数据
const updateBreadcrumb = () => {
  const matched = route.matched.filter(record => record.name && record.name !== '/' && record.name !== '首页');
  breadcrumbItems.value = matched.map((item) => ({
    name: item.name,
    path: item.path,
  }));
};

// 处理面包屑点击事件
const handleBreadcrumbClick = (item) => {
  router.push(item.path);
};

// 监听 userStore.userInfo 的变化
watch(
  () => userStore.userInfo,
  (newUserInfo) => {
    if (newUserInfo.avatar) {
      avatarUrl.value = newUserInfo.avatar;
    } else {
      avatarUrl.value = "https://example.com/avatar.png"; // 默认头像
    }
  },
  { immediate: true } // 立即执行一次
);

// 退出登录
const handleLogout = () => {
  let tableName = storage.get(config.CURRENT_SESSION_TABLE_KEY);
  request.post(`/${tableName}/logout`).then((data) => {
    userStore.reset();
    ElMessage.success("退出登录成功");
    router.push("/login");
  });
};

// 跳转个人中心
const handlePersonalCenter = () => {
  router.push("/personal-center");
};
// 跳转修改密码
const handleResetPassword = () => {
  router.push("/reset-password");
};

onMounted(() => {
  loginName.value = storage.get(config.CURRENT_LOGIN_NAME);
  roleName.value = storage.get(config.CURRENT_SESSION_ROLE_NAME_KEY);
  avatarUrl.value = userStore.userInfo.avatar || "https://example.com/avatar.png";
  updateBreadcrumb();
});
  titleName.value = route.name;
</script>

<style lang="scss" scoped>
.top-header {
  background-color: #909399;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between; // 使用 space-between 来分配左右间距
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); // 添加阴影
  border-bottom: 1px solid #e6e6e6; // 添加底部边框

  .left {
    display: flex;
    align-items: center;

    .title-container {
      font-size: 18px;
      font-weight: bold;
      color: #333333;
    }

    .el-breadcrumb {
      margin-left: 20px; // 增加一些间距
      font-size: 14px;
      line-height: 1.5;
    }
  }

  .right {
    display: flex;
    align-items: center;

    .user-info {
      display: flex;
      align-items: center;
      cursor: pointer;
      padding: 5px 10px;
      border-radius: 20px;
      transition: background-color 0.3s ease;

      &:hover {
        background-color: #909399; // 悬停背景色
      }

      .avatar {
        margin-right: 10px;
      }

      .nickname {
        font-size: 14px;
        font-weight: 500;
        color: #333333;
        margin-right: 5px;
      }

      .el-icon {
        color: #666666;
      }
    }
  }
}

.el-dropdown-menu {
  .el-dropdown-item {
    display: flex;
    align-items: center;
    font-size: 14px;
    padding: 10px 20px;

    .el-icon {
      margin-right: 8px;
      font-size: 16px;
    }

    &:hover {
      background-color: #909399;
      color: var(--el-color-primary);
    }
  }
}
</style>
