package gxj.study.activiti.model;

import lombok.Data;

import java.util.List;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/10 16:30
 * @description
 */
@Data
public class ProcessDefReqDTO {
    private String id;
    private String name;
    private StartEventDefinition startEvent;
    private EndEventDefinition endEvent;
    private List<UserTaskDefinition> userTasks;
    private List<GatewayDefinition> gateways;
    private List<SequenceFlowDefinition> sequenceFlows;

}
