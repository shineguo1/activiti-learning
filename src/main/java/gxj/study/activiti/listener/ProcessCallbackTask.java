package gxj.study.activiti.listener;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/13 17:44
 */
@Component
public interface ProcessCallbackTask {
    /**
     *  执行系统任务
     * @return 任务执行结果
     */
    Result doTask();
}
@Data
class Result{
    private boolean success;
    private String errorMsg;
}
