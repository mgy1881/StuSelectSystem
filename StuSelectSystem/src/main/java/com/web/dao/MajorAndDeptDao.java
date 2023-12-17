package com.web.dao;

import com.web.domain.po.Dept;
import com.web.domain.po.Major;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorAndDeptDao {

    List<Major> selectMajorList(Integer id);

    List<Dept> selectDeptList(Integer id);
}
