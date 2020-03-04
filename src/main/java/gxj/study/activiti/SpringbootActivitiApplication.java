package gxj.study.activiti;

//import gxj.study.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
        (scanBasePackages = {"gxj.study.activiti"})
@EnableAspectJAutoProxy(proxyTargetClass = false)
//@Import(AppConfig.class)
//@SpringBootApplication(scanBasePackages = "gxj.study",exclude = {
//        DataSourceAutoConfiguration.class
//})
public class SpringbootActivitiApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringbootActivitiApplication.class, args);
        System.out.println("===finish===");
    }

}
