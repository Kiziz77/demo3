import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
  {
    path: "/",
    redirect: "/index/home",
  },
  {
    path: "/index",
    component: () => import("@/views/index.vue"),
    children: [
      {
        path: "home",
        component: () => import("@/views/home/home.vue"),
      },{
        path:'personal-center',
        component:()=>import('@/views/personal-center/personal-center.vue')
      },
      {
        path:'shopping-cart',
        component:()=>import('@/views/shopping-cart/list.vue')
      },
      {
        path:'notices',
        component:()=>import('@/views/notices/list.vue')
      },
      {
        path:'noticesDetail',
        name:'noticesDetail',
        component:()=>import('@/views/notices/detail.vue')
      },
      {
        path: 'products',
        component: () => import('@/views/products/list.vue')
      },
      {
        path: 'productsDetail',
        name: 'productsDetail',
        component: () => import('@/views/products/detail.vue')
      },
    {
        path:'drugDisplay',
        component:()=>import('@/views/drugDisplay/list.vue')
    },
    {
        path:'drugDisplayDetail',
        name:'drugDisplayDetail',
        component:()=>import('@/views/drugDisplay/detail.vue')
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

export default router;
