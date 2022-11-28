package com.tensquare.user.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */

@TableName("tb_admin")
public class Admin implements Serializable{

	@TableId(type = IdType.INPUT)
	private String id;//ID

	private String loginname;//登陆名称
	private String password;//密码
	private String state;//状态

	
}
