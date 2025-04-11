package com.web.domain.Req;

import com.baomidou.mybatisplus.annotation.*;
import com.web.utils.PageInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * 点赞收藏表
 * @TableName like_collect
 */
@Data
public class LikeCollectReq extends PageInfo {


    private Integer userId;

    /**
     * 关联主体ID（数据id）
     */
    private Integer momentId;

    /**
     * '关联模块（表名 or ''标识''）'
     */
    private String signName;

    /**
     * 路由
     */
    private String fullPath;

    /**
     * 展示图片字段
     */
    private String picturesField;

    /**
     * 展示标题字段
     */
    private String titleField;
    public PageInfo getPageInfo() {
        return this;
    }

}