package com.wukong.sso.service;

import com.wukong.common.pojo.WukongResult;
import com.wukong.pojo.TbUser;

public interface UserService {

	WukongResult checkDate(String data, int type);

	WukongResult register(TbUser user);

	WukongResult login(String username, String password);

	WukongResult getUserByToken(String token);

	WukongResult logout(String token);
	TbUser geTbUser(String token);
}
