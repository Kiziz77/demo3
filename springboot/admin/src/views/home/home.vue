<template>
  <div class="home">
    <!-- 登录记录表格 -->
    <div class="card-group">
      <el-card class="custom-card" shadow="never">
        <div class="card-title">
          <h2>当前账户</h2>
          <div class="img-div">
            <img src="/src/assets/svg/contacts.svg">
          </div>
        </div>
        <div class="card-content" style="justify-content: left">

          <div class="user-picture">
            <img :src="userInfo.avatar">
          </div>

          <div class="user-info">
            <p>账户：{{ userInfo.accountNumber }}</p>
            <p>手机：{{ userInfo.phone }}</p>
            <p>邮箱：{{ userInfo.email }}</p>
          </div>
        </div>
      </el-card>

      <!-- 动态生成卡片 -->
      <el-card v-for="(item, index) in titleType" :key="index" class="custom-card" shadow="never">
        <div class="card-title">
          <h2>{{ item.title }}</h2>
          <div class="img-div">
            <img :src="item.img">
          </div>
        </div>
        <div class="card-content">
          <div>
            <div class="number">
              <h1>{{ item.count }}</h1>
            </div>
            <div class="percentage">+88%</div>
          </div>
          <div :ref="el => { if (el) titleRef[index].titleRef = el }" class="ioc-char"></div>
        </div>
      </el-card>
      <el-card class="custom-card" shadow="never">
        <div class="card-title">
          <h2>用户满意度</h2>
          <div class="img-div">
            <img src="/src/assets/svg/collection.svg">
          </div>
        </div>
        <div class="card-content">
          <div>
            <div class="number">
              <h1>100%</h1>
            </div>
            <div class="percentage">+100%</div>
          </div>
          <div ref="cakeRef" class="ioc-char"></div>
        </div>
      </el-card>
    </div>

    <!-- 登录记录表格 -->
    <el-card class="table-container" shadow="never">
      <div class="card-header">
        <h2>登录记录</h2>
        <div class="table-tips">最近登录用户行为监控</div>
      </div>
      <el-table :data="logInfoList" style="width: 100%" border stripe header-row-class-name="table-header"
                row-class-name="table-row">
        <el-table-column type="index" label="序号" width="80" align="center"></el-table-column>
        <el-table-column prop="userName" label="用户名" min-width="120"></el-table-column>
        <el-table-column prop="userIp" label="登录IP" min-width="100"></el-table-column>
        <el-table-column prop="userAgent" label="浏览器" min-width="200" show-overflow-tooltip></el-table-column>
        <el-table-column prop="referer" label="来源URL" min-width="120" show-overflow-tooltip></el-table-column>
        <el-table-column prop="loginTime" label="登录时间" width="180"></el-table-column>
        <el-table-column prop="sessionId" label="会话ID" width="260"></el-table-column>
      </el-table>
    </el-card>

    <!-- 图表部分 -->
    <el-card class="chart-container" shadow="never">
      <div class="card-header">
        <h3>用户登录趋势分析</h3>
      </div>
      <div class="chart-wrapper">
        <div ref="chartRef" class="chart-content"></div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import {ref, onMounted, nextTick, watch} from "vue";
import request from "@/utils/request";
import * as echarts from "echarts";
import {useResizeObserver} from "@vueuse/core";
import {useUserStore} from "@/stores/user.js";
// 获取用户
const userStore = useUserStore();
const userInfo = ref(null);
const logInfoList = ref([]);
const chartRef = ref(null);
// 饼状图ref
const cakeRef = ref(null);
// 曲线图，ref集合
const titleRef = ref([]);
// 曲线图 echarts 初始化集合
let titleChart = [];

let myChart = null;
// 监听用户信息
watch(
    () => userStore.userInfo,
    (newUserInfo) => {
      if (newUserInfo) {
        userInfo.value = userStore.userInfo;
      }
    },
    { immediate: true } // 立即执行一次
);

const titleType = ref([
  {
    title: '用户人数',
    count: 0,
    number: 300,
    img: '/src/assets/svg/information.svg',
    color: '#409EFF',
    data: [1600, 10, 2000, 1000, 3600, 4000]
  },
  {
    title: '登录次数',
    count: 100,
    img: '/src/assets/svg/mailbox.svg',
    color: '#e8b188',
    data: [3000, 1000, 3500, 1000, 2500, 4000]
  },

])

// 初始化 titleRef 数组
titleType.value.forEach(() => {
  titleRef.value.push({titleRef: null});
});

// 加载表格数据
const loadLogInfoList = () => {
  request
      .post("/login-info/page", {
        page: 1,
        limit: 5,
      })
      .then((res) => {
        logInfoList.value = res.data.list;
        titleType.value[1].count = res.data.list.length;
      });
};

// 加载图表数据
const loadChartData = async () => {
  const {data} = await request.get("/login-info/getData1");

  await nextTick();
  myChart = echarts.init(chartRef.value);

  const option = {
    title: {
      left: "center",
      textStyle: {
        fontSize: 16, // 调整标题字体大小
        fontWeight: "bold",
        color: "#333",
      },
    },
    tooltip: {
      trigger: "axis",
      backgroundColor: "rgba(0,0,0,0.8)",
      borderColor: "#333",
      textStyle: {
        color: "#fff",
      },
    },
    grid: {
      left: "10%", // 缩小左边的间距
      right: "10%", // 缩小右边的间距
      bottom: "10%", // 缩小底部的间距
      containLabel: true, // 确保刻度标签不会被裁切
    },
    xAxis: {
      type: "category",
      data: data.key,
      axisLine: {
        lineStyle: {
          color: "#999",
          width: 1,
        },
      },
      axisLabel: {
        color: "#666",
        fontSize: 12,
        rotate: 45, // 可以设置旋转角度，防止标签重叠
        overflow: "break", // 确保刻度标签不会被截断
        show: true, // 确保标签始终显示
      },
      axisTick: {
        show: true,
        alignWithLabel: true,
        lineStyle: {
          color: "#999",
        },
      },
    },

    yAxis: {
      type: "value",
      axisLine: {
        lineStyle: {
          color: "#999",
          width: 1,
        },
      },
      axisLabel: {
        color: "#666",
        fontSize: 12, // 调整字体大小
      },
      splitLine: {
        lineStyle: {
          color: "#eee",
          type: "dashed",
        },
      },
    },
    series: [
      {
        name: "登录次数",
        data: data.val,
        type: "line",
        smooth: true,
        symbol: "circle",
        symbolSize: 6, // 缩小符号大小
        itemStyle: {
          color: "#409EFF",
          borderColor: "#fff",
          borderWidth: 2,
        },
        lineStyle: {
          width: 2, // 调整线条宽度
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {offset: 0, color: "rgba(64,158,255,0.4)"},
            {offset: 1, color: "rgba(64,158,255,0.02)"},
          ]),
        },
        label: {
          show: true,
          position: "top",
          formatter: "{c}",
          color: "#409EFF",
          fontSize: 10, // 调整数据标签字体大小
        },
      },
    ],
    animationDuration: 1500,
    animationEasing: "cubicInOut",
    dataZoom: [
      {

        type: "slider",
        start: 0,
        end: 100,
        handleSize: "80%",
        textStyle: {
          color: "#666",
        },
        // 滑动时让 xAxis 刻度标签始终显示
        xAxisIndex: [0], // 关联到 x 轴
        filterMode: "filter", // 滤除不在显示范围内的数据
      },
      {
        type: "inside",
        start: 0,
        end: 100,
        xAxisIndex: [0], // 关联到 x 轴
        filterMode: "filter", // 滤除不在显示范围内的数据
      },
    ],

    legend: {
      data: ["登录次数"],
      top: "5%", // 调整图例位置
      left: "center",
      textStyle: {
        color: "#333",
        fontSize: 16, // 调整图例字体大小
      },
      itemWidth: 12, // 缩小图例宽度
      itemHeight: 8, // 缩小图例高度
      show: true, // 显示图例
    },
  };

  myChart.setOption(option);

  // 响应式调整
  useResizeObserver(chartRef, () => {
    myChart?.resize();
  });

  // 初始化每个图表实例 （目前使用死数据）
  for (let i = 0; i < titleType.value.length; i++) {
    if (titleRef.value[i]?.titleRef) {
      titleChart[i] = echarts.init(titleRef.value[i].titleRef);
      setOption(i);
    }
  }
  // 更新统计数据
  titleType.value[0].count = data.userCount;

  const cakeChart = echarts.init(cakeRef.value);
  const cakeOption = {
    legend: {
      orient: 'vertical',
      x: 'right',
      data: ['100%']
    },
    series: [
      {
        type: 'pie',
        radius: ['50%', '70%'],
        avoidLabelOverlap: true,
        label: {
          show: true,
          position: 'center'
        },
        labelLine: {
          show: false
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '20',
            fontWeight: 'bold',
          }
        },
        data: [
          { value: 335, name: '100%' },
        ]
      }
    ]
  };

  cakeChart.setOption(cakeOption);

};

const setOption = (index) => {
  const option = {
    tooltip: {trigger: 'item'},
    xAxis: {
      type: "category",
      show: false, // 隐藏整个x轴（包括轴线、刻度和标签）
      axisLine: {show: false}, // 确保轴线不显示
      axisTick: {show: false}, // 确保刻度不显示
      axisLabel: {show: false}, // 确保标签不显示
    },
    yAxis: {
      type: "value",
      show: false, // 隐藏整个y轴（包括轴线、刻度和标签）
      axisLine: {show: false}, // 确保轴线不显示
      axisTick: {show: false}, // 确保刻度不显示
      axisLabel: {show: false}, // 确保标签不显示
      splitLine: {show: false} // 确保网格线不显示
    },
    series: [
      {
        type: 'line',
        data: titleType.value[index].data,
        smooth: true,
        symbol: 'circle',
        symbolSize: 0,
        itemStyle: {color: titleType.value[index].color},
        lineStyle: {width: 2},
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {offset: 0, color: 'rgba(64,158,255,0.4)'},
            {offset: 1, color: 'rgba(64,158,255,0.02)'}
          ])
        }
      }
    ]
  };
  titleChart[index].setOption(option);
}

onMounted(() => {
  loadLogInfoList();
  loadChartData();
});
</script>

<style lang="scss" scoped>
.home {
  padding: 24px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  min-height: 100vh;
}

// 卡片公共样式
.el-card {
  border-radius: 12px;
  border: 1px solid #ebeef5;
  margin-bottom: 24px;
  transition: box-shadow 0.3s;

  &:hover {
    box-shadow: 0 6px 18px -8px rgba(0, 0, 0, 0.15);
  }
}

.card-header {
  padding: 16px 24px;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 16px;

  h2,
  h3 {
    margin: 0;
    font-size: 18px;
    color: #303133;
    font-weight: 600;
  }

  .table-tips,
  .chart-tips {
    font-size: 12px;
    color: #909399;
    margin-top: 10px;
  }
}

// 表格样式优化
.table-container {
  :deep(.table-header) {
    th {
      background: #f8f9fa;
      color: #606266;
      font-weight: 600;
      height: 48px;
    }
  }

  :deep(.table-row) {
    td {
      padding: 12px 0;
      height: 52px;
      transition: background 0.3s;

      &:hover {
        background: #f5f7fa !important;
      }
    }
  }
}

// 图表容器
.chart-container {
  .chart-wrapper {
    padding: 16px 24px 24px;
  }

  .chart-content {
    height: 480px;
    width: 100%;
  }
}

// 响应式适配
@media (max-width: 992px) {
  .home {
    padding: 16px;
  }

  .chart-content {
    height: 360px !important;
  }
}

.card-group {
  display: flex;
  justify-content: space-between;

  .custom-card {
    width: calc(25% - 20px); // 减去间距，根据实际情况调整
  }

  .card-title {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 18px;
    color: #303133;
    font-weight: 600;
    margin-bottom: 10px;
  }

  .card-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: 600;
    font-family: 微软雅黑;

    .ioc-char {
      height: 100px;
      width: 200px;
    }

    .user-picture {
      margin-left: 10px;
      width: 75px; // 设置你想要的宽度
      height: 75px; // 设置你想要的高度
      border-radius: 50%; // 使图片变成圆形
      overflow: hidden; // 确保图片不会超出边界
      background-color: #effaff; // 可选：为头像背景设置颜色

      img {
        width: 100%;
        height: 100%;
        object-fit: cover; // 确保图片覆盖整个容器并保持比例
      }
    }

    .user-info {
      margin-left: 30px;

      p {
        margin: 10px 0;
        font-size: 14px;
        font-weight: 600;
      }
    }

    .number {
      margin-bottom: 10px;
      font-size: 28px;
      color: #303133;

    }

    .percentage {
      color: #36cd89;
    }
  }

  .img-div {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    background-color: #effaff;
    width: 26px; // 设置你想要的宽度
    height: 26px; // 设置你想要的高度
    border-radius: 2px; // 如果需要圆形边框
    overflow: hidden; // 确保图片不会超出边界

    img {
      width: 70%;
      height: 70%;
      object-fit: contain; // 根据需要调整图片适应方式，如contain或cover
    }
  }
}
</style>
