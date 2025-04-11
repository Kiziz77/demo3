<template>
  <div class="password-container">
    <!-- 头部 -->
    <div class="header">
      <h2>修改密码</h2>
    </div>

    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="100px" :rules="formRules" class="form-wrapper">
      <!-- 旧密码 -->
      <el-form-item label="旧密码" prop="oldPassword">
        <el-input v-model="formData.oldPassword" type="password" placeholder="请输入旧密码" show-password />
      </el-form-item>

      <!-- 新密码 -->
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="formData.newPassword" type="password" placeholder="请输入新密码" show-password />
      </el-form-item>

      <!-- 确认新密码 -->
      <el-form-item label="确认新密码" prop="confirmPassword">
        <el-input v-model="formData.confirmPassword" type="password" placeholder="请再次输入新密码" show-password />
      </el-form-item>

      <!-- 操作按钮 -->
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">保存修改</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { ElMessage } from "element-plus";

import storage from "@/utils/storage";
import config from "@/config/config";
import request from "@/utils/request";

const formRef = ref(null);
const tableName = storage.get(config.CURRENT_SESSION_TABLE_KEY);
const formData = ref({
  oldPassword: "", // 旧密码
  newPassword: "", // 新密码
  confirmPassword: "", // 确认新密码
});

// 表单验证规则
const formRules = ref({
  oldPassword: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
  newPassword: [{ required: true, message: "请输入新密码", trigger: "blur" }],
  confirmPassword: [
    { required: true, message: "请确认新密码", trigger: "blur" },
    {
      validator: (rule, value, callback) => {
        if (value !== formData.value.newPassword) {
          callback(new Error("两次输入的密码不一致"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],
});

// 提交表单
const handleSubmit = async () => {
  try {
    // 表单验证
    await formRef.value.validate();

    // 调用修改密码接口
    const api = `${tableName}/updatePassword?oldPassword=${formData.value.oldPassword}&newPassword=${formData.value.newPassword}`;

    await request.post(api);

    // 提示成功
    ElMessage.success("密码修改成功,下次登录系统生效");

    // 清空表单
    handleReset();
  } catch (error) {
    console.error("密码修改失败:", error);
    ElMessage.error(error.response?.data?.message || "密码修改失败");
  }
};

// 重置表单
const handleReset = () => {
  formRef.value.resetFields();
};
</script>

<style lang="scss" scoped>
.password-container {
  max-width: 800px;
  margin: 20px auto;
  padding: 30px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

  .header {
    text-align: center;
    margin-bottom: 30px;
  }

  .form-wrapper {
    padding: 0 50px;

    :deep(.el-form-item__label) {
      font-weight: 500;
    }

    .el-input,
    .el-select {
      width: 100%;
    }
  }
}
</style>
