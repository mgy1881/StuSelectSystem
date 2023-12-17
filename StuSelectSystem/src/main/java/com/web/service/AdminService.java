package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.po.Admin;
import com.web.domain.po.LoginInfo;

public interface AdminService extends IService<Admin> {
    String LoginCheck(LoginInfo loginInfo);
}
