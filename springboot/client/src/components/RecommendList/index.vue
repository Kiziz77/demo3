<template>
  <div class="recommend" v-if="items.length > 0">
    <div class="title">
      <h1>{{ title }}</h1>
    </div>
    <div class="box">
      <div class="list">
        <div v-for="(item, index) in items" :key="index" class="list-item" :class="`list-item${column}`" @click="handleModuleClick(item.id)">
          <img :src="item.pictures" alt="" />
        </div>
      </div>

      <div class="more-btn-container">
        <button class="more-btn" @click="goToMore">查看更多</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { watchEffect } from "vue";
const router = useRouter();

// 定义 props，用于控制每行显示的列数
const props = defineProps({
  column: {
    type: Number,
    default: 3, // 默认每行显示 3 个
    validator: (value) => [3, 4, 5, 6].includes(value),
  },
  items: {
    type: Array,
    default: () => [],
  },
  title: {
    type: String,
    default: "信息推荐",
  },
  moreUrl: {
    required: true,
    type: String,
  },
});

// 模块点击事件
const handleModuleClick = (id) => {
  router.push({ name: "noticesDetail", query: { id: id } });
};

// 跳转更多页面
const goToMore = () => {
  router.push(props.moreUrl);
};

watchEffect(() => {
  document.documentElement.style.setProperty("--columns", props.column);
});
</script>

<style lang="scss" scoped>
.recommend {
  width: 1200px;
  margin: 0 auto;

  .title {
    width: 96%;
    margin: 0 auto;
    text-align: center;
    padding: 20px 15px;
    border-radius: 15px; /* 圆角边框 */
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2); /* 增加阴影效果 */
    font-family: "Arial", sans-serif;
    font-size: 30px;
    letter-spacing: 2px;
    position: relative;
    z-index: 10;
  }
  .box {
    .list {
      display: flex;
      flex-wrap: wrap;

      .list-item {
        box-sizing: border-box;
        padding: 20px 10px;
        text-align: center;
        transition: all 0.3s ease;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin: 10px 0;
        width: calc(100% / var(--columns));
        border-radius: 50px;
        img {
          width: 80%;
          border-radius: 50px;
        }
        &:hover {
          transform: scale(1.05);
          background-color: #e0e0e0;
        }
      }
    }
  }
  .more-btn-container {
    text-align: center;
    margin-top: 20px;

    .more-btn {
      background-color: #007bff;
      color: white;
      padding: 10px 20px;
      font-size: 16px;
      border-radius: 30px;
      border: none;
      cursor: pointer;
      transition: background-color 0.3s ease;

      &:hover {
        background-color: #0056b3;
      }
    }
  }
}
</style>
