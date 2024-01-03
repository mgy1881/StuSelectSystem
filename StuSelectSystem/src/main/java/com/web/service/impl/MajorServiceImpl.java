package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.DeptDao;
import com.web.dao.MajorDao;
import com.web.dao.StudentInfoDao;
import com.web.domain.po.Dept;
import com.web.domain.po.Major;
import com.web.domain.po.Student;
import com.web.service.MajorService;
import com.web.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl extends ServiceImpl<MajorDao, Major> implements MajorService {
    @Resource
    MajorDao majorDao;

    @Resource
    DeptDao deptDao;

    @Resource
    StudentService studentService;

    @Override
    public boolean updateMajorInfo(Major major) {
        if (lambdaQuery().eq(Major::getName, major.getName()).ne(Major::getId, major.getId()).exists())
            throw new RuntimeException("该专业名称已存在");
        if (deptDao.selectById(major.getDeptId()) == null)
            throw new RuntimeException("所属的院系不存在");
        return updateById(major);
    }

    @Override
    public List<Major> getMajorList(Integer id) {
        return majorDao.selectMajorList(id);
    }

    @Override
    public boolean add(Major major) {
        if (lambdaQuery().eq(Major::getName, major.getName()).exists())
            throw new RuntimeException("该专业已存在");
        if (deptDao.selectById(major.getDeptId()) == null)
            throw new RuntimeException("所属的院系不存在");
        return save(major);
    }

    @Override
    public boolean delete(Integer id) {
        if (studentService.lambdaQuery().eq(Student::getSmajorId, id).exists())
            throw new RuntimeException("请清空专业中学生后删除");
        return removeById(id);
    }
}
