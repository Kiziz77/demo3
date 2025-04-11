<template>
  <div class="form-container">
    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="120px" :rules="formRules">

     <el-row :span="24">

                          <el-col :span="12">
                     <el-form-item label="客户姓名" prop="customerName">
                         <el-input v-model="formData.customerName" placeholder="请输入客户姓名" />
                     </el-form-item>
                 </el-col>
                 <el-col :span="12">
                     <el-form-item label="联系电话" prop="phone">
                         <el-input v-model="formData.phone" placeholder="请输入联系电话" />
                     </el-form-item>
                 </el-col>
                <el-col :span="12">
                    <el-form-item label="药品ID" prop="drugId">
                                <module-select v-model="formData.drugId" placeholder="请选择药品ID"  titleName="drugName" url="/drugInfo/loadAll" optionKey="id"></module-select>
                                </el-form-item>

                </el-col>
                 <el-col :span="12">
                     <el-form-item label="购买数量" prop="quantity">
                         <el-input v-model="formData.quantity" placeholder="请输入购买数量" />
                     </el-form-item>
                 </el-col>
                 <el-form-item label="订单时间" prop="orderTime">
                     <el-date-picker type="datetime" placeholder="订单时间" v-model="formData.orderTime" value-format="YYYY-MM-DD HH:mm:ss"></el-date-picker>
                 </el-form-item>


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
    customerName: null,
    phone: null,
    drugId: null,
    quantity: null,
    orderTime: null});

// 表单验证规则
const formRules = ref({
    customerName: [{ required: true, message: '客户姓名不能为空', trigger: 'blur' }],
    drugId: [{ required: true, message: '药品ID不能为空', trigger: 'blur' }],
    quantity: [{ required: true, message: '购买数量不能为空', trigger: 'blur' }],
});

//初始化
const init = (id) => {
  if (id) {
    request.get(`customerOrder/info/` + id).then(({ data }) => {
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

  const api = formData.value.id ? "customerOrder/update" : "customerOrder/save";

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
