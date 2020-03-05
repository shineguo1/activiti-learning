package gxj.study.util;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2020/3/5 10:31
 * @description
 */
public class BpmnMockData {
    public static String getBpmn01(){
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
}
