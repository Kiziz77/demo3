<template>
  <div>
    <!-- 收藏展示 -->
    <div v-if="dataList.length > 0" class="module-container">
      <el-row :gutter="20">
        <el-col v-for="item in dataList" :key="item.id" :xs="24" :sm="12" :md="8" :lg="6" :xl="4">
          <el-card class="module-card" shadow="hover" @click="handleModuleClick(item.fullPath)">
            <div class="card-image">
              <img :src="item.pictures?.split(',')[0]" :alt="item.title" />
            </div>
            <div class="card-content">
              <h3 class="module-title">{{ item.title }}</h3>
              <div class="module-info">
                <span class="category"></span>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div v-else>
      <el-empty description="你还没有添加收藏哦~" />
    </div>
  </div>

</template>

<script setup>


// 定义 props，用于控制每行显示的列数
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import request from "@/utils/request.js";


const props = defineProps({

});

const router = useRouter();
// 收藏列表
const dataList = ref([
  {id: 1,pictures: 'https://picsum.photos/300/200?random=1', title: '标题' ,fullPath: '/index/noticesDetail?id=1'}
]);


// 跳转详情页面
const handleModuleClick = (fullPath) => {
  router.push(fullPath);
};

// 获取收藏列表
const queryDataLike = () => {
  request.get("/likeCollect/queryMyCollect").then(({ data }) => {
    dataList.value = data;
  });
};

onMounted(() => {
  queryDataLike();
});
</script>

<style lang="scss" scoped>
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

</style>
