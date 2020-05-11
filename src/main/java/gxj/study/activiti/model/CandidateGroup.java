package gxj.study.activiti.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;


/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/25 14:56
 * @description 本系统在Acitivi中配置2种用户组，一种是机构+职务，
 * 一种是系统管理员角色用户组，这个类用来表示前一种。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateGroup implements Serializable {
    /**
     * 机构id
     */
    private Long orgId;
    /**
     * 职务
     */
    private String duty;

    private static final String CANDIDATE_GROUP_ORG_PREFIX ="ORG";

    @JsonIgnore
    public String getGroupName() {
        if (Objects.isNull(orgId) ||orgId < 1L || StringUtils.isBlank(duty)) {
            return null;
        }
        return CANDIDATE_GROUP_ORG_PREFIX + String.valueOf(orgId) + "_" + duty;
    }

    public static CandidateGroup createInstance(String groupName) {
        CandidateGroup inst = new CandidateGroup();
        if(StringUtils.isNotBlank(groupName) && groupName.startsWith(CANDIDATE_GROUP_ORG_PREFIX)) {
            String[] splits = groupName.split("_");
            inst.orgId = Long.valueOf(splits[0].split("ORG")[1]);
            inst.duty = splits[1];
        }
        return inst;
    }

    @JsonIgnore
    public static boolean isEmpty(CandidateGroup candidateGroup){
        return Objects.isNull(candidateGroup) || StringUtils.isBlank(candidateGroup.getGroupName());
    }

    public static void main(String[] args) {
         CandidateGroup.createInstance("ORG12_STAFF");
    }
}
