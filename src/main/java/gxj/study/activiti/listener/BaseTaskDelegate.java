package gxj.study.activiti.listener;

import gxj.study.activiti.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/13 9:40
 * @description
 */
@Slf4j
@Component
public abstract class BaseTaskDelegate implements JavaDelegate {
    @Autowired
    private ProcessService processService;

    private String deleteReason="TaskExecuteError";

    @Override
    public void execute(DelegateExecution execution) {
        String processInstanceId = execution.getProcessInstanceId();
        boolean  isSuccess= doTask();
        if(!isSuccess) {
            processService.shutDownProcessInstance(processInstanceId, deleteReason);
        }
        System.out.println("系统自动执行回调任务, 结果："+isSuccess);

    }

    public void setDeleteReason(String reason){
        deleteReason = reason;
    }

    /**
     *  执行系统任务
     *  任务执行失败，请使用{@link #setDeleteReason(String)}设置错误信息,并返回false
     *  任务执行成功，请返回true
     * @return 任务执行结果
     */
    public abstract boolean doTask();



}