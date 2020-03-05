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
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/4 10:15
 * @description
 */
public class Activiti6Test extends BaseTest {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * activiti6 接口
     */
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

    /**
     * activiti6 接口
     */
    @Test
    public void test_query_process_by_repositoryService(){
        String deploymentId = "280e4400-5e8b-11ea-9ed9-00ff3d734641";
        // 使用repositoryService查询单个部署对象
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        Deployment deployment1 = deploymentQuery.deploymentId(deploymentId).singleResult();
        System.out.println("查询单个部署对象：");
        System.out.println("部署对象："+ deployment1);

// 使用repositoryService查询多个部署对象
        List<Deployment> deploymentList = deploymentQuery
                .orderByDeploymenTime()
                .asc()
                .listPage(0,100);
        System.out.println("查询多个部署对象：");
        for(Deployment deploy: deploymentList){
            System.out.println("部署对象："+ deploy);
        }

// 使用repositoryService查询单个流程定义
        ProcessDefinition processDefPinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();
        System.out.println("查询单个流程定义：");
        System.out.println("流程定义："+ processDefPinition);


// 使用repositoryService查询多个流程定义
        List<ProcessDefinition> processDefinitionList = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .listPage(0,100);
        System.out.println("查询多个流程定义：");
        for(ProcessDefinition pd: processDefinitionList){
            System.out.println("流程定义："+pd);
        }
    }





}
