
package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.Token;



/**
 * token
 */
public interface TokenService extends IService<Token> {

   	String generateToken(Integer userId,String userName,String tableName, String role);
   	
   	Token getTokenEntity(String token);
}
