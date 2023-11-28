package com.web.service.impl;

import com.web.dao.MajorAndDeptDao;
import com.web.pojo.Dept;
import com.web.pojo.Major;
import com.web.service.MajorAndSdeptService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorAndSdeptServiceImpl implements MajorAndSdeptService {
    @Resource
    MajorAndDeptDao majorAndDeptDao;
    @Override
    public List<Major> getMajorList(Integer id) {
        List<Major> majorList = majorAndDeptDao.selectMajorList(id);
        return majorList;
    }

    @Override
    public List<Dept> getDeptList(Integer id) {
        List<Dept> deptList = majorAndDeptDao.selectDeptList(id);
        return deptList;
    }
}
