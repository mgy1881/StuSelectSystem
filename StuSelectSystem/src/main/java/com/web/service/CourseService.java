package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.Course;

import java.util.List;

public interface CourseService extends IService<Course> {
    List<Course> getAllOrByMsg(Integer id, String cno, String cname, Integer majorId);

    boolean insert(Course course);

    boolean updateInfo(Course course);
}
