package com.web.domain.Resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论留言表
 * @TableName comments
 */
@Data
public class CommentsResp implements Serializable {

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


    private Integer deleted;


    // 子评论列表
    private List<CommentsResp> children;


    private String username;

    /**
     * 被回复人用户名
     */
    private String replyUsername;

    private String title;

    private String avatar;
}