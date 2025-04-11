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
                 <el-col :span="24">
                     <el-form-item label="药品图片" prop="drugImage">
                         <file-upload action="file/upload" :fileUrls="formData.drugImage" :limit="1" :multiple="false" @change="pictureUploadChange('drugImage',$event)"></file-upload>
                     </el-form-item>
                 </el-col>
                 <el-col :span="12">
                     <el-form-item label="展示状态" prop="displayStatus">
                         <el-input v-model="formData.displayStatus" placeholder="请输入展示状态" />
                     </el-form-item>
                 </el-col>
                <el-col :span="12">
                    <el-form-item label="单价" prop="unitPrice">
                                <module-select v-model="formData.unitPrice" placeholder="请选择单价"  titleName="unitPrice" url="/drugInfo/loadAll" optionKey="unitPrice"></module-select>
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
    drugImage: null,
    displayStatus: null,
    unitPrice: null});

// 表单验证规则
const formRules = ref({
    drugId: [{ required: true, message: '药品ID不能为空', trigger: 'blur' }],
    displayStatus: [{ required: true, message: '展示状态不能为空', trigger: 'blur' }],
});

//初始化
const init = (id) => {
  if (id) {
    request.get(`drugDisplay/info/` + id).then(({ data }) => {
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

  const api = formData.value.id ? "drugDisplay/update" : "drugDisplay/save";

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
