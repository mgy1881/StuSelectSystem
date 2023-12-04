package com.web.controller;

import com.web.pojo.Cs;
import com.web.pojo.Result;
import com.web.service.CsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cs")
public class CsController {
    @Resource
    CsService csService;


    @GetMapping("/{id}")
    public Result getCs(@PathVariable Integer id) {
        List<Cs> csList = csService.getListByCourseId(id);
        return Result.success(csList);
    }

    @DeleteMapping("/studentId/{studentId}/courseId/{courseId}")
    public Result deleteCs(@PathVariable Integer studentId,@PathVariable Integer courseId){
        boolean ret = csService.removeByStudentIdAndCourseId(studentId,courseId);
        if(ret)
            return Result.success();
        else
            return Result.error("删除失败！");
    }
}
