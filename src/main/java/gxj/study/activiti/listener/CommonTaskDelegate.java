package gxj.study.activiti.listener;

import gxj.study.activiti.service.ProcessFacade;
import gxj.study.activiti.service.ProcessService;
import gxj.study.activiti.util.SpringContextHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/13 9:40
 * @description
 */
@Slf4j
@Component
public class CommonTaskDelegate implements JavaDelegate,Serializable{
//    @Autowired
//    private ProcessService processService;

    private Map<String, ProcessCallbackTask> systemTasks = new ConcurrentHashMap<>();

    @Autowired
    public CommonTaskDelegate(List<ProcessCallbackTask> tasks){
        for(ProcessCallbackTask task: tasks){
            systemTasks.put(task.getClass().getSimpleName(),task);
        }
    }
    public CommonTaskDelegate(){

    }
    @Override
    public void execute(DelegateExecution execution) {
        String processInstanceId = execution.getProcessInstanceId();
        ProcessService processService = (ProcessService) SpringContextHolder.getApplicationContext().getBean("processService");
        Map<String, VariableInstance> variableInstances = processService.queryProcessVariables(processInstanceId);
        String taskKey = variableInstances.get("systemTask").getValue();
        ProcessCallbackTask task = systemTasks.get(taskKey);
        Result result=task.doTask();
        if(!result.isSuccess()) {
            processService.shutDownProcessInstance(processInstanceId, result.getErrorMsg());
        }
        System.out.println("系统自动执行回调任务, 结果："+result);

    }
}