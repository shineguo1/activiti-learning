package gxj.study.activiti.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/10 16:54
 * @description
 */
@Data
public class UserTaskDefinition extends FlowElement {
    private List<String> candidateUsers;
    private List<String> candidateGroups;
    private Integer priority;
    private String dueDate;
}
