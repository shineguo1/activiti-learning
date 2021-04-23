package gxj.study.activiti.service;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/4/14 16:48
 * @description
 */
@Service
public class UserInfoService {

    public String selectSuperior(String username){
        return "username:"+username;
    }

    public Object parseObject(String json){
        return JSON.parseObject(json);
    }

    public List<String> selectLeaders(){
        return Arrays.asList("a","b","c","d,dd");
    }
}
