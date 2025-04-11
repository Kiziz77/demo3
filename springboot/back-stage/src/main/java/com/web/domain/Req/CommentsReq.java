package com.web.domain.Req;

import com.web.utils.PageInfo;
import lombok.Data;


/**
 * 评论留言表
 * @TableName comments
 */
@Data
public class CommentsReq extends PageInfo {


    private Integer userId;

    /**
     * 关联主体ID（例:帖子ID/活动ID）
     */
    private Integer momentId;

    /**
     * 评论类型(例:1.帖子,2.活动)
     */
    private Long commentType;

    public PageInfo getPageInfo() {
        return this;
    }

}