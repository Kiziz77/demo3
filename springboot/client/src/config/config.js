export default {
  baseUrl: "http://localhost:9999",
  projectTitle: "药房销售管理系统",
  isHeader: true,
  NAV_KEY: "keyPath",
  CURRENT_SESSION_ROLE_KEY: "role",
  CURRENT_SESSION_ROLE_NAME_KEY: "roleName",
  CURRENT_SESSION_TABLE_KEY: "sessionTable",
  CURRENT_LOGIN_NAME: "loginName",
  indexNav: [
    {
      name: "首页",
      url: "/index/home",
    },
     {
      name: "药品信息",
      url: "/index/products",
    },
    {
      name: "公告信息",
      url: "/index/notices",
    },
    {
        name: "药品展示信息",
        url: "/index/drugDisplay",
    },

  ],
};
