<!-- 前端部分：ThemeDetail.vue -->
<template>
    <div class="theme-detail-container">
        <!-- 大标题 -->
        <h1 class="detail-title">详情页</h1>
        

        <!-- 信息展示 -->
        <div class="info-section">
            <div class="info-item" style="justify-content: center">
                <like-collect :momentId="Number(route.query.id)" :signName="tableName"
                              :fullPath="route.fullPath" :fieldNames="fieldNames"></like-collect>
            </div>
            <!-- Tab切换 -->
            <el-tabs v-model="activeTab">
                <el-tab-pane label="评论" name="comments">
                    <Comment :momentId="Number(route.query.id)" :commentType="commentTypeId" />
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script setup>
    import { onMounted, ref } from "vue";
    import request from "@/utils/request";
    import  { useRoute } from 'vue-router'
    import Comment from "@/views/comment/index.vue";
    const route = useRoute();
    const detailData = ref({});
    const tableName = "inventory";
    const commentTypeId = ref("850315");
    const fieldNames = ref([]);
    // Tab状态
    const activeTab = ref("detail");

    const loadDetailData = ()=>{
        request.get("/inventory/info/" + route.query.id).then(({data})=>{
            detailData.value = data;
        })
    }

    onMounted(() => {

        loadDetailData();
    });
</script>

<style lang="scss" scoped>
    .theme-detail-container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 30px;
        background-color: #f9f9f9;
        border-radius: 10px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    }

    .detail-title {
        text-align: center;
        margin-bottom: 40px;
        font-size: 36px;
        font-weight: 700;
        color: #333;
        letter-spacing: 1px;
    }

    .common-actions {
        display: flex;
        justify-content: flex-start;
        margin-bottom: 30px;

        el-button {
            margin-right: 12px;
            font-size: 16px;
            padding: 12px 25px;
            border-radius: 50px;
            background-color: #909399;
            color: white;
            transition: background-color 0.3s ease;

            &:hover {
                background-color: #66b1ff;
            }
        }
    }

    .carousel-image {
        width: 100%;
        height: 600px;
        object-fit: cover;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }

    .info-section {
        margin-top: 30px;
        padding: 25px;
        background: #ffffff;
        border-radius: 12px;
        box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);

        .info-item {
            display: flex;
            margin-bottom: 20px;
            font-size: 18px;
            line-height: 1.6;
            padding-bottom: 15px;
            border-bottom: 1px solid #eee;

            .label {
                width: 150px;
                font-weight: 600;
                color: #555;
                text-align: right;
                margin-right: 30px;
            }

            .value {
                font-weight: 400;
                color: #333;
            }
        }

        .info-item:last-child {
            border-bottom: none;
        }
    }

    .action-buttons {
        margin-top: 40px;
        text-align: center;

        el-button {
            margin: 10px;
            padding: 12px 30px;
            font-size: 16px;
            border-radius: 25px;
            background-color: #ff7f50;
            color: white;
            transition: background-color 0.3s ease;

            &:hover {
                background-color: #ff7043;
            }
        }
    }

    .el-tabs__header {
        border-bottom: 2px solid #f2f2f2;
        padding: 10px 0;
    }

    .el-tab-pane {
        padding: 25px;
        background: #f4f8fc;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    }

    .el-tabs__nav-wrap {
        justify-content: center;
        margin-bottom: 20px;
    }

    .el-tab-bar__item {
        font-size: 18px;
        font-weight: 500;
        color: #666;
        padding: 12px 24px;
        transition: color 0.3s ease;

        &:hover {
            color: #909399;
        }
    }

    .el-tab-bar__item.is-active {
        color: #909399;
        font-weight: 600;
        border-bottom: 3px solid #909399;
    }
</style>
