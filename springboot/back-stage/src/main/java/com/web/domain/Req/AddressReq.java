package com.web.domain.Req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.utils.PageInfo;
import lombok.Data;

import java.util.Date;

/**
 * 收获地址表
 * @TableName Address
 */
@Data
public class AddressReq extends PageInfo {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 地址
     */
    private String address;

    /**
     * 收货人
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 是否默认地址[ 0 是/ 1 否]
     */
    private int isDefault;

    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}