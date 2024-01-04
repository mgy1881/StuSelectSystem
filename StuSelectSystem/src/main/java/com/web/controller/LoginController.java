package com.web.controller;

import com.web.domain.po.LoginInfo;
import com.web.utils.Result;
import com.web.service.AdminService;
import com.web.service.StudentService;
import com.web.service.TeacherService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    @Resource
    StudentService studentInfoService;
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
