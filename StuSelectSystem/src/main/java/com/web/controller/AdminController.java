package com.web.controller;

import com.web.pojo.Admin;
import com.web.pojo.Result;
import com.web.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/info")
@CrossOrigin
public class AdminController {
    @Resource
    AdminService adminService;

    //新增管理员
    @PostMapping
    public Result insert(@RequestBody Admin admin) {
        adminService.save(admin);
        return Result.success();
    }
}
