package com.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/** 
 * token表
 */
@Data
@TableName("token")
public class Token implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
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

	public Token() {
	}

	public Token(Integer userId, String userName, String tableName, String role, String token, Date expiratedTime) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.tableName = tableName;
		this.role = role;
		this.token = token;
		this.expiratedTime = expiratedTime;
	}
}
