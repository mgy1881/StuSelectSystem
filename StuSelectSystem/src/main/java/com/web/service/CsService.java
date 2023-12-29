package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.po.Cs;
import com.web.domain.po.Course;
import com.web.domain.query.PageQuery;
import com.web.domain.vo.CourseVO;

import java.util.List;

public interface CsService extends IService<Cs> {
    boolean add(Cs cs);

    boolean removeByStudentIdAndCourseId(Integer id, Integer courseId);

    List<CourseVO> getSelectedCourse(Integer id);

    List<Course> getCoursedById(Integer id);

    List<Cs> getListByCourseId(Integer id);

}
