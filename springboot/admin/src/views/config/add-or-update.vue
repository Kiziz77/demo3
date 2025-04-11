<template>
  <div class="form-container">
    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="80px" :rules="formRules">
      <el-col :span="12">
        <el-form-item label="名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入名称" />
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="图片" prop="value">
          <file-upload action="file/upload" :fileUrls="formData.value" :limit="1" :multiple="false" @change="pictureUploadChange"></file-upload>
        </el-form-item>
      </el-col>

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
//事件
const emit = defineEmits(["handleSuccess"]);
// 表单数据
const formData = ref({
  id: null,
  name: "",
  value: "",
});

// 表单验证规则
const formRules = ref({
  name: [{ required: true, message: "请输入名称", trigger: "blur" }],
  value: [{ required: true, message: "请上传图片", trigger: "blur" }],
});

//初始化
const init = (id) => {
  console.log("🚀 ~ init ~ id:", id);
  if (id) {
    request.get(`config/info/${id}`).then(({ data }) => {
      formData.value = data;
    });
  }
};
//图片上传
const pictureUploadChange = (value) => {
  formData.value.value = value;
};
// 提交表单
const handleSubmit = () => {
  if (!formData.value.name || !formData.value.value) {
    ElMessage.warning("请填写完整信息");
    return;
  }

  const api = formData.value.id ? "config/update" : "config/save";

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

<style lang="scss" scoped></style>
