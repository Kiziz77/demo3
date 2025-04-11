package com.web.domain.Req;

import com.web.utils.PageInfo;
import lombok.Data;

/**
 * <p>
 * 配置文件
 * </p>
 *
 * 
 * 
 */
@Data
public class ConfigReq extends PageInfo {


    public PageInfo getPageInfo() {
        return this;
    }
}
