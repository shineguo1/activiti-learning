package gxj.study.activiti.listener.examples;

import gxj.study.activiti.listener.ProcessCallbackTask;
import gxj.study.activiti.listener.Result;
import org.springframework.stereotype.Component;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/16 10:00
 * @description
 */
@Component
public class MySecondCallBackTask implements ProcessCallbackTask {
    @Override
    public Result doTask() {

        Result result = new Result();
        result.setSuccess(true);
        result.setErrorMsg("JDBC ERROR");
        System.out.println(">>> MySecondCallBackTask 系统任务mock success");
        return result;
    }
}
