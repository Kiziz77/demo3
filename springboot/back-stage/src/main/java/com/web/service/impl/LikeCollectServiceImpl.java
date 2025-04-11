package com.web.service.impl;



import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.domain.LikeCollect;

import com.web.domain.Resp.LikeCollectResp;
import com.web.exception.BusinessException;
import com.web.mapper.LikeCollectMapper;
import com.web.service.LikeCollectService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
* @description 点赞收藏service实现类
*/
@Service
public class LikeCollectServiceImpl extends ServiceImpl<LikeCollectMapper, LikeCollect> implements LikeCollectService {

    @Resource
    private HttpServletRequest request;

    /**
     * 点赞/收藏
     * @param param
     */
    @Override
    public void likeCollect(LikeCollect param) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        Integer momentId = param.getMomentId();
        String signName = param.getSignName();
        List<LikeCollect> list = baseMapper.selectList(new QueryWrapper<LikeCollect>().eq("moment_id", momentId).eq("user_id", userId).eq("sign_name", signName));
        Assert.isFalse(list.size() > 1,() -> new BusinessException("点赞/收藏数据异常！"));

        if (CollUtil.isEmpty(list)){
            // 新增点赞/收藏
            param.setUserId(userId);
            baseMapper.insert(param);
        }else {
            LikeCollect likeCollect = list.get(0);
            // 修改点赞/收藏状态
            likeCollect.setIsCollect(param.getIsCollect());
            likeCollect.setIsLike(param.getIsLike());
            baseMapper.updateById(likeCollect);
        }
    }

    /**
     * 查询点赞/收藏
     * @param likeCollect
     * @return
     */
    @Override
    public Map<String, Integer> queryLikeCollect(LikeCollect likeCollect) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        List<LikeCollect> list = baseMapper.selectList(new QueryWrapper<LikeCollect>().eq("moment_id", likeCollect.getMomentId()).eq("sign_name", likeCollect.getSignName()));
        Map<String, Integer> map = new HashMap<>();

        if (CollUtil.isNotEmpty(list)){
            List<LikeCollect> myLikeCollect = list.stream().filter(f -> f.getUserId().equals(userId)).collect(Collectors.toList());
            Assert.isFalse(myLikeCollect.size() > 1,() -> new BusinessException("点赞/收藏数据异常！"));

            map.put("isLike", CollUtil.isNotEmpty(myLikeCollect) ? myLikeCollect.get(0).getIsLike() : 0);
            map.put("isCollect",CollUtil.isNotEmpty(myLikeCollect) ? myLikeCollect.get(0).getIsCollect() : 0);

            long likeCount = list.stream().filter(f -> f.getIsLike() == 1).count();
            long collectCount = list.stream().filter(f -> f.getIsCollect() == 1).count();

            map.put("likeCount", (int) likeCount);
            map.put("collectCount", (int) collectCount);

        }else {
            map.put("isLike", 0);
            map.put("isCollect", 0);
            map.put("likeCount",0);
            map.put("collectCount",0);
        }
        return map;
    }

    /**
     * 我的收藏
     * @return
     */
    @Override
    public List<LikeCollectResp> queryMyCollect() {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        List<LikeCollect> myCollect = baseMapper.selectList(new LambdaQueryWrapper<LikeCollect>().eq(LikeCollect::getUserId, userId).eq(LikeCollect::getIsCollect, 1));
        List<LikeCollectResp> dataList = new ArrayList<>();

        if (CollUtil.isNotEmpty(myCollect)){

            Map<String, List<LikeCollect>> listMap = myCollect.stream().collect(Collectors.groupingBy(LikeCollect::getSignName));
            for (Map.Entry<String, List<LikeCollect>> entry : listMap.entrySet()) {
                List<LikeCollect> value = entry.getValue();
                List<Integer> ids = value.stream().map(LikeCollect::getId).collect(Collectors.toList());
                String tableName = entry.getKey();
                String picturesField = value.get(0).getPicturesField();
                String titleField = value.get(0).getTitleField();

                List<LikeCollectResp> response = baseMapper.generalQueryCollect(ids, tableName, picturesField, titleField);
                dataList.addAll(response);
            }
        }
        return dataList;
    }

    /**
     * 删除点赞收藏数据
     * @param id
     * @param products
     */
    @Override
    public void deleteByIdTableName(Long id, String products) {
        baseMapper.delete(new LambdaQueryWrapper<LikeCollect>().eq(LikeCollect::getMomentId, id).eq(LikeCollect::getSignName, products));
    }
}




