package com.web.service;

import com.web.pojo.Student;

import java.util.List;

public interface StudentInfoSeivice {
//    List<student> getList();

    List<Student> selectAllOrByMsg( Integer sno, String sname, Integer smajorId, Integer sgender, Integer sage);

    boolean add(Student student);

    boolean deleteStudentById(Integer id);

    boolean updateStudentInfo(Student student);
}
