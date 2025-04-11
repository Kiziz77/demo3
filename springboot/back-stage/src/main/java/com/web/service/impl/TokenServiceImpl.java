package com.web.service.impl;



import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.domain.Token;
import com.web.mapper.TokenMapper;
import com.web.utils.SnowFlake;
import org.springframework.stereotype.Service;

import com.web.service.TokenService;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;


/**
 * token
 */
@Service("tokenService")
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

	@Resource
	private TokenMapper tokenMapper;

	@Resource
	private SnowFlake snowFlake;

	@Override
	public String generateToken(Integer userId, String userName, String tableName, String role) {
		Token selectOne = baseMapper.selectOne(
				Wrappers.lambdaQuery(Token.class)
						.eq(Token::getUserId, userId)
						.eq(Token::getUserName, userName)
						.eq(Token::getTableName, tableName)
						.eq(Token::getRole, role)
		);
		String token = String.valueOf(snowFlake.nextId());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 1);
		if (selectOne != null){
			selectOne.setToken(token);
			selectOne.setExpiratedTime(cal.getTime());
			tokenMapper.updateById(selectOne);
		}else {
			baseMapper.insert(new Token(userId, userName, tableName, role, token, cal.getTime()));
		}
		return token;
	}

	@Override
	public Token getTokenEntity(String token) {
		Token selectOne = tokenMapper.selectOne(
				Wrappers.lambdaQuery(Token.class).eq(Token::getToken, token)
		);
		if (selectOne == null || selectOne.getExpiratedTime().getTime() < System.currentTimeMillis()) {
			return null;
		}
		return selectOne;
	}
}
