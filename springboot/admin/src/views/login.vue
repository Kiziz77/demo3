<template>
  <div class="login-container">
    <div style="display: flex; flex-direction: column">
      <el-card class="login-box">
        <h2 class="login-title">登录</h2>
        <el-form :model="loginForm" :rules="rules" ref="loginFormRef">
          <el-form-item prop="userName">
            <el-input v-model="loginForm.userName" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="checkCode" class="captcha-container">
            <el-input v-model="loginForm.checkCode" placeholder="请输入验证码" class="captcha-input"></el-input>
            <canvas id="captchaCanvas" width="120" height="40" @click="handleGetCode" style="cursor: pointer"></canvas>
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
  checkCode: "",
  codeKey: "",
  role: "",
});
const codeImage = ref("");

const rules = {
  userName: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
  checkCode: [{ required: true, message: "请输入验证码", trigger: "blur" }],
};

const loginFormRef = ref(null);
const tableName = ref(null);
const roleName = ref(null);
const userStore = useUserStore();
//验证码
const captchaText = ref("");

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
      if (!validateCaptcha(loginForm.value.checkCode)) {
        ElMessage.error("验证码错误");
        handleGetCode(); // 验证码错误时重新生成验证码
        return;
      }

      request.post(`/${tableName.value}/login?userName=${loginForm.value.userName}&password=${loginForm.value.password}`).then(({ data }) => {
        userStore.setToken(data);
        getCurrentUser();
        storage.set(config.CURRENT_SESSION_ROLE_KEY, loginForm.value.role);
        storage.set(config.CURRENT_SESSION_ROLE_NAME_KEY, roleName.value);
        storage.set(config.CURRENT_SESSION_TABLE_KEY, tableName.value);
        storage.set(config.CURRENT_LOGIN_NAME, loginForm.value.userName);
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
    if (menus[i].hasBackLogin === "是") {
      let menuItem = {};
      menuItem["key"] = menus[i].roleName;
      menuItem["value"] = menus[i].tableName;
      roleOptions.value.push(menuItem);
    }
  }
};

// 生成验证码
const handleGetCode = () => {
  captchaText.value = generateRandomString(4); // 生成4个字符的验证码
  drawCaptcha(captchaText.value);
};

// 校验验证码
function validateCaptcha(inputCaptcha) {
  return inputCaptcha.toLowerCase() === captchaText.value.toLowerCase();
}

// 生成随机字符串
function generateRandomString(length) {
  const chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  let result = "";
  for (let i = length; i > 0; --i) {
    result += chars[Math.floor(Math.random() * chars.length)];
  }
  return result;
}

// 绘制验证码到Canvas
function drawCaptcha(captchaText) {
  const canvas = document.getElementById("captchaCanvas");
  const context = canvas.getContext("2d");

  // 清除画布
  context.clearRect(0, 0, canvas.width, canvas.height);

  // 设置字体和样式
  context.font = "bold 24px Arial";
  context.textAlign = "center";
  context.textBaseline = "middle";
  context.fillStyle = "#000";

  // 绘制背景色（可选）
  context.fillStyle = "#f3f3f3";
  context.fillRect(0, 0, canvas.width, canvas.height);

  // 添加一些干扰线
  for (let i = 0; i < 5; i++) {
    context.strokeStyle = getRandomColor();
    context.beginPath();
    context.moveTo(Math.random() * canvas.width, Math.random() * canvas.height);
    context.lineTo(Math.random() * canvas.width, Math.random() * canvas.height);
    context.stroke();
  }

  // 绘制验证码文本
  for (let i = 0; i < captchaText.length; i++) {
    context.fillStyle = getRandomColor();
    context.fillText(captchaText[i], 25 * i + 15, canvas.height / 2);
  }
}

// 获取随机颜色
function getRandomColor() {
  const r = Math.floor(Math.random() * 256);
  const g = Math.floor(Math.random() * 256);
  const b = Math.floor(Math.random() * 256);
  return `rgb(${r},${g},${b})`;
}

onMounted(() => {
  loadRoleOptions();
  handleGetCode();
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

  > div:first-child {
    text-align: center;
    color: #fff;

    h1 {
      font-size: 2.5rem; // 增大字体大小
      font-weight: bold; // 加粗字体
      text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3); // 添加文字阴影
      line-height: 1.2; // 调整行高
      margin: 0;
    }
  }

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

    // 验证码容器样式
    .captcha-container {
      display: flex;
      align-items: center;
    }

    // 验证码输入框样式
    .captcha-input {
      flex: 1;
      margin-right: 10px; // 输入框和图片之间的间距
    }

    // 验证码图片样式
    .captcha-image {
      width: 100px; // 设置图片宽度
      height: 40px; // 设置图片高度
      cursor: pointer; // 鼠标指针变为手型，表示可点击
      border-radius: 4px;
      object-fit: cover; // 图片适应容器大小
    }
  }
}
</style>
