<template>
  <div class="comment-container">
    <div class="title">评论 {{ total }}</div>
    <div class="comment-form">
      <div class="content">
        <div class="avatar-box">
          <div class="avatar">
            <img :src="avatar" alt="" />
          </div>
        </div>
        <div class="form-box">
          <el-input
            class="comment-input"
            v-model="commentsForm.commentVal"
            :rows="commentRows"
            type="textarea"
            placeholder="平等交流,友好表达"
            @focus="handleFocus"
            @blur="handleBlur"
          >
          </el-input>
          <!-- 使用 .stop 和 .prevent 修饰符来阻止事件的传播和默认行为 -->
          <div class="action-box">
            <emoji-popover @btnClick="handleActionBoxInteraction" :commentRows="Number(commentRows)" @emojiClick="emojiClick"></emoji-popover>
            <el-button class="add-comment" @mousedown.stop.prevent="handleActionBoxInteraction" @click="handlePublish" :disabled="commentRows !== 6"
              >发送</el-button
            >
          </div>
        </div>
      </div>
    </div>
    <comment-list
      ref="commentList"
      :dataList="dataList"
      :total="total"
      @replyHandler="replyHandler"
      @toDeleteHandler="toDeleteHandler"
    ></comment-list>
    <pagination :total="total" :page="params.page" :size="params.limit" @pageUpdate="pageUpdate"></pagination>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import { onMounted, ref, nextTick } from "vue";
import { checkIsNull } from "@/utils/validate";
import CommentList from "./components/CommentList.vue";
import { ElMessage } from "element-plus";
const avatar = ref("src/assets/avatar.webp");
const commentRows = ref(6);
const commentList = ref();
const props = defineProps({
  //评论主体id
  momentId: {
    type: Number,
    required: true,
  },
  //评论类型
  commentType: {
    type: Number,
    default: 1,
  },
});
/**
 * 评论表单
 */
const commentsForm = ref({
  commentVal: null,
  momentId: props.momentId,
  commentType: props.commentType,
  parentId: null,
  rootCommentId: null,
  replyComment: null,
});

/**
 * 查询表单
 */
const params = ref({
  page: 1,
  limit: 4,
  momentId: props.momentId,
  commentType: props.commentType,
});

const dataList = ref([]);
const total = ref(0);

const getCommentsList = () => {
  request.post("/comments/lists", params.value).then(({ data }) => {
    console.log("🚀 ~ request.post ~ data:", data)
    dataList.value = data.list;
    total.value = data.total;
  });
};

const toDeleteHandler = (id) => {
  request.delete("/comments/delete/" + id).then(({ data }) => {
    ElMessage.success("删除成功!");
    getCommentsList();
  });
};
const pageUpdate = (page) => {
  params.value.page = page;
  getCommentsList();
};
const handleFocus = async () => {
  commentRows.value = 6;

  await nextTick();
};

const handleBlur = async () => {
  setTimeout(async () => {
    if (document.activeElement !== document.querySelector(".form-box")) {
      commentRows.value = 4;
      await nextTick();
    }
  }, 200);
};

const handleActionBoxInteraction = () => {
  commentRows.value = 6;
};

const emojiClick = (value) => {
  commentsForm.value.commentVal = commentsForm.value.commentVal === null ? value : (commentsForm.value.commentVal += value);
};

const replyHandler = ({ commentVal, parentId, rootCommentId }) => {
  commentsForm.value.commentVal = commentVal;
  commentsForm.value.parentId = parentId;
  commentsForm.value.rootCommentId = rootCommentId;
  handlePublish();
};

const handlePublish = () => {
  if (!checkIsNull(commentsForm.value.commentVal)) {
    ElMessage.error("评论内容不能为空");
    return;
  }
  request.post("/comments/insert", commentsForm.value).then(({ data }) => {
    ElMessage.success("评论成功!");
    commentsForm.value.commentVal = null;
    commentsForm.value.parentId = null;
    commentsForm.value.rootCommentId = null;
    commentList.value.clearComments();
    getCommentsList();
  });
};

onMounted(() => {
  getCommentsList();
});
</script>
<script>
export default {
  name: "Comment",
};
</script>
<style lang="scss" scoped>
.comment-container {
  box-sizing: border-box;
  border-radius: 4px;

  background-color: #fff;
  border: 1px solid #ddd;
  padding: 20px;

  .title {
    color: #252933;
    font-size: 18px;
    font-weight: 600;
    line-height: 30px;
  }

  .comment-form {
    box-sizing: border-box;
    margin-top: 32px;
    > .content {
      display: flex;

      .avatar-box {
        flex: 0 0 auto;
        .avatar {
          height: 40px;
          width: 40px;
          border-radius: 50%;
          margin-right: 16px;
          img {
            width: 100%;
            height: 100%;
          }
        }
      }
      .form-box {
        width: 100%;
        position: relative;
        .action-box {
          width: 100%;
          display: flex;
          justify-content: space-between;
          position: absolute;
          bottom: 10px;
          .add-emoji {
            margin-left: 10px;
          }
          .add-comment {
            margin-right: 10px;
          }
        }
      }
    }
  }
}
.el-pagination {
  justify-content: center;
}
</style>
