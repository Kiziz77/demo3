<template>
  <div class="form-container">
    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="120px" :rules="formRules">

     <el-row :span="24">

                          <el-col :span="12">
                     <el-form-item label="药品名称" prop="drugName">
                         <el-input v-model="formData.drugName" placeholder="请输入药品名称" />
                     </el-form-item>
                 </el-col>
                 <el-col :span="12">
                     <el-form-item label="规格" prop="specification">
                         <el-input v-model="formData.specification" placeholder="请输入规格" />
                     </el-form-item>
                 </el-col>
                 <el-col :span="12">
                     <el-form-item label="生产商" prop="manufacturer">
                         <el-input v-model="formData.manufacturer" placeholder="请输入生产商" />
                     </el-form-item>
                 </el-col>
                 <el-col :span="12">
                     <el-form-item label="批准文号" prop="approvalNumber">
                         <el-input v-model="formData.approvalNumber" placeholder="请输入批准文号" />
                     </el-form-item>
                 </el-col>
                 <el-col :span="12">
                     <el-form-item label="单价" prop="unitPrice">
                         <el-input v-model="formData.unitPrice" placeholder="请输入单价" />
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
const formRef = ref(null);
//事件
const emit = defineEmits(["handleSuccess"]);
// 表单数据
const formData = ref({
    id: null,
    drugName: null,
    specification: null,
    manufacturer: null,
    approvalNumber: null,
    unitPrice: null});

// 表单验证规则
const formRules = ref({
    drugName: [{ required: true, message: '药品名称不能为空', trigger: 'blur' }],
    specification: [{ required: true, message: '规格不能为空', trigger: 'blur' }],
    manufacturer: [{ required: true, message: '生产商不能为空', trigger: 'blur' }],
    approvalNumber: [{ required: true, message: '批准文号不能为空', trigger: 'blur' }],
    unitPrice: [{ required: true, message: '单价不能为空', trigger: 'blur' }]});

//初始化
const init = (id) => {
  if (id) {
    request.get(`drugInfo/info/` + id).then(({ data }) => {
      formData.value = data;
    });
  }
};

//富文本编辑器文本变化
const editorChange = (field,$event) => {
  formData.value[field] = $event;
};
//图片上传
const pictureUploadChange = (field,$event) => {
    formData.value[field] = $event;
};
// 提交表单
const handleSubmit = async () => {
  await formRef?.value.validate();

  const api = formData.value.id ? "drugInfo/update" : "drugInfo/save";

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
