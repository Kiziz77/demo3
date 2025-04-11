package com.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.Comments;
import com.web.domain.LikeCollect;
import com.web.domain.Req.CommentsReq;
import com.web.domain.Resp.LikeCollectResp;
import com.web.utils.PageUtils;

import java.util.List;
import java.util.Map;


/**

* @description 点赞收藏Service
*/
public interface LikeCollectService extends IService<LikeCollect> {


    void likeCollect(LikeCollect likeCollect);

    Map<String, Integer> queryLikeCollect(LikeCollect likeCollect);

    List<LikeCollectResp> queryMyCollect();

    void deleteByIdTableName(Long id, String products);
}
