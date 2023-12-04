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
    private Integer capacity;
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
    private Integer id;
    /**
     * 所属专业id
     */
    private Integer majorId;
    /**
     * 选课人数
     */
    private Integer selectedNumber;
    /**
     * 教师id
     */
    private Integer teacherId;
    /**
     * 是否需要教材
     */
    @TableField(exist = false)
    private Integer needBook;

}
