package com.web.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.Comments;
import com.web.domain.Req.CommentsReq;
import com.web.domain.Resp.CommentsResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @description 针对表【comments(评论留言表)】的数据库操作Mapper
*/
@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {

    List<CommentsResp> getAllRootComments(Page page, @Param("req") CommentsReq req);

    List<CommentsResp> getChildren(@Param("parentId") Integer parentId,@Param("momentId") Integer momentId);

    List<CommentsResp> selectRespList(Page page,@Param("req")  CommentsReq req);
}




