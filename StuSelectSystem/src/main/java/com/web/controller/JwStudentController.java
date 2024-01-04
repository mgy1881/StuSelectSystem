package com.web.controller;

import com.web.domain.po.Cs;
import com.web.utils.Result;
import com.web.domain.po.Student;
import com.web.domain.query.CourseQuery;
import com.web.domain.vo.CourseVO;
import com.web.domain.vo.PageVO;
import com.web.service.CsService;
import com.web.service.CourseService;
import com.web.service.StudentService;
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
    StudentService studentInfoService;
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
    public Result getCourseInfo(CourseQuery courseQuery) {
        PageVO<CourseVO> courseVOPageVO = courseService.getAllOrByMsgPage(courseQuery);
        return Result.success(courseVOPageVO);
    }

    //学生选课
    @PostMapping("/course")
    public Result selectCourse(@RequestBody Cs cs) {
        try {
//            Integer id = (Integer) request.getSession().getAttribute("id");
//            cs.setStudentId(id);
            boolean ret = csService.add(cs);
            if (ret)
                return Result.success();
            else
                return Result.error("选课失败");
        } catch (Exception e) {
            return Result.error(e.getMessage().length() > 10 ? "选课失败" : e.getMessage());
        }

    }

    //学生已选课程查询
    @GetMapping("/selected-courses")
    public Result getSelectedCourse() {
        Integer id = (Integer) request.getSession().getAttribute("id");
        List<CourseVO> selectedCourse = csService.getSelectedCourse(id);
        return Result.success(selectedCourse);
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
