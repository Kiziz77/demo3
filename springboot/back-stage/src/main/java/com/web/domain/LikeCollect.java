package com.web.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 点赞收藏表
 * @TableName like_collect
 */
@Data
@TableName(value ="like_collect")
public class LikeCollect implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
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
     * 是否点赞 0：否 ；1：是
     */
    private Integer isLike;

    /**
     * 是否收藏 0：否 ；1：是
     */
    private Integer isCollect;

    /**
     * 是否删除(0未删除;1已删除)
     */
    @TableLogic
    private Integer deleted;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}