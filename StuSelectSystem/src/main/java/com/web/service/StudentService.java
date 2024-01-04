package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.po.LoginInfo;
import com.web.domain.po.Student;
import com.web.domain.query.StudentQuery;
import com.web.domain.vo.PageVO;
import com.web.domain.vo.StudentVO;

import java.util.List;

public interface StudentService extends IService<Student> {
//    List<student> getList();

//    List<Student> selectAllOrByMsg(String sno, String sname, Integer smajorId, Integer sgender, Integer sage);
    List<Student> selectAllOrByMsg(StudentQuery studentQuery);

    boolean add(Student student);

//    boolean deleteStudentById(Integer id);

    boolean updateStudentInfo(Student student);

    String LoginCheck(LoginInfo loginInfo);

    PageVO<StudentVO> selectAllOrByMsgPage(StudentQuery studentQuery);

    boolean deleteById(Integer id);
}
