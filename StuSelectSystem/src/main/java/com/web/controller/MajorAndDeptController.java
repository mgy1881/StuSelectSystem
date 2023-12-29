package com.web.controller;

import com.web.domain.po.Dept;
import com.web.domain.po.Major;
import com.web.domain.po.Result;
import com.web.service.DeptService;
import com.web.service.MajorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jiaowu/")
@CrossOrigin
public class MajorAndDeptController {
    @Resource
    MajorService majorService;

    @Resource
    DeptService deptService;
    @GetMapping("major")
    public Result getMajorList(Integer id){
        List<Major> majorList = majorService.getMajorList(id);
        return Result.success(majorList);
    }
    @GetMapping("dept")
    public Result getDeptList(Integer id){
        List<Dept> deptList = deptService.getDeptList(id);
        return Result.success(deptList);
    }
}
