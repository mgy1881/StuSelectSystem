package com.web.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class CourseVO {
    /**
     * 课程id
     */
    private Integer id;
    /**
     * 课程名
     */
    private String cname;
    /**
     * 课程号
     */
    private String cno;
    /**
     * 教师id
     */
    private Integer teacherId;
    /**
     * 课程容量
     */
    private Integer capacity;
    /**
     * 选课人数
     */
    private Integer selectedNumber;
    /**
     * 上课时间
     */
    private String date;

    /**
     * 所属专业id
     */
    private Integer majorId;

}
