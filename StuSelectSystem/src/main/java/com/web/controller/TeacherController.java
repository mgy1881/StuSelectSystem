package com.web.controller;

import com.web.pojo.LoginInfo;
import com.web.pojo.Result;
import com.web.pojo.Teacher;
import com.web.service.TeacherService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@Slf4j
@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {
    @Resource
    TeacherService teacherService;

    @GetMapping
    public Result getTeacherInfo(String tno, String tname,String tjob, Integer tdeptId,Integer tgender){
        List<Teacher> teacherList = teacherService.selectAllOrByMsg( tno, tname,tjob,tdeptId,tgender);
        return Result.success(teacherList);
    }

    @PostMapping
    public Result addTeacherInfo(@RequestBody Teacher teacher){
        boolean ret = teacherService.add(teacher);
        if(ret)
            return Result.success();
        else
            return Result.error("添加失败");
    }

    @DeleteMapping("/{id}")
    public Result deleteTeacherById(@PathVariable Integer id){
        boolean ret = teacherService.deleteById(id);
        if(ret){
            return Result.success();
        }
        else{
            return Result.error("删除失败");
        }
    }

    @PutMapping
    public Result updateTeacherInfo(@RequestBody Teacher teacher){
        boolean ret = teacherService.updateTeacherInfo(teacher);
        if(ret)
            return Result.success();
        else
            return Result.error("修改失败");
    }


}
