package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.LoginInfo;
import com.web.pojo.Teacher;

import java.util.List;

public interface TeacherService extends IService<Teacher> {
    List<Teacher> selectAllOrByMsg(String tno, String tname, String tjob, Integer tdeptId, Integer tgender);

    boolean add(Teacher teacher);

    boolean updateTeacherInfo(Teacher teacher);

    String LoginCheck(LoginInfo loginInfo);
}
