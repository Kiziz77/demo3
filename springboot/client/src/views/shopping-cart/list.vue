<template>
  <div class="module-showcase">
    <!-- 标题部分 -->
    <div class="header">
      <h1 class="title">购物车</h1>
    </div>

    <el-table
        v-if="dataList.length > 0"
        :data="dataList"
        @selection-change="handleSelectionChange"
        style="width: 100%">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column
          label="药品图片">
        <template  #default="scope">
          <div class="shangpin">
            <el-image
                style="width: 80px; height: 80px;border-radius: 5px"
                :src="scope.row.pictures"
                fit="fill"></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="药品名称" prop="title">
      </el-table-column>
      <el-table-column
          label="价格">
        <template  #default="scope">
          <span><span :style='{"fontSize":"12px"}'>￥</span>{{ scope.row.price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="数量">
        <template  #default="scope">
          <el-input-number v-model="scope.row.buyNumber" :min="1" @change="handleNumber(scope.$index,scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column
          label="总价">
        <template  #default="scope">
          <span><span :style='{"fontSize":"12px"}'>￥</span>{{ scope.row.totalPrice }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template  #default="scope">
          <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row.id)">移出购物车</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-else>
      <el-empty description="购物车是空的，快去看看感兴趣的吧~"></el-empty>
    </div>


    <div style="margin-top: 20px;display: flex;justify-content: right;align-items: center;">
      <span style="color: red;margin-right: 10px;font-weight: 700;">总价：<span :style='{"fontSize":"12px"}'>￥</span>{{totalPrice}}</span>
      <el-button type="warning" @click="buyClick">点击购买</el-button>
    </div>

    <!--  订单确认页面-->

    <order-confirm
        ref="orderConfirmRef"
        @paySuccess="paySuccess"
    ></order-confirm>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import request from "@/utils/request.js";
import { useRouter } from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
const router = useRouter();
const dataList = ref([]);
const totalPrice = ref(0);
const payDataList = ref(0);

const orderConfirmRef = ref()

const queryList = () => {
  request.get('/productOrder/queryShoppingCart').then(({ data }) => {
    dataList.value = data
  })
}
const paySuccess = () => {
  queryList();
  totalPrice.value = 0
}
// 要支付的价格
const handleSelectionChange = (val) => {
  payDataList.value = val;
  countTotal();
}

// 计算总金额
const countTotal = () => {
  if (payDataList.value.length > 0) {
    totalPrice.value = payDataList.value.map(item => item.totalPrice).reduce((acc, cur) => {
      acc += cur;
      return acc;
    }).toFixed(2);
  }else {
    totalPrice.value = 0;
  }
}

// 开始购买
const buyClick = () => {
  if (payDataList.value.length > 0) {
    //打开确认订单页面
    orderConfirmRef.value.openDialog(payDataList.value,true);
  }else {
    ElMessage.warning("购物车中没有药品，请先添加药品到购物车中")
  }
}
// 编辑购物车数量
const handleNumber = (index,model) => {
  request.put('/productOrder/editShoppingCart',model).then(({ data }) => {
    dataList.value[index].totalPrice = Number((model.price * model.buyNumber).toFixed(2));
    countTotal();
  })
}
const handleDelete = (id) => {
  ElMessageBox.confirm(
      '你确定要移除购物车吗?',
      '注意',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    request.delete("/productOrder/deletedGoods/" + id).then(({ data }) => {
      ElMessage.success("移出成功");
      queryList();
    });
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '已取消操作',
    })
  })
}

onMounted(() => {
  queryList();
});
</script>

<style lang="scss" scoped>
.module-showcase {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;

  .header {
    text-align: center;
    margin-bottom: 40px;
    padding: 20px;
    background: linear-gradient(135deg, #909399, #79bbff);
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);

    .title {
      font-size: 2.5rem;
      color: #fff;
      margin: 0;
      font-weight: bold;
      text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
    }
  }

  .search-area {
    display: flex;
    gap: 20px;
    margin-bottom: 30px;

    .search-input {
      flex: 1;
      max-width: 400px;
    }

    .category-select {
      width: 200px;
    }
  }

  .module-container {
    .module-card {
      margin-bottom: 20px;
      transition: transform 0.3s ease;

      &:hover {
        cursor: pointer;
        transform: translateY(-5px);
      }

      .card-image {
        height: 200px;
        overflow: hidden;
        border-radius: 4px;

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
          transition: transform 0.3s ease;

          &:hover {
            transform: scale(1.05);
          }
        }
      }

      .card-content {
        padding: 15px;

        .module-title {
          font-size: 1.1rem;
          color: #2d3a4b;
          margin: 0 0 10px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }

        .module-info {
          .meta {
            margin-top: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            color: #909399;
            font-size: 0.85rem;

            .favorites {
              display: flex;
              align-items: center;
              gap: 4px;

              .el-icon {
                color: #e6a23c;
              }
            }
          }
        }
      }
    }
  }

  .pagination-wrapper {
    margin-top: 20px;
    text-align: center;
    .el-pagination {
      justify-content: center;
    }
  }
}
</style>
