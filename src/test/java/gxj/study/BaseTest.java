package gxj.study;

import gxj.study.activiti.SpringbootActivitiApplication;
import gxj.study.activiti.config.SecurityUtil;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootActivitiApplication.class)
//@EnableAspectJAutoProxy(proxyTargetClass = false)
public class BaseTest {

}
