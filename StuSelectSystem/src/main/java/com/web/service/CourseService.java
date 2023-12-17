package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.po.Course;
import com.web.domain.query.CourseQuery;
import com.web.domain.vo.CourseVO;
import com.web.domain.vo.PageVO;

import java.util.List;

public interface CourseService extends IService<Course> {
    List<Course> getAllOrByMsg(CourseQuery courseQuery);
//    List<Course> getAllOrByMsg(Integer id, String cno, String cname, Integer majorId);

    boolean insert(Course course);

    boolean updateInfo(Course course);

    List<Course> selectByMajor(Integer majorId, Integer cnum, String cname, String teacher);

//    boolean add(Course course);

    boolean updateCourseInfo(Course course);

    boolean deleteById(Integer id);

    boolean deleteByIdFromTeacher(Integer courseId);

    PageVO<CourseVO> getAllOrByMsgPage(CourseQuery courseQuery);
}
