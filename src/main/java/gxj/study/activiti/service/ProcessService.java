package gxj.study.activiti.service;

import gxj.study.activiti.convert.BpmnConverter;
import gxj.study.activiti.model.ProcessDefReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.GetVariablesPayloadBuilder;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/13 10:25
 * @description
 */
@Slf4j
@Service
public class ProcessService {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ProcessRuntime processRuntime;

    /**
     * 部署流程定义
     *
     * @param reqDTO 流程定义
     * @return
     */
    public String deployProcessDefinition(ProcessDefReqDTO reqDTO) {
        BpmnModel bpmnModel = BpmnConverter.ProcessDefReqToBpmnModel(reqDTO);
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()//创建部署对象
                .addBpmnModel(reqDTO.getId() + ".bpmn", bpmnModel)
                .deploy();//完成部署
        log.info(">>> 部署详情：" + deployment);
        return deployment.getId();
    }

    public void createProcessInstance(String ProcessDefiniton, Map<String, Object> vars) {
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("myProcess_BpmnModel_01")
//                .withName("Processing Content: " + content)
                .withVariables(vars)
                .build());
        System.out.println(">>> 创建流程实例: " + processInstance);

    }

    public boolean shutDownProcessInstance(String processInstanceId, String reason) {
        ProcessInstance instance = processRuntime.delete(ProcessPayloadBuilder.delete().withProcessInstanceId(processInstanceId).withReason(reason).build());
        System.out.println(">>> 删除流程实例：" + processInstanceId + " 状态：" + instance.getStatus());
        return instance.getStatus().equals(ProcessInstance.ProcessInstanceStatus.DELETED);
    }

    public Map<String, VariableInstance> queryProcessVariables(String processInstanceId) {
        List<VariableInstance> variables = processRuntime.variables(new GetVariablesPayloadBuilder().withProcessInstanceId(processInstanceId).build());
        Map<String, VariableInstance> vars = new HashMap<>();
        for (VariableInstance v : variables) {
            vars.put(v.getName(),v);
        }
        return vars;

    }
}
