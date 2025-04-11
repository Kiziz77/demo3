<template>
  <div class="top-level">
    <div class="comment-item-left">
      <img :src="avatar" alt="" />
    </div>
    <div class="comment-item-right">
      <div class="comment-item-right-top">
        <span class="username">{{ data.username }}</span>
      </div>
      <div class="comment-content">
        <div class="content">
          {{ data.commentVal }}
        </div>
      </div>
      <div class="comment-action">
        <div class="pubdate">发布时间: {{ data.createTime }}</div>
        <div class="reply" @click="toReplyHandler(data.id)">回复</div>
        <div
          class="del"
          @click="deleteHandler(data.id)"
          v-if="data.createdBy === userStore.userInfo.id"
        >
          删除
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

import { ref } from "vue";
import { useUserStore } from "@/stores/user";
const avatar = ref("src/assets/avatar.webp");
const props = defineProps({
  data: {
    type: Object,
    required: true,
  },
  LocationIndex: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits(["toReplyHandler","toDeleteHandler"]);

const toReplyHandler = (id) => {
  console.log(id);

  emit("toReplyHandler", id, id, props.LocationIndex);
};
const userStore = useUserStore();
const deleteHandler = (id) => {
  emit("toDeleteHandler", id);
};
</script>

<style lang="scss" scoped>
.top-level {
  display: flex;
  margin-bottom: 10px;
  .comment-item-left img {
    margin-right: 16px;
  }

  .comment-item-right {
    display: flex;
    flex-direction: column;
    margin-top: 5px;
    .comment-item-right-top {
      display: flex;
      justify-content: space-between;
      .username {
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #fb7299;
        font-size: 14px;
        font-weight: 500;
        line-height: 22px;
      }
    }

    .comment-content {
      margin-top: 14px;
      text-overflow: ellipsis;
      word-wrap: break-word; /* 强制长单词换行 */
      & .content {
        font-size: 16px;
        font-weight: 400;
        line-height: 26px;
        overflow: hidden;
      }
    }

    .comment-action {
      margin-top: 8px;
      display: flex;
      align-items: center;
      color: #8a919f;
      font-size: 14px;
      font-weight: 400;
      line-height: 18px;

      .pubdate {
        margin-right: 20px;
      }

      .reply {
        margin-right: 20px;
      }

      .del {
        color: #8a919f;
        font-size: 14px;
        font-weight: 400;
        line-height: 22px;
        opacity: 0; /* 默认隐藏删除按钮 */
        transition: opacity 0.3s; /* 平滑过渡效果 */
        cursor: pointer; /* 鼠标悬停时显示为手型 */
      }
    }

    .comment-action:hover .del {
      cursor: pointer; /* 鼠标悬停时显示为手型 */
      opacity: 1 !important; /* 显示删除按钮 */
      color: #8a919f;
      font-size: 14px;
      font-weight: 400;
      line-height: 22px;
    }
  }
}

img {
  width: 40px;
  height: 40px;
}
</style>
