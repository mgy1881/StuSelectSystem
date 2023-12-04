package com.web.controller;

import com.web.pojo.LoginInfo;
import com.web.pojo.Result;
import com.web.pojo.Teacher;
import com.web.service.AdminService;
import com.web.service.StudentInfoService;
import com.web.service.TeacherService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    StudentInfoService studentInfoService;
    @Resource
    AdminService adminService;
    @Resource
    TeacherService teacherService;

    @PostMapping
    public Result LoginCheck(@RequestBody LoginInfo loginInfo) {
        Integer level = loginInfo.getLevel();
        String jwt = null;
        if (level == 0) {
            //校验管理员信息
            jwt = adminService.LoginCheck(loginInfo);
//            return Result.error("用户名或密码错误");
        }
        if (level == 1) {
            jwt = teacherService.LoginCheck(loginInfo);
            //校验教师信息

        }
        if (level == 2) {
            jwt = studentInfoService.LoginCheck(loginInfo);
        }
        if (jwt != null)
            return Result.success(jwt);
        else
            return Result.error("用户名或密码错误");
    }
}
