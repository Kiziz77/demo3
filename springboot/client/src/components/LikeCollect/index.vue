<template>
  <!--  收藏 点赞-->
  <div class="interaction-buttons">
    <el-button :type="isLiked ? 'success' : 'info'" :class="{ 'liked': isLiked }" @click="like">
      <img v-if="isLiked" style="width: 16px;margin-right: 5px" src="/src/assets/svg/like.svg" />
      <img v-else style="width: 16px;margin-right: 5px" src="/src/assets/svg/notLike.svg" />
      <p>{{ isLiked ? '已点赞' : '点赞' }}<span>{{ '(' + likeCount + ')' }}</span></p>
    </el-button>
    <el-button :type="isFavorited ? 'success' : 'info'" :class="{ 'liked': isFavorited }" @click="toggleFavorite">
      <img v-if="isFavorited" style="width: 16px;margin-right: 5px" src="/src/assets/svg/collect.svg" />
      <img v-else style="width: 16px;margin-right: 5px" src="/src/assets/svg/notCollect.svg" />
      <p>{{ isFavorited ? '已收藏' : '收藏' }}<span>{{ '(' + collectCount + ')' }}</span></p>
    </el-button>
  </div>
</template>

<script setup>


// 定义 props，用于控制每行显示的列数
import {onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import request from "@/utils/request";


const props = defineProps({
  //主体id
  momentId: {
    type: Number,
    required: true,
  },
  //模块/标识 表名称(必须是)
  signName: {
    type: String,
    default: 1,
  },
  //路由
  fullPath: {
    type: String,
    default: '',
  },
  //标题字段名
  fieldNames: {
    type: Array,
    default: [],
  },
});


const isFavorited = ref(false); // 收藏状态
const isLiked = ref(false); // 点赞状态
const likeCount = ref(0); // 点赞数
const collectCount = ref(0); // 收藏数

// 切换收藏状态
const toggleFavorite = () => {
  if (!isFavorited.value) {
    collectCount.value++;
  } else {
    collectCount.value--;
  }
  isFavorited.value = !isFavorited.value;
  // 这里可以调用API更新收藏状态
  setLikeCollect('collect');
};

// 点赞
const like = () => {
  if (!isLiked.value) {
    likeCount.value++;
  } else {
    likeCount.value--;
  }
  isLiked.value = !isLiked.value;
  // 这里同样可以调用API更新点赞状态
  setLikeCollect('like');
};

// 设置点赞/收藏
const setLikeCollect = (type) => {
  let form = {
    momentId: props.momentId,
    signName: props.signName,
    isLike: isLiked.value ? 1 : 0,
    isCollect: isFavorited.value ? 1 : 0,
    fullPath: props.fullPath,
    picturesField: props.fieldNames[0],
    titleField: props.fieldNames[1],
  }
  request.post("/likeCollect/likeCollect", form).then(({ data }) => {
    if (type === 'like'){
      ElMessage.success(isLiked.value ? "点赞成功!" : "已取消点赞");
    }else {
      ElMessage.success(isFavorited.value ? "收藏成功!" : "已取消收藏");
    }
  });
}

// 获取数据
const getOne = () => {
  let params = {
    momentId: props.momentId,
    signName: props.signName,
  }
  request.post("/likeCollect/queryLikeCollect", params).then(({ data }) => {
    isLiked.value = data.isLike === 1;
    isFavorited.value = data.isCollect === 1;
    likeCount.value = data.likeCount;
    collectCount.value = data.collectCount;
  });
}

onMounted(() => {
  getOne();
});
</script>

<style lang="scss" scoped>

.interaction-buttons {
  margin: 30px 0;
    text-align: center;
  .el-button {
    margin: 0 10px;
    transition: all 0.3s ease;

    &.favorited, &.liked {
      transform: scale(1.05);
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
    }
  }
}
</style>
