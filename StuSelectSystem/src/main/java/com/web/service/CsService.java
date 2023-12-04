package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.Cs;
import com.web.pojo.Course;

import java.util.List;

public interface CsService extends IService<Cs> {
    boolean add(Cs cs);

    boolean removeByStudentIdAndCourseId(Integer id, Integer courseId);

    List<Course> getSelectedCourse(Integer id);

    List<Course> getCoursedById(Integer id);

    List<Cs> getListByCourseId(Integer id);

}
