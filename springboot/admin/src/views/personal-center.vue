<template>
  <div class="personal-center-container">
    <!-- 公共头部 -->
    <div class="header">
      <h2>个人中心</h2>
      <el-avatar :size="100" :src="formData.avatar" class="avatar" />
      <file-upload action="file/upload" :fileUrls="formData.avatar" :limit="1" :show-file-list="false" @change="handleAvatarChange" />
    </div>

    <!-- 动态表单 -->
    <el-form ref="formRef" :model="formData" label-width="100px" :rules="formRules" class="form-wrapper">
      <!-- 管理员表单 -->
      <template v-if="isManager">
        <el-form-item label="账号" prop="accountNumber">
          <el-input v-model="formData.accountNumber" disabled />
        </el-form-item>

        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="formData.realName" />
        </el-form-item>
      </template>

      <!-- 用户表单 -->
      <template v-else>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="formData.userName" disabled />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-select v-model="formData.gender">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
      </template>

      <!-- 公共字段 -->
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="formData.phone" />
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="formData.email" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="handleSubmit">保存修改</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { useUserStore } from "@/stores/user";
import storage from "@/utils/storage";
import config from "@/config/config";
import request from "@/utils/request";

const userStore = useUserStore();
const formRef = ref(null);
const formData = ref({});
const currentUserType = computed(() => storage.get(config.CURRENT_SESSION_TABLE_KEY));
console.log("🚀 ~ currentUserType:", currentUserType);

// 判断是否是管理员
const isManager = computed(() => currentUserType.value === "managers");

// 表单验证规则
const formRules = ref({
  realName: [{ required: true, message: "请输入真实姓名", trigger: "blur" }],
  userName: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  phone: [
    { required: true, message: "请输入手机号", trigger: "blur" },
    { pattern: /^1[3-9]\d{9}$/, message: "手机号格式不正确", trigger: "blur" },
  ],
  email: [
    { required: true, message: "请输入邮箱", trigger: "blur" },
    { type: "email", message: "邮箱格式不正确", trigger: "blur" },
  ],
});

// 初始化表单数据
const initFormData = async () => {
  try {
    const api = isManager.value ? "managers/session" : "users/session";
    const { data } = await request.get(api);
    formData.value = data;
    console.log("🚀 ~ initFormData ~ data:", data);
  } catch (error) {
    ElMessage.error("获取用户信息失败");
  }
};

// 头像上传处理
const handleAvatarChange = (fileUrls) => {
  formData.value.avatar = fileUrls;
};

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate();

    const api = isManager.value ? "managers/update" : "users/update";
    await request.post(api, formData.value);

    // 更新store中的数据
    userStore.userInfo = formData.value;

    ElMessage.success("修改成功");
  } catch (error) {
    console.error("保存失败:", error);
  }
};

// 重置表单
const handleReset = () => {
  initFormData();
};

onMounted(() => {
  initFormData();
});
</script>

<style lang="scss" scoped>
.personal-center-container {
  max-width: 800px;
  margin: 20px auto;
  padding: 30px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

  .header {
    text-align: center;
    margin-bottom: 30px;

    .avatar {
      margin: 20px 0;
      border: 2px solid #eee;
    }
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
