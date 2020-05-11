package gxj.study.activiti.listener;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/13 17:44
 */
@Component
public interface ProcessCallbackTask extends Serializable {
    /**
     *  执行系统任务
     * @return 任务执行结果
     */
    Result doTask();
}
