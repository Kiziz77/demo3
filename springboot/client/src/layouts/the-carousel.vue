<template>
  <div class="carousel-container">
    <el-carousel
      :style="{ width: width }"
      :height="height"
      :autoplay="autoplay"
      :type="type"
      :direction="direction"
      :interval="interval"
      :arrow="arrow"
      :indicator-position="indicatorPosition"
      :loop="loop"
      :pause-on-hover="pauseOnHover"
    >
      <el-carousel-item v-for="(item, index) in images" :key="index">
        <img :src="item.value" :alt="`carousel-image-${index}`" class="carousel-image" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import request from '@/utils/request.js' 
// 图片列表
const images = ref([
  "https://img2.baidu.com/it/u=618885737,3967403611&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
  "https://img0.baidu.com/it/u=2324403362,331623073&fm=253&fmt=auto&app=138&f=JPEG?w=1422&h=800",
  "http://img2.baidu.com/it/u=3855160903,1675336111&fm=253&app=138&f=JPEG?w=1422&h=800",
  "https://img0.baidu.com/it/u=64680047,3091727575&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500",
]);

// 定义组件的 props
const props = defineProps({
  // 宽度
  width: {
    type: String,
    default: "100%",
  },
  // 高度
  height: {
    type: String,
    default: "400px",
  },
  // 是否自动播放
  autoplay: {
    type: Boolean,
    default: true,
  },
  // 自动播放间隔时间（毫秒）
  interval: {
    type: Number,
    default: 3000,
  },
  // 切换箭头显示方式
  arrow: {
    type: String,
    default: "hover", // 'always' | 'hover' | 'never'
  },
  // 指示器位置
  indicatorPosition: {
    type: String,
    default: "outside", // 'none' | 'outside'
  },
  // 轮播图类型
  type: {
    type: String,
    default: "", // 'card' 卡片式轮播图
  },
  // 轮播图方向
  direction: {
    type: String,
    default: "horizontal", // 'vertical' 垂直方向
  },
  // 是否循环播放
  loop: {
    type: Boolean,
    default: true,
  },
  // 鼠标悬停时是否暂停自动播放
  pauseOnHover: {
    type: Boolean,
    default: true,
  },
});


const loadDataList = ()=>{
    request.get('/config/loadAll').then(({data})=>{
        console.log(data)
        images.value = data
    })
}
onMounted(()=>{
    loadDataList()
})
</script>

<style lang="scss" scoped>
.carousel-container {
  margin: 20px auto;

  .el-carousel {
    margin: 0 auto;
    border-radius: 8px; // 增加圆角
    overflow: hidden; // 防止图片溢出
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); // 增加阴影

    .carousel-image {
      width: 100%;
      height: 100%;
      object-fit: cover; // 图片填充方式
      transition: transform 0.3s ease; // 增加图片切换动画
    }

    // 自定义指示器样式
    :deep(.el-carousel__indicator) {
      padding: 8px;

      .el-carousel__button {
        width: 10px;
        height: 10px;
        border-radius: 50%;
        background-color: #ccc;
        transition: background-color 0.3s ease;
      }

      &.is-active .el-carousel__button {
        background-color: #909399; // 激活状态的颜色
      }
    }

    // 自定义箭头样式
    :deep(.el-carousel__arrow) {
      background-color: rgba(0, 0, 0, 0.3);
      color: #fff;
      transition: background-color 0.3s ease;

      &:hover {
        background-color: rgba(0, 0, 0, 0.5);
      }
    }
  }
}
</style>
