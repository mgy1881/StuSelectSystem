package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.MajorAndDeptDao;
import com.web.dao.StudentInfoDao;
import com.web.pojo.LoginInfo;
import com.web.pojo.Student;
import com.web.service.StudentInfoService;
import com.web.utils.JwtUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoDao, Student> implements StudentInfoService {
    //    @Resource
//    StudentInfoDao studentInfoDao;
    @Resource
    MajorAndDeptDao majorAndDeptDao;

    //    @Override
//    public List<student> getList() {
//        List<student> studentList = studentInfoDao.selectAllOrByMsg();
//        return studentList;
//    }
    @Override
    public List<Student> selectAllOrByMsg(String sno, String sname, Integer smajorId, Integer sgender, Integer sage) {
        return lambdaQuery().like(sname != null && !sname.isEmpty(), Student::getSname, sname)
                .eq(sno != null && !sno.isEmpty(), Student::getSno, sno)
                .eq(sgender != null, Student::getSgender, sgender)
                .eq(sage != null, Student::getSage, sage)
                .eq(smajorId != null, Student::getSmajorId, smajorId)
                .list();
    }

    @Override
    public boolean add(Student student) {
        if (majorAndDeptDao.selectDeptList(student.getSdeptId()).isEmpty() ||
                majorAndDeptDao.selectMajorList(student.getSmajorId()).isEmpty()) {
            return false;
        }
//        studentInfoDao.insertStudent(student);
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
//        if (getById(student.getId()) != null)
//            return false;
        if (student.getSmajorId() != null && majorAndDeptDao.selectMajorList(student.getSmajorId()).isEmpty())
            return false;
        if (student.getSdeptId() != null && majorAndDeptDao.selectDeptList(student.getSdeptId()).isEmpty())
            return false;
//        studentInfoDao.updateStudenInfo(student);
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
