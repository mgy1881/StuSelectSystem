package com.web.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.CourseDao;
import com.web.dao.MajorAndDeptDao;
import com.web.dao.TeacherDao;
import com.web.domain.po.Course;
import com.web.domain.po.LoginInfo;
import com.web.domain.po.Teacher;
import com.web.domain.query.TeacherQuery;
import com.web.domain.vo.PageVO;
import com.web.domain.vo.TeacherVO;
import com.web.service.TeacherService;
import com.web.utils.JwtUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@Slf4j
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher> implements TeacherService {
    @Resource
    MajorAndDeptDao majorAndDeptDao;

    @Resource
    CourseDao courseDao;

    @Override
    public PageVO<TeacherVO> selectAllOrByMsgPage(TeacherQuery teacherQuery) {
        String tno = teacherQuery.getTno();
        String tname = teacherQuery.getTname();
        String tjob = teacherQuery.getTjob();
        Integer tgender = teacherQuery.getTgender();
        Integer tdeptId = teacherQuery.getTdeptId();
        int pageNo = teacherQuery.getPageNo() == null ? 1 : teacherQuery.getPageNo();
        int pageSize = teacherQuery.getPageSize() == null ? 5 : teacherQuery.getPageSize();

        Page<Teacher> page = Page.of(pageNo,pageSize);
        page.addOrder(new OrderItem("tno",true));
        lambdaQuery()
                .eq(tno != null && !tno.isEmpty(), Teacher::getTno, tno)
                .like(tname != null && !tname.isEmpty(), Teacher::getTname, tname)
                .like(tjob != null && !tjob.isEmpty(), Teacher::getTjob, tjob)
                .eq(tdeptId != null, Teacher::getTdeptId, tdeptId)
                .eq(tgender != null, Teacher::getTgender, tgender)
                .page(page);
        List<TeacherVO> teacherVOS = BeanUtil.copyToList(page.getRecords(), TeacherVO.class);

        return new PageVO<TeacherVO>(page.getTotal(),page.getPages(),teacherVOS);
    }

    @Override
    public List<Teacher> selectAllOrByMsg(TeacherQuery teacherQuery) {
        String tno = teacherQuery.getTno();
        String tname = teacherQuery.getTname();
        String tjob = teacherQuery.getTjob();
        Integer tgender = teacherQuery.getTgender();
        Integer tdeptId = teacherQuery.getTdeptId();

        return lambdaQuery()
                .eq(tno != null && !tno.isEmpty(), Teacher::getTno, tno)
                .like(tname != null && !tname.isEmpty(), Teacher::getTname, tname)
                .like(tjob != null && !tjob.isEmpty(), Teacher::getTjob, tjob)
                .eq(tdeptId != null, Teacher::getTdeptId, tdeptId)
                .eq(tgender != null, Teacher::getTgender, tgender).list();

    }

//    @Override
//    public List<Teacher> selectAllOrByMsg(String tno, String tname, String tjob, Integer tdeptId, Integer tgender) {
//        return lambdaQuery()
//                .eq(tno != null && !tno.isEmpty(), Teacher::getTno, tno)
//                .like(tname != null && !tname.isEmpty(), Teacher::getTname, tname)
//                .like(tjob != null && !tjob.isEmpty(), Teacher::getTjob, tjob)
//                .eq(tdeptId != null, Teacher::getTdeptId, tdeptId)
//                .eq(tgender != null, Teacher::getTgender, tgender).list();
//
//    }

    @Override
    public boolean add(Teacher teacher) {
        if (majorAndDeptDao.selectDeptList(teacher.getTdeptId()).isEmpty() ||
                lambdaQuery().eq(Teacher::getTno, teacher.getTno()).exists()) {
            return false;
        }
        return save(teacher);
    }

    @Override
    public boolean updateTeacherInfo(Teacher teacher) {
        if (teacher.getTdeptId() != null && majorAndDeptDao.selectDeptList(teacher.getTdeptId()).isEmpty())
            return false;
        return updateById(teacher);
    }

    @Override
    public String LoginCheck(LoginInfo loginInfo) {
        Teacher teacher = lambdaQuery().eq(Teacher::getTno, loginInfo.getUsername())
                .eq(Teacher::getPassword, loginInfo.getPassword())
                .one();
        if (teacher == null)
            return null;
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", teacher.getId());
        claims.put("level", loginInfo.getLevel());
        log.info("等级信息为:{}", loginInfo.getLevel());
        return JwtUtils.generateJwt(claims);
    }

    @Override
    public boolean deleteById(Integer id) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<Course>()
                .select(Course::getId)
                .eq(Course::getTeacherId, id);
        if (!courseDao.selectList(wrapper).isEmpty())
            return false;
        return removeById(id);
    }


}
