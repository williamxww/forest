package common.java.concurrent.ratelimit;

import com.bow.forest.common.utils.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vv
 * @since 2018/1/19.
 */
public class XmlParser {

    private static final String INTERFACE = "interfaceName";

    private static final String METHOD = "methodName";

    private static final String PARAM_INDEX = "paramIndex";

    private static final String VALUE = "value";

    private Map<String, Limit> limitMap = new ConcurrentHashMap();

    public XmlParser() {
        try {
            List<Limit> limits = parse();
            for (Limit lm : limits) {
                limitMap.put(lm.getKey(), lm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Limit getLimit(String interfaceName, String methodName) {
        return limitMap.get(Limit.getKey(interfaceName, methodName));
    }


    private List<Limit> parse() throws Exception {
        InputStream is = RateLimiterDemo.class.getClassLoader()
                .getResourceAsStream("common/java/concurrent/ratelimit/rateLimit.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        Element root = doc.getDocumentElement();
        NodeList limits = root.getChildNodes();
        List<Limit> result = new ArrayList();
        for (int i = 0; i < limits.getLength(); i++) {
            Node limitNode = limits.item(i);
            if (limitNode.getNodeType() != Element.ELEMENT_NODE) {
                // 说明不是元素节点不处理
                continue;
            }
            Limit obj = new Limit();
            obj.setInterfaceName(getAttr(limitNode, INTERFACE));
            obj.setMethodName(getAttr(limitNode, METHOD));
            obj.setParamIndex(getAttr(limitNode, PARAM_INDEX));
            int value = StringUtil.toInt(getAttr(limitNode, VALUE), 0);
            obj.setValue(value);
            result.add(obj);
        }
        return result;
    }

    private String getAttr(Node limitNode, String attrName) {
        NamedNodeMap attrs = limitNode.getAttributes();
        if (attrs != null) {
            Node attrNode = attrs.getNamedItem(attrName);
            return StringUtil.trim(attrNode.getNodeValue());
        }
        return null;
    }
}
