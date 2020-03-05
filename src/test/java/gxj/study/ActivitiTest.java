package gxj.study;

import gxj.study.activiti.config.SecurityUtil;
import gxj.study.util.BpmnMockData;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/4 10:15
 * @description
 */
public class ActivitiTest extends BaseTest {

    @Autowired
    private ProcessRuntime processRuntime;//实现流程定义相关操作

    @Autowired
    private TaskRuntime taskRuntime;//任务相关操作

    @Autowired
    private SecurityUtil securityUtil; //Security相关工具类

    @Autowired
    private ProcessEngine processEngine;

    //流程定义信息 查看
    //注意：processRuntime.processDefinitions接口会自动部署符合 resource/processes/*.bpmn 路径条件的所有bpmn文件
    @Test
    public void testDefinition() {
        //模拟登录用户，spring-security认证
        securityUtil.logInAs("other");
        //分页查询流程信息
        //注意：流程部署工作在activiti7与springboot整合后，会自动部署 resource/processes/*.bpmn
        Page processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));

        //查看已部署流程个数
        System.out.println(">>> 流程定义个数：" + processDefinitionPage.getTotalItems());

        //processDefinitionPage.getContent() 得到当前部署的每一个流程的信息
        //pd的类型是ProcessDefinition
        for (Object pd : processDefinitionPage.getContent()) {
            System.out.println(">>> 流程: " + pd);
        }
    }

    @Test
    public void test_deploy_a_process(){

        //读取字符串作为一个输入流
        InputStream bpmn = new ByteArrayInputStream(BpmnMockData.getBpmn01().getBytes());


        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .addInputStream("InputStream01.bpmn",bpmn)
//                .addInputStream("helloworld.png", pngfileInputStream)
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署时间："+deployment.getDeploymentTime());
    }
    //启动流程
    @Test
    public void test_create_process_instance() {
        securityUtil.logInAs("bob");

        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("myProcess_2")
//                .withName("Processing Content: " + content)
//                .withVariable("content", content)
                .build());
        System.out.println(">>> 创建流程实例: " + processInstance);

    }

    // 查询任务 & 完成任务
    @Test
    public void test_query_and_complete_tasks() {
        //模拟用户认证信息：orther组登录，查询任务
        securityUtil.logInAs("other");
        System.out.println(">>> otherTeam组中用户登录");
        Page<Task> tasks = queryTasks();

        //模拟用户认证信息
        securityUtil.logInAs("john");
        System.out.println(">>> activitiTeam组中用户登录");
        //查询当前任务
        tasks = queryTasks();
        //执行当前任务
        doTasks(tasks);
        //再查询一次任务
        System.out.println(">>> 二次查询任务");
        queryTasks();
    }

    private Page<Task> queryTasks(){
        //分页查询任务 : taskRuntime.tasks()
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10));
        if (taskPage.getTotalItems() > 0) {
            //查询到有任务
            for (Task task : taskPage.getContent()) {
                System.out.println(">>> 查询任务：" + task+"   ");
            }
        } else {
            System.out.println(">>> 查无任务");
        }
        return taskPage;
    }

    private Page<Task> doTasks(Page<Task> taskPage){
        if (taskPage.getTotalItems() > 0) {
            //查询到有任务
            for (Task task : taskPage.getContent()) {
                System.out.print(">>> 完成任务：" + task+"   ");
                //拾取任务: 个人获取组任务 -》组任务变为个人任务 -》组任务不可被他人获取
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
                System.out.print(">>> 拾取...   ");
                //执行任务
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
                System.out.println(">>> 执行...   ");
            }
        } else {
            System.out.println(">>> 查无任务");
        }
        return taskPage;
    }


}
