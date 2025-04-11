package com.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.Comments;
import com.web.domain.Req.CommentsReq;
import com.web.utils.PageUtils;


/**

* @description 针对表【comments(评论留言表)】的数据库操作Service
*/
public interface CommentsService extends IService<Comments> {

    void insert(Comments comments);

    PageUtils lists(CommentsReq commentsReq);

    void deleteById(Integer id);

    PageUtils selectPage(CommentsReq req);
}
