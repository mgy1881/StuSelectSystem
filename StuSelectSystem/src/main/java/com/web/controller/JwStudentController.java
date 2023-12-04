package com.web.controller;

import com.web.pojo.CS;
import com.web.pojo.Course;
import com.web.pojo.Result;
import com.web.pojo.Student;
import com.web.service.CSService;
import com.web.service.CourseService;
import com.web.service.StudentInfoService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jiaowu/student")
public class JwStudentController {
    @Autowired
    HttpServletRequest request; //通过注解获取一个request

    @Resource
    StudentInfoService studentInfoService;
    @Resource
    CourseService courseService;

    @Resource
    CSService csService;


    @GetMapping("/info")
    public Result getStudentInfo() {
        Integer id = (Integer) request.getSession().getAttribute("id");
        Student student = studentInfoService.getById(id);
        return Result.success(student);
    }

    @GetMapping("/course")
    public Result getCourseInfo(Integer majorId, Integer cnum, String cname, String teacher) {
        List<Course> courseList = courseService.selectByMajor(majorId, cnum, cname, teacher);
        return Result.success(courseList);
    }

    @PostMapping("/course")
    public Result selectCourse(@RequestBody CS cs) {
        boolean ret = csService.add(cs);
        if (ret)
            return Result.success();
        else
            return Result.error("添加失败");
    }

    @GetMapping("/selected-courses")
    public Result getSelectedCourse() {
        Integer id = (Integer) request.getSession().getAttribute("id");
        List<Course> courseList = csService.getSelectedCourse(id);
        return Result.success(courseList);
    }

    @DeleteMapping("/course/{courseId}")
    public Result delectSelectedCourse(@PathVariable Integer courseId) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        boolean ret = csService.removeByStudentIdAndCourseId(id, courseId);
        if (ret)
            return Result.success();
        else
            return Result.error("删除失败");
    }
}
