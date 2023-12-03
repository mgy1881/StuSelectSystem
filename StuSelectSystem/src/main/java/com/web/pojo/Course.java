package com.web.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course_table")
public class Course {
    /**
     * 课程容量
     */
    private long capacity;
    /**
     * 课程名
     */
    private String cname;
    /**
     * 课程号
     */
    private String cno;
    /**
     * 上课时间
     */
    private String date;
    /**
     * 课程id
     */
    private long id;
    /**
     * 所属专业id
     */
    private Long majorid;
    /**
     * 选课人数
     */
    private long selectedNumber;
    /**
     * 教师id
     */
    private long teacherId;

}
