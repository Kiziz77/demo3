const menu = {
  list() {
    return [
      {
        backMenu: [
          {
            child: [
              {
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "用户",
                menuJump: "列表",
                tableName: "users",
              },
            ],
            menu: "用户管理",
          },
          {
            child: [
              {
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "公告信息",
                tableName: "notices",
              },
            ],
            menu: "公告信息",
          },

          {
            child: [
              {
                buttons: ["查看", "修改", "删除"],
                menu: "轮播图管理",
                tableName: "config",
              },
            ],
            menu: "系统管理",
          },
        ],

        hasBackLogin: "是",
        hasBackRegister: "否",
        hasFrontLogin: "否",
        hasFrontRegister: "否",
        roleName: "管理员",
        tableName: "managers",
      },
      {
        backMenu: [
          {
            child: [
              {
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "公告信息",
                tableName: "notices",
              },
            ],
            menu: "公告信息",
          },
        ],
        hasBackLogin: "是",
        hasBackRegister: "否",
        hasFrontLogin: "是",
        hasFrontRegister: "是",
        roleName: "用户",
        tableName: "users",
      },
    ];
  },
};
export default menu;
