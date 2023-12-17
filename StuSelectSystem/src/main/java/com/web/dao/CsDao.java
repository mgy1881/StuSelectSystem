package com.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.po.Cs;
import com.web.domain.po.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CsDao extends BaseMapper<Cs> {

    @Delete("delete from cs_table where course_id = #{courseId} and student_id = #{id}")
    void removeByStudentIdAndCourseId(Integer id, Integer courseId);

    @Select("select id,cno,cname,teacher_id,capacity,selected_number,date,major_id,need_book from course_table, cs_table " +
            "where id = #{id} and student_id = #{id}")
    List<Course> getSelectedCourse(Integer id);

    @Select("select id,cno,cname,teacher_id,capacity,selected_number,date,major_id from cs_table,course_table " +
            "where teacher_id = #{id}")
    List<Course> getCoursedById(Integer id);

}
