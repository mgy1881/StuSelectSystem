package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.CourseDao;
import com.web.dao.MajorAndDeptDao;
import com.web.dao.TeacherDao;
import com.web.domain.po.Course;
import com.web.service.CourseService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {

    @Resource
    MajorAndDeptDao majorAndDeptDao;
    @Resource
    TeacherDao teacherDao;
    @Resource
    CourseDao courseDao;
    @Autowired
    HttpServletRequest request;

    @Override
    public List<Course> getAllOrByMsg(Integer id, String cno, String cname, Integer majorId) {
        return lambdaQuery()
                .eq(id != null, Course::getId, id)
                .eq(cno != null && !cno.isEmpty(), Course::getCno, cno)
                .like(cname != null && !cname.isEmpty(), Course::getCname, cname)
                .eq(majorId != null, Course::getMajorId, majorId)
                .list();
    }

    @Override
    public List<Course> selectByMajor(Integer majorId, Integer cnum, String cname, String teacher) {
        return lambdaQuery()
                .eq(majorId != null, Course::getId, majorId)
                .eq(cnum != null, Course::getCno, cnum)
                .like(cname != null && !cname.isEmpty(), Course::getCname, cname)
                .list();
    }

/*    @Override
    public boolean add(Course course) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        course.setTeacherId(id);
        if (teacherDao.selectById(id) == null) {
            return false;
        }
        return save(course);
    }*/

    @Override
    public boolean updateCourseInfo(Course course) {
        course.setTeacherId((Integer) request.getSession().getAttribute("id"));
        if (course.getMajorId() != null && majorAndDeptDao.selectMajorList(course.getMajorId()).isEmpty())
            return false;
        Course course1 = courseDao.selectById(course.getId());
        if (course1 != null && course1.getTeacherId().equals(course.getTeacherId()) &&
                !lambdaQuery().eq(course.getCno() != null, Course::getCno, course.getCno()).exists()) {
            return updateById(course);
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        if (courseDao.selectById(id).getSelectedNumber() > 0)
            return false;
        return removeById(id);
    }

    @Override
    public boolean deleteByIdFromTeacher(Integer courseId) {
        Integer teacherId = (Integer) request.getSession().getAttribute("id");
        Course course = courseDao.selectById(courseId);
        if (!course.getTeacherId().equals(teacherId) ||
                course.getSelectedNumber() > 0)
            return false;
        return deleteById(courseId);
    }

    @Override
    public boolean insert(Course course) {
        if (majorAndDeptDao.selectMajorList(course.getMajorId()) == null ||
                teacherDao.selectById(course.getTeacherId()) == null)
            return false;
        if (query().eq("cno", course.getCno()).exists())
            return false;
        return save(course);
    }

    @Override
    public boolean updateInfo(Course course) {
        if (course.getTeacherId() != null && teacherDao.selectById(course.getTeacherId()) == null)
            return false;

        if (course.getMajorId() != null && majorAndDeptDao.selectMajorList(course.getMajorId()) == null) {
            return false;
        }
        return updateById(course);
    }

}
