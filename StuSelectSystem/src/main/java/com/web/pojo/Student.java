package com.web.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("student_table")
public class Student {
    /*
      学生信息
     */
    /**
     * 学生id
     */
    private Integer id;
    /**
     * 年龄
     */
    private Integer sage;
    /**
     * 院系id
     */
    private Integer sdeptId;
    /**
     * 性别，0：男，1：女
     */
    private Integer sgender;
    /**
     * 专业id
     */
    private Integer smajorId;
    /**
     * 姓名
     */
    private String sname;
    /**
     * 学号
     */
    private String sno;
}
