package com.bow.forest.frontend.common.ratelimit.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bow.forest.common.utils.StringUtil;

/**
 * @author vv
 * @since 2018/1/19.
 */
public class WebXmlParser {

    private static final String LIMIT = "limit";

    private static final String URL = "url";

    private static final String PARAMS = "params";

    private static final String TPS = "tps";

    private Map<String, WebLimitInfo> limitMap = new ConcurrentHashMap();

    public WebXmlParser() {
        try {
            List<WebLimitInfo> limits = parse();
            for (WebLimitInfo lm : limits) {
                limitMap.put(lm.getUrl(), lm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebLimitInfo getLimit(String url) {
        return limitMap.get(url);
    }

    private List<WebLimitInfo> parse() throws Exception {
        InputStream is = WebXmlParser.class.getClassLoader().getResourceAsStream("webRateLimit.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        Element root = doc.getDocumentElement();
        NodeList limits = root.getChildNodes();
        List<WebLimitInfo> result = new ArrayList();
        for (int i = 0; i < limits.getLength(); i++) {
            Node limitNode = limits.item(i);
            if (limitNode.getNodeType() != Element.ELEMENT_NODE || !LIMIT.equals(limitNode.getNodeName())) {
                // 说明不是LIMIT元素节点不处理
                continue;
            }
            WebLimitInfo obj = new WebLimitInfo();
            obj.setUrl(getAttr(limitNode, URL));
            String paramStr = getAttr(limitNode, PARAMS);
            if (paramStr != null) {
                String[] params = StringUtil.split(paramStr, ",");
                obj.setParams(params);
            }
            int value = StringUtil.toInt(getAttr(limitNode, TPS), 0);
            obj.setTps(value);
            result.add(obj);
        }
        return result;
    }

    private String getAttr(Node limitNode, String attrName) {
        NamedNodeMap attrs = limitNode.getAttributes();
        if (attrs != null) {
            Node attrNode = attrs.getNamedItem(attrName);
            if (attrNode != null) {
                return StringUtil.trim(attrNode.getNodeValue());
            }
        }
        return null;
    }
}
