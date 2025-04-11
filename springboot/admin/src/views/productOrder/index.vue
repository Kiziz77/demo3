<template>
  <div class="module-container">
    <div v-if="showIndexFlag">
      <!-- 查询条件 -->
      <div class="filter-container">
        <el-input v-model="params.orderNumber" placeholder="请输入商品编号" class="filter-item" clearable />
        <el-input v-model="params.title" placeholder="请输入商品名称" class="filter-item" clearable />
        <el-button type="primary" @click="handleSearch">查询</el-button>
      </div>

      <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
<!--        <el-tab-pane label="未支付" :name="0"></el-tab-pane>-->
        <el-tab-pane label="已支付" :name="1"></el-tab-pane>
        <el-tab-pane label="已发货" :name="2"></el-tab-pane>
        <el-tab-pane label="已完成" :name="3"></el-tab-pane>
        <el-tab-pane label="已退款" :name="4"></el-tab-pane>
<!--        <el-tab-pane label="已取消" :name="5"></el-tab-pane>-->
      </el-tabs>

      <!-- 表格 -->
      <el-table :show-overflow-tooltip="true" :data="dataList" style="width: 100%" border stripe>
        <el-table-column prop="orderNumber" label="订单编号" width="130" header-align="center" align="center" />
        <el-table-column prop="phone" label="商品图片" header-align="center" align="center" width="130">
          <template #default="{ row }">
            <el-image v-if="row.pictures" style="width: 100px; height: 100px" :src="row.pictures" fit="cover" />
            <span v-else>无图片</span>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="商品名称" width="130" header-align="center" align="center"/>
        <el-table-column prop="orderStatus" label="支付状态" header-align="center" align="center" width="100px" >
          <template #default="{ row }">
            <span>{{ orderStatus[row.orderStatus].label }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="支付方式" header-align="center" align="center" width="90px">
          <template #default="{ row }">
            <el-tag v-if="row.paymentMethod === 0" type="success">微信</el-tag>
            <el-tag v-if="row.paymentMethod === 1" >支付宝</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="单价" header-align="center" align="center" width="80"/>
        <el-table-column prop="buyNumber" label="数量" header-align="center" align="center" width="80" />
        <el-table-column prop="totalPrice" label="支付金额" header-align="center" align="center" width="100" />
        <el-table-column prop="buyDatetime" label="支付时间" header-align="center" align="center" />
        <el-table-column prop="consignee" label="收货人" header-align="center" align="center" />
        <el-table-column prop="phone" label="电话" header-align="center" align="center" />
        <el-table-column prop="address" label="地址" header-align="center" align="center" />
        <el-table-column prop="remark" label="备注" header-align="center" align="center" />
        <el-table-column label="操作" header-align="center" align="center" width="150">
          <template #default="{ row }">
            <el-button v-if="row.orderStatus === 1" class="operation-btn" type="primary" size="small" @click="handleEmit(row)">发货</el-button>
            <el-button class="operation-btn" type="danger" size="small" @click="handleDelete(row)">删除</el-button>
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

  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import request from "@/utils/request";
import { ElMessage, ElMessageBox } from "element-plus";

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
});
const activeName = ref(1);
const orderStatus = [
  {label: '未支付', value: 0},
  {label: '已支付', value: 1},
  {label: '已发货', value: 2},
  {label: '已完成', value: 3},
  {label: '已退款', value: 4},
  {label: '已取消', value: 5},
]

// 获取数据列表
const getDataList = () => {
  let param = {
    page: params.value.page,
    limit: params.value.limit,
    orderNumber: params.value.orderNumber,
    title: params.value.title,
    orderStatus: activeName.value,
  }

  request.post("productOrder/queryMyOrderPage", param).then(({ data }) => {
    dataList.value = data.list;
    total.value = data.total;
  });
};

// 导航栏方法
const handleClick = (val) => {
  getDataList();
}

// 查询
const handleSearch = () => {
  params.value.page = 1;
  getDataList();
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
  ElMessageBox.confirm(`确定要删除订单 ${row.title} 吗?`, "提示", {
    type: "warning",
  })
    .then(() => {
      let params = {
        id: row.id,
        statusIndex: 4,
      }
      request.post("/productOrder/orderOperation",params).then(({ data }) => {
        ElMessage.success("删除成功！");
        getDataList();
      });
    })
    .catch(() => {});
};

// 发货
const handleEmit = (row) => {
  let params = {
    id: row.id,
    statusIndex: 3,
  }
  request.post("/productOrder/orderOperation",params).then(({ data }) => {
    ElMessage.success("发货成功！");
    getDataList();
  });
};

const addOrUpdateFlag = ref(false);
const addOrUpdateRef = ref();

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
