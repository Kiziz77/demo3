import { createRouter, createWebHashHistory } from "vue-router";
import { useUserStore } from "@/stores/user";
const routes = [
  {
    path: "/",
    redirect: "/index/home",
  },
  {
    path: "/index",
    name: "/",
    component: () => import("@/views/index.vue"),
    children: [
      {
        path: "home",
        name: "首页",
        component: () => import("@/views/home/home.vue"),
      },
      {
        path: "/personal-center",
        component: () => import("@/views/personal-center.vue"),
      },
      {
        path: "/reset-password",
        component: () => import("@/views/reset-password.vue"),
      },
      {
        path: "/notices",
        name: "公告信息",
        component: () => import("@/views/notices/index.vue"),
      },
      {
        path: "/products",
        name: "商品管理",
        component: () => import("@/views/products/index.vue"),
      },
      {
        path: "/productOrder",
        name: "订单管理",
        component: () => import("@/views/productOrder/index.vue"),
      },
      {
        path: "/users",
        name: "用户",
        component: () => import("@/views/users/index.vue"),
      },
      {
        path: "/config",
        name: "轮播图管理",
        component: () => import("@/views/config/index.vue"),
      },
      {
        path: "/drugReservation",
        name: "药品预约",
        component: () => import("@/views/drugReservation/index.vue"),
      },
      {
        path: "/supplierInfo",
        name: "供应商信息",
        component: () => import("@/views/supplierInfo/index.vue"),
      },
      {
        path: "/drugInfo",
        name: "药品信息",
        component: () => import("@/views/drugInfo/index.vue"),
      },
      {
        path: "/customerOrder",
        name: "购买订单",
        component: () => import("@/views/customerOrder/index.vue"),
      },
      {
        path: "/drugDisplay",
        name: "药品展示",
        component: () => import("@/views/drugDisplay/index.vue"),
      },
      {
        path: "/salesRecord",
        name: "销售记录",
        component: () => import("@/views/salesRecord/index.vue"),
      },
      {
        path: "/purchaseRecord",
        name: "采购记录",
        component: () => import("@/views/purchaseRecord/index.vue"),
      },
      {
        path: "/inventory",
        name: "库存信息",
        component: () => import("@/views/inventory/index.vue"),
      },

    ],
  },
  {
    path: "/login",
    component: () => import("@/views/login.vue"),
  },
  {
    path: "/register",
    component: () => import("@/views/register.vue"),
  },
  {
    path: "/:pathMatch(.*)",
    component: () => import("@/views/404.vue"),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

// 前置守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore();

  let token = userStore.getToken();

  // 如果用户访问的是登录或注册页面，直接放行
  if (to.path === "/login" || to.path === "/register") {
    // 如果用户已经登录，跳转到首页
    if (token) {
      next("/index/home");
    } else {
      next();
    }
  } else {
    if (!token) {
      // 如果没有 token，跳转到登录页面
      next("/login");
    } else {
      // 如果有 token，允许访问
      next();
    }
  }
});

export default router;
