package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.dao.CourseDao;
import com.web.pojo.Course;
import com.web.service.CourseService;
import org.springframework.stereotype.Service;


@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao,Course> implements CourseService  {

}
