package gxj.study.activiti.enums;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/10 17:09
 * @description
 */
@Getter
@AllArgsConstructor
public enum ProcessGateWayTypeEnum {

    /**
     * 排他网关
     */
    EXCLUSIVE("exclusive"),

    /**
     * 排他网关
     */
    PARALLEL("parallel"),

    /**
     * 排他网关
     */
    INCLUSIVE("inclusive");
    /**
     * 类型
     */
    private  String code;



}
