package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.po.LoginInfo;
import com.web.domain.po.Teacher;
import com.web.domain.query.TeacherQuery;
import com.web.domain.vo.PageVO;
import com.web.domain.vo.TeacherVO;

import java.util.List;

public interface TeacherService extends IService<Teacher> {
    List<Teacher> selectAllOrByMsg(TeacherQuery teacherQuery);
//    List<Teacher> selectAllOrByMsg(String tno, String tname, String tjob, Integer tdeptId, Integer tgender);

    boolean add(Teacher teacher);

    boolean updateTeacherInfo(Teacher teacher);

    String LoginCheck(LoginInfo loginInfo);

    boolean deleteById(Integer id);

    PageVO<TeacherVO> selectAllOrByMsgPage(TeacherQuery teacherQuery);
}
