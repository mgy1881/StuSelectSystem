package com.web.controller;

import com.web.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/course")
public class CourseController {
    @Resource
    CourseService courseService;


}
