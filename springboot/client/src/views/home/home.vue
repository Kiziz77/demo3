<template>
  <div class="home">
    <RecommendList :column="3" :items="noticesList" title="公告信息" moreUrl="notices" />
  </div>
</template>

<script setup>
import request from "@/utils/request";
import { onMounted, ref } from "vue";

const params = ref({
  page: 1,
  limit: 6,
});

const noticesList = ref([]);
const loadNoticesList = async () => {
  const { data } = await request.post("/notices/list", params.value);
  noticesList.value = data.list;
};

onMounted(() => {
  loadNoticesList();
});
</script>

<style lang="scss" scoped>
.home {
  padding: 20px;
  margin: 10px 0;
}
</style>
