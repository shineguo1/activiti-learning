package gxj.study.activiti.service;

import gxj.study.activiti.model.CandidateGroup;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/4/14 10:11
 * @description
 */
@Service
@Slf4j
public class BizService {


    public boolean hasNextStepssss(Long itemId) {
        //bizId 指 日常工作id（itemId）
        if (Objects.isNull(itemId)) {
            throw new RuntimeException("流程丢失BizId");
        }
        log.info("查WORK_DAILY_ITEM_STEP表，WHERE itemId = bizId and 实际完成时间 = null");

        //if(list.size > 0) return true;

        return true;

    }

    //itemId查
    /**
     * 通过itemId查工作步骤StepId
     */
    public List<Long> selectWorkStepIds(long itemId) {
        //itemId 指 日常工作id（itemId）
        log.info("查WORK_DAILY_ITEM_STEP表，WHERE itemId = itemId");
        return Arrays.asList(3L, 4L);
    }

    /**
     * 通过itemId查工作步骤deptId
     */
    public List<Long> selectDeptIds(long itemId) {
        //itemId 指 日常工作id（itemId）
        log.info("查WORK_DAILY_ITEM_STEP表，WHERE itemId = itemId");
        return Arrays.asList(3L, 4L);
    }

    //deptId查

    /**
     * 查关联科室正职领导
     */
    public String selectDeptLeaders(long deptId, String... unified) {
        //itemId 指 日常工作id（itemId）
        log.info("查WORK_DAILY_ITEM_STEP表，WHERE itemId = itemId");
        return "bob"+ StringUtils.join(unified,",");

    }

    //stepId查
    /**
     * 通过stepId查部门工作步骤StepDeptId
     */
    public List<Long> selectStepDeptIds(long stepId) {
        //bizId 指 日常工作id（itemId）
        log.info("查WORK_DAILY_ITEM_STEP表，WHERE itemId = bizId");
        return Arrays.asList(3L, 4L);

    }


    /**
     * 通过stepId查当前步骤责任人（发起部门）
     */
    public String selectStepOwner(long stepId) {
        log.info("查表 ITEM_STEP_USER WHERE 工作步骤id:" + stepId + " 类型：" + "发起部门责任人");
        return "bob";

    }

    /**
     * 通过stepId查下一步骤责任人（发起部门）
     */
    public String selectNextStepOwner(long stepId) {
        log.info("查表 ITEM_STEP_USER WHERE 工作步骤id:" + stepId + " 类型：" + "发起部门责任人");
        return "bob";
    }


    //stepDeptId查
    /**
     * 通过stepDeptId查参与部门责任人 - 上传材料
     */
    public String selectStepUser(long stepDeptId) {
        log.info("查表 ITEM_STEP_USER WHERE 参与部门工作步骤id:" + stepDeptId);
        return "bob";
    }

    /**
     * 通过stepDeptId 查部门日常工作（状态）
     */
    public Object selectItemDeptByStepDeptId(long stepDeptId){
        return null;
    }


    //username查
    /**
     * 查职位（正职LEADER，副职位DEPUTY，员工STAFF）
     */
    public String selectUnifiedDuty(String username) {
        log.info("查表用户username的统一职位.");
        return "STAFF";
    }

    /**
     * 查询username部门的正职领导 userService
     */
    public String selectLeader(String username) {
        log.info("查表用户username的科长.");
        return "bob";
    }

    /**
     * 查询username部门的正职/副职领导 userService
     */
    public String selectLeadersIncludeDeputy(String username) {
        log.info("查表用户username的副科长/科长:");
        return "bob";
    }

    /**
     * 当前用户是否是考督组
     */
    public boolean isKaoDuZu(Task t){

        return false;
    }

    /**
     * 当前用户是否是考督组
     */
    public boolean isKaoDuZu(String username){

        return true;
    }

    /**
     * 当前用户是否是考督组
     */
    public boolean isKaoDuZu(int username){

        return false;
    }

    /**
     * 返回考督组 候选组（activiti）
     */
    public String selectKaoDuZu(){

        CandidateGroup leader = new CandidateGroup(17L, "科长");
        return leader.getGroupName();
    }    /**
     * 返回考督组 候选组（activiti）
     */

    public String selectFenGuanLingDao(){

        return "fenguanlingdao";
    }
}
