package com.web.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.Comments;
import com.web.domain.LikeCollect;
import com.web.domain.Req.CommentsReq;
import com.web.domain.Resp.CommentsResp;
import com.web.domain.Resp.LikeCollectResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @description 点赞收藏Mapper
*/
@Mapper
public interface LikeCollectMapper extends BaseMapper<LikeCollect> {

    List<LikeCollectResp> generalQueryCollect(@Param("ids") List<Integer> ids, @Param("tableName")String tableName, @Param("picturesField")String picturesField, @Param("titleField")String titleField);
}




