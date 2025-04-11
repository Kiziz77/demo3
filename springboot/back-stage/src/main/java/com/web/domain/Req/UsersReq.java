package com.web.domain.Req;

import com.web.utils.PageInfo;
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
public class UsersReq  extends PageInfo {


      public PageInfo getPageInfo() {
    return this;
  }

      @ApiModelProperty("用户名")
      private String userName;

      @ApiModelProperty("邮箱")
      private String email;

      @ApiModelProperty("电话")
      private String phone;



}
