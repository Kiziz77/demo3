<template>
  <!-- 收获地址 -->
  <div >
    <div class="title-container">
      <h1 class="order-title">收货地址</h1>
    </div>

    <el-button type="primary" @click="toAddAddr"><el-icon style="margin-right: 8px"><Plus /></el-icon>添加新地址</el-button>
    <div v-if="tableData.length>0">
      <el-table
          :data="tableData"
          style="width: 100%">
        <el-table-column
            label="联系人"
            prop="name"
            width="100">
        </el-table-column>
        <el-table-column
            label="手机号"
            prop="phone"
            width="120">
        </el-table-column>
        <el-table-column
            label="地址"
            prop="address">
        </el-table-column>
        <el-table-column
            label="默认地址"
            prop="isDefault"
            width="80">
          <template #default="scope">
            <el-tag :type="scope.row.isDefault === 0 ? 'primary' : 'success'">{{ scope.row.isDefault === 0 ? '否' : '是' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" @click="deletedById(scope.row.id)">删除</el-button>

          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-else>
      <el-empty description="你还没有设置收货地址哦~" />
    </div>

<!--    新增收货地址-->
    <el-dialog
        :model-value="dialogVisible"
        :title="title"
        width="600px"
        :close-on-press-escape="false"
        :close-on-click-modal="false"
        @close="cancel"
    >

      <el-form class="form-wrapper" ref="formRef" label-width="80" :model="form" :rules="rules" >
        <el-form-item  label="联系人" prop="name">
          <el-input v-model="form.name" placeholder="联系人"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="form.phone" placeholder="手机号码"></el-input>
        </el-form-item>
        <el-form-item label="默认地址" prop="isDefault">
          <el-radio-group v-model="form.isDefault">
            <el-radio :value="1" size="large">是</el-radio>
            <el-radio :value="0" size="large">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input type="textarea" :rows="3" v-model="form.address" placeholder="请输入地址"></el-input>
        </el-form-item>
        <el-form-item >
          <el-button  type="primary" @click="onSubmit">添加</el-button>
          <el-button  @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script setup>
import {ref, reactive, onMounted} from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import request from '@/utils/request';

const dialogVisible = ref(false);
const formRef = ref(null);
const title = ref('');
const isEdit = ref(false);
const form = ref({
  phone: "",
  name: "",
  address: "",
  isDefault: null,
});
const rules = ref({
  name: [
    { required: true, message: '联系人不能为空', trigger: 'blur' },
  ],
  phone: [
    { required: true, message: '手机号码不能为空', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: "请输入正确的手机号", trigger: "blur" },
  ],
  isDefault: [
    { required: true, message: '请选择默认地址', trigger: 'blur' },
  ],
  address: [
    { required: true, message: '地址不能为空', trigger: 'blur' },
  ],
})

const tableData = ref([
  {
    phone: '',
    name: '',
    address: '',
    isDefault: 0
  },
])

const cancel = () => {
  formRef.value.resetFields();
  dialogVisible.value = false;
}

const toAddAddr = () => {
  title.value = '添加地址';
  isEdit.value = false;
  dialogVisible.value = true;
}

const handleEdit = (model) => {
  title.value = '编辑地址';
  isEdit.value = true;
  dialogVisible.value = true;
  //深拷贝
  form.value = { ...model };
}

const onSubmit = async () => {
  await formRef.value.validate((valid, fields) => {
    if (valid) {
      if (isEdit.value){
        // 编辑
        request.put("/address/edit",form.value).then(({ data }) => {
          ElMessage.success("编辑成功");
          cancel();
          queryDataList();
        });
      }else {
        // 添加
        request.post("/address/add",form.value).then(({ data }) => {
          ElMessage.success("添加成功");
          cancel();
          queryDataList();
        });
      }
    }
  })
}

const deletedById = (id) => {
  ElMessageBox.confirm(
      '你确定要删除吗?',
      '注意',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        request.delete("/address/deletedById/" + id).then(({ data }) => {
          ElMessage.success("删除成功");
          queryDataList();
        });
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '已取消删除',
        })
      })

}

const queryDataList = () => {
  request.get("/address/queryList").then(({ data }) => {
    tableData.value = data;
  });
}

onMounted(() => {
  queryDataList();
});

</script>

<style lang="scss" scoped>// 标题样式

.title-container {
  padding: 20px;
  background-color: #f5f7fa;
  border-bottom: 1px solid #ebeef5;
  margin-bottom: 20px;

  .order-title {
    font-size: 2rem; // 增大字体大小
    color: #303133;
    margin: 0;
    text-align: center;
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 2px;
  }
}
</style>
