package gxj.study.activiti.model;

import lombok.Data;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/10 17:04
 * @description
 */
@Data
public class SequenceFlowDefinition extends FlowElement {
    private  String sourceRef;
    private String targetRef;
    private String conditionExpression;
}
