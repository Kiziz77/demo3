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
                buttons: [],
                menu: "商品管理",
                tableName: "products",
              },
            ],
            menu: "商品管理",
          },
          {
            child: [
              {
                buttons: [],
                menu: "订单管理",
                tableName: "productOrder",
              },
            ],
            menu: "订单管理",
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
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "药品预约",
                tableName: "drugReservation",
              },
            ],
            menu: "药品预约",
          },
          {
            child: [
              {
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "供应商信息",
                tableName: "supplierInfo",
              },
            ],
            menu: "供应商信息",
          },
          {
            child: [
              {
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "药品信息",
                tableName: "drugInfo",
              },
            ],
            menu: "药品信息",
          },
          {
            child: [
              {
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "购买订单",
                tableName: "customerOrder",
              },
            ],
            menu: "购买订单",
          },
          {
            child: [
              {
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "药品展示",
                tableName: "drugDisplay",
              },
            ],
            menu: "药品展示",
          },
          {
            child: [
              {
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "销售记录",
                tableName: "salesRecord",
              },
            ],
            menu: "销售记录",
          },
          {
            child: [
              {
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "采购记录",
                tableName: "purchaseRecord",
              },
            ],
            menu: "采购记录",
          },
          {
            child: [
              {
                buttons: ["新增", "查看", "修改", "删除"],
                menu: "库存信息",
                tableName: "inventory",
              },
            ],
            menu: "库存信息",
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
        backMenu: [],
        hasBackLogin: "否",
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
