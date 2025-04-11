package com.web.controller;

import com.web.domain.Comments;
import com.web.domain.Req.CommentsReq;
import com.web.service.CommentsService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@RestController
@RequestMapping("/comments")
public class CommentsController {


    @Resource
    private CommentsService commentsService;


    @PostMapping("/page")
    public Result page(@RequestBody CommentsReq req){
        PageUtils page = commentsService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 前台发表评论
     * @param comments
     * @return
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody Comments comments) {
        commentsService.insert(comments);
        return Result.success();
    }


    /**
     * 前台获取评论列表
     * @param commentsReq
     * @return
     */
    @PostMapping("/lists")
    public Result delete(@RequestBody CommentsReq commentsReq) {
        PageUtils pageVo =  commentsService.lists(commentsReq);
        return Result.success(pageVo);
    }


    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        commentsService.deleteById(id);
        return Result.success();
    }


    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        return Result.success(commentsService.removeById(id));
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Integer[] ids){
        return Result.success(commentsService.removeBatchByIds(Arrays.asList(ids)));
    }
}
