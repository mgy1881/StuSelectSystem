package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.*;
import com.web.domain.po.Course;
import com.web.domain.po.Cs;
import com.web.domain.po.LoginInfo;
import com.web.domain.po.Student;
import com.web.domain.query.StudentQuery;
import com.web.domain.vo.PageVO;
import com.web.domain.vo.StudentVO;
import com.web.service.CourseService;
import com.web.service.StudentService;
import com.web.utils.JwtUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentInfoDao, Student> implements StudentService {
    //    @Resource
//    StudentInfoDao studentInfoDao;
    @Resource
    MajorDao majorDao;

    @Resource
    DeptDao deptDao;

    @Resource
    CsDao csDao;


    //    @Override
//    public List<student> getList() {
//        List<student> studentList = studentInfoDao.selectAllOrByMsg();
//        return studentList;
//    }

    @Override
    public PageVO<StudentVO> selectAllOrByMsgPage(StudentQuery studentQuery) {
        String sname = studentQuery.getSname();
        String sno = studentQuery.getSno();
        Integer sgender = studentQuery.getSgender();
        Integer sage = studentQuery.getSage();
        Integer smajorId = studentQuery.getSmajorId();
        int pageNo = studentQuery.getPageNo() == null ? 1 : studentQuery.getPageNo();
        int pageSize = studentQuery.getPageSize() == null ? 5 : studentQuery.getPageSize();

        Page<Student> page = Page.of(pageNo, pageSize);
        page.addOrder(new OrderItem("sno", true));
        lambdaQuery().like(sname != null && !sname.isEmpty(), Student::getSname, sname)
                .eq(sno != null && !sno.isEmpty(), Student::getSno, sno)
                .eq(sgender != null, Student::getSgender, sgender)
                .eq(sage != null, Student::getSage, sage)
                .eq(smajorId != null, Student::getSmajorId, smajorId)
                .page(page);
        List<StudentVO> list = BeanUtil.copyToList(page.getRecords(), StudentVO.class);

        return new PageVO<StudentVO>(page.getTotal(), page.getPages(), list);
    }

    @Override
    public boolean deleteById(Integer id) {
        LambdaQueryWrapper<Cs> wrapper = new LambdaQueryWrapper<Cs>()
                .select(Cs::getStudentId)
                .eq(Cs::getStudentId, id);
        if (!csDao.selectList(wrapper).isEmpty())
            throw new RuntimeException("该生仍选择课程,无法删除");
        return removeById(id);
    }

    @Override
    public List<Student> selectAllOrByMsg(StudentQuery studentQuery) {
        String sname = studentQuery.getSname();
        String sno = studentQuery.getSno();
        Integer sgender = studentQuery.getSgender();
        Integer sage = studentQuery.getSage();
        Integer smajorId = studentQuery.getSmajorId();

        return lambdaQuery().like(sname != null && !sname.isEmpty(), Student::getSname, sname)
                .eq(sno != null && !sno.isEmpty(), Student::getSno, sno)
                .eq(sgender != null, Student::getSgender, sgender)
                .eq(sage != null, Student::getSage, sage)
                .eq(smajorId != null, Student::getSmajorId, smajorId)
                .list();
    }

    @Override
    public boolean add(Student student) {
        if (deptDao.selectDeptList(student.getSdeptId()).isEmpty() ||
                majorDao.selectMajorList(student.getSmajorId()).isEmpty()
        ) {
            throw new RuntimeException("专业或院系不存在");
        }
        if (lambdaQuery().eq(Student::getSno, student.getSno()).exists())
            throw new RuntimeException("该学号已存在");
        return save(student);
    }

/*    @Override
    public boolean deleteStudentById(Integer id) {
        if (studentInfoDao.selectById(id) == null)
            return false;
        studentInfoDao.deleteById(id);
        return true;
    }*/

    @Override
    public boolean updateStudentInfo(Student student) {
        if (student.getSmajorId() != null && majorDao.selectMajorList(student.getSmajorId()).isEmpty())
            throw new RuntimeException("专业信息不存在");

        if (student.getSdeptId() != null && deptDao.selectDeptList(student.getSdeptId()).isEmpty())
            throw new RuntimeException("院系信息不存在");

        return updateById(student);
    }

    @Override
    public String LoginCheck(LoginInfo loginInfo) {
        Student student = lambdaQuery().eq(Student::getSno, loginInfo.getUsername())
                .eq(Student::getPassword, loginInfo.getPassword())
                .one();
        if (student == null)
            return null;
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", student.getId());
        claims.put("level", loginInfo.getLevel());
        log.info("等级信息为:{}", loginInfo.getLevel());
        return JwtUtils.generateJwt(claims);
    }


}
