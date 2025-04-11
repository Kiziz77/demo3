package com.web.domain.Resp;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户
 * </p>
 *
 * 
 * 
 */
@Data
public class UsersResp implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      private Integer id;

      @ApiModelProperty("用户名")
      private String userName;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("头像")
      private String avatar;

      @ApiModelProperty("余额")
      private Double balance;

      @ApiModelProperty("邮箱")
      private String email;

      @ApiModelProperty("电话")
      private String phone;

      @ApiModelProperty("性别")
      private String gender;

      @ApiModelProperty("创建时间")
      @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
      private Date createTime;


      @ApiModelProperty("修改时间")
      @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
      private Date updateTime;


}
