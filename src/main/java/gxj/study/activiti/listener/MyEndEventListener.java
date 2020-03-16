package gxj.study.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/12 10:07
 * @description
 */
@Service
public class MyEndEventListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) {
        if(execution.getEventName().equals(EVENTNAME_END)){
            System.out.println(execution.isEnded());
            System.out.println(">>> 流程结束");


        }
    }
}
