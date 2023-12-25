package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.po.Dept;
import com.web.domain.po.Major;

import java.util.List;

public interface MajorAndSdeptService extends IService<Major> {

    boolean updateMajorInfo(Major major) ;

    List<Major> getMajorList(Integer id);

    List<Dept> getDeptList(Integer id);

//    boolean updateDeptInfo(Dept dept);

    boolean add(Major major);


//    boolean addDept(Dept dept);
}
