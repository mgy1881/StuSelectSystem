package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.po.Dept;

import java.util.List;

public interface DeptService extends IService<Dept> {
    List<Dept> getDeptList(Integer id);

    boolean add(Dept dept);

    boolean updateDeptInfo(Dept dept);

    boolean delete(Integer id);
}
