package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.CourseDao;
import com.web.dao.MajorDao;
import com.web.dao.TeacherDao;
import com.web.domain.po.Course;
import com.web.domain.po.Teacher;
import com.web.domain.query.CourseQuery;
import com.web.domain.vo.CourseVO;
import com.web.domain.vo.PageVO;
import com.web.service.CourseService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {

    @Resource
    MajorDao majorAndDeptDao;
    @Resource
    TeacherDao teacherDao;
    @Resource
    CourseDao courseDao;
    @Autowired
    HttpServletRequest request;

    @Override
    public PageVO<CourseVO> getAllOrByMsgPage(CourseQuery courseQuery) {
        Integer id = courseQuery.getId();
        String cno = courseQuery.getCno();
        String cname = courseQuery.getCname();
        Integer majorId = courseQuery.getMajorId();
        int pageNo = courseQuery.getPageNo() == null ? 1 : courseQuery.getPageNo();
        int pageSize = courseQuery.getPageSize() == null ? 5 : courseQuery.getPageSize();

        Page<Course> page = Page.of(pageNo, pageSize);
        page.addOrder(new OrderItem("id", true));

        lambdaQuery()
                .eq(id != null, Course::getId, id)
                .eq(cno != null && !cno.isEmpty(), Course::getCno, cno)
                .like(cname != null && !cname.isEmpty(), Course::getCname, cname)
                .eq(majorId != null, Course::getMajorId, majorId)
                .page(page);
        List<CourseVO> courseVOS = BeanUtil.copyToList(page.getRecords(), CourseVO.class);
        courseVOS.forEach(p -> {
            Teacher teacher = teacherDao.selectById(p.getTeacherId());
            p.setTeacherName(teacher == null ? "佚名" : teacher.getTname());
        });
        return new PageVO<>(page.getTotal(), page.getPages(), courseVOS);
    }

    @Override
    public List<Course> getAllOrByMsg(CourseQuery courseQuery) {
        Integer id = courseQuery.getId();
        String cno = courseQuery.getCno();
        String cname = courseQuery.getCname();
        Integer majorId = courseQuery.getMajorId();

        return lambdaQuery()
                .eq(id != null, Course::getId, id)
                .eq(cno != null && !cno.isEmpty(), Course::getCno, cno)
                .like(cname != null && !cname.isEmpty(), Course::getCname, cname)
                .eq(majorId != null, Course::getMajorId, majorId)
                .list();
    }


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
            throw new RuntimeException("选课学生非空!");
        return removeById(id);
    }

    @Override
    public boolean deleteByIdFromTeacher(Integer courseId) {
        Integer teacherId = (Integer) request.getSession().getAttribute("id");
        Course course = courseDao.selectById(courseId);
        if (!course.getTeacherId().equals(teacherId))
            throw new RuntimeException("禁止操作非本人课程!");
        if (course.getSelectedNumber() > 0)
            throw new RuntimeException("选课学生非空!");
        return removeById(courseId);
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
