<template>
  <div>
    <div class="title-container">
      <h1 class="order-title">我的订单</h1>
    </div>
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <!--      <el-tab-pane label="未支付" :name="0"></el-tab-pane>-->
      <el-tab-pane label="已支付" :name="1"></el-tab-pane>
      <el-tab-pane label="已发货" :name="2"></el-tab-pane>
      <el-tab-pane label="已完成" :name="3"></el-tab-pane>
      <el-tab-pane label="已退款" :name="4"></el-tab-pane>
      <!--      <el-tab-pane label="已取消" :name="5"></el-tab-pane>-->
    </el-tabs>

    <!-- 订单 -->
    <div v-if="dataList.length > 0" class="module-container">
      <el-row :gutter="20">
        <el-col v-for="item in dataList" :key="item.id" :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="module-card" shadow="hover" >
            <div class="card-content-wrapper">
              <div class="card-image">
                <img :src="item.pictures?.split(',')[0]" :alt="item.title" />
              </div>
              <div class="card-info">
                <h3 class="module-title">{{ item.title }}</h3>
                <div class="order-info">
                  <div>
                    <p class="info-item">订单编号：{{ item.orderNumber }}</p>
                    <p class="info-item">支付状态：{{ orderStatus[item.orderStatus].label }}</p>
                    <p class="info-item">电话：{{ item.phone }}</p>
                  </div>
                  <div class="li">
                    <p class="info-item">数量：{{ item.buyNumber }}</p>
                    <p class="info-item">收货人：{{ item.consignee }}</p>
                    <p class="info-item">地址：{{ item.address }}</p>
                  </div>
                  <div class="li">
                    <p class="info-item">单价：<span class="amount">{{ item.price }} 元</span></p>
                    <p class="info-item">支付时间：{{ item.buyDatetime }}</p>
                    <p class="info-item">备注：{{ item.remark }}</p>
                  </div>
                  <div class="li">
                    <p class="info-item">支付金额：<span class="amount">{{ item.totalPrice }} 元</span></p>
                    <p class="info-item">支付方式：
                      <el-tag v-if="item.paymentMethod === 0" type="success">微信</el-tag>
                      <el-tag v-if="item.paymentMethod === 1" >支付宝</el-tag>
                    </p>
                  </div>
                </div>
              </div>
            </div>
            <!--            操作按钮-->
            <div style="display: flex;justify-content: right">
              <el-button v-if="item.orderStatus === 1" size="small" type="danger" @click="handleOrderOperate(item.id,0)">退款</el-button>
              <el-button v-if="item.orderStatus === 2" size="small" type="danger" @click="handleOrderOperate(item.id,1)">确认收货</el-button>
              <el-button v-if="item.orderStatus === 3" size="small" type="danger" @click="handleOrderOperate(item.id,2)">退货</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div v-else>
      <el-empty description="你还没有订单哦~" />
    </div>

    <!-- 分页部分 -->
    <div class="pagination-wrapper">
      <el-pagination
          v-model:current-page="queryParams.page"
          v-model:page-size="queryParams.limit"
          :total="total"
          layout="total,  prev, pager, next, jumper"
          background
          @current-change="handleCurrentChange"
      />
    </div>
  </div>

</template>

<script setup>


// 定义 props，用于控制每行显示的列数
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";


const router = useRouter();
// 收藏列表
const dataList = ref([]);

const total = ref(0);
const activeName = ref(1);
const queryParams = ref({
  page: 1,
  limit: 5,
})

const orderStatus = [
  {label: '未支付', value: 0},
  {label: '已支付', value: 1},
  {label: '已发货', value: 2},
  {label: '已完成', value: 3},
  {label: '已退款', value: 4},
  {label: '已取消', value: 5},
]

// 跳转详情页面
const handleModuleClick = (fullPath) => {
  router.push(fullPath);
};

// 标签页方法
const handleClick = () => {
  queryDataLike();
}

// 当前页码改变
const handleCurrentChange = (page) => {
  queryParams.page = page;
  queryDataLike();
};
// 订单操作按钮
const handleOrderOperate = (id,index) => {
  ElMessageBox.confirm(
      `你确定要 ${index === 0 ? '退款' : index === 1 ? '收货' : '退货'} 吗?`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    let params = {
      id: id,
      statusIndex: index,
    }
    request.post("/productOrder/orderOperation",params).then(({ data }) => {
      if (index === 0){
        ElMessage.success("退款成功！");
      }else if (index === 1){
        ElMessage.success("确认收货成功！");
      }else if(index === 2){
        ElMessage.success("退货成功！");
      }
      queryDataLike();
    });
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '已取消操作',
    })
  })

};

// 获取订单列表
const queryDataLike = () => {
  let params = {
    page: queryParams.value.page,
    limit: queryParams.value.limit,
    orderStatus: activeName.value,
  }
  request.post("/productOrder/queryMyOrderPage",params).then(({ data }) => {
    dataList.value = data.list;
    total.value = data.total;
  });
};

onMounted(() => {
  queryDataLike();
});
</script>

<style lang="scss" scoped>
// 标题样式
.title-container {
  padding: 20px;
  background-color: #f5f7fa;
  border-bottom: 1px solid #ebeef5;

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

.module-container {
  .module-card {
    margin-bottom: 20px;
    transition: transform 0.3s ease;

    &:hover {
      cursor: pointer;
      transform: translateY(-5px);
    }

    .card-content-wrapper {
      display: flex;
      align-items: flex-start; // 垂直顶部对齐
      gap: 20px; // 图片和内容之间的间距
    }

    .card-image {
      width: 120px; // 设置图片宽度
      height: 120px; // 设置图片高度
      overflow: hidden;
      border-radius: 8px;

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

    .card-info {
      flex: 1; // 让内容部分占据剩余空间
      display: flex;
      flex-direction: column;
      justify-content: space-between;

      .module-title {
        font-size: 1.5rem; // 增大标题字体大小
        color: #2d3a4b;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .order-info {
        font-size: 15px;
        color: #606266;
        display: flex;

        .li {
          margin: 0 35px;
        }

        .info-item {
          margin: 15px 0;
        }

        .amount {
          font-weight: 700;
          color: red;
        }
      }
    }
  }
}

// 确保在不同屏幕尺寸下每行显示一张卡片
@media (min-width: 992px) {
  .module-container .el-col-lg-24 {
    width: calc(100% - 40px); // 减去一些间距以适应容器的padding和margin
  }
}

@media (min-width: 768px) and (max-width: 991px) {
  .module-container .el-col-md-24 {
    width: calc(100% - 40px); // 减去一些间距以适应容器的padding和margin
  }
}

@media (max-width: 767px) {
  .module-container .el-col-sm-24 {
    width: calc(100% - 40px); // 减去一些间距以适应容器的padding和margin
  }
}
</style>