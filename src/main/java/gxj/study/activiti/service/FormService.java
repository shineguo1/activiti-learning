package gxj.study.activiti.service;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/9 11:26
 * @description
 */
@Service
public class FormService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    public List<FormProperty> getFormPropertiesFromBpmn(String  processDefinitionId, String taskId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Collection<FlowElement> flowElementCollection = bpmnModel.getMainProcess().getFlowElements();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            System.out.println("taskid XXX is not existed.");
            return null;
        }
        String taskDefinitionKey = task.getTaskDefinitionKey();
        for (FlowElement flowElement: flowElementCollection) {
            if ("UserTask".equals(flowElement.getClass().getSimpleName()) && taskDefinitionKey.equals(flowElement.getId())) {
                return ((UserTask)flowElement).getFormProperties();
            }
        }
        return null;
    }
}