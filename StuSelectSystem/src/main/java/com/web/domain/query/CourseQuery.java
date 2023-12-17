package com.web.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CourseQuery extends PageQuery {
    Integer id;
    String cno;
    String cname;
    Integer majorId;
}
