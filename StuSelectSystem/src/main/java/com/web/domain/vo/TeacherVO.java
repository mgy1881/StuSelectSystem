package com.web.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class TeacherVO {
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

}
