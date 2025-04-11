package com.web.domain.Resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 登录日志
 * </p>
 *
 * 
 * 
 */
@Data
@TableName("login_info")
public class LoginInfoResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("登录ip")
    private String userIp;

    @ApiModelProperty("登录浏览器")
    private String userAgent;

    @ApiModelProperty("来源URL")
    private String referer;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    @ApiModelProperty("会话id")
    private String sessionId;

    @ApiModelProperty("登录地点")
    private String location;


}
