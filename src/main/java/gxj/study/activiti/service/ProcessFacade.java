package gxj.study.activiti.service;

import org.activiti.api.model.shared.model.VariableInstance;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/16 11:15
 */
public interface ProcessFacade extends Serializable {

    boolean shutDownProcessInstance(String processInstanceId, String errorMsg);

    Map<String,VariableInstance> queryProcessVariables(String processInstanceId);
}
