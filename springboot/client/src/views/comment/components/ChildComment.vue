<template>
  <div v-if="childComments && childComments.length > 0">
    <div v-for="(child, index) in childComments" :key="index">
      <div class="child-comment">
        <div class="comment-item-left">
          <img :src="avatar" alt="" />
        </div>
        <div class="comment-item-right">
          <div class="comment-item-right-top">
            <span class="username">{{ child.username }}</span>
            <span class="reply">回复</span>
            <span class="reply-username">@{{ child.replyUsername }}</span>
          </div>
          <div class="comment-content">
            <div class="content">
              {{ child.commentVal }}
            </div>
          </div>
          <div class="comment-action">
            <div class="pubdate">发布时间:{{ child.createTime }}</div>
            <div class="reply" @click="replyHandler(child.id)">回复</div>
            <div
              class="del"
              @click="deleteHandler(child.id)"
              v-if="child.createdBy === userStore.userInfo.id"
            >
              删除
            </div>
          </div>
        </div>
      </div>

      <child-comment
        :childComments="child.children"
        :LocationIndex="LocationIndex"
        :rootCommentId="rootCommentId"
        @toReplyHandler="replyHandler"
        @toDeleteHandler="deleteHandler"
      ></child-comment>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/user";

const avatar = ref("src/assets/avatar.webp");
const props = defineProps({
  childComments: {
    type: Array,
    required: true,
  },
  LocationIndex: {
    type: Number,
    required: true,
  },
  rootCommentId: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits(["toReplyHandler", "toDeleteHandler"]);
const replyHandler = (id) => {
  emit("toReplyHandler", id, props.rootCommentId, props.LocationIndex);
};

const userStore = useUserStore();
const deleteHandler = (id) => {
  console.log("🚀 ~ deleteHandler ~ id:", id);
  emit("toDeleteHandler", id);
};
</script>
<script>
export default {
  name: "ChildComment",
};
</script>
<style lang="scss" scoped>
.child-comment {
  margin-left: 56px;
  margin-top: 8px;
  margin-bottom: 10px;
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
      & .username,
      .reply,
      .reply-username {
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;

        font-size: 14px;
        font-weight: 500;
        line-height: 18px;
      }

      & .username {
        color: #fb7299;
      }

      & .reply {
        margin: 0 8px;
      }

      & .reply-username {
        color: #00aeec;
      }
    }

    .comment-content {
      margin-top: 14px;
      overflow-wrap: break-word; /* 强制长单词换行 */
      word-break: break-word; /* 确保长单词或数字在达到容器边界时换行 */
      & .content {
        font-size: 16px;
        font-weight: 400;
        line-height: 26px;
        overflow: hidden;
        text-overflow: ellipsis;
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
  width: 40px;
}
</style>
