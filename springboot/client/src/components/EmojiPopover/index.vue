<template>
    <el-popover
      :placement="placement"
      title="添加表情"
      :width="240"
      trigger="click"
    >
      <template #reference>
        <el-button
          @mousedown.stop.prevent="handleClick"
          class="add-emoji"
          :disabled="commentRows !== 6"
          >添加表情</el-button
        >
      </template>
      <ul class="emoji-list" @mousedown.stop.prevent="handleClick">
        <li
          class="emoji-item"
          v-for="(item, index) in emojiList"
          :key="index"
          @click="emojiClick(item)"
        >
          <span class="emoji">{{ item }}</span>
        </li>
      </ul>
    </el-popover>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import { emojiList } from "@/utils/emojis.js";
  const placement = ref("bottom");
  const emit = defineEmits(["btnClick", "emojiClick"]);
  const handleClick = (event) => {
    //   event.stopPropagation();
    //   event.preventDefault();
    // 其他处理逻辑
    emit("btnClick");
  };
  
  const emojiClick = (value) => {
    emit("emojiClick", value);
  };
  
  defineProps({
    commentRows: {
      type: Number,
      default: 6,
    },
  });
  </script>
  
  <style lang="scss" scoped>
  .add-emoji {
    margin-left: 10px;
  }
  .emoji-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    max-height: 120px;
    overflow: auto;
    overflow-x: hidden;
  
    // 自定义滚动条样式
    &::-webkit-scrollbar {
      width: 8px; // 控制滚动条的宽度
    }
  
    &::-webkit-scrollbar-track {
      background: transparent; // 滚动条轨道的背景色
    }
  
    &::-webkit-scrollbar-thumb {
      background: rgba(0, 0, 0, 0.3); // 滚动条的颜色和透明度
      border-radius: 10px; // 控制滚动条的圆角
    }
  
    &::-webkit-scrollbar-thumb:hover {
      background: rgba(0, 0, 0, 0.5); // 滚动条在悬停时的颜色
    }
  
    /*  方案二
    scrollbar-width: thin; // Firefox 滚动条的宽度
    scrollbar-color: rgba(0, 0, 0, 0.2) transparent; // 滚动条颜色和背景色 */
  
    .emoji-item {
      .emoji {
        font-size: 21px;
        margin: 2px;
      }
    }
  }
  </style>
  