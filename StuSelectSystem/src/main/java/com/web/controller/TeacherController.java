package com.web.controller;

import com.web.domain.po.Result;
import com.web.domain.po.Teacher;
import com.web.service.TeacherService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@Slf4j
@RestController
@RequestMapping("/admin/teacher")
@CrossOrigin
public class TeacherController {
    @Resource
    TeacherService teacherService;

    //管理员根据条件获取教师信息
    @GetMapping
    public Result getTeacherInfo(String tno, String tname,String tjob, Integer tdeptId,Integer tgender){
        List<Teacher> teacherList = teacherService.selectAllOrByMsg( tno, tname,tjob,tdeptId,tgender);
        return Result.success(teacherList);
    }

    //管理员新增教师
    @PostMapping
    public Result addTeacherInfo(@RequestBody Teacher teacher){
        boolean ret = teacherService.add(teacher);
        if(ret)
            return Result.success();
        else
            return Result.error("添加失败");
    }

    //管理员删除教师
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

    //管理员修改教师信息
    @PutMapping
    public Result updateTeacherInfo(@RequestBody Teacher teacher){
        boolean ret = teacherService.updateTeacherInfo(teacher);
        if(ret)
            return Result.success();
        else
            return Result.error("修改失败");
    }


}
