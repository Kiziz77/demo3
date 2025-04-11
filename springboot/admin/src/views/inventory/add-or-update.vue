<template>
  <div class="form-container">
    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="120px" :rules="formRules">

     <el-row :span="24">

                         <el-col :span="12">
                    <el-form-item label="药品ID" prop="drugId">
                                <module-select v-model="formData.drugId" placeholder="请选择药品ID"  titleName="drugName" url="/drugInfo/loadAll" optionKey="id"></module-select>
                                </el-form-item>

                </el-col>
                 <el-col :span="12">
                     <el-form-item label="批次号" prop="batchNumber">
                         <el-input v-model="formData.batchNumber" placeholder="请输入批次号" />
                     </el-form-item>
                 </el-col>
                 <el-form-item label="入库时间" prop="storageTime">
                     <el-date-picker type="datetime" placeholder="入库时间" v-model="formData.storageTime" value-format="YYYY-MM-DD HH:mm:ss"></el-date-picker>
                 </el-form-item>
                 <el-form-item label="有效期至" prop="expiryDate">
                     <el-date-picker type="date" placeholder="有效期至" v-model="formData.expiryDate" value-format="YYYY-MM-DD"></el-date-picker>
                 </el-form-item>
                 <el-col :span="12">
                     <el-form-item label="当前数量" prop="quantity">
                         <el-input v-model="formData.quantity" placeholder="请输入当前数量" />
                     </el-form-item>
                 </el-col>
                <el-col :span="12">
                    <el-form-item label="采购记录ID" prop="purchaseId">
                                <module-select v-model="formData.purchaseId" placeholder="请选择采购记录ID"  titleName="purchaseId" url="/purchaseRecord/loadAll" optionKey="id"></module-select>
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
    drugId: null,
    batchNumber: null,
    storageTime: null,
    expiryDate: null,
    quantity: null,
    purchaseId: null});

// 表单验证规则
const formRules = ref({
    drugId: [{ required: true, message: '药品ID不能为空', trigger: 'blur' }],
    batchNumber: [{ required: true, message: '批次号不能为空', trigger: 'blur' }],
    storageTime: [{ required: true, message: '入库时间不能为空', trigger: 'blur' }],
    expiryDate: [{ required: true, message: '有效期至不能为空', trigger: 'blur' }],
    quantity: [{ required: true, message: '当前数量不能为空', trigger: 'blur' }],
    purchaseId: [{ required: true, message: '采购记录ID不能为空', trigger: 'blur' }]});

//初始化
const init = (id) => {
  if (id) {
    request.get(`inventory/info/` + id).then(({ data }) => {
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

  const api = formData.value.id ? "inventory/update" : "inventory/save";

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
