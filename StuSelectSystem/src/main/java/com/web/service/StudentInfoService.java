package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.LoginInfo;
import com.web.pojo.Student;

import java.util.List;

public interface StudentInfoService extends IService<Student> {
//    List<student> getList();

    List<Student> selectAllOrByMsg(String sno, String sname, Integer smajorId, Integer sgender, Integer sage);

    boolean add(Student student);

//    boolean deleteStudentById(Integer id);

    boolean updateStudentInfo(Student student);

    String LoginCheck(LoginInfo loginInfo);
}
