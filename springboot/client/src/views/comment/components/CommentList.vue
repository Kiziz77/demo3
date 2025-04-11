<template>
  <div class="comment-list">
    <div class="comment-item" v-for="(item, index) in dataList" :key="index">
      <top-level
        :data="item"
        :LocationIndex="index"
        @toReplyHandler="toReplyHandler"
        @toDeleteHandler="toDeleteHandler"

      ></top-level>
      <div
        class="childs-comment-container"
        v-if="item.children && item.children.length > 0"
      >
        <second-comment
          :secondComments="item.children"
          :LocationIndex="index"
          @toReplyHandler="toReplyHandler"
          @toDeleteHandler="toDeleteHandler"
        ></second-comment>
        <template
          v-for="(child, childIndex) in item.children"
          :key="childIndex"
        >
          <child-comment
            :childComments="child.children"
            :LocationIndex="index"
            :rootCommentId="item.id"
            @toReplyHandler="toReplyHandler"
            @toDeleteHandler="toDeleteHandler"
          ></child-comment>
        </template>
      </div>
      <div id="reply-container" v-show="replyContainerIndex == index">
        <div class="reply-box">
          <div class="reply-avatar">
            <img :src="avatar" alt="" />
          </div>
          <div class="reply-content">
            <el-input
              type="textarea"
              v-model="commentsForm.commentVal"
              :rows="4"
              placeholder="回复"
            ></el-input>
            <div class="action-box">
              <emoji-popover @emojiClick="emojiClick"></emoji-popover>
              <el-button class="reply-btn" @click="doReplyHandler"
                >回复</el-button
              >
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import SecondComment from "./SecondComment.vue";
import TopLevel from "./TopLevel.vue";
import ChildComment from "./ChildComment.vue";
import { ref } from "vue";
import { checkIsNull } from "@/utils/validate";
import { ElMessage } from "element-plus";
const avatar = ref("src/assets/avatar.webp");
const replyContainerIndex = ref(-1);
defineProps({
  dataList: {
    type: Array,
    required: true,
  },
  total: {
    type: Number,
    required: true,
  },
});

const commentsForm = ref({
  commentVal: null,
  parentId: null,
  rootCommentId: null,
});

const emit = defineEmits(["replyHandler","toDeleteHandler"]);

/**
 
 * @param parentId 回复的父id
 * @param rootCommentId  回复的根id
 * @param LocationIndex  激活所处项的回复框
 */
const toReplyHandler = (
  parentId,
  rootCommentId,
  LocationIndex,
  replyUsername
) => {
  commentsForm.value = {
    commentVal: null,
    parentId: null,
    rootCommentId: null,
  };

  replyContainerIndex.value = LocationIndex;
  commentsForm.value.parentId = parentId;
  commentsForm.value.rootCommentId = rootCommentId;

  // emit("replyHandler",parentId,rootCommentId,LocationIndex);
};

const emojiClick = (value) => {
  commentsForm.value.commentVal =
    commentsForm.value.commentVal === null
      ? value
      : (commentsForm.value.commentVal += value);
};

/**
 * 点击回复按钮进行回复
 */
const doReplyHandler = () => {
  if (!checkIsNull(commentsForm.value.commentVal)) {
    ElMessage.error("回复内容不能为空");
    return;
  }
  emit("replyHandler", commentsForm.value);
};

const toDeleteHandler = (id)=>{
   emit("toDeleteHandler",id);
    
}

const clearComments = () => {
  commentsForm.value.commentVal = null;
};

defineExpose({
  clearComments,
});
</script>

<style lang="scss" scoped>
.comment-list {
  margin-top: 32px;
  .comment-item {
    width: 100%;
    margin: 10px 0;
    border-bottom: 1px solid #e3e5e7;

    .childs-comment-container {
      max-height: 300px;
      width: 100%;
      overflow-y: auto;
      scrollbar-width: none; // Firefox 滚动条的宽度
      scrollbar-color: rgba(0, 0, 0, 0.1) transparent; // 滚动条颜色和背景色
    }

    #reply-container {
      margin: 8px 0 10px 112px;

      .reply-box {
        display: flex;

        .reply-avatar {
          width: 50px;
          height: 50px;
          margin-right: 8px;

          img {
            width: 100%;
            height: 100%;
          }
        }

        .reply-content {
          width: 100%;
          margin-top: 4px;

          .action-box {
            margin-top: 10px;
            display: flex;
            justify-content: space-between;
            .reply-btn {
              margin-right: 8px;
            }
          }
        }
      }
    }
  }
}
</style>
