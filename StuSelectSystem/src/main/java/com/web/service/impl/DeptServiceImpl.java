package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.DeptDao;
import com.web.dao.MajorDao;
import com.web.domain.po.Dept;
import com.web.domain.po.Major;
import com.web.domain.po.Student;
import com.web.domain.po.Teacher;
import com.web.service.DeptService;
import com.web.service.MajorService;
import com.web.service.StudentService;
import com.web.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptDao, Dept> implements DeptService {
    @Resource
    DeptDao deptDao;

    @Resource
    MajorService majorService;
    @Resource
    StudentService studentService;
    @Resource
    TeacherService teacherService;

    @Override
    public List<Dept> getDeptList(Integer id) {

        return lambdaQuery().eq(id != null, Dept::getId, id).list();
    }

    @Override
    public boolean add(Dept dept) {
        if (lambdaQuery().eq(Dept::getName, dept.getName()).exists())
            throw new RuntimeException("该院系已存在");
        return save(dept);
    }

    @Override
    public boolean updateDeptInfo(Dept dept) {
        if (lambdaQuery().eq(Dept::getName, dept.getName()).exists())
            throw new RuntimeException("该院系名称已存在");
        return updateById(dept);
    }

    @Override
    public boolean delete(Integer id) {
        if (majorService.lambdaQuery().eq(Major::getDeptId, id).exists())
            throw new RuntimeException("请清空院系中专业后删除");
        if(teacherService.lambdaQuery().eq(Teacher::getTdeptId,id).exists())
            throw new RuntimeException("请清空院系中教师后删除");
        if(studentService.lambdaQuery().eq(Student::getSdeptId,id).exists())
            throw new RuntimeException("请清空院系中学生后删除");
        return removeById(id);
    }
}
