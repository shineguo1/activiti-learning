package gxj.study.activiti.model;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/30 14:01
 * @description 整合了org.activiti.engine.runtime.ProcessInstance和org.activiti.api.process.model.ProcessInstance
 */
@Data
public class ActivitiProcessInstance implements ProcessInstance {

    private String id;
    private String name;
    private Date startDate;
    private String initiator;
    private String businessKey;
    private Map<String, Object> processVariables;
    private String description;
    private String processDefinitionKey;
    private Integer processDefinitionVersion;
    private String processDefinitionName;

    /**
     * org.activiti.engine.runtime.ProcessInstance中与initiator同义字段.
     * 设置set方法用于BeanUtils.copyProperties自动转换.
     */
    public void setStartUserId(String startUserId) {
        this.initiator = startUserId;
    }

    /**
     * org.activiti.engine.runtime.ProcessInstance中与startDate同义字段.
     * 设置set方法用于BeanUtils.copyProperties自动转换.
     */
    public void setStartTime(Date startTime){
        this.startDate = startTime;
    }

    public void setProcessInstanceId(String instanceId){
        this.id = instanceId;
    }

}
