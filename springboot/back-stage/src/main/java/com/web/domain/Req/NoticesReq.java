package com.web.domain.Req;

import com.web.utils.PageInfo;
import lombok.Data;

/**
 * 公告
 */
@Data
public class NoticesReq extends PageInfo {


    private String title;
    public PageInfo getPageInfo() {
       return this;
    }

}
