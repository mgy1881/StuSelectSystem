package com.web.domain.query;

import lombok.Data;

@Data
public class PageQuery {
    /**
     * 页码
     */
    private Integer pageNo ;
    /**
     * 每页大小
     */
    private Integer pageSize ;
}
