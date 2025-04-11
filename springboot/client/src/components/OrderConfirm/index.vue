<template>
  <!-- 订单确定，模态框 -->
  <el-dialog
      :model-value="dialogVisible"
      title="订单确认"
      width="1000"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      @close="dialogVisible = false"
  >

    <div class="title-container">
      <h1 class="order-title">订单确认</h1>
    </div>
    <el-descriptions title="选择收货地址"></el-descriptions>
    <el-table
        :data="addressList"
        style="width: 100%;padding: 0 10px;">
      <el-table-column
          label="选择"
          width="55">
        <template  #default="scope">
          <el-radio
              v-model="radio"
              :value="scope.$index"
              >
          </el-radio>
        </template>
      </el-table-column>
      <el-table-column
          label="收件人"
          prop="name">
      </el-table-column>
      <el-table-column
          label="联系方式"
          prop="phone">
      </el-table-column>
      <el-table-column
          label="地址"
          prop="address">
      </el-table-column>
    </el-table>

    <el-descriptions title="清单列表" style="margin-top: 20px"></el-descriptions>
    <el-table
        :data="dataList"
        style="width: 100%">
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
          <span>{{ scope.row.buyNumber }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="总价">
        <template  #default="scope">
          <span><span :style='{"fontSize":"12px"}'>￥</span>{{ scope.row.totalPrice }}</span>
        </template>
      </el-table-column>
    </el-table>
    <div style="width: 100%;padding: 12px;box-sizing: border-box;display: flex;align-items: center;">
      <span style="width: 80px; font-size: 14px;">备注：</span><el-input type="textarea" v-model="remark" placeholder="备注" style="width: calc(100% - 80px);"></el-input>
    </div>
    <div style="display: flex;justify-content: right;align-items: center;">
        <span style="color: red;margin-right: 10px;font-weight: 700;">总价：<span :style='{"fontSize":"12px"}'>￥</span>{{totalPrice}}</span>
        <el-button style="width: 80px" type="success" @click="payClick">支 付</el-button>
    </div>


    <!-- 支付方式选择模态框 -->
    <el-dialog v-model="dialogPayVisible" title="选择支付方式" width="30%" :before-close="handleClose">
      <div class="payment-methods">
        <el-radio-group v-model="selectedPaymentMethod">
          <el-radio value="0">微信支付</el-radio>
          <el-radio value="1">支付宝支付</el-radio>
        </el-radio-group>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleClose">取消</el-button>
          <el-button type="primary" @click="confirmPayment">确认支付</el-button>
        </span>
      </template>
    </el-dialog>

  </el-dialog>
</template>

<script setup>
import { ref, reactive } from 'vue';
import request from '@/utils/request';
import {ElMessage} from "element-plus";

// 对话框是否展示
const dialogVisible = ref(false)
const dialogPayVisible = ref(false)
const dataList = ref([])
const addressList = ref([])
const radio = ref(null)
const remark = ref("")
const totalPrice = ref(0)
const emit = defineEmits(['paySuccess'])
const ids = ref([]);// 购物车，要删除的ids


// 选中的支付方式
const selectedPaymentMethod = ref('');

// 入口 model = []
const openDialog = (list,isCart) => {
  ids.value = isCart ? list.map(itme => itme.id) : [];
  dialogVisible.value = true;
  dataList.value = list;
  if (dataList.value.length > 0) {
    totalPrice.value = dataList.value.map(item => item.totalPrice).reduce((acc, cur) => {
      acc += cur;
      return acc;
    })
  }

  // 查询获取收货人信息
  request.get("/address/queryList").then(({ data }) => {
    addressList.value = data;
    if (addressList.value.length > 0) {
      addressList.value.forEach((item,index) => {
        if (item.isDefault === 1) radio.value = index
      })
    }
  });
};

const payClick = () => {
  if (radio.value === null){
    ElMessage.warning('请选择收件地址');
    return;
  }
  // 支付
  dialogPayVisible.value = true;
}

// 关闭模态框时的操作
const handleClose = () => {
  selectedPaymentMethod.value = '';
  dialogPayVisible.value = false;
};
// 确认支付
const confirmPayment = () => {
  if (!selectedPaymentMethod.value) {
    ElMessage.warning('请选择支付方式');
    return;
  }
  if (radio.value === null){
    ElMessage.warning('未选择收件地址');
  }

  let address = {};
  addressList.value.forEach((item,index) => {
    if (index === radio.value){
      address = item;
    }
  })

  const form = {
    productOrderList: dataList.value,
    orderType: 0,//订单
    orderStatus: 1,//已支付
    paymentMethod: selectedPaymentMethod.value,
    address: address.address,
    phone: address.phone,
    consignee: address.name,
    remark: remark.value,
    ids: ids.value,
  };
  // 检查余额是否足够

  request.post('/productOrder/buyProduct', form).then(({ data }) => {
    if (data.code === 200) {
      dialogPayVisible.value = false;
      dialogVisible.value = false;
      ElMessage.success('支付成功！');
      emit('paySuccess');
    }else {
      ElMessage.warning(data.message);
    }
  }).catch(error => {
    ElMessage.error('支付失败，请重试！');
  });
};

defineExpose({
  openDialog,
})
</script>

<style lang="scss" scoped>

.title-container {
  padding: 20px;
  background-color: #909399;
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

.interaction-buttons {
  display: flex;
  justify-content: center;
  margin-top: 20px;

  .el-button {
    margin-left: 5px;
    display: flex;
    align-items: center;
    font-size: 16px;
    background-color: #909399;
    border-color: #909399;

    img {
      margin-right: 8px;
    }

    span {
      color: white;
      font-size: 12px;
    }
  }

  .payment-methods {
    .el-radio-group {

      .el-radio {
        padding: 10px;
        border-radius: 4px;
        transition: background-color 0.3s ease;

        &:hover {
          background-color: #f0f2f5;
        }
      }
    }
  }

  .dialog-footer {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
  }
}
</style>
