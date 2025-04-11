<template>
  <div class="theme-detail-container">
    <el-button type="primary" @click="goBack" class="back-button">
      <el-icon><ArrowLeft /></el-icon>
      返回列表
    </el-button>

    <!-- 图片轮播 -->
    <div style="display: flex;">
      <div style="width: 45%;">
        <img :src="detailData.imageUrl" class="carousel-image" alt="详情图片" />
      </div>
      <div style="width: 55%;">
        <div class="info-section">
          <div class="info-item">
            <span class="label">药品名称:</span>
            <span class="value">{{ detailData.productsName }}</span>
          </div>
          <div class="info-item">
            <span class="label">上架时间:</span>
            <span class="value">{{ detailData.createTime }}</span>
          </div>
          <div class="info-item">
            <span class="label">药品品牌:</span>
            <span class="value">{{ detailData.brand }}</span>
          </div>
          <div class="info-item">
            <span class="label">库存:</span>
            <span class="value">{{ detailData.stockQuantity }}</span>
          </div>
          <div class="info-item">
            <span class="label">价格:</span>
            <span class="value" style="color: red; font-weight: 700">{{ detailData.price }} 元</span>
          </div>
        </div>
        <div style="margin-left: 10px">
          <!-- 购买 -->
          <order-buy
              :momentId="Number(route.query.id)"
              :signName="'products'"
              :fullPath="route.fullPath"
              :pictures="detailData.imageUrl"
              :title="detailData.productsName"
              :price="Number(detailData.price)"
          ></order-buy>
        </div>
        <div>
          <like-collect
              :momentId="Number(route.query.id)"
              :signName="'products'"
              :fullPath="route.fullPath"
              :fieldNames="fieldNames"
          ></like-collect>
        </div>
      </div>
    </div>

    <!-- Tab切换 -->
    <el-tabs v-model="activeTab">
      <el-tab-pane label="药品描述" name="detail">
        <div v-html="detailData.description"></div>
      </el-tab-pane>
      <el-tab-pane label="评论" name="comments">
        <Comment :momentId="Number(route.query.id)" :commentType="33" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import request from "@/utils/request";
import {useRoute, useRouter} from "vue-router";
import Comment from "@/views/comment/index.vue";

const router = useRouter();
const route = useRoute();
const detailData = ref({});
// 定义字段名
const fieldNames = ['image_url', 'products_name'];
// Tab状态
const activeTab = ref("detail");

const loadDetailData = () => {
  request.get("/products/info/" + route.query.id).then(({data}) => {
    detailData.value = data;
  });
};

// 返回上一页
const goBack = () => {
  router.go(-1);
};

onMounted(() => {
  loadDetailData();
});
</script>

<style lang="scss" scoped>
.theme-detail-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px 0 0 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.info-section {
  padding: 25px;
  background: #ffffff;
  border-radius: 0 12px 12px 0;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);

  .info-item {
    display: flex;
    margin-bottom: 20px;
    font-size: 18px;
    line-height: 1.6;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;

    .label {
      width: 150px;
      font-weight: 600;
      color: #555;
      text-align: right;
      margin-right: 30px;
    }

    .value {
      font-weight: 400;
      color: #333;
    }
  }

  .info-item:last-child {
    border-bottom: none;
  }
}

.el-tabs__header {
  border-bottom: 2px solid #f2f2f2;
  padding: 10px 0;
}

.el-tab-pane {
  padding: 25px;
  background: #f4f8fc;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.el-tabs__nav-wrap {
  justify-content: center;
  margin-bottom: 20px;
}

.el-tab-bar__item {
  font-size: 18px;
  font-weight: 500;
  color: #666;
  padding: 12px 24px;
  transition: color 0.3s ease;

  &:hover {
    color: #909399;
  }
}

.el-tab-bar__item.is-active {
  color: #909399;
  font-weight: 600;
  border-bottom: 3px solid #909399;
}
.back-button {
  margin-bottom: 30px;
  padding: 10px 25px;
  font-size: 0.95rem;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;

  &:hover {
    transform: translateX(-5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }
}
</style>