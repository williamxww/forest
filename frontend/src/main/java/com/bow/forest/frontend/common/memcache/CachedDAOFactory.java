package com.bow.forest.frontend.common.memcache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.TreeMap;

public final class CachedDAOFactory
{
    private static Map<String, CachedDAO> cachedDAOMap = new HashMap<String, CachedDAO>();
    private static Map<String, CacheMapValue> cacheNameMap = new HashMap<String, CacheMapValue>();
    
    public static void init(String configFile)
    {
        cachedDAOMap.clear();
      
        try
        {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder; 
            builder = builderFactory.newDocumentBuilder();
              
            Document doc = builder.parse(configFile);
            NodeList daoNodes = doc.getElementsByTagName("item");
            for (int daoIndex = 0; daoIndex < daoNodes.getLength(); daoIndex++)
            {
                Element daoElem = (Element) daoNodes.item(daoIndex);
                String className = daoElem.getAttribute("daoClass");
                Class<?> daoClass = Class.forName(className);
                CachedDAO daoInstance = (CachedDAO) daoClass.newInstance();
                NodeList childList = daoElem.getChildNodes();
                CacheMapValue mapValue = new CacheMapValue();
                for (int i = 0; i < childList.getLength(); i++)
                {
                    Node childNode = childList.item(i);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element element = (Element) childNode;
                        String tagName = element.getTagName();
                        String textContent = element.getTextContent().trim();
                        if ("name".equals(tagName))
                        {
                            checkSameName(textContent);
                            cachedDAOMap.put(textContent, daoInstance);
                            cacheNameMap.put(textContent, mapValue);
                        }
                        else if ("refreshMode".equals(tagName))
                        {
                            if (daoInstance instanceof RefreshableDAO)
                            {
                                checkRefreshMode(textContent);

                                int refreshMode = Integer.parseInt(textContent);;
                                ((RefreshableDAO) daoInstance).setRefreshMode(refreshMode);
                            }
                        }
                        else if ("needLock".equals(tagName))
                        {
                            if (daoInstance instanceof RefreshableDAO)
                            {
                                checkNeedLock(textContent);
                                boolean needLock = "true".equals(textContent);
                                ((RefreshableDAO) daoInstance).setNeedLock(needLock);
                            }
                        }
                        else if ("Cache".equals(tagName))
                        {
                            Class<?> cacheClz = Class.forName(textContent);
                            Cache cacheInstance = (Cache) cacheClz.newInstance();
                            cacheInstance.init();
                            daoInstance.setCache(cacheInstance);
                        }
                        else if ("DBDtaLoader".equals(tagName))
                        {
                            Class<?> loaderClz = Class.forName(textContent);
                            DBDataLoader loader = (DBDataLoader) loaderClz.newInstance();
                            daoInstance.setDataLoader(loader);
                        }
                        else if ("prefixName".equals(tagName))
                        {
                            mapValue.setPrefixName(textContent);
                        }
                        else if ("cfgName".equals(tagName))
                        {
                            mapValue.setConfigName(textContent);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static CachedDAO getCachedDAO(String cacheName)
    {
        return cachedDAOMap.get(cacheName);
    }
    
    public static void checkSameName(String checkName) throws Exception
    {        
        if (cachedDAOMap.containsKey(checkName))
        {
            throw new Exception("exist same cache name");   
        }                     
    }
    
    public static void checkNeedLock(String needLock) throws Exception
    {
        if (!"true".equals(needLock) && !"false".equals(needLock))
        {
            throw new Exception("error needLock");
        }        
    }
    
    public static void checkRefreshMode(String refreshMode) throws Exception
    {
        String syncMode = RefreshableDAO.REFRESH_MODE_SYNC + "";
        String asyncMode = RefreshableDAO.REFRESH_MODE_ASYNC + "";
        if (!syncMode.equals(refreshMode) && !asyncMode.equals(refreshMode))
        {
            throw new Exception("error refreshMode");
        }        
    }
    
    public static Map<String, CachedDAO> sort()
    {
        Map<String, CachedDAO> sortedMap = new TreeMap<String, CachedDAO>(new Comparator<String>()
        {
            public int compare(String str1, String str2)
            {
                int result = str1.compareTo(str2);
                return result;
            }
        });
        Set<String> col = cachedDAOMap.keySet();
        Iterator<String> iterator = col.iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            CachedDAO value = cachedDAOMap.get(key);
            sortedMap.put(key, value);
        }
        return sortedMap;
    }
    
    public static String getPrefix(String cacheName)
    {
        if (null == cacheName || cacheName.isEmpty())
        {
            return null;
        }
        return cacheNameMap.get(cacheName).getPrefixName();
    }

    public static String getCfgName(String cacheName)
    {
        if (null == cacheName || cacheName.isEmpty())
        {
            return null;
        }
        return cacheNameMap.get(cacheName).getConfigName();
    }
    
}
