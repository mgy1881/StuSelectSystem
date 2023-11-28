package com.web.dao;

import com.web.pojo.Dept;
import com.web.pojo.Major;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorAndDeptDao {

    List<Major> selectMajorList(Integer id);

    List<Dept> selectDeptList(Integer id);
}
