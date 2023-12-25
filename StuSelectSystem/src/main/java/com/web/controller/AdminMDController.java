package com.web.controller;

import com.web.domain.po.Course;
import com.web.domain.po.Major;
import com.web.domain.po.Result;
import com.web.service.MajorAndSdeptService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")

public class AdminMDController {

    @Autowired
    HttpServletRequest request;

    @Resource
    MajorAndSdeptService majorAndSdeptService;
//    @PutMapping("/major")
//    public Result updateMajor(@RequestBody Major major){
//        boolean ret = majorAndSdeptService.updateMajorInfo(major);
//        if(ret)
//            return Result.success();
//        else
//            return Result.error("修改错误");
//    }
}
