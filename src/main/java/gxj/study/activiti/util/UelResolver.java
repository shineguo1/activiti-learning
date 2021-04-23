package gxj.study.activiti.util;


import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.ExpressionManager;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/3/22 10:02
 * @description
 */
public class UelResolver {

    private static final ExpressionManager DEFAULT_EXPRESSION_MANAGER = new ExpressionManager();

    public static String resolveComposite(String compositeUelContext, VariableScope variableScope) {
        StringBuilder result = new StringBuilder();
        char[] chars = compositeUelContext.toCharArray();
        int left = 0, right = 0;
        int length = chars.length;
        while (left < length) {
            if (chars[left] == '$' && left + 1 < length && chars[left + 1] == '{') {
                //uel表达式
                StringBuilder uel = new StringBuilder();
                uel.append("${");
                right = left + 2;
                while (right < length && chars[right] != '}') {
                    uel.append(chars[right]);
                    right++;
                }
                if(right < length && chars[right] == '}'){
                    uel.append("}");
                }
                result.append(resolve(uel.toString(),variableScope));
                left = right + 1;
            } else {
                //字面量
                result.append(chars[left++]);
            }
        }
        return result.toString();
    }

    public static Object resolve(String uelContext, VariableScope variableScope) {
        Expression expression = DEFAULT_EXPRESSION_MANAGER.createExpression(uelContext);
        return expression.getValue(variableScope);
    }

}
