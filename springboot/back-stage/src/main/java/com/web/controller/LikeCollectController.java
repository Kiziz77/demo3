package com.web.controller;

import com.web.domain.LikeCollect;
import com.web.domain.Resp.LikeCollectResp;
import com.web.service.LikeCollectService;
import com.web.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/likeCollect")
public class LikeCollectController {

    @Autowired
    private LikeCollectService service;

    /**
     * 点赞/收藏
     * @param likeCollect
     * @return
     */
    @PostMapping("/likeCollect")
    public Result likeCollect(@RequestBody LikeCollect likeCollect) {
        service.likeCollect(likeCollect);
        return Result.success();
    }

    /**
     * 查询是否点赞
     * @param likeCollect
     * @return
     */
    @PostMapping("/queryLikeCollect")
    public Result queryLikeCollect(@RequestBody LikeCollect likeCollect) {
        Map<String,Integer> map = service.queryLikeCollect(likeCollect);
        return Result.success(map);
    }

    /**
     * 查询我的收藏
     * @return
     */
    @GetMapping("/queryMyCollect")
    public Result queryMyCollect() {
        List<LikeCollectResp> list = service.queryMyCollect();
        return Result.success(list);
    }

}
