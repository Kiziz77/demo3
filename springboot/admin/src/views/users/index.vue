<template>
  <div class="module-container">
    <div v-if="showIndexFlag">
      <!-- 查询条件 -->
      <div class="filter-container">
        <el-input v-model="params.userName" placeholder="请输入用户名" class="filter-item" clearable />
        <el-button type="primary" @click="handleSearch">查询</el-button>
      </div>

      <!-- 操作按钮 -->
      <div class="operation-container">
        <el-button class="operation-btn" type="primary" @click="handleAdd"> 新增 </el-button>
        <el-button class="operation-btn" type="primary" :disabled="multipleSelection.length === 0" @click="handleBatchDelete"> 批量删除 </el-button>
      </div>

      <!-- 表格 -->
      <el-table :data="dataList" style="width: 100%" @selection-change="handleSelectionChange" border stripe>
        <el-table-column type="selection" width="55" header-align="center" align="center" />
        <el-table-column type="index" label="序号" width="65" header-align="center" align="center" />
        <el-table-column prop="userName" label="用户名" header-align="center" align="center" />
        <el-table-column prop="avatar" label="头像" header-align="center" align="center">
          <template #default="{ row }">
            <el-image v-if="row.avatar" style="width: 100px; height: 100px" :src="row.avatar" fit="cover" />
            <span v-else>无图片</span>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" header-align="center" align="center" />
        <el-table-column prop="phone" label="电话" header-align="center" align="center" />
        <el-table-column prop="createTime" label="创建时间" header-align="center" align="center" />
        <el-table-column prop="updateTime" label="修改时间" header-align="center" align="center" />
        <el-table-column label="操作" header-align="center" align="center">
          <template #default="{ row }">
            <el-button class="operation-btn" type="primary" @click="handleDelete(row)">删除</el-button>
            <el-button class="operation-btn" type="primary" @click="handleEdit(row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="params.page"
        v-model:page-size="params.limit"
        :total="total"
        :page-sizes="[5, 10, 15, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="getDataList"
        @current-change="getDataList"
      />
    </div>

    <!-- 新增或编辑 -->
    <add-or-update v-if="addOrUpdateFlag" ref="addOrUpdateRef" @handleSuccess="handleSuccess"></add-or-update>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import request from "@/utils/request";
import { ElMessage, ElMessageBox } from "element-plus";
import AddOrUpdate from "./add-or-update.vue";

// 数据列表
const dataList = ref([]);
const total = ref(0);
// 多选数据
const multipleSelection = ref([]);
// 默认展示表格页面
const showIndexFlag = ref(true);
// 查询参数
const params = ref({
  page: 1,
  limit: 5,
  userName: "", // 查询条件改为用户名
});

// 获取数据列表
const getDataList = () => {
  request.post("users/page", params.value).then(({ data }) => {
    dataList.value = data.list;
    total.value = data.total;
  });
};

// 查询
const handleSearch = () => {
  params.value.page = 1;
  getDataList();
};

// 多选
const handleSelectionChange = (selection) => {
  multipleSelection.value = selection;
};

// 批量删除
const handleBatchDelete = () => {
  if (multipleSelection.value.length === 0) {
    ElMessage.warning("请选择要删除的用户");
    return;
  }
  ElMessageBox.confirm("确定要删除选中的用户吗?", "提示", {
    type: "warning",
  })
    .then(() => {
      const ids = multipleSelection.value.map((item) => Number(item.id));
      request.post("users/del/batch", ids).then(() => {
        ElMessage.success("删除成功");
        getDataList();
      });
    })
    .catch(() => {});
};

// 单个删除
const handleDelete = (row) => {
  ElMessageBox.confirm(`确定要删除用户 ${row.userName} 吗?`, "提示", {
    type: "warning",
  })
    .then(() => {
      request.delete(`users/del/${row.id}`).then(() => {
        ElMessage.success("删除成功");
        getDataList();
      });
    })
    .catch(() => {});
};

const addOrUpdateFlag = ref(false);
const addOrUpdateRef = ref();

// 新增
const handleAdd = () => {
  showIndexFlag.value = false;
  addOrUpdateFlag.value = true;
  addOrUpdateRef.value?.init();
};

// 编辑
const handleEdit = (row) => {
  showIndexFlag.value = false;
  addOrUpdateFlag.value = true;
  nextTick(() => {
    addOrUpdateRef.value?.init(row.id);
  });
};

// 新增或编辑成功
const handleSuccess = () => {
  addOrUpdateFlag.value = false;
  showIndexFlag.value = true;
  getDataList();
};

// 初始化加载数据
onMounted(() => {
  getDataList();
});
</script>

<style lang="scss" scoped>
.filter-container {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  .filter-item {
    width: 200px;
    margin-right: 10px;
  }
}

.operation-container {
  margin-bottom: 20px;
}

.operation-btn {
  margin: 5px;
}
</style>
