<template>
  <div class="form-container">
    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="120px" :rules="formRules">

     <el-row :span="24">

                          <el-col :span="12">
                     <el-form-item label="供应商名称" prop="supplierName">
                         <el-input v-model="formData.supplierName" placeholder="请输入供应商名称" />
                     </el-form-item>
                 </el-col>
                 <el-col :span="12">
                     <el-form-item label="联系人" prop="contactPerson">
                         <el-input v-model="formData.contactPerson" placeholder="请输入联系人" />
                     </el-form-item>
                 </el-col>
                 <el-col :span="12">
                     <el-form-item label="联系电话" prop="phone">
                         <el-input v-model="formData.phone" placeholder="请输入联系电话" />
                     </el-form-item>
                 </el-col>
                 <el-col :span="12">
                     <el-form-item label="地址" prop="address">
                         <el-input v-model="formData.address" placeholder="请输入地址" />
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
    supplierName: null,
    contactPerson: null,
    phone: null,
    address: null});

// 表单验证规则
const formRules = ref({
    supplierName: [{ required: true, message: '供应商名称不能为空', trigger: 'blur' }],
    contactPerson: [{ required: true, message: '联系人不能为空', trigger: 'blur' }],
    phone: [{ required: true, message: '联系电话不能为空', trigger: 'blur' }],
    address: [{ required: true, message: '地址不能为空', trigger: 'blur' }]});

//初始化
const init = (id) => {
  if (id) {
    request.get(`supplierInfo/info/` + id).then(({ data }) => {
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

  const api = formData.value.id ? "supplierInfo/update" : "supplierInfo/save";

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
