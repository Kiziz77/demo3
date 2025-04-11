<template>
  <div class="register-container">
    <el-card class="register-box">
      <h2 class="register-title">注册</h2>
      <el-form :model="registerForm" :rules="rules" ref="registerFormRef">
        <el-form-item prop="userName">
          <el-input v-model="registerForm.userName" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请确认密码"></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input v-model="registerForm.phone" placeholder="请输入电话"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister" class="register-button">注册</el-button>
        </el-form-item>
      </el-form>
      <div class="login-link">
        <span>已有账号？</span>
        <el-link type="primary" @click="goToLogin">立即登录</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import request from "@/utils/request.js";
const router = useRouter();

const registerForm = ref({
  userName: "",
  password: "",
  confirmPassword: "",
  email: "",
  phone: "",
});

const validatePassword = (rule, value, callback) => {
  if (value !== registerForm.value.password) {
    callback(new Error("两次输入的密码不一致"));
  } else {
    callback();
  }
};

const rules = {
  userName: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
  confirmPassword: [
    { required: true, message: "请确认密码", trigger: "blur" },
    { validator: validatePassword, trigger: "blur" },
  ],
  email: [
    { required: true, message: "请输入邮箱", trigger: "blur" },
    { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] },
  ],
  phone: [
    { required: true, message: "请输入电话", trigger: "blur" },
    { pattern: /^1[3-9]\d{9}$/, message: "请输入正确的手机号码", trigger: "blur" },
  ],
};

const registerFormRef = ref(null);

const handleRegister = () => {
  registerFormRef.value.validate((valid) => {
    if (valid) {
      request.post(`/users/register`, registerForm.value).then(({ data }) => {
        ElMessage.success("注册成功");
        router.push("/login");
      });
    } else {
      ElMessage.error("请填写完整的注册信息");
    }
  });
};

const goToLogin = () => {
  router.push("/login");
};
</script>

<style lang="scss" scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url(https://i1.mifile.cn/f/i/18/mitv4A/43y/index_bannerbg2.png);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;

  .register-box {
    width: 400px;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    .register-title {
      text-align: center;
      margin-bottom: 20px;
      font-size: 24px;
      color: #303133;
    }

    .register-button {
      width: 100%;
    }

    .login-link {
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
