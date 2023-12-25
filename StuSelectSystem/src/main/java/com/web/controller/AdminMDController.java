package com.web.controller;

import com.web.domain.po.Course;
import com.web.domain.po.Dept;
import com.web.domain.po.Major;
import com.web.domain.po.Result;
import com.web.service.MajorAndSdeptService;
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
    MajorAndSdeptService majorAndSdeptService;
    @PutMapping("/major")
    public Result updateMajor(@RequestBody Major major){
        boolean ret = majorAndSdeptService.updateMajorInfo(major);
        if(ret)
            return Result.success();
        else
            return Result.error("修改错误");
    }

//    @PutMapping("/dept")
//    public Result updateDept(@RequestBody Dept dept){
//        boolean ret = majorAndSdeptService.updateDeptInfo(dept);
//        if(ret)
//            return Result.success();
//        else
//            return Result.error("修改错误");
//    }

    @PostMapping("/new_major")
    public Result addMajorInfo(@RequestBody Major major) {
        try{
            boolean ret = majorAndSdeptService.add(major);
            if(ret)
                return Result.success();
            else
                return Result.error("添加失败");
        } catch (Exception e){
            return Result.error("添加失败");
        }
    }

//    @PostMapping("/new_dept")
//    public Result addDeptInfo(@RequestBody Dept dept){
//        try{
//            boolean ret = majorAndSdeptService.addDept(dept);
//            if(ret)
//                return Result.success();
//            else
//                return Result.error("添加失败");
//        } catch (Exception e){
//            return Result.error("添加失败");
//        }
//    }
@DeleteMapping("/delete_major/{id}")
public Result deleteMajorById(@PathVariable Integer id) {
    boolean ret = majorAndSdeptService.removeById(id);
    if (ret) {
        return Result.success();
    } else {
        return Result.error("删除失败");
    }
}


}
