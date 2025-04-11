<template>
    <el-pagination
      v-model:current-page="initPage"
      :page-size="initSize"
      background
      layout=" prev, pager, next"
      :total="initTotal"
      @update:current-page="currentPageChange"
    />
  </template>
  
  <script setup>
  import { onMounted, ref, watch } from "vue";
  
  const initPage = ref(5);
  const initSize = ref(6);
  const initTotal = ref(100);
  const props = defineProps({
    total: {
      type: Number,
      required: true,
    },
    page: {
      type: Number,
      required: true,
    },
    size: {
      type: Number,
      required: true,
    },
  });
  const emit = defineEmits(["pageUpdate"]);
  const currentPageChange = (page) => {
    emit("pageUpdate", initPage.value);
  };
  
  onMounted(() => {
    initPage.value = props.page;
    initSize.value = props.size;
    initTotal.value = props.total;
  
  });
  
  watch(
    () => props.total,
    (newValue) => {
      initTotal.value = newValue
    }
  );
  </script>
  
  <style lang="scss" scoped></style>
  