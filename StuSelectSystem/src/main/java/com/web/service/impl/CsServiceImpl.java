package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.CsDao;
import com.web.dao.CourseDao;
import com.web.dao.StudentInfoDao;
import com.web.dao.TeacherDao;
import com.web.domain.po.Cs;
import com.web.domain.po.Course;
import com.web.domain.po.Teacher;
import com.web.domain.vo.CourseVO;
import com.web.service.CsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class CsServiceImpl extends ServiceImpl<CsDao, Cs> implements CsService {
    @Resource
    HttpServletRequest request;
    @Resource
    StudentInfoDao studentInfoDao;
    @Resource
    CourseDao courseDao;
    @Resource
    TeacherDao teacherDao;
    @Resource
    CsDao csDao;

    @Override
    public boolean add(Cs cs) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        cs.setStudentId(id);
        if (studentInfoDao.selectById(id) == null) {
            return false;
        }
        Course course = courseDao.selectById(cs.getCourseId());
        if (course == null) {
            return false;
        }
        if (lambdaQuery().eq(Cs::getStudentId, id)
                .eq(Cs::getCourseId, cs.getCourseId())
                .count() > 0)
            throw new RuntimeException("不可重复选课");
        if (course.getCapacity() - course.getSelectedNumber() < 1)
            throw new RuntimeException("课容量已满");
        course.setSelectedNumber(course.getSelectedNumber() + 1);
        courseDao.updateById(course);
        return save(cs);
    }

    @Override
    public boolean removeByStudentIdAndCourseId(Integer id, Integer courseId) {
        if (!lambdaQuery()
                .eq(Cs::getStudentId, id)
                .eq(Cs::getCourseId, courseId).exists())
            return false;
        csDao.removeByStudentIdAndCourseId(id, courseId);
        Course course = courseDao.selectById(courseId);
        course.setSelectedNumber(course.getSelectedNumber() - 1);
        courseDao.updateById(course);
        return true;
    }

    @Override
    public List<CourseVO> getSelectedCourse(Integer id) {

        List<Course> selectedCourse = csDao.getSelectedCourse(id);
        List<CourseVO> courseVOS = BeanUtil.copyToList(selectedCourse, CourseVO.class);
        courseVOS.forEach(p -> {
            Teacher teacher = teacherDao.selectById(p.getTeacherId());
            p.setTeacherName(teacher == null ? "佚名" : teacher.getTname());
        });
        return courseVOS;
    }

    @Override
    public List<Course> getCoursedById(Integer id) {
        return csDao.getCoursedById(id);
    }

    @Override
    public List<Cs> getListByCourseId(Integer id) {
        return lambdaQuery()
                .eq(Cs::getCourseId, id)
                .list();
    }

}
