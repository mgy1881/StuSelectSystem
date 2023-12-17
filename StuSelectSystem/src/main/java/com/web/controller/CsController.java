package com.web.controller;

import com.web.domain.po.Cs;
import com.web.domain.po.Result;
import com.web.service.CsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cs")
@CrossOrigin
public class CsController {
    @Resource
    CsService csService;


    //管理员获取选课学生信息
    @GetMapping("/{id}")
    public Result getCs(@PathVariable Integer id) {
        List<Cs> csList = csService.getListByCourseId(id);
        return Result.success(csList);
    }

    //管理员退选学生课程
    @DeleteMapping("/studentId/{studentId}/courseId/{courseId}")
    public Result deleteCs(@PathVariable Integer studentId,@PathVariable Integer courseId){
        boolean ret = csService.removeByStudentIdAndCourseId(studentId,courseId);
        if(ret)
            return Result.success();
        else
            return Result.error("删除失败！");
    }
}
