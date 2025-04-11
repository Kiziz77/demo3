<template>
  <div class="form-container">
    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="120px" :rules="formRules">

     <el-row :span="24">

                         <el-col :span="12">
                    <el-form-item label="库存ID" prop="inventoryId">
                                <module-select v-model="formData.inventoryId" placeholder="请选择库存ID"  titleName="drugId" url="/inventory/loadAll" optionKey="id"></module-select>
                                </el-form-item>

                </el-col>
                 <el-col :span="12">
                     <el-form-item label="销售数量" prop="salesQuantity">
                         <el-input v-model="formData.salesQuantity" placeholder="请输入销售数量" />
                     </el-form-item>
                 </el-col>
                 <el-form-item label="销售时间" prop="salesTime">
                     <el-date-picker type="datetime" placeholder="销售时间" v-model="formData.salesTime" value-format="YYYY-MM-DD HH:mm:ss"></el-date-picker>
                 </el-form-item>
                 <el-col :span="12">
                     <el-form-item label="客户名称" prop="customerName">
                         <el-input v-model="formData.customerName" placeholder="请输入客户名称" />
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
    inventoryId: null,
    salesQuantity: null,
    salesTime: null,
    customerName: null});

// 表单验证规则
const formRules = ref({
    inventoryId: [{ required: true, message: '库存ID不能为空', trigger: 'blur' }],
    salesQuantity: [{ required: true, message: '销售数量不能为空', trigger: 'blur' }],
    salesTime: [{ required: true, message: '销售时间不能为空', trigger: 'blur' }],
    customerName: [{ required: true, message: '客户名称不能为空', trigger: 'blur' }]});

//初始化
const init = (id) => {
  if (id) {
    request.get(`salesRecord/info/` + id).then(({ data }) => {
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

  const api = formData.value.id ? "salesRecord/update" : "salesRecord/save";

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
