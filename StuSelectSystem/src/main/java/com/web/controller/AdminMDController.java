package com.web.controller;

import com.web.domain.po.Dept;
import com.web.domain.po.Major;
import com.web.domain.po.Result;
import com.web.service.DeptService;
import com.web.service.MajorService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")

public class AdminMDController {

    @Autowired
    HttpServletRequest request;
    @Resource
    MajorService majorService;
    @Resource
    DeptService deptService;

    @PutMapping("/major")
    public Result updateMajor(@RequestBody Major major) {
        boolean ret = majorService.updateMajorInfo(major);
        try {
            if (ret)
                return Result.success();
            else
                return Result.error("修改错误");
        } catch (Exception e) {
            return Result.error(e.getMessage().length() > 10 ? "修改失败" : e.getMessage());
        }
    }

    @PutMapping("/dept")
    public Result updateDept(@RequestBody Dept dept) {
        try {
            boolean ret = deptService.updateDeptInfo(dept);
            if (ret)
                return Result.success();
            else
                return Result.error("修改错误");
        } catch (Exception e) {
            return Result.error(e.getMessage().length() > 10 ? "修改失败" : e.getMessage());
        }

    }

    @PostMapping("/major")
    public Result addMajorInfo(@RequestBody Major major) {
        try {
            boolean ret = majorService.add(major);
            if (ret)
                return Result.success();
            else
                return Result.error("添加失败");
        } catch (Exception e) {
            return Result.error(e.getMessage().length() > 10 ? "添加失败" : e.getMessage());
        }
    }

    @PostMapping("/dept")
    public Result addDeptInfo(@RequestBody Dept dept) {
        try {
            boolean ret = deptService.add(dept);
            if (ret)
                return Result.success();
            else
                return Result.error("添加失败");
        } catch (Exception e) {
            return Result.error(e.getMessage().length() > 10 ? "添加失败" : e.getMessage());
        }
    }

    @DeleteMapping("/major/{id}")
    public Result deleteMajorById(@PathVariable Integer id) {
        try {
            boolean ret = majorService.delete(id);
            if (ret) {
                return Result.success();
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage().length() > 10 ? "删除失败" : e.getMessage());
        }
    }

    @DeleteMapping("/dept/{id}")
    public Result deleteDeptById(@PathVariable Integer id) {
        try {
            boolean ret = deptService.delete(id);
            if (ret) {
                return Result.success();
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage().length() > 10 ? "删除失败" : e.getMessage());
        }

    }
}
