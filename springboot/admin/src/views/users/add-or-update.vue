<template>
  <div class="form-container">
    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="80px" :rules="formRules">
      <el-row :gutter="24">
        <!-- 用户名 -->
        <el-col :span="12">
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="formData.userName" placeholder="请输入用户名" />
          </el-form-item>
        </el-col>

        <!-- 头像 -->
        <el-col :span="24">
          <el-form-item label="头像" prop="avatar">
            <file-upload action="file/upload" :fileUrls="formData.avatar" :limit="1" :multiple="false" @change="avatarUploadChange"></file-upload>
          </el-form-item>
        </el-col>

        <!-- 邮箱 -->
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="formData.email" placeholder="请输入邮箱" />
          </el-form-item>
        </el-col>

        <!-- 电话 -->
        <el-col :span="12">
          <el-form-item label="电话" prop="phone">
            <el-input v-model="formData.phone" placeholder="请输入电话" />
          </el-form-item>
        </el-col>

        <!-- 性别 -->
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="formData.gender" placeholder="请选择性别">
              <el-option label="男" value="男" />
              <el-option label="女" value="女" />
              <el-option label="其他" value="其他" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 操作按钮 -->
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
        <el-button @click="handleCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { ElMessage } from "element-plus";
import request from "@/utils/request";

// 事件
const emit = defineEmits(["handleSuccess"]);

// 表单数据
const formData = ref({
  id: null,
  userName: "",
  password: "",
  avatar: "",
  email: "",
  phone: "",
  balance: 0,
  gender: "",
});

// 表单验证规则
const formRules = ref({
  userName: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  email: [
    { required: true, message: "请输入邮箱", trigger: "blur" },
    { type: "email", message: "请输入正确的邮箱格式", trigger: "blur" },
  ],
  phone: [
    { required: true, message: "请输入电话", trigger: "blur" },
    { pattern: /^1[3-9]\d{9}$/, message: "请输入正确的手机号", trigger: "blur" },
  ],
  gender: [{ required: true, message: "请选择性别", trigger: "change" }],
});

// 初始化
const init = (id) => {
  if (id) {
    request.get(`users/info/${id}`).then(({ data }) => {
      formData.value = data;
    });
  } else {
    formData.value = {
      id: null,
      userName: "",
      password: "",
      avatar: "",
      email: "",
      phone: "",
      balance: 0,
      gender: "",
    };
  }
};

// 头像上传
const avatarUploadChange = (value) => {
  formData.value.avatar = value;
};

// 提交表单
const handleSubmit = () => {
  if (!formData.value.userName  || !formData.value.email || !formData.value.phone) {
    ElMessage.warning("请填写完整信息");
    return;
  }

  const api = formData.value.id ? "users/update" : "users/save";

  request.post(api, formData.value).then(() => {
    ElMessage.success("保存成功");
    emit("handleSuccess");
  });
};

// 取消
const handleCancel = () => {
  emit("handleSuccess");
};

defineExpose({
  init,
});
</script>

<style lang="scss" scoped>
.form-container {
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
}
</style>
