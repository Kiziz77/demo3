package com.web.domain.Resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/** 
 * token表
 */
@Data
public class TokenResp implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 角色
	 */
	private String role;
	
	/**
	 * token凭证
	 */
	private String token;
	
	/**
	 * 过期时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date expiratedTime;
	
	/**
	 * 新增时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	
}
