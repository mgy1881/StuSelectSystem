package com.web.pojo;

import lombok.Data;

@Data
public class LoginInfo {
    /**
     * 用户名，一般为学号或教工号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 等级
     */
    private Integer level;
}
