package com.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.po.Student;
import org.apache.ibatis.annotations.*;


@Mapper
public interface StudentInfoDao extends BaseMapper <Student> {

//    @Select("select * from student_table")
//    List<student> getList();

    /*@Select("select * from student_table where Sno = #{Sno}")
    Student selectBySno(String Sno);

    List<Student> selectAllOrByMsg(Integer sno, String sname, Integer smajorId, Integer sgender, Integer sage);

    @Insert("insert into student_table(sno, sname, sgender, sage, smajor_id, sdept_id) "+
            "values (#{sno},#{sname},#{sgender},#{sage},#{smajorId},#{sdeptId})")
    void insertStudent(Student student);

    @Select("select * from student_table where id = #{id}")
    Student selectById(Integer id);

    @Delete("delete from student_table where id = #{id}")
    void deleteById(Integer id);

    void updateStudenInfo(Student student);*/
}
