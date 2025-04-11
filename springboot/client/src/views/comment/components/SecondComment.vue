<template>
  <div v-if="secondComments && secondComments.length > 0">
    <div class="second-comment" v-for="(item, childIndex) in secondComments">
      <div class="comment-item-left">
        <img :src="avatar" alt="" />
      </div>
      <div class="comment-item-right">
        <div class="comment-item-right-top">
          <span class="username">{{ item.username }}</span>
          <span
            class="del"
            @click="deleteHandler"
            v-if="item.createdBy === userStore.userInfo.id"
            >删除</span
          >
        </div>
        <div class="comment-content">
          <div class="content">
            {{ item.commentVal }}
          </div>
        </div>
        <div class="comment-action">
          <div class="pubdate">发布时间: {{ item.createTime }}</div>
          <div class="reply" @click="toReplyHandler(item.id, item.parentId)">
            回复
          </div>
          <div
          class="del"
          @click="deleteHandler(item.id)"
          v-if="item.createdBy === userStore.userInfo.id"
        >
          删除
        </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
const avatar = ref("src/assets/avatar.webp");
import { useUserStore } from "@/stores/user";
const props = defineProps({
  secondComments: {
    type: Array,
    required: true,
  },
  LocationIndex: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits(["toReplyHandler","toDeleteHandler"]);
const toReplyHandler = (id, rootCommentId) => {
  emit("toReplyHandler", id, rootCommentId, props.LocationIndex);
};

const userStore = useUserStore();
const deleteHandler = (id) => {
  console.log("🚀 ~ deleteHandler ~ id:", id)
  emit("toDeleteHandler", id);
};
</script>

<style lang="scss" scoped>
.second-comment {
  margin-left: 56px;
  margin-top: 8px;
  display: flex;
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
        line-height: 18px;
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

    .comment-item-right-top:hover .del {
      opacity: 1; /* 显示删除按钮 */
      color: #8a919f;
      font-size: 14px;
      font-weight: 400;
      line-height: 22px;
    }

    .comment-content {
      margin-top: 14px;
      overflow-wrap: break-word; /* 强制长单词换行 */
      word-break: break-word; /* 确保长单词或数字在达到容器边界时换行 */
      & .content {
        font-size: 16px;
        font-weight: 400;
        line-height: 26px;
      }
    }

    .comment-action {
      margin-top: 8px;
      display: flex;
      align-items: center;
      color: #8a919f;
      font-size: 14px;
      font-weight: 400;
      line-height: 22px;

      .pubdate {
        margin-right: 20px;
      }
      .reply{
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
  width: 40px;
}
</style>
