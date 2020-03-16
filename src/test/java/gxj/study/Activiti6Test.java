package gxj.study;

import gxj.study.activiti.util.BpmnMockData;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
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
    public void test_deploy_a_process() {

        //读取字符串作为一个输入流
        InputStream bpmn = new ByteArrayInputStream(BpmnMockData.getBpmnFormProcess01().getBytes());


        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .addInputStream("formProcess01.bpmn", bpmn)
//                .addInputStream("helloworld.png", pngfileInputStream)
                .deploy();//完成部署
//        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署详情：" + deployment);//1
//        System.out.println("部署时间："+deployment.getDeploymentTime());
    }

    @Test
    public void test_deploy_a_process_by_bpmnModel() {

        BpmnModel bpmnModel = BpmnMockData.getBpmnCondition02Model();
        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .addBpmnModel("bpmnModel01.bpmn", bpmnModel)
                .deploy();//完成部署
        System.out.println("部署详情：" + deployment);//1
    }

    /**
     * activiti6 接口
     */
    @Test
    public void test_query_process_by_repositoryService() {
//        String deploymentId = "280e4400-5e8b-11ea-9ed9-00ff3d734641";
        // 使用repositoryService查询单个部署对象
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
//        Deployment deployment1 = deploymentQuery.deploymentId(deploymentId).singleResult();
//        System.out.println("查询单个部署对象：");
//        System.out.println("部署对象：" + deployment1);

// 使用repositoryService查询多个部署对象
        List<Deployment> deploymentList = deploymentQuery
                .orderByDeploymenTime()
                .asc()
                .listPage(0, 100);
        System.out.println("查询多个部署对象：");
        for (Deployment deploy : deploymentList) {
            System.out.println("部署对象：" + deploy);
        }

//// 使用repositoryService查询单个流程定义
//        ProcessDefinition processDefPinition = repositoryService
//                .createProcessDefinitionQuery()
//                .deploymentId(deploymentId)
//                .singleResult();
//        System.out.println("查询单个流程定义：");
//        System.out.println("流程定义：" + processDefPinition);


// 使用repositoryService查询多个流程定义
        List<ProcessDefinition> processDefinitionList = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionCategoryLike("")
                .processDefinitionName("definitionname")
//                .deploymentId(deploymentId)
                .listPage(0, 100);
        System.out.println("查询多个流程定义：");
        for (ProcessDefinition pd : processDefinitionList) {
            System.out.println("流程定义：" + pd);
        }
    }

    @Autowired
    TaskService taskService;

    /**
     * 查询任务
     */
    @Test
    public void test_query_task() {
        String candidateUser = "other";
        String processDefinitionKey = "myProcess_inputStream_01";
// 创建TaskService
        TaskService taskService = processEngine.getTaskService();
//查询组任务
        List<Task> list = taskService.createTaskQuery()//
//                .processDefinitionKey(processDefinitionKey)//
                .taskCandidateUser(candidateUser)//根据候选人查询
                .list();
        System.out.println(">>> other任务：" + list);

        candidateUser = "john";
        list = taskService.createTaskQuery()//
//                .processDefinitionKey(processDefinitionKey)//
                .taskCandidateUser(candidateUser)//根据候选人查询
                .list();
        System.out.println(">>> john任务：" + list);
    }


    @Autowired
    HistoryService historyService;

    /**
     * 查询历史
     */
    @Test
    public void test_query_history() {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        List<HistoricProcessInstance> list = historicProcessInstanceQuery
                .involvedUser("bob")
//                .processDefinitionKey("myProcess_BpmnModel_01")
                .processInstanceBusinessKey("someBusiness")
                .list();
        System.out.println(">>>历史记录："+list.size()+"条");
        list.forEach(hisProcess -> {
            System.out.println(">>>历史记录：" + hisProcess);
            String superProcessInstanceId = hisProcess.getSuperProcessInstanceId();//是null，没做到过滤
            HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
            List<HistoricTaskInstance> tasks = historicTaskInstanceQuery
                    .processInstanceId(superProcessInstanceId)
                    .list();
            System.out.println(">>>历史记录："+tasks.size()+"条");
            tasks.forEach(hisTask -> System.out.println(">>>历史记录：" + hisTask));


        });

//        historyService.createProcessInstanceHistoryLogQuery()
    }


}
