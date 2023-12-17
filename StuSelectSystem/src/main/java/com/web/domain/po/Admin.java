package com.web.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admin_table")
public class Admin {
    private Integer id;
    private String username;
    @TableField(select = false)
    private String password;
}
