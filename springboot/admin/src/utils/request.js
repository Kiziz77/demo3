import axios from "axios";
import { useUserStore } from "@/stores/user";
import config from "@/config/config";
import { ElNotification } from "element-plus";
import router from "@/router/router";
const service = axios.create({
  baseURL: config.baseUrl, // 使用 config.js 中的基础 URL
  timeout: 5000, // 请求超时时间
});

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    const userStore = useUserStore();
    const token = userStore.getToken();
    if (token) {
      config.headers["Token"] = token; // 将 token 添加到请求头
    }
    return config;
  },
  (error) => {
    // 请求错误处理

    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  (response) => {
    const res = response.data;
    // 如果返回的状态码不是 200，抛出错误
    if (res.code !== 200) {
      handleErrorResponse(res.code, res.msg);

      return Promise.reject(new Error(res.msg || "Error"));
    } else {
      return res;
    }
  },
  (error) => {
    let message = error.message;
    if (error.response) {
      switch (error.response.status) {
        case 400:
          message = "请求错误";
          break;
        case 401:
          message = "未授权，请重新登录";
          handleUnauthorized();
          break;
        case 403:
          message = "禁止访问";
          break;
        case 404:
          message = "请求地址出错";
          break;
        case 500:
          message = "服务器内部错误";
          break;
        default:
          message = "未知错误";
      }
    }
    handleErrorResponse(error.response ? error.response.status : 500, message);
    return Promise.reject(error);
  }
);

function handleErrorResponse(code, msg) {
  console.log("🚀 ~ handleErrorResponse ~ code:", code)
  const userStore = useUserStore();
  ElNotification({
    title: "错误",
    message: msg,
    type: "error",
  });
  userStore.reset();
  if (code === 401) {
    handleUnauthorized();
  }
}

function handleUnauthorized() {
  const userStore = useUserStore();
  userStore.reset();

  router.push("/login");
}

export default service;
