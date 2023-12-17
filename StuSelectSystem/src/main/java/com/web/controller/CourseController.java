package com.web.controller;

import com.web.domain.po.Course;
import com.web.domain.po.Result;
import com.web.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/course")
@CrossOrigin
public class CourseController {
    @Resource
    CourseService courseService;

    //管理员根据条件获取课程信息
    @GetMapping
    public Result getList(Integer id, String cno, String cname, Integer majorId) {
        List<Course> courseList = courseService.getAllOrByMsg(id, cno, cname, majorId);
        return Result.success(courseList);
    }

    //管理员新增课程信息
    @PostMapping
    public Result insert(@RequestBody Course course) {
        boolean ret = courseService.insert(course);
        if (!ret)
            return Result.error("新建失败");
        else
            return Result.success();
    }

    //管理员删除课程
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean ret = courseService.deleteById(id);
        if (!ret)
            return Result.error("删除失败");
        else
            return Result.success();
    }

    //管理员修改课程信息
    @PutMapping
    public Result update(@RequestBody Course course) {
        boolean ret = courseService.updateInfo(course);
        if (!ret)
            return Result.error("更新失败");
        else
            return Result.success();
    }

}
