package gxj.study.activiti.model;


import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/30 14:43
 */
public interface ProcessInstance {
    /**
     * 流程实例ID
     * @return
     */
    String getId();
    /**
     * 流程实例名
     */
    String getName();

    /**
     * 流程实例发起日期
     * @return
     */
    Date getStartDate();

    /**
     * 流程实例发起人
     * @return
     */
    String getInitiator();

    /**
     * 流程实例business key
     */
    String getBusinessKey();

    /**
     * 流程实例持有的变量
     */
    Map<String, Object> getProcessVariables();

    /**
     * 流程实例描述
     */
    String getDescription();

    /**
     * 流程定义的key
     */
    String getProcessDefinitionKey();

    /**
     * 流程定义的版本
     */
    Integer getProcessDefinitionVersion();

    /**
     * 流程定义的名字
     */
    String getProcessDefinitionName();

    static ProcessInstance copyProperties(Object source, Class<? extends ProcessInstance > toObject){
        ProcessInstance target = null;
        try {
            target = toObject.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
        }
        return target;
    }

    static List<ProcessInstance> convertList(List source, Class<? extends ProcessInstance> toObject){
        if(source == null){
            return null;
        }
        List<ProcessInstance> instances = new ArrayList<>();
        for (Object instance : source) {
            ProcessInstance target = null;
            try {
                target = toObject.newInstance();
                BeanUtils.copyProperties(source, target);
                instances.add(target);
            } catch (Exception e) {
            }
        }
        return instances;
    }

}
