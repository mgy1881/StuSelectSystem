package com.web.controller;

import com.web.pojo.Result;
import com.web.pojo.Student;
import com.web.service.StudentInfoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/student")
public class StudentInfoController {
    @Resource
    StudentInfoService studentInfoSeivice;

    //管理员根据条件获取学生信息
    @GetMapping
    public Result getStudentInfo(String sno, String sname, Integer smajorId, Integer sgender, Integer sage) {

        List<Student> studentList = studentInfoSeivice.selectAllOrByMsg(sno, sname, smajorId, sgender, sage);
        return Result.success(studentList);
    }

    //管理员新增学生
    @PostMapping
    public Result addStudentInfo(@RequestBody Student student) {
        boolean ret = studentInfoSeivice.add(student);
        if (ret)
            return Result.success();
        else
            return Result.error("添加失败");

    }

    //管理员删除学生
    @DeleteMapping("/{id}")
    public Result deleteStudentById(@PathVariable Integer id) {

        boolean ret = studentInfoSeivice.removeById(id);

        if (ret)
            return Result.success();
        else
            return Result.error("删除失败");
    }

    //管理员修改学生信息
    @PutMapping
    public Result updateStudentInfo(@RequestBody Student student) {
        boolean ret = studentInfoSeivice.updateStudentInfo(student);
        if (ret)
            return Result.success();
        else
            return Result.error("修改失败");
    }
}
