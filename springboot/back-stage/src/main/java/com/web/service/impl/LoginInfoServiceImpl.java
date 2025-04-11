package com.web.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.LoginInfo;
import com.web.domain.Req.LoginInfoReq;
import com.web.domain.Resp.LoginInfoResp;
import com.web.mapper.LoginInfoMapper;
import com.web.service.LoginInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * 
 * 
 */
@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoMapper, LoginInfo> implements LoginInfoService {

    @Resource
    private LoginInfoMapper loginInfoMapper;


    @Override
    public PageUtils selectPage(LoginInfoReq req) {
        Page<LoginInfoResp> page = new Query<LoginInfoResp>(req.getPageInfo()).getPage();
        page.setRecords(loginInfoMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    @Override
    public LoginInfoResp info(Integer id) {
        return BeanUtil.copyProperties(loginInfoMapper.selectById(id), LoginInfoResp.class);
    }

    @Override
    // 保存登录信息
    public void saveLoginInfo(String userName, String userIp, String userAgent, String referer, String sessionId) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName(userName);
        loginInfo.setUserIp(userIp);
        loginInfo.setUserAgent(userAgent);
        loginInfo.setReferer(referer);
        loginInfo.setSessionId(sessionId);
        loginInfo.setLoginTime(new Date());


        // 保存到数据库
        baseMapper.insert(loginInfo);
    }

    @Override
    public List<Map<String, Object>> getData1() {
        return baseMapper.getData1();
    }

    private String getLocationFromIp(String ip) {
        String urlString = "http://ip-api.com/json/" + ip; // ip-api 的查询接口

        try {
            // 创建 URL 对象
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 获取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 解析 JSON 响应
            JSONObject jsonResponse = new JSONObject(response.toString());

            // 提取省份和城市等信息
            String country = jsonResponse.getStr("country", "Unknown");
            String region = jsonResponse.getStr("regionName", "Unknown");
            String city = jsonResponse.getStr("city", "Unknown");

            return String.format("Country: %s, Region: %s, City: %s", country, region, city);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving location for IP: " + ip;
        }
    }
}
