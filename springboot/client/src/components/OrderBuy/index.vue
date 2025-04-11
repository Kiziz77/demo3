<template>
  <!-- 订单购买 -->
  <div class="interaction-buttons">
    <el-input-number v-model="buyNumber" :min="1" />
    <el-button @click="handelShoppingCart" type="primary">
      <img style="width: 16px; margin-right: 5px" src="/src/assets/svg/shopingCart.svg" alt="购买图标">
      <span>加入购物车</span>
    </el-button>
    <el-button @click="handelBuy" type="primary">
      <img style="width: 16px; margin-right: 5px" src="/src/assets/svg/buy.svg" alt="购买图标">
      <span>立即购买</span>
    </el-button>

    <!--  订单确认页面-->

    <order-confirm
        ref="orderConfirmRef"
    ></order-confirm>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import request from '@/utils/request';

// 定义 props
const props = defineProps({
  momentId: {
    type: Number,
    required: true,
  },
  signName: {
    type: String,
    default: '',
  },
  fullPath: {
    type: String,
    default: '',
  },
  pictures: {
    type: String,
    default: '',
  },
  title: {
    type: String,
    default: '',
  },
  //单价
  price: {
    type: Number,
    default: 0,
  },
});

// 购买数量
const buyNumber = ref(1)

// 控制模态框的显示与隐藏
const dialogVisible = ref(false);

const orderConfirmRef = ref()

// 打开模态框并准备购买数据
const handelBuy = () => {
  let model = {
    momentId: props.momentId,
    signName: props.signName,
    fullPath: props.fullPath,
    pictures: props.pictures,
    title: props.title,
    price: props.price,
    buyNumber: buyNumber.value,
    totalPrice: (buyNumber.value * props.price).toFixed(2),
  }

  let modelList = [];
  modelList.push(model);
  //打开确认订单页面
  orderConfirmRef.value.openDialog(modelList, false)

};


// 加入购物车
const handelShoppingCart = () => {
  let form = {
    momentId: props.momentId,
    signName: props.signName,
    fullPath: props.fullPath,
    pictures: props.pictures,
    title: props.title,
    price: props.price,
    buyNumber: Number(buyNumber.value),
    totalPrice: buyNumber.value * props.price,
  }

  request.post('/productOrder/addShoppingCart', form).then(({data}) => {
    if (data.code === 200) {
      ElMessage.success(data.message);
    }else {
      ElMessage.warning(data.message);
    }
  })

};


defineExpose({
  handelBuy,
})

</script>

<style lang="scss" scoped>
.interaction-buttons {
  display: flex;
  margin-top: 20px;
    justify-content: center;
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
