package gxj.study.activiti.model;

import lombok.Data;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/24 14:25
 * @description 运行时流程实例查询条件装载类
 */
@Data
public class ProcessInstanceQueryPayload {

    String businessKey;
    String processInstanceName;
    String processInstanceId;
    String processDefinitionKey;
    String startUser;
    String queryKey;

    public ProcessInstanceQuery enhance(ProcessInstanceQuery query){
        if (!StringUtils.isBlank(this.getBusinessKey())) {
            query.processInstanceBusinessKey(this.getBusinessKey());
        }
        if (!StringUtils.isBlank(this.getProcessDefinitionKey())) {
            query.processDefinitionKey(this.getProcessDefinitionKey());
        }
        if (!StringUtils.isBlank(this.getProcessInstanceId())) {
            query.processInstanceId(this.getProcessInstanceId());
        }
        if (!StringUtils.isBlank(this.getStartUser())) {
            query.involvedUser(this.getStartUser());
        }
        if (!StringUtils.isBlank(this.getQueryKey())) {
            query.variableValueEquals("QUERY_KEY", this.getQueryKey());
        }
        return query;
    }

}
