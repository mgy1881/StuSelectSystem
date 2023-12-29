package com.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.web.domain.po.Result;
import com.web.domain.po.Student;
import com.web.domain.query.StudentQuery;
import com.web.domain.vo.PageVO;
import com.web.domain.vo.StudentVO;
import com.web.service.StudentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/student")
@CrossOrigin
public class StudentController {
    @Resource
    StudentService studentService;


    /**
     * 根据id获取学生信息
     */
    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable Integer id) {
        Student student = studentService.getById(id);
        StudentVO studentVO = BeanUtil.copyProperties(student, StudentVO.class);
        return Result.success(studentVO);
    }

    //    @GetMapping
//    public Result getStudentInfo(String sno, String sname, Integer smajorId, Integer sgender, Integer sage) {
//
//        List<Student> studentList = studentService.selectAllOrByMsg(sno, sname, smajorId, sgender, sage);
//        return Result.success(studentList);
//    }

    @GetMapping("/page")
    public Result getStudentPage(StudentQuery studentQuery) {
        PageVO<StudentVO> studentVOPageVO = studentService.selectAllOrByMsgPage(studentQuery);
        return Result.success(studentVOPageVO);
    }

    //管理员根据条件获取学生信息
    @GetMapping
    public Result getStudentInfo(StudentQuery studentQuery) {

        List<Student> studentList = studentService.selectAllOrByMsg(studentQuery);
        return Result.success(studentList);
    }

    //管理员新增学生
    @PostMapping
    public Result addStudentInfo(@RequestBody Student student) {
        try {
            boolean ret = studentService.add(student);
            if (ret)
                return Result.success();
            else
                return Result.error("添加失败");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    //管理员删除学生
    @DeleteMapping("/{id}")
    public Result deleteStudentById(@PathVariable Integer id) {

        boolean ret = studentService.removeById(id);

        if (ret)
            return Result.success();
        else
            return Result.error("删除失败");
    }

    //管理员修改学生信息
    @PutMapping
    public Result updateStudentInfo(@RequestBody Student student) {
        boolean ret = studentService.updateStudentInfo(student);
        if (ret)
            return Result.success();
        else
            return Result.error("修改失败");
    }


}
