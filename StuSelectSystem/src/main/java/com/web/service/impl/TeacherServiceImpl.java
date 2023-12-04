package com.web.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.MajorAndDeptDao;
import com.web.dao.TeacherDao;
import com.web.pojo.LoginInfo;
import com.web.pojo.Teacher;
import com.web.service.TeacherService;
import com.web.utils.JwtUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;
@Slf4j
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherDao,Teacher> implements TeacherService {
    @Resource
    MajorAndDeptDao majorAndDeptDao;
    @Override
    public List<Teacher> selectAllOrByMsg(String tno, String tname, String tjob, Integer tdeptId, Integer tgender) {
        return lambdaQuery()
                .eq(tno != null && !tno.isEmpty(),Teacher::getTno,tno)
                .like(tname != null && !tname.isEmpty(),Teacher::getTname,tname)
                .like(tjob != null && !tjob.isEmpty(), Teacher::getTjob,tjob)
                .eq(tdeptId!=null,Teacher::getTdeptId,tdeptId)
                .eq(tgender!=null,Teacher::getTgender,tgender).list();

    }

    @Override
    public boolean add(Teacher teacher){
        if(majorAndDeptDao.selectDeptList(teacher.getTdeptId()).isEmpty()){
            return false;
        }
        return save(teacher);
    }

    @Override
    public boolean updateTeacherInfo(Teacher teacher){
        if(teacher.getTdeptId() != null && majorAndDeptDao.selectDeptList(teacher.getTdeptId()).isEmpty())
            return false;
        return updateById(teacher);
    }
    @Override
    public String LoginCheck(LoginInfo loginInfo){
        Teacher teacher = lambdaQuery().eq(Teacher::getTno,loginInfo.getUsername())
                .eq(Teacher::getPassword,loginInfo.getPassword())
                .one();
        if(teacher == null)
            return null;
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",teacher.getId());
        claims.put("level",loginInfo.getLevel());
        log.info("等级信息为:{}",loginInfo.getLevel());
        return JwtUtils.generateJwt(claims);
    }


}
