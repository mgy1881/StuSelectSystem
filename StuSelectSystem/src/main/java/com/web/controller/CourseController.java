package com.web.controller;

import com.web.pojo.Course;
import com.web.pojo.Result;
import com.web.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/course")
public class CourseController {
    @Resource
    CourseService courseService;

    @GetMapping
    public Result getList(Integer id, String cno, String cname, Integer majorId) {
        List<Course> courseList = courseService.getAllOrByMsg(id, cno, cname, majorId);
        return Result.success(courseList);
    }

    @PostMapping
    public Result insert(@RequestBody Course course) {
        boolean ret = courseService.insert(course);
        if (!ret)
            return Result.error("新建失败");
        else
            return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean ret = courseService.removeById(id);
        if (!ret)
            return Result.error("删除失败");
        else
            return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Course course) {
        boolean ret = courseService.updateInfo(course);
        if (!ret)
            return Result.error("更新失败");
        else
            return Result.success();
    }

}
