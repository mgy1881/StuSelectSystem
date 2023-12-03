package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.Admin;
import com.web.pojo.LoginInfo;

public interface AdminService extends IService<Admin> {
    String LoginCheck(LoginInfo loginInfo);
}
