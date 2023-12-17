package com.web.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class StudentVO {
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
    /**
     * 等级
     */
    private Integer level;
}
