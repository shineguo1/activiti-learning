package gxj.study.activiti.listener;

import gxj.study.activiti.model.CandidateGroup;
import gxj.study.activiti.service.UserInfoService;
import gxj.study.activiti.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/4/13 11:01
 * @description
 */
@Component
@Slf4j
public class AutoPassDelegate implements JavaDelegate {

    /**
     * 通过Bpmn中 serviceTask > extensionElements > activiti:field 属性注入
     * 自动通过审批，原节点配置的候选人
     */
    private Expression candidateUsers;
    /**
     * 通过Bpmn中 serviceTask > extensionElements > activiti:field 属性注入
     * 自动通过审批，原节点配置的候选组
     */
    private Expression candidateGroups;
    /**
     * 通过Bpmn中 serviceTask > extensionElements > activiti:field 属性注入
     * 自动通过审批，原节点配置的候选人为流程发起人属性
     */
    private Expression processAssigneeFlag;
    /**
     * 通过Bpmn中 serviceTask > extensionElements > activiti:field 属性注入
     * 自动通过审批，原节点配置的候选人为上一节点处理人直属上级属性
     */
    private Expression superiorFlag;

    @Override
    public void execute(DelegateExecution execution) {
        log.info(execution.getEventName());
        System.out.println(execution.getId());
        //serviceTask定义的class只会创建一个java类的实例.注入的属性是线程不安全的。
        //官方文档和源码通过expression.getValue(execution)从execution中取值解决这个问题，如下
        String localCandidateUsers = getStringValue(candidateUsers, execution);
        String localCandidateGroups = getStringValue(candidateGroups, execution);
        String localAssigneeFlag = getStringValue(processAssigneeFlag, execution);
        String localSuperiorFlag = getStringValue(superiorFlag, execution);

        List<String> users = new ArrayList<>();
        //流程发起人
        Object assignee = getAssignee(execution, localAssigneeFlag);
        if (Objects.nonNull(assignee)) {
            users.add(String.valueOf(assignee));
        }
        //直属上级
        String superior = getSuperior(execution, localSuperiorFlag);
        if (!StringUtils.isEmpty(superior)) {
            users.add(superior);
        }
        //直接传的自定义候选人
        if (Objects.nonNull(localCandidateUsers) && !StringUtils.isEmpty(localCandidateUsers)) {
            String[] split = localCandidateUsers.split(",");
            users.addAll(Arrays.asList(split));
        }
        //保存候选人
        if (!users.isEmpty()) {
            execution.setVariableLocal("candidateUsers" + ":" + execution.getCurrentActivityId() + ":" + execution.getId(), StringUtils.join(users, ","));
            execution.setVariable("lastAssignee", users.get(0));
        }
        //保存候选组
        if (Objects.nonNull(localCandidateGroups) && !StringUtils.isEmpty(localCandidateGroups)) {
            execution.setVariableLocal("candidateGroups" + ":" + execution.getCurrentActivityId() + ":" + execution.getId(), localCandidateGroups);
            execution.setVariable("lastAssignee", localCandidateGroups.split(",")[0]);

        }
    }

    private String getSuperior(DelegateExecution execution, String localSuperiorFlag) {
        if (Objects.nonNull(localSuperiorFlag) && Boolean.valueOf(localSuperiorFlag)) {
            Object lastAssignee = execution.getVariable("lastAssignee");
            if (Objects.nonNull(lastAssignee)) {
                //lastAssignee可能是候选组（机构+职务）或者某员工username
                CandidateGroup instance = CandidateGroup.createInstance((String)lastAssignee);
                //如果是候选组
                if(!CandidateGroup.isEmpty(instance)){
                    return selectSuperiorByOrgId(instance.getOrgId());
                }
                //如果是候选人
                UserInfoService userInfoService = SpringContextHolder.getBean(UserInfoService.class);
                return userInfoService.selectSuperior(String.valueOf(lastAssignee));

            }
        }
        return null;
    }

    private String selectSuperiorByOrgId(Long orgId) {
        return "jam";
    }

    private Object getAssignee(DelegateExecution execution, String localAssigneeFlag) {
        if (Objects.nonNull(localAssigneeFlag) && Boolean.valueOf(localAssigneeFlag)) {
            return execution.getVariable("assignee");
        }
        return null;
    }

    public String getStringValue(Expression expression, DelegateExecution execution) {
        try {
            return (String) expression.getValue(execution);
        } catch (Exception e) {
            log.warn("AutoPassDelegate：ServiceTask解析属性注入获取为空:{}", expression);
        }
        return null;
    }
}
