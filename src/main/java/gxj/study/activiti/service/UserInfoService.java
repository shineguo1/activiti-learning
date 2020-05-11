package gxj.study.activiti.service;

import org.springframework.stereotype.Service;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/4/14 16:48
 * @description
 */
@Service
public class UserInfoService {

    public String selectSuperior(String username){
        return "admin";
    }
}
