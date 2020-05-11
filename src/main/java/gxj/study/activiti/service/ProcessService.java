package gxj.study.activiti.service;

import gxj.study.activiti.model.ActivitiProcessInstance;
import gxj.study.activiti.model.ProcessInstanceQueryPayload;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.GetVariablesPayloadBuilder;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.GetTasksPayloadBuilder;
import org.activiti.api.task.model.builders.SetTaskVariablesPayloadBuilder;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ProcessService{
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskRuntime taskRuntime;

//    /**
//     * 部署流程定义
//     *
//     * @param reqDTO 流程定义
//     * @return
//     */
//    public String deployProcessDefinition(ProcessDefDTO reqDTO) {
//        BpmnModel bpmnModel = BpmnConvert.ProcessDefReqToBpmnModel(reqDTO);
//        Deployment deployment = processEngine.getRepositoryService()
//                .createDeployment()//创建部署对象
//                .addBpmnModel(reqDTO.getId() + ".bpmn", bpmnModel)
//                .deploy();//完成部署
//        log.info(">>> 部署详情：" + deployment);
//        return deployment.getId();
//    }

    public gxj.study.activiti.model.ProcessInstance createProcessInstance(String processDefinitionKey, String businessKey, String instanceName, Map<String, Object> vars) {

        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey(processDefinitionKey)
                .withBusinessKey(businessKey)
                .withProcessInstanceName(instanceName)
                .withVariables(vars)
                .build());
        log.info(">>> 创建流程实例: " + processInstance);
        return gxj.study.activiti.model.ProcessInstance.copyProperties(processInstance, ActivitiProcessInstance.class);

    }

    public void shutDownProcessInstance(String processInstanceId, String reason) {
        ProcessInstance instance = processRuntime.delete(ProcessPayloadBuilder.delete().withProcessInstanceId(processInstanceId).withReason(reason).build());
        log.info(">>> 删除流程实例：" + processInstanceId + " 状态：" + instance.getStatus());
    }

//    public queryHistoryProcessInst(String processId){
//        return
//    }

    public Map<String, VariableInstance> queryProcessVariables(String processInstanceId) {
        List<VariableInstance> variables = processRuntime.variables(new GetVariablesPayloadBuilder().withProcessInstanceId(processInstanceId).build());
        Map<String, VariableInstance> vars = new HashMap<>(8);
        for (VariableInstance v : variables) {
            vars.put(v.getName(), v);
        }
        return vars;
    }

    /**
     * 执行流程所有运行任务
     * 请确保操作人同时是所有任务节点的候选人
     *
     * @param processInstanceId 流程实例id
     * @return 任务执行成功 返回true; 没有执行到任务 返回false。
     */
    public Boolean completeRunningTasksInProcess(String processInstanceId) {
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10), new GetTasksPayloadBuilder().withProcessInstanceId(processInstanceId).build());
        if (taskPage.getTotalItems() > 0) {
            taskPage.getContent().forEach(task -> {
                doTask(task, new HashMap<>(0), false);
            });
            return true;
        }
        return false;
    }

    public ProcessInstance queryProcessInstance(String processInstanceId) {
        return processRuntime.processInstance(processInstanceId);
    }

    public List<gxj.study.activiti.model.ProcessInstance> queryProcessInstances(ProcessInstanceQueryPayload payload) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
        List<org.activiti.engine.runtime.ProcessInstance> list = payload.enhance(query).list();
        return gxj.study.activiti.model.ProcessInstance.convertList(list, ActivitiProcessInstance.class);
    }

    public List<HistoricProcessInstance> queryHistoryProcessInstance(String processInstanceId) {
        return processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).list();
    }

    /**
     * 拾取并执行任务
     *
     * @param task        任务
     * @param vars        变量
     * @param isLocalOnly 是否是局部变量
     * @return 行结果
     */
    private Boolean doTask(Task task, Map<String, Object> vars, boolean isLocalOnly) {
        //拾取任务: 个人获取组任务 -》组任务变为个人任务 -》组任务不可被他人获取
        log.info(">>> 拾取任务开始：{}", task.getId());
        String loginUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (StringUtils.isBlank(task.getAssignee())) {
            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
        } else if (!task.getAssignee().equals(loginUser)) {
            log.info(">>> processInstanceId:{}, taskId:{}, 任务已经被他人拾取, 拾取人:{}, 当前用户:{}", task.getProcessInstanceId(),
                    task.getId(), task.getAssignee(), loginUser);
        }
        //设置任务 可见参数：taskId - 任务序列号；localOnly - 是否全局，local会有新参数id，非local会覆盖之前的值，并保留下去；variables - 参数列表。
        SetTaskVariablesPayloadBuilder builder = TaskPayloadBuilder.setVariables().withTaskId(task.getId()).withVariables(vars);
        if (isLocalOnly) {
            builder.localOnly();
        }
        taskRuntime.setVariables(builder.build());
        //执行任务
        log.info(">>> 执行任务开始：{}", task.getId());
        Task completed = taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
        return completed.getStatus().equals(Task.TaskStatus.COMPLETED);
    }

    public String getCandidates(String assignee,Boolean b1,Boolean b2,String... candidates){
        List<String> strings = new ArrayList<>(Arrays.asList(candidates));
        if(b1){
            strings.add(assignee);
        }
        if(b2){
            strings.add("直属上级");
        }
        String str1=StringUtils.join(strings, ",");

        return str1;
    }


    public String getCandidates(String lastAssignee){
//        List<String> strings = new ArrayList<>(Arrays.asList(candidates));
//        if(b1){
//            strings.add(assignee);
//        }
//        if(b2){
//            strings.add("直属上级");
//        }
//        String str1=StringUtils.join(strings, ",");

        return lastAssignee;
    }
}
