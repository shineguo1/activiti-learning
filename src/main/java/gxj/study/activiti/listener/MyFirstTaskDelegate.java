package gxj.study.activiti.listener;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/13 11:01
 * @description
 */
@Component
public class MyFirstTaskDelegate extends BaseTaskDelegate implements Serializable{
    @Override
    boolean doTask() {
        // suppose that system do some business tasks and find some jdbc errors
        // or you can return true while task is succeeded
        this.setDeleteReason("system tasks failed with jdbc errors：Duplicate entry '5' for key 'PRIMARY'");
        return false;
    }
}
