package com.web.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cs_table")
public class CS {

    /**
     * 学生选课信息
     */
        private Integer courseId;
        private Integer needBook;
        private Integer studentId;
}
