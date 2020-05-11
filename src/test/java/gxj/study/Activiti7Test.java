package gxj.study;

import gxj.study.activiti.config.SecurityUtil;
import gxj.study.activiti.listener.CommonTaskDelegate;
import gxj.study.activiti.listener.examples.MyFirstCallBackTask;
import gxj.study.activiti.listener.examples.MyFirstTaskDelegate;
import gxj.study.activiti.service.FormService;
import gxj.study.activiti.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.GetVariablesPayloadBuilder;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.model.builders.ResumeProcessPayloadBuilder;
import org.activiti.api.process.model.payloads.ResumeProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.GetTaskVariablesPayloadBuilder;
import org.activiti.api.task.model.builders.GetTasksPayloadBuilder;
import org.activiti.api.task.model.builders.SetTaskVariablesPayloadBuilder;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/4 10:15
 * @description
 */
@Slf4j
public class Activiti7Test extends BaseTest {

    @Autowired
    private ProcessRuntime processRuntime;//实现流程定义相关操作

    @Autowired
    private TaskRuntime taskRuntime;//任务相关操作

    @Autowired
    private SecurityUtil securityUtil; //Security相关工具类

    @Autowired
    private FormService formService;

    @Autowired
    private ProcessEngine processEngine;

    /**
     * activiti7 接口
     * 流程定义信息 查看
     */
    //注意：processRuntime.processDefinitions接口会自动部署符合 resource/processes/*.bpmn 路径条件的所有bpmn文件
    @Test
    public void testDefinition() {
        //模拟登录用户，spring-security认证
        securityUtil.logInAs("other");
        //分页查询流程信息
        //注意：流程部署工作在activiti7与springboot整合后，会自动部署 resource/processes/*.bpmn
        Page processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
//        new GetProcessDefinitionsPayloadBuilder().w
        //查看已部署流程个数
        System.out.println(">>> 流程定义个数：" + processDefinitionPage.getTotalItems());

        //processDefinitionPage.getContent() 得到当前部署的每一个流程的信息
        //pd的类型是ProcessDefinition
        for (Object pd : processDefinitionPage.getContent()) {
            System.out.println(">>> 流程: " + pd);
        }
    }

    @Autowired
    private RepositoryService repositoryService;

    /**
     * activiti7 接口
     * 启动流程
     */
    @Test
    public void test_create_process_instance() {
        securityUtil.logInAs("bob");
        HashMap<String, Object> vars = new HashMap<>();
        vars.put("amount", 2);
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("myProcess_BpmnModel_01")
                .withBusinessKey("someBusiness")
//                .withName("Processing Content: " + content)
                .withVariables(vars)
                .build());
        System.out.println(">>> 创建流程实例: " + processInstance);

    }

    /**
     * activiti7 接口
     * 启动流程 - 表单
     */
    @Test
    public void test_create_process_instance_withForm() {
        test_clear_all();
        securityUtil.logInAs("other");
        HashMap<String, Object> vars = new HashMap<>();
//        vars.put("delegateImpl", SpringContextHolder.getBean("commonTaskDelegate"));
//        vars.put("delegateImpl", SpringContextHolder.getBean("serializableTaskDelegate"));
//        vars.put("systemTask", MyFirstCallBackTask.class.getSimpleName());
//        vars.put("formTempId","63");
        vars.put("assignee", "other");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("my_test_process2")
//                .withName("Processing Content: " + content)
                .withVariables(vars)
                .build());
        System.out.println(">>> 创建流程实例: " + processInstance);
        String processDefinitionId = processInstance.getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        List<String> startEventFormTypes = bpmnModel.getStartEventFormTypes();
        System.out.println(">>> 流程表单详情: " + startEventFormTypes);
//        test_query_and_complete_tasks();
    }

    /**
     * 遍历两个用户组，查询并完成所有任务
     */
    @Test
    public void test_query_and_complete_tasks() {
//        for (int i = 0; i < 5; i++) {
//        test_login_and_query_and_complete_users_tasks("other");
//        test_login_and_query_and_complete_users_tasks("bob");
//        test_login_and_query_and_complete_users_tasks("bob");
//        test_login_and_query_and_complete_users_tasks("other");

        test_login_and_query_and_complete_users_tasks("john");
//        test_login_and_query_and_complete_users_tasks("other");
//        }
    }

    /**
     * 查询并完成用户所在用户组的任务
     *
     * @param username 用户
     */
    private void test_login_and_query_and_complete_users_tasks(String username) {
        //模拟用户认证信息
        securityUtil.logInAs(username);
        Map<String, Object> vars = new HashMap<>();
        vars.put("operation","restart");
//        vars.put("assignee","j");
        //查询当前任务
        Page<Task> tasks = queryTasks();
        if (tasks.getTotalItems() > 0) {
            //执行当前任务
            doTasks(tasks, vars, false);
            //再查询一次任务
//            System.out.println(">>> 二次查询任务");
//            queryTasks();
        }
    }

    /**
     * activiti7 接口
     */
    private Page<Task> queryTasks() {
        //分页查询任务 : taskRuntime.tasks()
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10), new GetTasksPayloadBuilder().withGroup("activitiGroup").build());
        if (taskPage.getTotalItems() > 0) {
            //查询到有任务
            for (Task task : taskPage.getContent()) {
                System.out.println(">>> 查询任务：" + task + "   ");
                System.out.println(">>> 查询任务状态：" + task.getStatus());
            }
        } else {
            System.out.println(">>> 查无任务");
        }
        return taskPage;
    }

    /**
     * 执行所有任务
     *
     * @param taskPage 任务集合 - 分页查询结果 - 无传入参数
     * @return
     */
    private void doTasks(Page<Task> taskPage) {
        doTasks(taskPage, new HashMap<>(), false);
    }

    @Autowired
    TaskService taskService;

    /**
     * 执行所有任务
     *
     * @param taskPage    任务集合 - 分页查询结果
     * @param vars        参数 - 传入每个任务
     * @param isLocalOnly - 参数是否全局 - 传入每个任务
     * @return
     */
    private void doTasks(Page<Task> taskPage, Map<String, Object> vars, boolean isLocalOnly) {
        if (taskPage.getTotalItems() > 0) {
            //查询到有任务
            for (Task task : taskPage.getContent()) {
//                taskService.getVariable();
//                taskService.addCandidateUser();
                doTask(task, vars, isLocalOnly);
            }
        } else {
            System.out.println(">>> 查无任务");
        }
    }

    /**
     * @param task
     * @param vars
     * @param isLocalOnly
     * @return
     */
    private void doTask(Task task, Map<String, Object> vars, boolean isLocalOnly) {
        System.out.print(">>> 完成任务：" + task + "   ");
        //拾取任务: 个人获取组任务 -》组任务变为个人任务 -》组任务不可被他人获取
        String loginUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (StringUtils.isBlank(task.getAssignee())) {
            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
        } else if (!task.getAssignee().equals(loginUser)) {
            log.info(">>> processInstanceId:{}, taskId:{}, 任务已经被他人拾取, 拾取人:{}, 当前用户:{}", task.getProcessInstanceId(),
                    task.getId(), task.getAssignee(), loginUser);
//            throw new PostLoanException(PostLoanCodeEnum.PROC100002);
        }
//        taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
        if(Objects.isNull(vars))vars = new HashMap<>();
        vars.put("lastAssignee",loginUser);
        System.out.print(">>> 拾取...   ");
        //设置任务 可见参数：taskId - 任务序列号；localOnly - 是否全局，local会有新参数id，非local会覆盖之前的值，并保留下去；variables - 参数列表。
//        vars.put("amout",5);
        SetTaskVariablesPayloadBuilder builder = TaskPayloadBuilder.setVariables().withTaskId(task.getId()).withVariables(vars);
        if (isLocalOnly) {
            builder.localOnly();
        }
        taskRuntime.setVariables(builder.build());
        //执行任务
        taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
        System.out.println(">>> 执行...   ");
    }




    /**
     * 删除流程实例
     */
    @Test
    public void test_delete_processInstance() {
        securityUtil.logInAs("john");
        queryProcessInstances();
        String processInstanceId = "bc4caa02-5f4e-11ea-afbe-00ff3d734641";
        String reason = "Unknown property used in expression: ${amout > 3}";
        processRuntime.delete(ProcessPayloadBuilder.delete().withProcessInstanceId(processInstanceId).withReason(reason).build());
        System.out.println(">>> 删除流程实例：" + processInstanceId);
        queryProcessInstances();
    }

    /**
     * 中止所有流程实例（清空）
     */
    @Test
    public void test_clear_all() {
        securityUtil.logInAs("other");
        Page<ProcessInstance> processInstancePage = queryProcessInstances();
        processInstancePage.getContent().forEach(pi -> {
            String reason = "something";
            processRuntime.delete(ProcessPayloadBuilder.delete().withProcessInstanceId(pi.getId()).withReason(reason).build());
            System.out.println(">>> 删除流程实例：" + pi.getId());

        });
        queryProcessInstances();

    }

    @Test
    public void qurey_ten_processInstances() {
        securityUtil.logInAs("other");
        queryProcessInstances();
    }


    private Page<ProcessInstance> queryProcessInstances() {
        Page<ProcessInstance> processInstancePage = processRuntime.processInstances(Pageable.of(0, 10));
        if (processInstancePage.getTotalItems() > 0) {
            processInstancePage.getContent().forEach(pi -> System.out.println(">>> 流程实例：" + pi + "  状态:" + pi.getStatus()));
        } else {
            System.out.println(">>> 查无流程实例");
        }
        return processInstancePage;
    }

    /**
     * 查询运行时任务的变量
     */
    @Test
    public void query_variables() {
        String taskId = "";
        taskRuntime.variables(new GetTaskVariablesPayloadBuilder().withTaskId(taskId).build());

        String processInstanceId = "";
        processRuntime.variables(new GetVariablesPayloadBuilder().withProcessInstanceId(processInstanceId).build());
    }
}
