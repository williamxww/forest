package com.bow.forest.frontend.common.memcache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.bow.forest.frontend.common.serialize.KryoTranscoder;
import com.bow.forest.frontend.common.servlet.snsserver.SystemConfig;
import com.bow.forest.frontend.common.servlet.snsserver.Util;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


/**
 * Memcached的管理类
 */
public final class MemcachedClientManager
{

    /* ****日志对象******* */
    private static final Logger LOG = LoggerFactory.getLogger(MemcachedClientManager.class);

    // instance
    private static MemcachedClientManager instance = new MemcachedClientManager();

    // 针对需要连接多个memcache的模块
    private Map<String, IMemcachedClient> map = new HashMap<String, IMemcachedClient>(10);

    // 存放实际的缓存条目和对应的组列表
    private Map<String, List<String>> itemGroupLstMap = new HashMap<String, List<String>>();

    // 获取配置文件的信息
    private static MemcachedConfigBean memcachedConfigBean = MemcachedClientManager.getMemecachedParam(SystemConfig
        .getInstance().getConfigPath());

    private Random random = new Random();

    // init tag
    private boolean inited = false;

    /**
     * 路径分割符号
     */
    public static final String FILE_SEPERATOR = System.getProperty("file.separator");

    /**
     * 缓存参数的BeanId
     */
    private static final String MEMCONFIG = "memConfig";

    /**
     * 私有化构造方法
     */
    private MemcachedClientManager()
    {
    }

    /**
     * 获取一个自身的实例
     * 
     * @return MemcachedClientManager
     */
    public static MemcachedClientManager getInstance()
    {
        return instance;
    }

    /**
     * 初始化配置参数
     */
    public synchronized void init()
    {
        LOG.debug("Enter MemcachedClientManager init");

        try
        {
            if (!inited)
            {
                if (null != memcachedConfigBean)
                {

                    // 获取緩存服务器组的列表
                    Map<String, MemcachedEntity> cachedMap = memcachedConfigBean.getCacheParams();

                    // 从cachedMap中获取Iterator
                    Iterator<Entry<String, MemcachedEntity>> it = cachedMap.entrySet().iterator();

                    // 遍历缓存服务器的Map
                    while (it.hasNext())
                    {
                        Entry<String, MemcachedEntity> entry = (Entry<String, MemcachedEntity>) it.next();

                        // 获取缓存组的名称
                        String key = (String) entry.getKey();

                        // 获取缓存对象列表和缓存服务器列表的Bean
                        MemcachedEntity value = entry.getValue();

                        // 配置cached 节点
                        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddressMap(Util
                            .trim(value.getCacheServerList())));

                        // 设置一致性哈希
                        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
                        // builder.setSessionLocator(new ElectionMemcachedSessionLocator());

                        // 设置二进制协议
                        builder.setCommandFactory(new BinaryCommandFactory());

                        // 设置NIO的连接数
                        builder.setConnectionPoolSize(Integer.valueOf(value.getConnectSize()));

                        // modified by l65566 at 2011-10-26 for SAO-001 begin

                        // 使用其他的序列化对象
                        if (!Boolean.valueOf(value.getIsDefaultTranscode()).booleanValue())
                        {
                            int initSize = value.getInitBufSize();
                            int maxSize = value.getMaxBufSize();
                            KryoTranscoder transcoder = new KryoTranscoder(initSize, maxSize);

                            // 对象压缩门限至2048字节
                            transcoder.setCompressionThreshold(Integer.parseInt(value.getCompressionThreshold()));
                            builder.setTranscoder(transcoder);
                        }

                        // 构造缓存客户端对象
                        MemcachedClient client = builder.build();
                        // 如果模块只需要一个cache，以DEFAULT_CACHE为KEY
                        if (null != client)
                        {
                            // 合并因子参数配置
                            client.setMergeFactor(Integer.parseInt(value.getMergeFactor()));
                            client.setConnectTimeout(value.getConnectTimeout());
                            map.put(key, new XMemcachedClientImpl(client));
                        }

                    }// end of while (it.hasNext())

                    // 迭代处理所有cachegroup
                    for (Entry<String, MemcachedEntity> entry : memcachedConfigBean.getCacheParams().entrySet())
                    {
                        String groupName = entry.getKey();
                        MemcachedEntity memEntity = entry.getValue();
                        if (null != memEntity && !Util.isEmpty(memEntity.getObjectList()))
                        {
                            String objLst = Util.trim(memEntity.getObjectList());
                            String[] tmpArr = objLst.split(",");
                            List<String> objNameLst = Arrays.asList(tmpArr);
                            for (String objName : objNameLst)// 迭代处理该组下所配置的所有对象
                            {
                                List<String> groupNameList = itemGroupLstMap.get(objName);
                                if (null == groupNameList)
                                {
                                    groupNameList = new LinkedList<String>();
                                    itemGroupLstMap.put(objName, groupNameList);
                                }

                                // 如果当前组名列表中不包含则添加到List中
                                if (!groupNameList.contains(groupName))
                                {
                                    groupNameList.add(groupName);
                                }
                            }
                        }
                    }// end of for (Map.Entry<String, MemcachedEntity> entry :
                     // memcachedConfigBean.getCacheParams().entrySet())
                }
                // modified by l65566 at 2011-10-26 for SAO-001 end
                inited = true;
            }
        }
        catch (Exception e)
        {
            LOG.error("Init Memcached Parameters Failure Reson: ", e);
        }

        LOG.debug("Exit MemcachedClientManager init");
    }

    /**
     * 获取Memcached的对象
     * 
     * @return IMemcachedClient
     */
    public IMemcachedClient getMemcachedClient(String key)
    {

        return map.get(key);
    }

    /**
     * 获取Memcached的配置信息
     * 
     * @return MemcachedConfigBean
     */
    public static MemcachedConfigBean getMemecachedParam(final String path)
    {
        LOG.debug("Enter MemcachedClientManager getMemecachedParam,path=" + path);

        // 根据Id获取实体Bean
        MemcachedConfigBean memcachedConfigBean = null;
        try
        {
            // 获取信息
            Resource rs = new FileSystemResource(path);
            BeanFactory factory = new XmlBeanFactory(rs);
            memcachedConfigBean = (MemcachedConfigBean) factory.getBean(MEMCONFIG);
        }
        catch (Exception e)
        {
            LOG.error("MemcachedClientManager getMemecachedParam is Read Memcached Config File failure :", e);
        }

        LOG.debug("Exit MemcachedClientManager getMemecachedParam,path=" + path);

        return memcachedConfigBean;

    }

    /**
     * 根据缓存对象的名称获取所在的缓存组的名称
     * 
     * @param cachedItem
     *            缓存对象的名称
     * @return 缓存组列表
     */
    public List<String> getCachedGroupByCacheItem(String cachedItem)
    {
        LOG.debug("Enter MemcachedClientManager getCachedGroupByCacheItem");

        // 定义List保存缓存组列表
        List<String> caList = new ArrayList<String>();

        try
        {

            Map<String, MemcachedEntity> map = memcachedConfigBean.getCacheParams();

            // 从cachedMap中获取Iterator
            Iterator<Entry<String, MemcachedEntity>> it = map.entrySet().iterator();

            // 遍历缓存服务器的Map
            while (it.hasNext())
            {

                Entry<String, MemcachedEntity> entry = (Entry<String, MemcachedEntity>) it.next();
                // 获取缓存组的名称
                String key = (String) entry.getKey();

                // 获取缓存对象列表和缓存组列表的Bean
                MemcachedEntity value = entry.getValue();

                // modify by kf56385 at 2011-12-26 for IRD-21877 begin
                // 看缓存对象列表是否包含cachedItem
                if (value.getObjList().contains(cachedItem))
                {

                    caList.add(key);
                }
                // modify by kf56385 at 2011-12-26 for IRD-21877 end
            }
        }
        catch (Exception e)
        {
            LOG.error("MemcachedClientManager.getCachedGroupByCacheItem() error:", e);
        }

        LOG.debug("Exit MemcachedClientManager getCachedGroupByCacheItem");
        return caList;
    }

    /**
     * 根据缓存组列表随即获取一个服务组的名称
     * 
     * @return
     */
    public String getGroupName(String cacheItemName)
    {
        LOG.debug("Enter MemcachedClientManager getGroupName");

        // 定义缓存组的名称
        String groupName = "";

        try
        {
            // 根据配置缓对象的名称查询缓存服务器的列表
            // List<String> cacheList = getCachedGroupByCacheItem(cacheItemName);

            List<String> cacheList = itemGroupLstMap.get(cacheItemName);
            // 获取列表的大小
            int size = 0;
            if (null != cacheList)
            {
                size = cacheList.size();
            }

            // 如果没有一个缓存组，则抛出一个异常
            if (0 == size)
            {
                throw new RuntimeException("CachedGroupNotFoundException");
            }
            else if (1 == size)
            {// 如果大小为1，则直接获取

                // 获取缓存组中的组名称
                groupName = cacheList.get(0);
            }
            else
            {// 如果列表大于2,则随机选取1个
                groupName = cacheList.get(random.nextInt(size));
            }
        }
        catch (Exception e)
        {
            LOG.error("", e);
        }

        LOG.debug("Exit MemcachedClientManager getGroupName");
        return groupName;
    }

}
