package gxj.study.activiti.convert;


import gxj.study.activiti.enums.ProcessGateWayTypeEnum;
import gxj.study.activiti.listener.MyEndEventListener;
import gxj.study.activiti.model.EndEventDefinition;
import gxj.study.activiti.model.GatewayDefinition;
import gxj.study.activiti.model.ProcessDefReqDTO;
import gxj.study.activiti.model.SequenceFlowDefinition;
import gxj.study.activiti.model.StartEventDefinition;
import gxj.study.activiti.model.UserTaskDefinition;
import lombok.Data;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.InclusiveGateway;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.UserTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/10 17:32
 * @description
 */
@Data
public class BpmnConverter {

    public static BpmnModel ProcessDefReqToBpmnModel(ProcessDefReqDTO reqDTO) {
        BpmnModel bpmnModel = new BpmnModel();

        Process process = new Process();
        process.setId(reqDTO.getId());
        process.setName(reqDTO.getName());

        StartEvent startEvent = new StartEvent();
        StartEventDefinition startEventDefinition = reqDTO.getStartEvent();
        startEvent.setId(startEventDefinition.getId());
        startEvent.setName(startEventDefinition.getName());
        process.addFlowElement(startEvent);

        EndEvent endEvent = new EndEvent();
        EndEventDefinition endEventDefinition = reqDTO.getEndEvent();
        endEvent.setId(endEventDefinition.getId());
        endEvent.setName(endEventDefinition.getName());
        process.addFlowElement(endEvent);

        List<UserTaskDefinition> userTasks = reqDTO.getUserTasks();
        userTasks.forEach(taskDef -> {
            UserTask userTask = new UserTask();
            userTask.setId(taskDef.getId());
            userTask.setName(taskDef.getName());
            userTask.setCandidateUsers(taskDef.getCandidateUsers());
            userTask.setCandidateGroups(taskDef.getCandidateGroups());
            if (taskDef.getPriority() != null) {
                userTask.setPriority(String.valueOf(taskDef.getPriority()));
            }
            userTask.setDueDate(taskDef.getDueDate());
//            userTask.setTaskListeners();
//            userTask.setExecutionListeners();

            process.addFlowElement(userTask);
        });

        List<GatewayDefinition> gateways = reqDTO.getGateways();
        gateways.forEach(gatewayDef->{
            String type = gatewayDef.getType();
            org.activiti.bpmn.model.FlowElement gateway = null;
            if(type.equals(ProcessGateWayTypeEnum.EXCLUSIVE.getCode())){
                gateway = new ExclusiveGateway();
            }
            if(type.equals(ProcessGateWayTypeEnum.PARALLEL.getCode())){
                gateway = new ParallelGateway();
            }
            if(type.equals(ProcessGateWayTypeEnum.INCLUSIVE.getCode())){
                gateway = new InclusiveGateway();
            }
            if(Objects.nonNull(gateway)){
                gateway.setId(gatewayDef.getId());
                gateway.setName(gatewayDef.getName());
                process.addFlowElement(gateway);
            }
        });

        List<SequenceFlowDefinition> sequenceFlows = reqDTO.getSequenceFlows();
        sequenceFlows.forEach(sequenceFlowDef->{
            SequenceFlow sequenceFlow = new SequenceFlow();
            sequenceFlow.setId(sequenceFlowDef.getId());
            sequenceFlow.setName(sequenceFlowDef.getName());
            sequenceFlow.setSourceRef(sequenceFlowDef.getSourceRef());
            sequenceFlow.setTargetRef(sequenceFlowDef.getTargetRef());
            sequenceFlow.setConditionExpression(sequenceFlowDef.getConditionExpression());
            process.addFlowElement(sequenceFlow);
        });


//        endEvent.setExecutionListeners(Arrays.asList(new MyEndEventListener()));
//        process.setEventListeners(Arrays.asList(new MyEndEventListener()));
        process.setDocumentation("document123");
        process.setName("name1323");
        bpmnModel.addProcess(process);

        return bpmnModel;
    }
}
