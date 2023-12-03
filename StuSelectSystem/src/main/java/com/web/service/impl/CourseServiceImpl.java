package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.CourseDao;
import com.web.dao.MajorAndDeptDao;
import com.web.pojo.Course;
import com.web.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {

    @Resource
    MajorAndDeptDao majorAndDeptDao;

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
    public boolean insert(Course course) {
        /**
         * 待实现判断教师id是否存在的函数
         */

        if (majorAndDeptDao.selectMajorList(course.getMajorId()) == null)
            return false;
        if (query().eq("cno", course.getCno()).exists())
            return false;
        return save(course);
    }

    @Override
    public boolean updateInfo(Course course) {
        /**
         * 待实现判断教师id是否存在函数
         */

        if (course.getMajorId() != null && majorAndDeptDao.selectMajorList(course.getMajorId()) == null) {
            return false;
        }
        return updateById(course);
    }
}
