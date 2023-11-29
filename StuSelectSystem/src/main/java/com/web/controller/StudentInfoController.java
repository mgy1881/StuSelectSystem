package com.web.controller;

import com.web.pojo.Result;
import com.web.pojo.Student;
import com.web.service.StudentInfoSeivice;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/student")
public class StudentInfoController {
    @Resource
    StudentInfoSeivice studentInfoSeivice;

    @GetMapping
    public Result getStudentInfo(Integer sno, String sname, Integer smajorId, Integer sgender, Integer sage) {

        List<Student> studentList = studentInfoSeivice.selectAllOrByMsg(sno, sname, smajorId, sgender, sage);
        return Result.success(studentList);
    }

    @PostMapping
    public Result addStudentInfo(@RequestBody Student student) {
        boolean ret = studentInfoSeivice.add(student);
        if (ret)
            return Result.success();
        else
            return Result.error("添加失败");

    }
    @DeleteMapping("/{id}")
    public Result deleteStudentById(@PathVariable Integer id){
        boolean ret= studentInfoSeivice.deleteStudentById(id);
        if (ret)
            return Result.success();
        else
            return Result.error("删除失败");
    }
}
