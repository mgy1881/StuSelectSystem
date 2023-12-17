package com.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.po.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseDao extends BaseMapper<Course> {
}
