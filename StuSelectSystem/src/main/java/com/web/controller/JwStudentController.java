package com.web.controller;

import com.web.pojo.Cs;
import com.web.pojo.Course;
import com.web.pojo.Result;
import com.web.pojo.Student;
import com.web.service.CsService;
import com.web.service.CourseService;
import com.web.service.StudentInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jiaowu/student")
@CrossOrigin
public class JwStudentController {
    @Autowired
    HttpServletRequest request; //通过注解获取一个request

    @Resource
    StudentInfoService studentInfoService;
    @Resource
    CourseService courseService;

    @Resource
    CsService csService;


    //学生获取个人信息
    @GetMapping("/info")
    public Result getStudentInfo() {
        Integer id = (Integer) request.getSession().getAttribute("id");
        Student student = studentInfoService.getById(id);
        return Result.success(student);
    }

    //学生获取课程列表
    @GetMapping("/course")
    public Result getCourseInfo(Integer majorId, Integer cnum, String cname, String teacher) {
        List<Course> courseList = courseService.selectByMajor(majorId, cnum, cname, teacher);
        return Result.success(courseList);
    }

    //学生选课
    @PostMapping("/course")
    public Result selectCourse(@RequestBody Cs cs) {
        boolean ret = csService.add(cs);
        if (ret)
            return Result.success();
        else
            return Result.error("添加失败");
    }

    //学生已选课程查询
    @GetMapping("/selected-courses")
    public Result getSelectedCourse() {
        Integer id = (Integer) request.getSession().getAttribute("id");
        List<Course> courseList = csService.getSelectedCourse(id);
        return Result.success(courseList);
    }

    //学生退选课程
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
