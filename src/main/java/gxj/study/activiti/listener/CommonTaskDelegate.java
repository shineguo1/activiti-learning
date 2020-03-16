package gxj.study.activiti.listener;

import gxj.study.activiti.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.builders.GetVariablesPayloadBuilder;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/13 9:40
 * @description
 */
@Slf4j
@Component
public abstract class CommonTaskDelegate implements JavaDelegate {
    @Autowired
    private ProcessService processService;

    private Map<String, ProcessCallbackTask> systemTasks;

    @Autowired
    public CommonTaskDelegate(List<ProcessCallbackTask> tasks){
        for(ProcessCallbackTask task: tasks){
            systemTasks.put(task.getClass().toString(),task);
        }
    }

    @Override
    public void execute(DelegateExecution execution) {
        String processInstanceId = execution.getProcessInstanceId();
        Map<String, VariableInstance> variableInstances = processService.queryProcessVariables(processInstanceId);
        ProcessCallbackTask task = variableInstances.get("taskName").getValue();
        Result result=task.doTask();
        if(!result.isSuccess()) {
            processService.shutDownProcessInstance(processInstanceId, result.getErrorMsg());
        }
        log.info("系统自动执行回调任务, 结果："+ result);

    }

}