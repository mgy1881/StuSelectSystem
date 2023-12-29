package com.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.po.Dept;
import com.web.domain.po.Major;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorDao extends BaseMapper<Major> {

    List<Major> selectMajorList(Integer id);

    List<Dept> selectDeptList(Integer id);
}
