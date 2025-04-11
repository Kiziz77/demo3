<template>
  <div class="detail-page">
    <!-- 返回按钮 -->
    <el-button type="primary" @click="goBack" class="back-button">
      <el-icon><ArrowLeft /></el-icon>
      返回列表
    </el-button>

    <!-- 详情内容 -->
    <div class="detail-content">
      <!-- 新增大标题 -->
      <div class="page-header">
        <h1 class="main-title">公告详情</h1>
        <div class="header-divider"></div>
      </div>

      <h2 class="detail-title">{{ detailData.title }}</h2>
      <div class="detail-meta">
        <el-tag type="info" class="time-tag">
          <el-icon><Clock /></el-icon>
          {{ detailData.createTime }}
        </el-tag>
      </div>
      <div class="detail-body" v-html="detailData.content"></div>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import request from "@/utils/request.js";
import Comment from "@/views/comment/index.vue";

const route = useRoute();
const router = useRouter();
const detailData = ref({});
// Tab状态
const activeTab = ref("comments");

const loadDataDetail = async () => {
  const { data } = await request.get(`/notices/info/${route.query.id}`);
  detailData.value = data;
};

// 定义字段名 [图片、标题]
const fieldNames = ['pictures', 'title'];

// 返回上一页
const goBack = () => {
  router.go(-1);
};

// 组件挂载时获取数据
onMounted(() => {
  loadDataDetail();
});
</script>

<style lang="scss" scoped>
.detail-page {
  max-width: 1000px;
  margin: 0 auto;
  padding: 30px 20px;

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

  .detail-content {
    background: #fff;
    padding: 40px;
    border-radius: 12px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.06);

    .page-header {
      text-align: center;
      margin-bottom: 40px;

      .main-title {
        font-size: 2.4rem;
        color: #2d3a4b;
        margin: 0 0 15px;
        font-weight: 600;
        letter-spacing: 2px;
      }

      .header-divider {
        width: 80px;
        height: 4px;
        background: linear-gradient(90deg, #909399, rgba(64, 158, 255, 0.3));
        margin: 0 auto 30px;
        border-radius: 2px;
      }
    }

    .detail-title {
      font-size: 1.8rem;
      color: #2d3a4b;
      text-align: center;
      margin: 0 0 25px;
      padding-bottom: 15px;
      border-bottom: 1px solid #eee;
    }

    .detail-meta {
      text-align: center;
      margin-bottom: 35px;

      .time-tag {
        padding: 8px 20px;
        font-size: 0.95rem;
        border-radius: 20px;

        .el-icon {
          margin-right: 8px;
          font-size: 1rem;
        }
      }
    }

    .detail-body {
      font-size: 1rem;
      line-height: 1.8;
      color: #4a5568;
      min-height: 400px;

      // 富文本内容样式增强
      :deep(h1),
      :deep(h2),
      :deep(h3) {
        text-align: center;
        margin: 35px 0 20px;
        position: relative;
        padding-bottom: 10px;

        &::after {
          content: "";
          position: absolute;
          bottom: 0;
          left: 50%;
          transform: translateX(-50%);
          width: 50px;
          height: 2px;
          background: #909399;
        }
      }

      :deep(h1) {
        font-size: 1.8rem;
      }

      :deep(h2) {
        font-size: 1.6rem;
      }

      :deep(h3) {
        font-size: 1.4rem;
      }

      :deep(p) {
        margin: 0 0 20px;
        text-align: justify;
      }

      :deep(ul),
      :deep(ol) {
        margin: 20px 0;
        padding-left: 30px;

        li {
          margin-bottom: 10px;
          padding-left: 10px;
          position: relative;

          &::before {
            content: "•";
            color: #909399;
            position: absolute;
            left: -15px;
            top: 1px;
          }
        }
      }

      :deep(a) {
        color: #909399;
        font-weight: 500;
        text-decoration: none;
        border-bottom: 1px dashed #909399;
        transition: all 0.2s ease;

        &:hover {
          color: #337ecc;
          border-bottom-style: solid;
        }
      }

      :deep(img) {
        max-width: 100%;
        height: auto;
        border-radius: 12px;
        margin: 25px 0;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s ease;

        &:hover {
          transform: scale(1.02);
        }
      }

      :deep(blockquote) {
        background: #f8f9fa;
        border-left: 4px solid #909399;
        margin: 20px 0;
        padding: 15px 25px;
        color: #666;
        font-style: italic;
        border-radius: 4px;
      }

      :deep(table) {
        width: 100%;
        margin: 25px 0;
        border-collapse: collapse;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);

        th,
        td {
          padding: 12px 15px;
          border: 1px solid #eee;
        }

        th {
          background: #f8f9fa;
          font-weight: 600;
        }
      }
    }
  }
}

@media (max-width: 768px) {
  .detail-page {
    padding: 20px 15px;

    .detail-content {
      padding: 25px;

      .main-title {
        font-size: 2rem !important;
      }

      .detail-title {
        font-size: 1.5rem;
      }

      .detail-body {
        :deep(h1) {
          font-size: 1.5rem;
        }
        :deep(h2) {
          font-size: 1.3rem;
        }
        :deep(h3) {
          font-size: 1.1rem;
        }
      }
    }
  }
}
</style>
