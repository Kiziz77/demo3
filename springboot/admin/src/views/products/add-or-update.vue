<template>
  <div class="form-container">
    <!-- 表单 -->
    <el-form ref="formRef" :model="formData" label-width="120px" :rules="formRules">
      <el-row :span="24">
        <el-col :span="12">
          <el-form-item label="商品名称" prop="productsName">
            <el-input v-model="formData.productsName" placeholder="请输入商品名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商品品牌" prop="brand">
            <el-input v-model="formData.brand" placeholder="请输入商品品牌" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商品价格" prop="price">
            <el-input-number style="width: 300px" min="0" v-model="formData.price" placeholder="请输入商品价格" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="库存数量" prop="stockQuantity">
            <el-input-number style="width: 300px" min="0" v-model="formData.stockQuantity" placeholder="请输入库存数量" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="商品图片" prop="imageUrl">
            <file-upload
                action="file/upload"
                :fileUrls="formData.imageUrl"
                :limit="1"
                :multiple="false"
                @change="pictureUploadChange('imageUrl', $event)"
            ></file-upload>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="商品描述" prop="description">
            <rich-editor :initValue="formData.description" @change="editorChange('description', $event)"></rich-editor>
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
  productsName: null,
  description: null,
  filePath: null,
  imageUrl: null,
  price: null,
  brand: null,
  stockQuantity: null,
});

// 表单验证规则
const formRules = ref({
  productsName: [{ required: true, message: "商品名称不能为空", trigger: "blur" }],
  description: [{ required: true, message: "商品描述不能为空", trigger: "blur" }],
  price: [{ required: true, message: "商品价格不能为空", trigger: "blur" }],
  brand: [{ required: true, message: "商品品牌不能为空", trigger: "blur" }],
  stockQuantity: [{ required: true, message: "库存数量不能为空", trigger: "blur" }],
  filePath: [{ required: true, message: "商品展示视频不能为空", trigger: "blur" }],
  imageUrl: [{ required: true, message: "商品图片不能为空", trigger: "blur" }],
});

//初始化
const init = (id) => {
  if (id) {
    request.get(`products/info/` + id).then(({ data }) => {
      formData.value = data;
    });
  }
};

//富文本编辑器文本变化
const editorChange = (field, $event) => {
  formData.value[field] = $event;
};
//商品图片上传
const pictureUploadChange = (field, $event) => {
  formData.value[field] = $event;
};
// 提交表单
const handleSubmit = async () => {
  await formRef?.value.validate();

  const api = formData.value.id ? "products/update" : "products/save";

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
