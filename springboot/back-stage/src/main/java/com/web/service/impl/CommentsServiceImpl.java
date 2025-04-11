package com.web.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.domain.Comments;
import com.web.domain.Req.CommentsReq;
import com.web.domain.Resp.CommentsResp;
import com.web.mapper.CommentsMapper;
import com.web.service.CommentsService;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
* @description 针对表【comments(评论留言表)】的数据库操作Service实现
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    @Resource
    private HttpServletRequest request;

    @Override
    public void insert(Comments comments) {
        Integer currentUserId = (Integer) request.getSession().getAttribute("userId");
        Date now = new Date();
        comments.setUserId(currentUserId);
        comments.setCreatedBy(currentUserId);
        comments.setUpdatedBy(currentUserId);
        comments.setCreateTime(now);
        comments.setUpdateTime(now);

        if(ObjectUtil.isNull(comments.getParentId())){
            comments.setParentId(0);
            //设置评论业务状态  1:评论   2:回复
            comments.setStatus(1);
        }else {
            comments.setStatus(2);
        }

        baseMapper.insert(comments);
    }

    @Override
    public PageUtils lists(CommentsReq req) {
        Page<CommentsResp> page = new Query<CommentsResp>(req.getPageInfo()).getPage();
        //查询根评论数据
        List<CommentsResp> rootComments = baseMapper.getAllRootComments(page,req);
        for (CommentsResp commentResp : rootComments) {
            commentResp.setChildren(getChildrenComments(commentResp.getId(),req.getMomentId()));
        }
        page.setRecords(rootComments);
        return new PageUtils(page);
    }

    @Override
    public void deleteById(Integer id) {

        // 删除所有子评论（包括二级、三级等）
        deleteAllCommentsByParentId(id);

        //删除评论
        baseMapper.deleteById(id);

    }

    @Override
    public PageUtils selectPage(CommentsReq req) {
        Page<CommentsResp> page = new Query<CommentsResp>(req.getPageInfo()).getPage();
        page.setRecords(baseMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    private void deleteAllCommentsByParentId(Integer parentId) {

        // 递归删除所有子评论
        List<Comments> children = findByParentId(parentId);
        for (Comments comment : children) {
            deleteAllCommentsByParentId(comment.getId());
        }

        deleteByParentId(parentId);
    }

    private void deleteByParentId(Integer parentId) {
        LambdaQueryWrapper<Comments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comments::getParentId, parentId);
        baseMapper.delete(queryWrapper);
    }


    private List<Comments> findByParentId(Integer parentId) {
        LambdaQueryWrapper<Comments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comments::getParentId, parentId);
        return baseMapper.selectList(queryWrapper);
    }


    /**
     * 获取子评论
     * @param parentId 父评论id
     * @param momentId  主体id
     * @return
     */
    private List<CommentsResp> getChildrenComments(Integer parentId,Integer momentId){
        // 查所有的子评论（需要的是该子评论的直接父评论ID，一开始从二级评论开始查，也就是调用此方法时传进来的顶级评论id[这就是二级评论的直接父评论ID]；还有关联主体id）
        List<CommentsResp> commentsVOList = baseMapper.getChildren(parentId, momentId);

        for (CommentsResp commentsResp : commentsVOList) {

            commentsResp.setChildren(getChildrenComments(commentsResp.getId(),momentId));
        }
        return commentsVOList;
    }
}




