<template>
  <div class="form-container">
    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="120px" :rules="formRules">

     <el-row :span="24">

                         <el-col :span="12">
                    <el-form-item label="供应商ID" prop="supplierId">
                                <module-select v-model="formData.supplierId" placeholder="请选择供应商ID"  titleName="supplierName" url="/supplierInfo/loadAll" optionKey="id"></module-select>
                                </el-form-item>

                </el-col>
                <el-col :span="12">
                    <el-form-item label="药品ID" prop="drugId">
                                <module-select v-model="formData.drugId" placeholder="请选择药品ID"  titleName="drugName" url="/drugInfo/loadAll" optionKey="id"></module-select>
                                </el-form-item>

                </el-col>
                 <el-col :span="12">
                     <el-form-item label="采购数量" prop="purchaseQuantity">
                         <el-input v-model="formData.purchaseQuantity" placeholder="请输入采购数量" />
                     </el-form-item>
                 </el-col>
                 <el-form-item label="采购时间" prop="purchaseTime">
                     <el-date-picker type="datetime" placeholder="采购时间" v-model="formData.purchaseTime" value-format="YYYY-MM-DD HH:mm:ss"></el-date-picker>
                 </el-form-item>
                 <el-col :span="12">
                     <el-form-item label="采购记录ID" prop="purchaseId">
                         <el-input v-model="formData.purchaseId" placeholder="请输入采购记录ID" />
                     </el-form-item>
                 </el-col>
                 <el-col :span="12">
                     <el-form-item label="采购单价" prop="purchaseUnitPrice">
                         <el-input v-model="formData.purchaseUnitPrice" placeholder="请输入采购单价" />
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
    supplierId: null,
    drugId: null,
    purchaseQuantity: null,
    purchaseTime: null,
    purchaseId: null,
    purchaseUnitPrice: null});

// 表单验证规则
const formRules = ref({
    supplierId: [{ required: true, message: '供应商ID不能为空', trigger: 'blur' }],
    drugId: [{ required: true, message: '药品ID不能为空', trigger: 'blur' }],
    purchaseQuantity: [{ required: true, message: '采购数量不能为空', trigger: 'blur' }],
    purchaseTime: [{ required: true, message: '采购时间不能为空', trigger: 'blur' }],
    purchaseId: [{ required: true, message: '采购记录ID不能为空', trigger: 'blur' }],
    purchaseUnitPrice: [{ required: true, message: '采购单价不能为空', trigger: 'blur' }]});

//初始化
const init = (id) => {
  if (id) {
    request.get(`purchaseRecord/info/` + id).then(({ data }) => {
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

  const api = formData.value.id ? "purchaseRecord/update" : "purchaseRecord/save";

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
