package gxj.study.activiti.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/10 17:09
 * @description
 */
@Getter
@AllArgsConstructor
public enum TypeEnum {

    /**
     * 排他网关
     */
    EXCLUSIVE(0,"exclusive"),

    /**
     * 排他网关
     */
    PARALLEL(1,"parallel"),

    /**
     * 排他网关
     */
    INCLUSIVE(2,"inclusive");

    private int num;
    /**
     * 类型
     */
    private  String code;



}
