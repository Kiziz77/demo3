<template>
    <div class="module-showcase">
        <!-- 标题部分 -->
        <div class="header">
            <h1 class="title">药品展示信息</h1>
        </div>

        <!-- 搜索条件 -->
        <div class="search-area">
                    <el-input
                            v-model="searchParams.drugId"
                            placeholder="请输入药品ID"
                            class="search-input"
                            clearable
                            @clear="handleSearch"
                            @keyup.enter="handleSearch"
                    >
                        <template #prefix>
                            <el-icon><Search /></el-icon>
                        </template>
                    </el-input>
        </div>

        <!-- 模块展示 -->
        <div class="module-container">
            <el-row :gutter="20">
                <el-col v-for="item in dataList" :key="item.id" :xs="24" :sm="12" :md="8" :lg="6" :xl="4">
                    <el-card class="module-card" shadow="hover" @click="handleModuleClick(item.id)">
                        <div class="card-image">
                                    <img :src="item.drugImage?.split(',')[0]" :alt="item.药品图片" />
                        </div>
                        <div class="card-content">
                                    <h3 class="module-title" v-if="item.drugId">{{ item.drugId }}</h3>
                            <div class="module-info">
                                <span class="category"></span>
                                <div class="meta">
                                            <span class="time" v-if="item.createTime">{{ item.createTime }}</span>
                                    <span class="favorites"> </span>
                                </div>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>

        <!-- 分页部分 -->
        <div class="pagination-wrapper">
            <el-pagination
                    v-model:current-page="searchParams.page"
                    v-model:page-size="searchParams.limit"
                    :total="total"
                    layout="total,  prev, pager, next, jumper"
                    background
                    @current-change="handleCurrentChange"
            />
        </div>
    </div>
</template>

<script setup>
    import { ref, reactive, onMounted } from "vue";
    import request from "@/utils/request.js";
    import { useRouter } from "vue-router";
    const router = useRouter();
    const dataList = ref([]);
    const total = ref(0);
    // 搜索参数
    const searchParams = reactive({
        page: 1,
        limit: 4,
            id: null,
            drugId: null,
    });

    // 加载数据列表
    const loadDataList = async () => {
        const { data } = await request.post("/drugDisplay/list", searchParams);
        dataList.value = data.list;
        total.value = data.total;
    };

    // 搜索
    const handleSearch = () => {
        searchParams.page = 1;
        loadDataList();
    };

    // 当前页码改变
    const handleCurrentChange = (page) => {
        searchParams.page = page;
        loadDataList();
    };

    // 模块点击事件
    const handleModuleClick = (id) => {
        router.push({ name: "drugDisplayDetail", query: { id: id } });
    };

    onMounted(() => {
        loadDataList();
    });
</script>

<style lang="scss" scoped>
    .module-showcase {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;

        .header {
            text-align: center;
            margin-bottom: 40px;
            padding: 20px;
            background: linear-gradient(135deg, #909399, #79bbff);
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);

            .title {
                font-size: 2.5rem;
                color: #fff;
                margin: 0;
                font-weight: bold;
                text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
            }
        }

        .search-area {
            display: flex;
            gap: 20px;
            margin-bottom: 30px;

            .search-input {
                flex: 1;
                max-width: 400px;
            }

            .category-select {
                width: 200px;
            }
        }

        .module-container {
            .module-card {
                margin-bottom: 20px;
                transition: transform 0.3s ease;

                &:hover {
                    cursor: pointer;
                    transform: translateY(-5px);
                }

                .card-image {
                    height: 200px;
                    overflow: hidden;
                    border-radius: 4px;

                    img {
                        width: 100%;
                        height: 100%;
                        object-fit: cover;
                        transition: transform 0.3s ease;

                        &:hover {
                            transform: scale(1.05);
                        }
                    }
                }

                .card-content {
                    padding: 15px;

                    .module-title {
                        font-size: 1.1rem;
                        color: #2d3a4b;
                        margin: 0 0 10px;
                        white-space: nowrap;
                        overflow: hidden;
                        text-overflow: ellipsis;
                    }

                    .module-info {
                        .meta {
                            margin-top: 10px;
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            color: #909399;
                            font-size: 0.85rem;

                            .favorites {
                                display: flex;
                                align-items: center;
                                gap: 4px;

                                .el-icon {
                                    color: #e6a23c;
                                }
                            }
                        }
                    }
                }
            }
        }

        .pagination-wrapper {
            margin-top: 20px;
            text-align: center;
            .el-pagination {
                justify-content: center;
            }
        }
    }
</style>
