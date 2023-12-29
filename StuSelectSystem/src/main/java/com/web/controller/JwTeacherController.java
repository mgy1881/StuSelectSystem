package com.web.controller;

import com.web.domain.po.Course;
import com.web.domain.po.Result;
import com.web.domain.po.Teacher;
import com.web.service.AdminService;
import com.web.service.CsService;
import com.web.service.CourseService;
import com.web.service.TeacherService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jiaowu/teacher")
@CrossOrigin
public class JwTeacherController {
    @Autowired
    HttpServletRequest request;

    @Resource
    CourseService courseService;

    @Resource
    CsService csService;

    @Resource
    TeacherService teacherService;


    //教师获取本人开课列表
    @GetMapping("/course-info")
    public Result getTeachedCourse() {
        Integer id = (Integer) request.getSession().getAttribute("id");
        List<Course> courseList = csService.getCoursedById(id);
        return Result.success(courseList);
    }


    //教师新增课程
    @PostMapping("/new")
    public Result addCourse(@RequestBody Course course) {
        try {
            Integer id = (Integer) request.getSession().getAttribute("id");
            course.setTeacherId(id);
            boolean ret = courseService.insert(course);
            if (ret)
                return Result.success();
            else
                return Result.error("添加失败");
        } catch (Exception e) {
            return Result.error("添加失败");
        }
    }

    //教师修改个人开课信息
    @PutMapping("/change")
    public Result updateCourse(@RequestBody Course course) {
        boolean ret = courseService.updateCourseInfo(course);
        if (ret)
            return Result.success();
        else
            return Result.error("修改失败");
    }

    //教师删除自身开的课程
    @DeleteMapping("/delete/{courseId}")
    public Result deleteCourse(@PathVariable Integer courseId) {
        try {
            boolean ret = courseService.deleteByIdFromTeacher(courseId);
            if (ret)
                return Result.success();
            else
                return Result.error("删除失败");
        } catch (Exception e) {
            return Result.error(e.getMessage() == null ? "删除失败" : e.getMessage());
        }
    }

    //教师获取个人信息
    @GetMapping("/info")
    public Result getTeacherInfo() {
        Integer id = (Integer) request.getSession().getAttribute("id");
        Teacher teacher = teacherService.getById(id);
        return Result.success(teacher);
    }


    //根据教师id获取教师信息
    @GetMapping("/getbyid/{id}")
    public Result getTeacherInfoById(@PathVariable Integer id) {
        Teacher teacher = teacherService.getById(id);
        return Result.success(teacher);
    }
}
