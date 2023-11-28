package com.web.service;

import com.web.pojo.Dept;
import com.web.pojo.Major;

import java.util.List;

public interface MajorAndSdeptService {

    List<Major> getMajorList(Integer id);

    List<Dept> getDeptList(Integer id);
}
