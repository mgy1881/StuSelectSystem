package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.CS;
import com.web.pojo.Course;

import java.util.List;

public interface CSService extends IService<CS> {
    boolean add(CS cs);

    boolean removeByStudentIdAndCourseId(Integer id, Integer courseId);

    List<Course> getSelectedCourse(Integer id);

    List<Course> getCoursedById(Integer id);
}
