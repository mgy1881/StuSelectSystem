package com.web.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentQuery extends PageQuery {
    String sno;
    String sname;
    Integer smajorId;
    Integer sgender;
    Integer sage;

}
