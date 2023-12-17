package com.web.domain.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherQuery extends PageQuery {
    String tno;
    String tname;
    String tjob;
    Integer tdeptId;
    Integer tgender;
}
