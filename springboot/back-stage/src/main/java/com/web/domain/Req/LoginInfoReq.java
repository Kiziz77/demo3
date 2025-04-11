package com.web.domain.Req;

import com.web.utils.PageInfo;
import lombok.Data;

/**
 * ClassName: LoginInfoRes
 * Description:
 *
 * @Author Mr.hu
 * @Create 2025-02-11 19:31
 */
@Data
public class LoginInfoReq extends PageInfo {

    public PageInfo getPageInfo() {
        return this;
    }

}
