package gxj.study.activiti.aspect;


import lombok.extern.slf4j.Slf4j;
import org.activiti.api.runtime.shared.NotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/20 9:36
 * @description
 */
@Aspect
@Slf4j
@Component
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class ActivitiAspect {
    public ActivitiAspect(){

    }

    /**
     * 处理错误
     * @param pjp
     * @throws Throwable
     */
    @Around("this(gxj.study.activiti.service.ProcessService)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("===around start====");
        try {
            return pjp.proceed();
        }
        catch (NotFoundException e){
            log.error("activti流程错误："+e);
            throw e;
        }
        catch (Exception e){
            log.error("activti流程错误："+e);
            throw e;
        }finally {
            System.out.println("===around end===");
        }
    }

}
