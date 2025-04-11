<template>
  <div class="form-container">
    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="80px" :rules="formRules">
      <!-- 标题 -->
      <el-col :span="12">
        <el-form-item label="标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入标题" />
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="图片" prop="pictures">
          <file-upload action="file/upload" :fileUrls="formData.pictures" :limit="1" :multiple="false" @change="pictureUploadChange"></file-upload>
        </el-form-item>
      </el-col>
      <!-- 内容 -->
      <el-col :span="24">
        <el-form-item label="内容" prop="content">
          <rich-editor :initValue="formData.content" @change="editorChange"></rich-editor>
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
  title: "",
  content: "",
  pictures: "",
});

// 表单验证规则
const formRules = ref({
  title: [{ required: true, message: "请输入标题", trigger: "blur" }],
  content: [{ required: true, message: "请输入内容", trigger: "blur" }],
  pictures: [],
});

//初始化
const init = (id) => {
  console.log("🚀 ~ init ~ id:", id);
  if (id) {
    request.get(`notices/info/${id}`).then(({ data }) => {
      formData.value = data;
    });
  }
};

//富文本编辑器文本变化
const editorChange = (value) => {
  formData.value.content = value;
};
//图片上传
const pictureUploadChange = (value) => {
  formData.value.pictures = value;
};
// 提交表单
const handleSubmit = () => {
  if (!formData.value.title || !formData.value.content) {
    ElMessage.warning("请填写完整信息");
    return;
  }

  const api = formData.value.id ? "notices/update" : "notices/save";

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
