package common.java.concurrent.ratelimit;

import com.bow.forest.common.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vv
 * @since 2018/1/19.
 */
public class Limit {

    private String interfaceName;

    private String methodName;

    private String paramIndex;

    /**
     * 此接口被限制的tps
     */
    private int value;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParamIndex() {
        return paramIndex;
    }

    public void setParamIndex(String paramIndex) {
        this.paramIndex = paramIndex;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return getKey(interfaceName, methodName);
    }

    public static String getKey(String interfaceName, String methodName) {
        StringBuilder sb = new StringBuilder();
        if (interfaceName == null) {
            return sb.toString();
        }
        sb.append(interfaceName);
        if (methodName == null) {
            return sb.toString();
        }
        sb.append("-");
        sb.append(methodName);
        return sb.toString();
    }

    public List<Integer> getParamIndexList() {
        String[] paramStr = StringUtil.split(paramIndex, ",");
        List<Integer> result = new ArrayList<>();
        for (String s : paramStr) {
            result.add(StringUtil.toInt(s));
        }
        return result;
    }
}
