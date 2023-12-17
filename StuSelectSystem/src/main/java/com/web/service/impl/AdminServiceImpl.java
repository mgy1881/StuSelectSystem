package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.AdminDao;
import com.web.domain.po.Admin;
import com.web.domain.po.LoginInfo;
import com.web.service.AdminService;
import com.web.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminDao, Admin> implements AdminService {

    @Override
    public String LoginCheck(LoginInfo loginInfo) {
        Admin admin = lambdaQuery()
                .eq(Admin::getUsername, loginInfo.getUsername())
                .eq(Admin::getPassword, loginInfo.getPassword())
                .one();
        if (admin == null)
            return null;
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", admin.getId());
        claims.put("level", loginInfo.getLevel());
        return JwtUtils.generateJwt(claims);
    }
}
