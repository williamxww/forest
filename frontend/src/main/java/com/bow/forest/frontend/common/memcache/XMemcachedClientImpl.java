package com.bow.forest.frontend.common.memcache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

import com.bow.forest.frontend.common.servlet.snsserver.ConcurrentController;
import com.bow.forest.frontend.common.servlet.snsserver.TPSController;
import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientStateListener;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.ReconnectRequest;
import net.rubyeye.xmemcached.networking.Connector;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.utils.Protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * MemcacheClient二次封装类，通过对MemcacheClient接口的简单二次封装，实现 屏蔽MemcacheServer网络交互异常，超时异常，透传MemcacheException
 * 统一入口，方便业务对MemcacheClient进行二次定制开发，如增加流控功能 统一监控，对MemcacheServer连接交互类异常由统一入口进行状态监控，便于接入网管
 * 
 * @author j65521
 * @version [版本号, 2011-4-19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class XMemcachedClientImpl implements IMemcachedClient
{
    private static final Logger dlog = LoggerFactory.getLogger(XMemcachedClientImpl.class);

    // 数据读
    public static final String OPERATE_GET = "get";

    // 数据锁读
    public static final String OPERATE_GETS = "gets";

    // 数据添加
    public static final String OPERATE_ADD = "add";

    // 数据追加
    public static final String OPERATE_APPEND = "append";

    // 数据乐观锁写
    public static final String OPERATE_CAS = "cas";

    // 数据更新
    public static final String OPERATE_SET = "set";

    // 数据准备
    public static final String OPERATE_PREPEND = "prepend";

    // 数据替换
    public static final String OPERATE_REPLACE = "replace";

    // 计数器加
    public static final String OPERATE_INCR = "incr";

    // 计数器减
    public static final String OPERATE_DECR = "decr";

    // 数据删除
    public static final String OPERATE_DELETE = "delete";

    // 缓存刷新
    public static final String OPERATE_FLUSHALL = "flushall";

    // 缓存服务器状态和数据统计
    public static final String OPERATE_STATS = "stats";

    // 缓存服务器版本
    public static final String OPERATE_VERSION = "version";

    // 缓存对象迭代
    public static final String OPERATE_KeyIterator = "keyIterator";

    // MemcachedClient对象
    private MemcachedClient mc;

    // ConcurrentController
    private ConcurrentController parent = null;

    private ConcurrentController readController = null;

    private ConcurrentController writeController = null;

    /**
     * 构造方法
     */
    public XMemcachedClientImpl(MemcachedClient mc)
    {
        this.mc = mc;

        parent = new ConcurrentController();
        readController = new ConcurrentController(ConcurrentController.NO_LIMIT, parent);
        writeController = new ConcurrentController(ConcurrentController.NO_LIMIT, parent);

        readController.setTpsController(new TPSController(0, 1));
        writeController.setTpsController(new TPSController(0, 1));
    }

    // ------------------------------------------
    // ---- Memcache Server Management
    // ------------------------------------------

    public void addServer(String server, int port) throws IOException
    {
        mc.addServer(server, port);
    }

    public void addServer(InetSocketAddress inetSocketAddress) throws IOException
    {
        mc.addServer(inetSocketAddress);
    }

    public void addServer(String hostList) throws IOException
    {
        mc.addServer(hostList);
    }

    public void addServer(String server, int port, int weight) throws IOException
    {
        mc.addServer(server, port, weight);
    }

    public void addServer(InetSocketAddress inetSocketAddress, int weight) throws IOException
    {
        mc.addServer(inetSocketAddress, weight);
    }

    public void removeServer(String hostList)
    {
        mc.removeServer(hostList);
    }

    public Collection<InetSocketAddress> getAvaliableServers()
    {
        return mc.getAvaliableServers();
    }

    public void addStateListener(MemcachedClientStateListener listener)
    {
        mc.addStateListener(listener);
    }

    public void removeStateListener(MemcachedClientStateListener listener)
    {
        mc.removeStateListener(listener);
    }

    public Collection<MemcachedClientStateListener> getStateListeners()
    {
        return mc.getStateListeners();
    }

    // ------------------------------------------
    // ---- Memcache Operation
    // ------------------------------------------
    // ---- GET 数据读，支持批量操作
    // ---- GETS 数据锁读，支持批量操作
    // ---- ADD 数据新增，缓存中必须无key对象，支持无反馈操作
    // ---- APPEND 数据追加，支持无反馈操作
    // ---- CAS 数据乐观锁写，乐观锁不匹配时更新失败，支持无反馈操作
    // ---- SET 数据更新，无条件限制，支持无反馈操作
    // ---- PREPEND 数据准备
    // ---- REPLACE 数据替换，缓存中必须有key对象，支持无反馈操作
    // ---- INCR 计数器加，支持无反馈操作
    // ---- DECR 计数器减，支持无反馈操作
    // ---- DELETE 数据删除，支持无反馈操作
    // ---- FLUSHALL 缓存刷新，支持无反馈操作
    // ---- STATS 缓存服务器状态和数据统计
    // ---- VERSION 缓存服务器版本

    // ---- GET
    public Object get(String key, long timeout, Transcoder<Object> transcoder) throws MemcachedException
    {
        try
        {
            try
            {
                readController.beginTransaction();
            }
            catch (InterruptedException e)
            {
                onInterruptedException(OPERATE_GET, key, e);
            }

            try
            {
                return mc.get(key, timeout, transcoder);
            }
            catch (TimeoutException e)
            {
                onTimeoutException(OPERATE_GET, key, e);
            }
            catch (InterruptedException e)
            {
                onInterruptedException(OPERATE_GET, key, e);
            }
        }
        finally
        {
            readController.finishTransaction();
        }
        return null;
    }

    public Object get(String key, long timeout) throws MemcachedException
    {
        try
        {
            return mc.get(key, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GET, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GET, key, e);
        }

        return null;
    }

    public Object get(String key, Transcoder<Object> transcoder) throws MemcachedException
    {
        try
        {
            return mc.get(key, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GET, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GET, key, e);
        }

        return null;
    }

    public Object get(String key) throws MemcachedException
    {
        try
        {
            try
            {
                readController.beginTransaction();
            }
            catch (InterruptedException e)
            {
                onInterruptedException(OPERATE_GET, key, e);
            }

            try
            {
                return mc.get(key);
            }
            catch (TimeoutException e)
            {
                onTimeoutException(OPERATE_GET, key, e);
            }
            catch (InterruptedException e)
            {
                onInterruptedException(OPERATE_GET, key, e);
            }

        }
        finally
        {
            readController.finishTransaction();
        }

        return null;
    }

    public Map<String, Object> get(Collection<String> keyCollections, long timeout, Transcoder<Object> transcoder)
        throws MemcachedException
    {
        try
        {
            mc.get(keyCollections, timeout, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GET, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GET, "", e);
        }

        return null;
    }

    public Map<String, Object> get(Collection<String> keyCollections, Transcoder<Object> transcoder)
        throws MemcachedException
    {
        try
        {
            mc.get(keyCollections, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GET, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GET, "", e);
        }

        return null;
    }

    public Map<String, Object> get(Collection<String> keyCollections) throws MemcachedException
    {
        try
        {
            mc.get(keyCollections);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GET, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GET, "", e);
        }

        return null;
    }

    public Map<String, Object> get(Collection<String> keyCollections, long timeout) throws MemcachedException
    {
        try
        {
            mc.get(keyCollections, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GET, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GET, "", e);
        }

        return null;
    }

    // ---- GETS

    public GetsResponse<Object> gets(String key, long timeout, Transcoder<Object> transcoder) throws MemcachedException
    {
        try
        {
            return mc.gets(key, timeout, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GETS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GETS, key, e);
        }

        return null;
    }

    public GetsResponse<Object> gets(String key) throws MemcachedException
    {
        try
        {
            return mc.gets(key);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GETS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GETS, key, e);
        }

        return null;
    }

    public GetsResponse<Object> gets(String key, long timeout) throws MemcachedException
    {
        try
        {
            return mc.gets(key, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GETS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GETS, key, e);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public GetsResponse<Object> gets(String key, Transcoder transcoder) throws MemcachedException
    {
        try
        {
            return mc.gets(key, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GETS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GETS, key, e);
        }

        return null;
    }

    public Map<String, GetsResponse<Object>> gets(Collection<String> keyCollections, long timeout,
        Transcoder<Object> transcoder) throws MemcachedException
    {
        try
        {
            return mc.gets(keyCollections, timeout, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GETS, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GETS, "", e);
        }

        return null;
    }

    public Map<String, GetsResponse<Object>> gets(Collection<String> keyCollections) throws MemcachedException
    {
        try
        {
            mc.gets(keyCollections);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GETS, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GETS, "", e);
        }

        return null;
    }

    public Map<String, GetsResponse<Object>> gets(Collection<String> keyCollections, long timeout)
        throws MemcachedException
    {
        try
        {
            return mc.gets(keyCollections, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GETS, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GETS, "", e);
        }

        return null;
    }

    public Map<String, GetsResponse<Object>> gets(Collection<String> keyCollections, Transcoder<Object> transcoder)
        throws MemcachedException
    {
        try
        {
            return mc.gets(keyCollections, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_GETS, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_GETS, "", e);
        }

        return null;
    }

    // ---- ADD

    public boolean add(String key, int exp, Object value, Transcoder<Object> transcoder, long timeout)
        throws MemcachedException
    {
        try
        {
            return mc.add(key, exp, value, transcoder, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_ADD, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_ADD, key, e);
        }

        return false;
    }

    public boolean add(String key, int exp, Object value) throws MemcachedException
    {
        try
        {
            try
            {
                writeController.beginTransaction();
            }
            catch (InterruptedException e)
            {
                onInterruptedException(OPERATE_ADD, key, e);
            }

            try
            {
                return mc.add(key, exp, value);
            }
            catch (TimeoutException e)
            {
                onTimeoutException(OPERATE_ADD, key, e);
            }
            catch (InterruptedException e)
            {
                onInterruptedException(OPERATE_ADD, key, e);
            }
        }
        finally
        {
            writeController.finishTransaction();
        }

        return false;
    }

    public boolean add(String key, int exp, Object value, long timeout) throws MemcachedException
    {
        try
        {
            return mc.add(key, exp, value, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_ADD, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_ADD, key, e);
        }

        return false;
    }

    public boolean add(String key, int exp, Object value, Transcoder<Object> transcoder) throws MemcachedException
    {
        try
        {
            return mc.add(key, exp, value, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_ADD, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_ADD, key, e);
        }

        return false;
    }

    public void addWithNoReply(String key, int exp, Object value) throws MemcachedException
    {
        try
        {
            mc.addWithNoReply(key, exp, value);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_ADD, key, e);
        }
    }

    public void addWithNoReply(String key, int exp, Object value, Transcoder<Object> transcoder)
        throws MemcachedException
    {
        try
        {
            mc.addWithNoReply(key, exp, value, transcoder);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_ADD, key, e);
        }
    }

    // ---- APPEND

    public boolean append(String key, Object value) throws MemcachedException
    {
        try
        {
            return mc.append(key, value);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_APPEND, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_APPEND, key, e);
        }

        return false;
    }

    public boolean append(String key, Object value, long timeout) throws MemcachedException
    {
        try
        {
            return mc.append(key, value, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_APPEND, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_APPEND, key, e);
        }

        return false;
    }

    public void appendWithNoReply(String key, Object value) throws MemcachedException
    {
        try
        {
            mc.appendWithNoReply(key, value);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_APPEND, key, e);
        }
    }

    // ---- CAS

    public boolean cas(String key, int exp, Object value, long cas) throws MemcachedException
    {
        try
        {
            return mc.cas(key, exp, value, cas);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }

        return false;
    }

    public boolean cas(String key, int exp, Object value, Transcoder<Object> transcoder, long timeout, long cas)
        throws MemcachedException
    {
        try
        {
            return mc.cas(key, exp, value, transcoder, timeout, cas);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }

        return false;
    }

    public boolean cas(String key, int exp, Object value, long timeout, long cas) throws MemcachedException
    {
        try
        {
            return mc.cas(key, exp, value, timeout, cas);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }

        return false;
    }

    public boolean cas(String key, int exp, Object value, Transcoder<Object> transcoder, long cas)
        throws MemcachedException
    {
        try
        {
            return mc.cas(key, exp, value, transcoder, cas);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }

        return false;
    }

    public boolean cas(String key, int exp, CASOperation<Object> operation, Transcoder<Object> transcoder)
        throws MemcachedException
    {
        try
        {
            return mc.cas(key, exp, operation, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }

        return false;
    }

    public boolean cas(String key, int exp, GetsResponse<Object> getsReponse, CASOperation<Object> operation,
        Transcoder<Object> transcoder) throws MemcachedException
    {
        try
        {
            return mc.cas(key, exp, getsReponse, operation, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }

        return false;
    }

    public boolean cas(String key, int exp, GetsResponse<Object> getsReponse, CASOperation<Object> operation)
        throws MemcachedException
    {
        try
        {
            return mc.cas(key, exp, getsReponse, operation);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }

        return false;
    }

    public boolean cas(String key, GetsResponse<Object> getsResponse, CASOperation<Object> operation)
        throws MemcachedException
    {
        try
        {
            return mc.cas(key, getsResponse, operation);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }

        return false;
    }

    public boolean cas(String key, int exp, CASOperation<Object> operation) throws MemcachedException
    {
        try
        {
            return mc.cas(key, exp, operation);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }

        return false;
    }

    public boolean cas(String key, CASOperation<Object> operation) throws MemcachedException
    {
        try
        {
            return mc.cas(key, operation);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }

        return false;
    }

    public void casWithNoReply(String key, GetsResponse<Object> getsResponse, CASOperation<Object> operation)
        throws MemcachedException
    {
        try
        {
            mc.casWithNoReply(key, getsResponse, operation);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }
    }

    public void casWithNoReply(String key, int exp, GetsResponse<Object> getsReponse, CASOperation<Object> operation)
        throws MemcachedException
    {
        try
        {
            mc.casWithNoReply(key, exp, getsReponse, operation);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }
    }

    public void casWithNoReply(String key, int exp, CASOperation<Object> operation) throws MemcachedException
    {
        try
        {
            mc.casWithNoReply(key, exp, operation);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }
    }

    public void casWithNoReply(String key, CASOperation<Object> operation) throws MemcachedException
    {
        try
        {
            mc.casWithNoReply(key, operation);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_CAS, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_CAS, key, e);
        }
    }

    // ---- SET

    public boolean set(String key, int exp, Object value, Transcoder<Object> transcoder, long timeout)
        throws MemcachedException
    {
        try
        {
            return mc.set(key, exp, value, transcoder, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_SET, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_SET, key, e);
        }

        return false;
    }

    public boolean set(String key, int exp, Object value) throws MemcachedException
    {
        try
        {
            try
            {
                writeController.beginTransaction();
            }
            catch (InterruptedException e)
            {
                onInterruptedException(OPERATE_SET, key, e);
            }
            try
            {
                return mc.set(key, exp, value);
            }
            catch (TimeoutException e)
            {
                onTimeoutException(OPERATE_SET, key, e);
            }
            catch (InterruptedException e)
            {
                onInterruptedException(OPERATE_SET, key, e);
            }
        }
        finally
        {
            writeController.finishTransaction();
        }
        return false;
    }

    public boolean set(String key, int exp, Object value, long timeout) throws MemcachedException
    {
        try
        {
            return mc.set(key, exp, value, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_SET, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_SET, key, e);
        }

        return false;
    }

    public boolean set(String key, int exp, Object value, Transcoder<Object> transcoder) throws MemcachedException
    {
        try
        {
            return mc.set(key, exp, value, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_SET, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_SET, key, e);
        }

        return false;
    }

    public void setWithNoReply(String key, int exp, Object value) throws MemcachedException
    {
        try
        {
            mc.setWithNoReply(key, exp, value);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_SET, key, e);
        }
    }

    public void setWithNoReply(String key, int exp, Object value, Transcoder<Object> transcoder)
        throws MemcachedException
    {
        try
        {
            mc.setWithNoReply(key, exp, value, transcoder);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_SET, key, e);
        }
    }

    // ---- PREPEND
    public boolean prepend(String key, Object value) throws MemcachedException
    {
        try
        {
            return mc.prepend(key, value);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_PREPEND, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_PREPEND, key, e);
        }

        return false;
    }

    public boolean prepend(String key, Object value, long timeout) throws MemcachedException
    {
        try
        {
            return mc.prepend(key, value, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_PREPEND, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_PREPEND, key, e);
        }

        return false;
    }

    public void prependWithNoReply(String key, Object value) throws MemcachedException
    {
        try
        {
            mc.prependWithNoReply(key, value);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_PREPEND, key, e);
        }
    }

    // ---- REPLACE
    public boolean replace(String key, int exp, Object value, Transcoder<Object> transcoder, long timeout)
        throws MemcachedException
    {
        try
        {
            return mc.replace(key, exp, value, transcoder, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_REPLACE, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_REPLACE, key, e);
        }

        return false;
    }

    public boolean replace(String key, int exp, Object value) throws MemcachedException
    {
        try
        {
            return mc.replace(key, exp, value);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_REPLACE, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_REPLACE, key, e);
        }

        return false;
    }

    public boolean replace(String key, int exp, Object value, long timeout) throws MemcachedException
    {
        try
        {
            return mc.replace(key, exp, value, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_REPLACE, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_REPLACE, key, e);
        }

        return false;
    }

    public boolean replace(String key, int exp, Object value, Transcoder<Object> transcoder) throws MemcachedException
    {
        try
        {
            return mc.replace(key, exp, value, transcoder);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_REPLACE, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_REPLACE, key, e);
        }

        return false;
    }

    public void replaceWithNoReply(String key, int exp, Object value) throws MemcachedException
    {
        try
        {
            mc.replaceWithNoReply(key, exp, value);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_REPLACE, key, e);
        }
    }

    public void replaceWithNoReply(String key, int exp, Object value, Transcoder<Object> transcoder)
        throws MemcachedException
    {
        try
        {
            mc.replaceWithNoReply(key, exp, value, transcoder);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_REPLACE, key, e);
        }
    }

    // ---- INCR
    public long incr(String key, long delta) throws MemcachedException
    {
        try
        {
            return mc.incr(key, delta);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_INCR, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_INCR, key, e);
        }

        return 0;
    }

    public long incr(String key, long delta, long initValue) throws MemcachedException
    {
        try
        {
            return mc.incr(key, delta, initValue);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_INCR, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_INCR, key, e);
        }

        return 0;
    }

    public long incr(String key, long delta, long initValue, long timeout) throws MemcachedException
    {
        try
        {
            return mc.incr(key, delta, initValue, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_INCR, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_INCR, key, e);
        }

        return 0;
    }

    public long incr(String key, long delta, long initValue, long timeout, int exp) throws MemcachedException
    {
        try
        {
            return mc.incr(key, delta, initValue, timeout, exp);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_INCR, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_INCR, key, e);
        }

        return 0;
    }

    public void incrWithNoReply(String key, long delta) throws MemcachedException
    {
        try
        {
            mc.incrWithNoReply(key, delta);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_INCR, key, e);
        }
    }

    // ----- DECR
    public long decr(String key, long delta) throws MemcachedException
    {
        try
        {
            return mc.decr(key, delta);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_DECR, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_DECR, key, e);
        }

        return 0;
    }

    public long decr(String key, long delta, long initValue) throws MemcachedException
    {
        try
        {
            return mc.decr(key, delta, initValue);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_DECR, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_DECR, key, e);
        }

        return 0;
    }

    public long decr(String key, long delta, long initValue, long timeout) throws MemcachedException
    {
        try
        {
            return mc.decr(key, delta, initValue, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_DECR, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_DECR, key, e);
        }

        return 0;
    }

    public long decr(String key, long delta, long initValue, long timeout, int exp) throws MemcachedException
    {
        try
        {
            return mc.decr(key, delta, initValue, timeout, exp);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_DECR, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_DECR, key, e);
        }

        return 0;
    }

    public void decrWithNoReply(String key, long delta) throws MemcachedException
    {
        try
        {
            mc.decrWithNoReply(key, delta);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_DECR, key, e);
        }
    }

    // ---- DELETE

    public boolean delete(String key, long opTimeout) throws MemcachedException
    {
        try
        {
            return mc.delete(key, opTimeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_DELETE, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_DELETE, key, e);
        }

        return false;
    }

    public boolean delete(String key) throws MemcachedException
    {
        try
        {
            return mc.delete(key);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_DELETE, key, e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_DELETE, key, e);
        }

        return false;
    }

    public void deleteWithNoReply(String key) throws MemcachedException
    {
        try
        {
            mc.deleteWithNoReply(key);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_DELETE, key, e);
        }
    }

    // ---- FLUSHALL
    public void flushAll() throws MemcachedException
    {
        try
        {
            mc.flushAll();
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_FLUSHALL, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_FLUSHALL, "", e);
        }
    }

    public void flushAll(long timeout) throws MemcachedException
    {
        try
        {
            mc.flushAll(timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_FLUSHALL, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_FLUSHALL, "", e);
        }
    }

    public void flushAll(InetSocketAddress address) throws MemcachedException
    {
        try
        {
            mc.flushAll(address);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_FLUSHALL, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_FLUSHALL, "", e);
        }
    }

    public void flushAll(InetSocketAddress address, long timeout) throws MemcachedException
    {
        try
        {
            mc.flushAll(address, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_FLUSHALL, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_FLUSHALL, "", e);
        }
    }

    public void flushAll(int exptime, long timeout) throws MemcachedException
    {
        try
        {
            mc.flushAll(exptime, timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_FLUSHALL, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_FLUSHALL, "", e);
        }
    }

    public void flushAll(InetSocketAddress address, long timeout, int exptime) throws MemcachedException
    {
        try
        {
            mc.flushAll(address, timeout, exptime);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_FLUSHALL, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_FLUSHALL, "", e);
        }
    }

    public void flushAllWithNoReply() throws MemcachedException
    {
        try
        {
            mc.flushAllWithNoReply();
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_FLUSHALL, "", e);
        }
    }

    public void flushAllWithNoReply(InetSocketAddress address) throws MemcachedException
    {
        try
        {
            mc.flushAllWithNoReply(address);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_FLUSHALL, "", e);
        }
    }

    public void flushAllWithNoReply(int exptime) throws MemcachedException
    {
        try
        {
            mc.flushAllWithNoReply(exptime);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_FLUSHALL, "", e);
        }
    }

    public void flushAllWithNoReply(InetSocketAddress address, int exptime) throws MemcachedException
    {
        try
        {
            mc.flushAllWithNoReply(address, exptime);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_FLUSHALL, "", e);
        }
    }

    // ---- STATS
    public Map<InetSocketAddress, Map<String, String>> getStats(long timeout) throws MemcachedException
    {
        try
        {
            return mc.getStats(timeout);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_STATS, "", e);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_STATS, "", e);
        }

        return null;
    }

    public Map<InetSocketAddress, Map<String, String>> getStats() throws MemcachedException
    {
        try
        {
            return mc.getStats();
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_STATS, "", e);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_STATS, "", e);
        }

        return null;
    }

    public Map<InetSocketAddress, Map<String, String>> getStatsByItem(String itemName) throws MemcachedException
    {
        try
        {
            return mc.getStatsByItem(itemName);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_STATS, "", e);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_STATS, "", e);
        }

        return null;
    }

    public Map<InetSocketAddress, Map<String, String>> getStatsByItem(String itemName, long timeout)
        throws MemcachedException
    {
        try
        {
            return mc.getStatsByItem(itemName, timeout);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_STATS, "", e);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_STATS, "", e);
        }

        return null;
    }

    public Map<String, String> stats(InetSocketAddress address) throws MemcachedException
    {
        try
        {
            return mc.stats(address);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_STATS, "", e);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_STATS, "", e);
        }

        return null;
    }

    public Map<String, String> stats(InetSocketAddress address, long timeout) throws MemcachedException
    {
        try
        {
            return mc.stats(address, timeout);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_STATS, "", e);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_STATS, "", e);
        }

        return null;
    }

    // ---- VERSION
    public Map<InetSocketAddress, String> getVersions() throws MemcachedException
    {
        try
        {
            return mc.getVersions();
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_VERSION, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_VERSION, "", e);
        }

        return null;
    }

    public Map<InetSocketAddress, String> getVersions(long timeout) throws MemcachedException
    {
        try
        {
            return mc.getVersions(timeout);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_VERSION, "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_VERSION, "", e);
        }

        return null;
    }

    // ---- KEYIterator
    public KeyIterator getKeyIterator(InetSocketAddress address) throws MemcachedException
    {
        try
        {
            return mc.getKeyIterator(address);
        }
        catch (InterruptedException e)
        {
            onInterruptedException(OPERATE_KeyIterator, "", e);
        }
        catch (TimeoutException e)
        {
            onTimeoutException(OPERATE_KeyIterator, "", e);
        }

        return null;
    }

    // ------------------------------------------
    // ---- Memcache Client Management
    // ------------------------------------------

    public void setLoggingLevelVerbosity(InetSocketAddress address, int level) throws MemcachedException
    {
        try
        {
            mc.setLoggingLevelVerbosity(address, level);
        }
        catch (TimeoutException e)
        {
            onTimeoutException("", "", e);
        }
        catch (InterruptedException e)
        {
            onInterruptedException("", "", e);
        }
    }

    public void setLoggingLevelVerbosityWithNoReply(InetSocketAddress address, int level) throws MemcachedException
    {
        try
        {
            mc.setLoggingLevelVerbosityWithNoReply(address, level);
        }
        catch (InterruptedException e)
        {
            onInterruptedException("", "", e);
        }
    }

    public Map<InetSocketAddress, AuthInfo> getAuthInfoMap()
    {
        return mc.getAuthInfoMap();
    }

    public String getName()
    {
        return mc.getName();
    }

    public long getOpTimeout()
    {
        return mc.getOpTimeout();
    }

    public Protocol getProtocol()
    {
        return mc.getProtocol();
    }

    public Queue<ReconnectRequest> getReconnectRequestQueue()
    {
        return mc.getReconnectRequestQueue();
    }

    public List<String> getServersDescription()
    {
        return mc.getServersDescription();
    }

    public long getConnectTimeout()
    {
        return mc.getConnectTimeout();
    }

    public Connector getConnector()
    {
        return mc.getConnector();
    }

    public Counter getCounter(String key)
    {
        return mc.getCounter(key);
    }

    public Counter getCounter(String key, long initialValue)
    {
        return mc.getCounter(key, initialValue);
    }

    public long getHealSessionInterval()
    {
        return mc.getHealSessionInterval();
    }

    @SuppressWarnings("unchecked")
    public Transcoder getTranscoder()
    {
        return mc.getTranscoder();
    }

    public boolean isFailureMode()
    {
        return mc.isFailureMode();
    }

    public boolean isSanitizeKeys()
    {
        return mc.isSanitizeKeys();
    }

    public boolean isShutdown()
    {
        return mc.isShutdown();
    }

    public void setAuthInfoMap(Map<InetSocketAddress, AuthInfo> map)
    {
        mc.setAuthInfoMap(map);
    }

    public void setConnectTimeout(long connectTimeout)
    {
        mc.setConnectTimeout(connectTimeout);
    }

    public void setConnectionPoolSize(int poolSize)
    {
        mc.setConnectionPoolSize(poolSize);
    }

    public void setEnableHeartBeat(boolean enableHeartBeat)
    {
        mc.setEnableHeartBeat(enableHeartBeat);
    }

    public void setFailureMode(boolean failureMode)
    {
        mc.setFailureMode(failureMode);
    }

    public void setHealSessionInterval(long healConnectionInterval)
    {
        mc.setHealSessionInterval(healConnectionInterval);
    }

    public void setMergeFactor(int mergeFactor)
    {
        mc.setMergeFactor(mergeFactor);
    }

    public void setName(String name)
    {
        mc.setName(name);
    }

    public void setOpTimeout(long opTimeout)
    {
        mc.setOpTimeout(opTimeout);
    }

    public void setOptimizeGet(boolean optimizeGet)
    {
        mc.setOptimizeGet(optimizeGet);
    }

    public void setOptimizeMergeBuffer(boolean optimizeMergeBuffer)
    {
        mc.setOptimizeMergeBuffer(optimizeMergeBuffer);
    }

    public void setPrimitiveAsString(boolean primitiveAsString)
    {
        mc.setPrimitiveAsString(primitiveAsString);
    }

    public void setSanitizeKeys(boolean sanitizeKey)
    {
        mc.setSanitizeKeys(sanitizeKey);
    }

    @SuppressWarnings("unchecked")
    public void setTranscoder(Transcoder transcoder)
    {
        mc.setTranscoder(transcoder);
    }

    public void shutdown() throws IOException
    {
        mc.shutdown();
    }

    /**
     * @return 返回 ConcurrentController
     */
    public ConcurrentController getParentConcurrentController()
    {
        return parent;
    }

    /**
     * @param parent 对ConcurrentController进行赋值
     */
    public void setParentConcurrentController(ConcurrentController parent)
    {
        this.parent = parent;
    }

    /**
     * @return 返回 ConcurrentController
     */
    public ConcurrentController getReadConcurrentController()
    {
        return readController;
    }

    /**
     * @param readController 对ConcurrentController进行赋值
     */
    public void setReadConcurrentController(ConcurrentController readController)
    {
        this.readController = readController;
    }

    /**
     * @return 返回 ConcurrentController
     */
    public ConcurrentController getWriteConcurrentController()
    {
        return writeController;
    }

    /**
     * @param writeController 对ConcurrentController进行赋值
     */
    public void setWriteConcurrentController(ConcurrentController writeController)
    {
        this.writeController = writeController;
    }

    // ------------------------------------------
    // ---- Memcache Client Exception Process
    // ------------------------------------------

    private void onTimeoutException(String operate, String key, TimeoutException ex)
    {
        dlog.error("" + operate + " " + key + " TimeoutException.");
    }

    private void onInterruptedException(String operate, String key, InterruptedException ex)
    {
        dlog.error("" + operate + " " + key + " InterruptedException.");
    }
}
