package com.web.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher_table")
public class Teacher {
    /**
     * 教师id
     */
    private Integer id;
    /**
     * 院系id
     */
    private Integer tdeptId;
    /**
     * 性别，0：男，1：女
     */
    private Integer tgender;
    /**
     * 职称
     */
    private String tjob;
    /**
     * 教师姓名
     */
    private String tname;
    /**
     * 教工号
     */
    private String tno;
    /**
     * 密码
     */
    @TableField(select = false)
    private String password;

}
