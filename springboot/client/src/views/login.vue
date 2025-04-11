<template>
  <div class="login-container">
    <el-card class="login-box">
      <h2 class="login-title">登录</h2>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef">
        <el-form-item prop="userName">
          <el-input v-model="loginForm.userName" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item v-if="roleOptions.length > 1" prop="role">
          <el-radio-group v-model="loginForm.role">
            <el-radio v-for="item in roleOptions" :value="item.value">{{ item.key }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" class="login-button">登录</el-button>
        </el-form-item>
      </el-form>
      <div class="register-link">
        <span>还没有账号？</span>
        <el-link type="primary" @click="goToRegister">立即注册</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { useUserStore } from "@/stores/user";
import config from "@/config/config";
import request from "@/utils/request";
import menu from "@/utils/menu";
import storage from "@/utils/storage";
const router = useRouter();
const loginForm = ref({
  userName: "",
  password: "",
  role: "",
});

const rules = {
  userName: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
};

const loginFormRef = ref(null);
const tableName = ref(null);
const roleName = ref(null);
const userStore = useUserStore();
const handleLogin = () => {
  console.log("🚀 ~ handleLogin ~ roleOptions.value:", roleOptions.value);
  if (roleOptions.value.length > 1) {
    if (!loginForm.value.role) {
      ElMessage.error("请选择角色");
      return;
    } else {
      let menus = menu.list();
      for (let i = 0; i < menus.length; i++) {
        if (menus[i].tableName === loginForm.value.role) {
          tableName.value = menus[i].tableName;
          roleName.value = menus[i].roleName;
        }
      }
    }
  } else {
    loginForm.value.role = roleOptions.value[0].value;
    tableName.value = roleOptions.value[0].value;
    roleName.value = roleOptions.value[0].key;
  }
  loginFormRef.value.validate((valid) => {
    if (valid) {
      request.post(`/${tableName.value}/login?userName=${loginForm.value.userName}&password=${loginForm.value.password}`).then(({ data }) => {
        userStore.setToken(data);
        getCurrentUser();
        storage.set(config.CURRENT_SESSION_ROLE_KEY, loginForm.value.role);
        storage.set(config.CURRENT_SESSION_ROLE_NAME_KEY, roleName.value);
        storage.set(config.CURRENT_SESSION_TABLE_KEY, tableName.value);
        storage.set(config.CURRENT_LOGIN_NAME, loginForm.value.userName);
        storage.remove(config.NAV_KEY);
        ElMessage.success("登录成功");
        router.push("/");
      });
    } else {
      ElMessage.error("请填写完整的登录信息");
    }
  });
};

const getCurrentUser = () => {
  request.get(`/${tableName.value}/session`).then(({ data }) => {
    userStore.setUserInfo(data);
  });
};

const goToRegister = () => {
  router.push("/register");
};

const roleOptions = ref([]);
const loadRoleOptions = () => {
  let menus = menu.list();
  console.log("🚀 ~ loadRoleOptions ~ menus:", menus);
  for (let i = 0; i < menus.length; i++) {
    if (menus[i].hasFrontLogin === "是") {
      let menuItem = {};
      menuItem["key"] = menus[i].roleName;
      menuItem["value"] = menus[i].tableName;
      roleOptions.value.push(menuItem);
    }
  }
};

onMounted(() => {
  loadRoleOptions();
});
</script>

<style lang="scss" scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url(https://i1.mifile.cn/f/i/18/mitv4A/43y/index_bannerbg2.png);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;

  .login-box {
    width: 400px;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    .login-title {
      text-align: center;
      margin-bottom: 20px;
      font-size: 24px;
      color: #303133;
    }

    .login-button {
      width: 100%;
    }

    .register-link {
      display: flex;
      justify-content: center;
      margin-top: 15px;
      color: #606266;
      font-size: 14px;
      .el-link {
        margin-left: 5px;
      }
    }
  }
}
</style>
