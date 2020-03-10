package gxj.study.util;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/5 10:31
 * @description
 */
public class BpmnMockData {
    public static String getBpmn01() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:tns=\"http://www.activiti.org/testm1583286749413\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" expressionLanguage=\"http://www.w3.org/1999/XPath\" id=\"m1583286749413\" name=\"\" targetNamespace=\"http://www.activiti.org/testm1583286749413\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <process id=\"myProcess_inputStream_01\" isClosed=\"false\" isExecutable=\"true\" processType=\"None\">\n" +
                "    <startEvent id=\"_2\" name=\"StartEvent\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_3\" name=\"初审_字节流_01\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_4\" name=\"复审_字节流_01\"/>\n" +
                "    <endEvent id=\"_5\" name=\"EndEvent\"/>\n" +
                "    <sequenceFlow id=\"_6\" sourceRef=\"_2\" targetRef=\"_3\"/>\n" +
                "    <sequenceFlow id=\"_7\" sourceRef=\"_3\" targetRef=\"_4\"/>\n" +
                "    <sequenceFlow id=\"_8\" sourceRef=\"_4\" targetRef=\"_5\"/>\n" +
                "  </process>\n" +
                "  </definitions>\n";
    }

    public static String getBpmnCondition01() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:tns=\"http://www.activiti.org/testm1583286749413\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" expressionLanguage=\"http://www.w3.org/1999/XPath\" id=\"m1583286749413\" name=\"\" targetNamespace=\"http://www.activiti.org/testm1583286749413\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "   <process id=\"myProcess_condition_01\" isClosed=\"false\" isExecutable=\"true\" processType=\"None\">\n" +
                "    <startEvent id=\"_2\" name=\"StartEvent\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_3\" name=\"task1\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_4\" name=\"task2\"/>\n" +
                "    <endEvent id=\"_5\" name=\"EndEvent\"/>\n" +
                "    <sequenceFlow id=\"_6\" sourceRef=\"_2\" targetRef=\"_3\"/>\n" +
                "    <userTask activiti:candidateGroups=\"otherTeam\" activiti:exclusive=\"true\" id=\"_9\" name=\"task3\"/>\n" +
                "    <exclusiveGateway gatewayDirection=\"Unspecified\" id=\"_10\" name=\"ExclusiveGateway\"/>\n" +
                "    <sequenceFlow id=\"_11\" sourceRef=\"_3\" targetRef=\"_10\"/>\n" +
                "    <sequenceFlow id=\"_12\" sourceRef=\"_10\" targetRef=\"_4\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${amount<=3}]]></conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"_13\" sourceRef=\"_10\" targetRef=\"_9\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${amount > 3}]]></conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"_14\" sourceRef=\"_4\" targetRef=\"_9\"/>\n" +
                "    <sequenceFlow id=\"_15\" sourceRef=\"_9\" targetRef=\"_5\"/>\n" +
                "  </process>\n" +
                "</definitions>\n";
    }

    public static String getBpmnCondition02() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:tns=\"http://www.activiti.org/testm1583286749413\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" expressionLanguage=\"http://www.w3.org/1999/XPath\" id=\"m1583286749413\" name=\"\" targetNamespace=\"http://www.activiti.org/testm1583286749413\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <process activiti:candidateStarterGroups=\"otherGroup\" activiti:candidateStarterUsers=\"other\" id=\"myProcess_condition_02\" isClosed=\"false\" isExecutable=\"true\" processType=\"None\">\n" +
                "    <startEvent activiti:initiator=\"other,john\" id=\"_2\" name=\"StartEvent\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_3\" name=\"task1\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_4\" name=\"task2\"/>\n" +
                "    <endEvent id=\"_5\" name=\"EndEvent\"/>\n" +
                "    <sequenceFlow id=\"_6\" sourceRef=\"_2\" targetRef=\"_3\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_9\" name=\"task3\"/>\n" +
                "    <exclusiveGateway gatewayDirection=\"Unspecified\" id=\"_10\" name=\"ExclusiveGateway\"/>\n" +
                "    <sequenceFlow id=\"_12\" sourceRef=\"_10\" targetRef=\"_4\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${amount<=3}]]></conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"_13\" sourceRef=\"_10\" targetRef=\"_9\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${amount > 3}]]></conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"_14\" sourceRef=\"_4\" targetRef=\"_9\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_7\" name=\"task1.1\"/>\n" +
                "    <sequenceFlow id=\"_8\" sourceRef=\"_3\" targetRef=\"_7\"/>\n" +
                "    <sequenceFlow id=\"_11\" sourceRef=\"_7\" targetRef=\"_10\"/>\n" +
                "    <exclusiveGateway gatewayDirection=\"Unspecified\" id=\"_15\" name=\"ExclusiveGateway\"/>\n" +
                "    <sequenceFlow id=\"_16\" sourceRef=\"_9\" targetRef=\"_15\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_17\" name=\"task4\"/>\n" +
                "    <userTask activiti:candidateGroups=\"otherTeam\" activiti:exclusive=\"true\" id=\"_18\" name=\"task5\"/>\n" +
                "    <sequenceFlow id=\"_19\" sourceRef=\"_15\" targetRef=\"_17\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${amount<=3}]]></conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"_20\" sourceRef=\"_15\" targetRef=\"_18\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${amount > 3}]]></conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"_21\" sourceRef=\"_17\" targetRef=\"_18\"/>\n" +
                "    <sequenceFlow id=\"_22\" sourceRef=\"_18\" targetRef=\"_5\"/>\n" +
                "  </process>\n" +
                "</definitions>\n";
    }


    public static String getBpmnFormProcess01(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:tns=\"http://www.activiti.org/testm1583286749413\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" expressionLanguage=\"http://www.w3.org/1999/XPath\" id=\"m1583286749413\" name=\"\" targetNamespace=\"http://www.activiti.org/testm1583286749413\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <error id=\"ERR_1\"/>\n" +
                "  <process activiti:candidateStarterGroups=\"otherGroup\" activiti:candidateStarterUsers=\"other\" id=\"myProcess_withForm_01\" isClosed=\"false\" isExecutable=\"true\" processType=\"None\">\n" +
                "    <startEvent activiti:formKey=\"fk_startenvet\" activiti:initiator=\"other,john\" id=\"_2\" name=\"StartEvent\">\n" +
                "      <extensionElements>\n" +
                "        <activiti:formProperty id=\"startName\" name=\"n_startName\" readable=\"true\" required=\"true\" type=\"String\" variable=\"v_startName\" writable=\"true\">\n" +
                "          <activiti:value id=\"startName\" name=\"n_startName\"/>\n" +
                "        </activiti:formProperty>\n" +
                "        <activiti:formProperty/>\n" +
                "      </extensionElements>\n" +
                "    </startEvent>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_3\" name=\"task1\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_4\" name=\"task2\"/>\n" +
                "    <endEvent id=\"_5\" name=\"EndEvent\"/>\n" +
                "    <sequenceFlow id=\"_6\" sourceRef=\"_2\" targetRef=\"_3\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_9\" name=\"task3\"/>\n" +
                "    <exclusiveGateway gatewayDirection=\"Unspecified\" id=\"_10\" name=\"ExclusiveGateway\"/>\n" +
                "    <sequenceFlow id=\"_12\" sourceRef=\"_10\" targetRef=\"_4\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${amount<=3}]]></conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"_13\" sourceRef=\"_10\" targetRef=\"_9\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${amount > 3}]]></conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"_14\" sourceRef=\"_4\" targetRef=\"_9\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" activiti:formKey=\"fk_task1.1form\" id=\"_7\" name=\"task1.1\">\n" +
                "      <extensionElements>\n" +
                "        <activiti:formProperty id=\"name\" name=\"姓名\" readable=\"true\" required=\"true\" type=\"String\" variable=\"v_name\" writable=\"true\">\n" +
                "          <activiti:value id=\"name1\" name=\"n_name1\"/>\n" +
                "          <activiti:value id=\"name2\" name=\"n_name2\"/>\n" +
                "          <activiti:value id=\"name3\" name=\"n_name3\"/>\n" +
                "        </activiti:formProperty>\n" +
                "        <activiti:formProperty id=\"age\" name=\"年龄\" readable=\"true\" required=\"false\" type=\"String\" variable=\"v_age\" writable=\"true\">\n" +
                "          <activiti:value id=\"age1\" name=\"n_age1\"/>\n" +
                "          <activiti:value id=\"age2\" name=\"n_age2\"/>\n" +
                "        </activiti:formProperty>\n" +
                "      </extensionElements>\n" +
                "    </userTask>\n" +
                "    <sequenceFlow id=\"_8\" sourceRef=\"_3\" targetRef=\"_7\"/>\n" +
                "    <sequenceFlow id=\"_11\" sourceRef=\"_7\" targetRef=\"_10\"/>\n" +
                "    <exclusiveGateway gatewayDirection=\"Unspecified\" id=\"_15\" name=\"ExclusiveGateway\"/>\n" +
                "    <sequenceFlow id=\"_16\" sourceRef=\"_9\" targetRef=\"_15\"/>\n" +
                "    <userTask activiti:candidateGroups=\"activitiTeam\" activiti:exclusive=\"true\" id=\"_17\" name=\"task4\"/>\n" +
                "    <userTask activiti:candidateGroups=\"otherTeam\" activiti:exclusive=\"true\" id=\"_18\" name=\"task5\"/>\n" +
                "    <sequenceFlow id=\"_19\" sourceRef=\"_15\" targetRef=\"_17\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${amount<=3}]]></conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"_20\" sourceRef=\"_15\" targetRef=\"_18\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\"><![CDATA[${amount > 3}]]></conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"_21\" sourceRef=\"_17\" targetRef=\"_18\"/>\n" +
                "    <sequenceFlow id=\"_22\" sourceRef=\"_18\" targetRef=\"_5\"/>\n" +
                "  </process>\n" +
                " </definitions>\n";
    }

    public BpmnModel getBpmnCondition02Model(){
        BpmnModel bpmnModel = new BpmnModel();
//        Process process = new Process();
//        process.addFlowElement();
//        bpmnModel.addProcess();

        return bpmnModel;

    }
}
