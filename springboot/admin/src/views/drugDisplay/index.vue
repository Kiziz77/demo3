<template>
  <div class="module-container">
    <div v-if="showIndexFlag">
      <!-- 查询条件 -->
      <div class="filter-container">
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
                <el-table-column prop="drugId" label="药品ID" header-align="center" align="center">
                  <template #default="{ row }" >
                    {{row.t2Drugname}}
                  </template>
                </el-table-column>
            <el-table-column prop="drugImage" label="药品图片" header-align="center" align="center">
              <template #default="{ row }">
                <el-image v-if="row.drugImage" style="width: 100px; height: 100px" :src="row.drugImage.split(',')[0]" fit="cover" />
                <span v-else>无图片</span>
              </template>
            </el-table-column>
              <el-table-column prop="displayStatus" label="展示状态" header-align="center" align="center"></el-table-column>
                <el-table-column prop="unitPrice" label="单价" header-align="center" align="center">
                  <template #default="{ row }" >
                    {{row.t3Unitprice}}
                  </template>
                </el-table-column>
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
//默认展示表格页面
const showIndexFlag = ref(true);
// 查询参数
const params = ref({
  page: 1,
  limit: 5,
  title: "",
});

// 获取数据列表
const getDataList = () => {
  request.post("drugDisplay/page", params.value).then(({ data }) => {
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
    ElMessage.warning("请选择要删除的信息");
    return;
  }
  ElMessageBox.confirm("确定要删除选中的信息吗?", "提示", {
    type: "warning",
  })
    .then(() => {
      const ids = multipleSelection.value.map((item) => Number(item.id));
      console.log("🚀 ~ .then ~ ids:", ids);
      request.post("drugDisplay/del/batch", ids).then(() => {
        ElMessage.success("删除成功");
        getDataList();
      });
    })
    .catch(() => {});
};

// 单个删除
const handleDelete = (row) => {
  ElMessageBox.confirm(`确定要删除该信息吗?`, "提示", {
    type: "warning",
  })
    .then(() => {
      request.delete(`drugDisplay/del/` + row.id).then(() => {
        ElMessage.success("删除成功");
        getDataList();
      });
    })
    .catch(() => {});
};

const addOrUpdateFlag = ref(false);
const addOrUpdateRef = ref();

//新增
const handleAdd = () => {
  showIndexFlag.value = false;
  addOrUpdateFlag.value = true;
  addOrUpdateRef.value?.init();
};
//编辑
const handleEdit = (row) => {
  showIndexFlag.value = false;
  addOrUpdateFlag.value = true;
  console.log(addOrUpdateRef.value);

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
