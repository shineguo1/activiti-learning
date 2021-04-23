package gxj.study.activiti.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.*;
import org.activiti.engine.impl.el.ExpressionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/13 9:40
 * @description
 */
@Slf4j
@Component("taskCreateListener")
public class TaskCreateListener implements TaskListener,JavaDelegate,Serializable{
//    @Autowired
//    private ProcessService processService;

    private Map<String, ProcessCallbackTask> systemTasks = new ConcurrentHashMap<>();

    @Autowired
    public TaskCreateListener(List<ProcessCallbackTask> tasks){
        for(ProcessCallbackTask task: tasks){
            systemTasks.put(task.getClass().getSimpleName(),task);
        }
    }
    public TaskCreateListener(){

    }
    @Override
    public void execute(DelegateExecution execution) {
//        String processInstanceId = execution.getProcessInstanceId();
//        ProcessService processService = (ProcessService) SpringContextHolder.getApplicationContext().getBean("processService");
//        Map<String, VariableInstance> variableInstances = processService.queryProcessVariables(processInstanceId);
//        String taskKey = variableInstances.get("systemTask").getValue();
//        ProcessCallbackTask task = systemTasks.get(taskKey);
//        Result result=task.doTask();
//        if(!result.isSuccess()) {
//            processService.shutDownProcessInstance(processInstanceId, result.getErrorMsg());
//        }
        System.out.println("系统自动执行回调任务, 结果：");

    }

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.getVariable("assignee");
//        delegateTask.getDescription()
//        String processDefinitionKey = delegateTask.getProcessDefinitionId().split(":")[0];
//        ProcessCallback processCallback = ProcessCallbackHolder.getByProcDefKey(processDefinitionKey);
//        Class formDataClazz = (Class) getGenericTypes(processCallback.getClass())[0];
//        Object formDataObj = BeanUtil.copyProperties(formData, formDataClazz);
//        boolean isSuccess = processCallback.createTaskCallback( formDataObj);
        Expression expression = new ExpressionManager().createExpression("${car.engine}");
//      下面这个表达式值为true。 formData = {'a':'A', 'b':'B'};
//        Expression expression = new ExpressionManager().createExpression("${userInfoService.parseObject(formData).a != 'a' && userInfoService.parseObject(formData).b == 'B'}");
        expression.getValue(delegateTask);
        System.out.println("系统自动执行回调任务, 结果：");

    }
}