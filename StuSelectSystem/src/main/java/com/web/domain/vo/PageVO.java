package com.web.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageVO<T> {
    private Long total;

    private Long pages;

    private List<T> list;
}
