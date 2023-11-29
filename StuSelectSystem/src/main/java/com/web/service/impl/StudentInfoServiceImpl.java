package com.web.service.impl;

import com.web.dao.MajorAndDeptDao;
import com.web.dao.StudentInfoDao;
import com.web.pojo.Student;
import com.web.service.StudentInfoSeivice;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInfoServiceImpl implements StudentInfoSeivice {
    @Resource
    StudentInfoDao studentInfoDao;
    @Resource
    MajorAndDeptDao majorAndDeptDao;

    //    @Override
//    public List<student> getList() {
//        List<student> studentList = studentInfoDao.selectAllOrByMsg();
//        return studentList;
//    }
    @Override
    public List<Student> selectAllOrByMsg(Integer sno, String sname, Integer smajorId, Integer sgender, Integer sage) {
        List<Student> studentList = studentInfoDao.selectAllOrByMsg(sno, sname, smajorId, sgender, sage);
        return studentList;
    }

    @Override
    public boolean add(Student student) {
        if (studentInfoDao.selectBySno(student.getSno()) != null ||
                majorAndDeptDao.selectDeptList(student.getSdeptId()).isEmpty() ||
                majorAndDeptDao.selectMajorList(student.getSmajorId()).isEmpty()) {
            return false;
        }

        studentInfoDao.insertStudent(student);
        return true;
    }

    @Override
    public boolean deleteStudentById(Integer id) {
        if(studentInfoDao.selectById(id) == null)
            return false;
        studentInfoDao.deleteById(id);
        return true;
    }
}
