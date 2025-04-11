package com.web.domain.Resp;

import com.baomidou.mybatisplus.annotation.*;
import com.web.domain.LikeCollect;
import lombok.Data;

import java.io.Serializable;

/**
 * 点赞收藏表
 * @TableName like_collect
 */
@Data
public class LikeCollectResp extends LikeCollect implements Serializable  {

    private String pictures;

    private String title;
}