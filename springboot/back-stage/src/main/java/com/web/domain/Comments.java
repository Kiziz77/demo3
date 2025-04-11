package com.web.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论留言表
 * @TableName comments
 */
@Data
@TableName(value ="comments")
public class Comments implements Serializable {
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
     * 评论内容
     */
    private String commentVal;

    /**
     * 关联主体ID（例:帖子ID/活动ID）
     */
    private Integer momentId;

    /**
     * 评论类型(例:1.帖子,2.活动)
     */
    private Long commentType;

    /**
     * 直接父级ID(顶级留言ID;子级留言ID)
     */
    private Integer parentId;

    /**
     * 顶级评论ID(区分顶级留言和子留言)
     */
    private Integer rootCommentId;

    /**
     * 业务状态：1 评论 2 回复
     */
    private Integer status;

    /**
     * 回复详情
     */
    private String replyComment;

    /**
     * 留言图片
     */
    private String imageUrls;

    /**
     * 创建人
     */
    private Integer createdBy;

    /**
     * 创建时间
     */

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人
     */
    private Integer updatedBy;

    /**
     * 更新时间
     */

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 是否删除(0未删除;1已删除)
     */
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}