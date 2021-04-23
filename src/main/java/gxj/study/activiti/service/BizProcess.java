package gxj.study.activiti.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/4/14 10:11
 * @description
 */
@Service
@Slf4j
public class BizProcess {


    public boolean hasNextStep(Long bizId){
        //bizId 指 日常工作id（itemId）
        if(Objects.isNull(bizId)){
            throw new RuntimeException("流程丢失BizId");
        }
        //查WORK_DAILY_ITEM_STEP表，WHERE itemId = bizId and 实际完成时间 = null

        //if(list.size > 0) return true;

        return true;
    }

    public List<String> selectNextStepRelDepts(long bizid){
        return Arrays.asList("a","b","c","d,dd");
    }
}
