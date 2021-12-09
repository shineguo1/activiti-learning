package gxj.study.activiti.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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



    /**
     * 查询username实际审批部门的正职/副职领导 userService
     */
    public String selectRootLeaders(String username, String... unifiedDuties) {
        List<String> users = Arrays.asList("a", "b", "c");
        return StringUtils.join(users.stream().collect(Collectors.toList()), ",");
    }

    /**
     * 查询username实际审批部门的正职/副职领导 userService
     */
    public String[] selectRootLeaderArray(String username, String... unifiedDuties) {
        List<String> users = Arrays.asList("ab", "b", "c");
        return users.stream().map(o->o).toArray(String[]::new);
    }

    /**
     * 查询username实际审批部门的正职/副职领导 userService
     */
    public List<String> selectRootLeaderList(String username, String... unifiedDuties) {
        List<String> users = Arrays.asList("ab", "b", "c");
        return users;
    }

    public boolean valueIn(String value, String... valueList) {
        return Arrays.asList(valueList).contains(value);
    }
}
