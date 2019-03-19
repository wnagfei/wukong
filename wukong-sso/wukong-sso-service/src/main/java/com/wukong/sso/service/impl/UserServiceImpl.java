package com.wukong.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.wukong.common.pojo.WukongResult;
import com.wukong.common.utils.JsonUtils;
import com.wukong.jedis.JedisClient;
import com.wukong.mapper.TbCartMapper;
import com.wukong.mapper.TbItemMapper;
import com.wukong.mapper.TbUserMapper;
import com.wukong.pojo.TbUser;
import com.wukong.pojo.TbUserExample;
import com.wukong.pojo.TbUserExample.Criteria;
import com.wukong.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private TbUserMapper userMapper;
	@Resource
	private JedisClient jedisClient;
	@Resource
	private TbCartMapper cartMapper;
	@Resource
	private TbItemMapper itemMapper;
	@Value("${USER_SESSION}")
	private String USER_SESSION;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	@Value("${CART_KEY}")
	private String CART_KEY;
	@Value("${CART_EXPIER}")
	private Integer CART_EXPIER;
	@Value("WU_KONG")
	private String WU_KONG;

	@Override
	public WukongResult checkDate(String data, int type) {
		// TODO Auto-generated method stub
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		// 判断用户名是否可用
		if (type == 1) {
			criteria.andUsernameEqualTo(data);
		} // 判断手机号是否可以使用
		else if (type == 2) {
			criteria.andPhoneEqualTo(data);
		} // 判断邮箱是否可用
		else if (type == 3) {
			criteria.andEmailEqualTo(data);
		} else {
			return WukongResult.build(400, "非法数据");
		}
		// 执行查询
		List<TbUser> selectByExample = userMapper.selectByExample(example);
		if (selectByExample != null && selectByExample.size() > 0) {
			// 查询数据不可用
			return WukongResult.ok(false);
		}
		// 数据可用
		return WukongResult.ok(true);
	}

	@Override
	public WukongResult register(TbUser user) {
		// TODO Auto-generated method stub
		// 检查数据是否有效
		if (StringUtils.isBlank(user.getUsername())) {
			return WukongResult.build(400, "用户名不能为空");
		}
		// 用户名不能重复
		WukongResult wukongResult = checkDate(user.getUsername(), 1);
		if (!(boolean) wukongResult.getData()) {
			return WukongResult.build(400, "用户名重复");
		}
		// 密码不能为空
		if (StringUtils.isBlank(user.getPassword())) {
			return WukongResult.build(400, "密码不能为空");
		}
		// 验证手机
		if (StringUtils.isNotBlank(user.getPhone())) {
			// 是否重复校验
			wukongResult = checkDate(user.getPhone(), 2);
			if (!(boolean) wukongResult.getData()) {
				return WukongResult.build(400, "电话号码重复");
			}
		}
		// 验证email
		if (StringUtils.isNotBlank(user.getEmail())) {
			// 是否重复校验
			wukongResult = checkDate(user.getEmail(), 2);
			if (!(boolean) wukongResult.getData()) {
				return WukongResult.build(400, "email重复");
			}
		}

		// 补全属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		// 进行md5加密
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5DigestAsHex);
		// 插入数据
		userMapper.insert(user);

		return WukongResult.ok();
	}

	@Override
	public WukongResult login(String username, String password) {
		// TODO Auto-generated method stub
		// 判斷用戶名密码正确
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> selectByExample = userMapper.selectByExample(example);
		if (selectByExample == null || selectByExample.size() == 0) {
			return WukongResult.build(400, "用户名或密码不正确");
		}
		TbUser user = selectByExample.get(0);

		// 密码进行md5加密再校验
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return WukongResult.build(400, "用户名或密码不正确");
		}

		// 生成token，使用uuid
		String token = UUID.randomUUID().toString();
		// 清空密码
		user.setPassword(null);
		// 把用户信息保存到redis，key为token，value就是用户信息
		jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user));

		// 设置过期时间
		jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);

		// 返回成功，把token返回
		return WukongResult.ok(token);
	}

	@Override
	public WukongResult getUserByToken(String token) {
		// TODO Auto-generated method stub
		String json = jedisClient.get(USER_SESSION + ":" + token);
		if (StringUtils.isBlank(json)) {
			return WukongResult.build(400, "登陆已过期");
		}
		// 重置过期时间
		jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
		// 把json转换成USer对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return WukongResult.ok(user);
	}

	@Override
	public WukongResult logout(String token) {
		// TODO Auto-generated method stub

		jedisClient.del(USER_SESSION + ":" + token);

		return WukongResult.ok();
	}

	@Override
	public TbUser geTbUser(String token) {
		// TODO Auto-generated method stub
		TbUser user = JsonUtils.jsonToPojo(jedisClient.get(USER_SESSION + ":" + token), TbUser.class);
		return user;
	}

}
