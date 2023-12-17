package com.web.service;

import com.web.domain.po.Dept;
import com.web.domain.po.Major;

import java.util.List;

public interface MajorAndSdeptService {

    List<Major> getMajorList(Integer id);

    List<Dept> getDeptList(Integer id);
}
