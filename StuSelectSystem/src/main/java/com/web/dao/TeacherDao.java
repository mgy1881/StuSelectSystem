package com.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherDao extends BaseMapper<Teacher> {
}
