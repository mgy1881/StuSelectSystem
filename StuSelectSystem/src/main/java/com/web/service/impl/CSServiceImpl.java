package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.CSDao;
import com.web.dao.CourseDao;
import com.web.dao.StudentInfoDao;
import com.web.pojo.CS;
import com.web.pojo.Course;
import com.web.service.CSService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class CSServiceImpl extends ServiceImpl<CSDao, CS> implements CSService {
    @Autowired
    HttpServletRequest request;
    @Resource
    StudentInfoDao studentInfoDao;
    @Resource
    CourseDao courseDao;
    @Resource
    CSDao csDao;

    @Override
    public boolean add(CS cs) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        cs.setStudentId(id);
        if (studentInfoDao.selectById(id) == null) {
            return false;
        }
        if (courseDao.selectById(cs.getCourseId()) == null) {
            return false;
        }
        return save(cs);
    }

    @Override
    public boolean removeByStudentIdAndCourseId(Integer id, Integer courseId) {
        if (!lambdaQuery()
                .eq(CS::getStudentId, id)
                .eq(CS::getCourseId, courseId).exists())
            return false;
        csDao.removeByStudentIdAndCourseId(id, courseId);
        return true;
    }

    @Override
    public List<Course> getSelectedCourse(Integer id) {

        return csDao.getSelectedCourse(id);
    }

    @Override
    public List<Course> getCoursedById(Integer id) {
        return csDao.getCoursedById(id);
    }
}
