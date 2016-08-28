package com.bow.forest.frontend.common.memcache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClientStateListener;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.ReconnectRequest;
import net.rubyeye.xmemcached.networking.Connector;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.utils.Protocol;

/**
 * The memcached client's interface
 * 
 * @author dennis
 * 
 */
public interface IMemcachedClient {
    /**
     * Set the merge factor,this factor determins how many 'get' commands would
     * be merge to one multi-get command.default is 150
     *
     */
    void setMergeFactor(final int mergeFactor);

    long getConnectTimeout();

    /**
     * Set the connect timeout,default is 1 minutes
     * 
     * @param connectTimeout
     */
    void setConnectTimeout(long connectTimeout);

    /**
     * return the session manager
     * 
     * @return
     */
    Connector getConnector();

    /**
     * Enable/Disable merge many get commands to one multi-get command.true is
     * to enable it,false is to disable it.Default is true.Recommend users to
     * enable it.
     * 
     * @param optimizeGet
     */
    void setOptimizeGet(final boolean optimizeGet);

    /**
     * Enable/Disable merge many command's buffers to one big buffer fit
     * socket's send buffer size.Default is true.Recommend true.
     * 
     * @param optimizeMergeBuffer
     */
    void setOptimizeMergeBuffer(final boolean optimizeMergeBuffer);

    /**
     * @return
     */
    boolean isShutdown();

    /**
     * Aadd a memcached server,the thread call this method will be blocked until
     * the connecting operations completed(success or fail)
     * 
     * @param server
     *            host string
     * @param port
     *            port number
     */
    void addServer(final String server, final int port) throws IOException;

    /**
     * Add a memcached server,the thread call this method will be blocked until
     * the connecting operations completed(success or fail)
     * 
     * @param inetSocketAddress
     *            memcached server's socket address
     */
    void addServer(final InetSocketAddress inetSocketAddress) throws IOException;

    /**
     * Add many memcached servers.You can call this method through JMX or
     * program
     * 
     * @param hostList
     *            String like [host1]:[port1] [host2]:[port2] ...
     */
    void addServer(String hostList) throws IOException;

    /**
     * Get current server list.You can call this method through JMX or program
     */
    List<String> getServersDescription();

    /**
     * Remove many memcached server
     * 
     * @param hostList
     *            String like [host1]:[port1] [host2]:[port2] ...
     */
    void removeServer(String hostList);

    /**
     * Get value by key
     * 
     * @param key
     *            Key
     * @param timeout
     *            Operation timeout,if the method is not returned in this
     *            time,throw TimeoutException
     * @param transcoder
     *            The value's transcoder
     * @return
     */
    Object get(final String key, final long timeout, final Transcoder<Object> transcoder) throws MemcachedException;

    Object get(final String key, final long timeout) throws MemcachedException;

    Object get(final String key, final Transcoder<Object> transcoder) throws MemcachedException;

    Object get(final String key) throws MemcachedException;

    /**
     * Just like get,But it return a GetsResponse,include cas value for cas
     * update.
     *
     * @param key
     *            key
     * @param timeout
     *            operation timeout
     * @param transcoder
     * 
     * @return GetsResponse
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    GetsResponse<Object> gets(final String key, final long timeout, final Transcoder<Object> transcoder)
            throws MemcachedException;

    GetsResponse<Object> gets(final String key) throws MemcachedException;

    GetsResponse<Object> gets(final String key, final long timeout) throws MemcachedException;

    @SuppressWarnings("unchecked")
    GetsResponse<Object> gets(final String key, final Transcoder transcoder) throws MemcachedException;

    /**
     * memcached
     * @param keyCollections
     * @param timeout
     * @param transcoder
     * @return map
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    Map<String, Object> get(final Collection<String> keyCollections, final long timeout,
            final Transcoder<Object> transcoder) throws MemcachedException;

    Map<String, Object> get(final Collection<String> keyCollections, final Transcoder<Object> transcoder)
            throws MemcachedException;

    Map<String, Object> get(final Collection<String> keyCollections) throws MemcachedException;

    Map<String, Object> get(final Collection<String> keyCollections, final long timeout) throws MemcachedException;

    /**
     * 
     * @param keyCollections
     * @param timeout
     * @param transcoder
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    Map<String, GetsResponse<Object>> gets(final Collection<String> keyCollections, final long timeout,
            final Transcoder<Object> transcoder) throws MemcachedException;

    Map<String, GetsResponse<Object>> gets(final Collection<String> keyCollections) throws MemcachedException;

    Map<String, GetsResponse<Object>> gets(final Collection<String> keyCollections, final long timeout)
            throws MemcachedException;

    Map<String, GetsResponse<Object>> gets(final Collection<String> keyCollections, final Transcoder<Object> transcoder)
            throws MemcachedException;

    /**
     * Store key-value item to memcached
     * 
     * @param key
     *            stored key
     * @param exp
     *            expire time
     * @param value
     *            stored data
     * @param transcoder
     *            transocder
     * @param timeout
     *            operation timeout,in milliseconds
     * @return boolean result
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    boolean set(final String key, final int exp, final Object value, final Transcoder<Object> transcoder,
            final long timeout) throws MemcachedException;

    boolean set(final String key, final int exp, final Object value) throws MemcachedException;

    boolean set(final String key, final int exp, final Object value, final long timeout) throws MemcachedException;

    boolean set(final String key, final int exp, final Object value, final Transcoder<Object> transcoder)
            throws MemcachedException;

    /**
     * Store key-value item to memcached,doesn't wait for reply
     * 
     * @param key
     *            stored key
     * @param exp
     *            expire time
     * @param value
     *            stored data
     *
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    void setWithNoReply(final String key, final int exp, final Object value) throws MemcachedException;

    void setWithNoReply(final String key, final int exp, final Object value, final Transcoder<Object> transcoder)
            throws MemcachedException;

    /**
     * Add key-value item to memcached, success only when the key is not exists
     * in memcached.
     *
     * @param key
     * @param exp
     * @param value
     * @param transcoder
     * @param timeout
     * @return boolean result
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    boolean add(final String key, final int exp, final Object value, final Transcoder<Object> transcoder,
            final long timeout) throws MemcachedException;

    boolean add(final String key, final int exp, final Object value) throws MemcachedException;

    boolean add(final String key, final int exp, final Object value, final long timeout) throws MemcachedException;

    boolean add(final String key, final int exp, final Object value, final Transcoder<Object> transcoder)
            throws MemcachedException;

    /**
     * Add key-value item to memcached, success only when the key is not exists
     * in memcached.This method doesn't wait for reply.
     *
     * @param key
     * @param exp
     * @param value
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */

    void addWithNoReply(final String key, final int exp, final Object value) throws MemcachedException;

    void addWithNoReply(final String key, final int exp, final Object value, final Transcoder<Object> transcoder)
            throws MemcachedException;

    /**
     * Replace the key's data item in memcached,success only when the key's data
     * item is exists in memcached.This method will wait for reply from server.
     *
     * @param key
     * @param exp
     * @param value
     * @param transcoder
     * @param timeout
     * @return boolean result
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    boolean replace(final String key, final int exp, final Object value, final Transcoder<Object> transcoder,
            final long timeout) throws MemcachedException;

    boolean replace(final String key, final int exp, final Object value) throws MemcachedException;

    boolean replace(final String key, final int exp, final Object value, final long timeout) throws MemcachedException;

    boolean replace(final String key, final int exp, final Object value, final Transcoder<Object> transcoder)
            throws MemcachedException;

    /**
     * Replace the key's data item in memcached,success only when the key's data
     * item is exists in memcached.This method doesn't wait for reply from
     * server.
     *
     * @param key
     * @param exp
     * @param value
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    void replaceWithNoReply(final String key, final int exp, final Object value) throws MemcachedException;

    void replaceWithNoReply(final String key, final int exp, final Object value, final Transcoder<Object> transcoder)
            throws MemcachedException;

    /**
     * Append value to key's data item,this method will wait for reply
     * 
     * @param key
     * @param value
     * @return boolean result
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    boolean append(final String key, final Object value) throws MemcachedException;

    boolean append(final String key, final Object value, final long timeout) throws MemcachedException;

    /**
     * Append value to key's data item,this method doesn't wait for reply.
     * 
     * @param key
     * @param value
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    void appendWithNoReply(final String key, final Object value) throws MemcachedException;

    /**
     * Prepend value to key's data item in memcached.This method doesn't wait
     * for reply.
     * 
     * @param key
     * @param value
     * @return boolean result
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    boolean prepend(final String key, final Object value) throws MemcachedException;

    boolean prepend(final String key, final Object value, final long timeout) throws MemcachedException;

    /**
     * Prepend value to key's data item in memcached.This method doesn't wait
     * for reply.
     * 
     * @param key
     * @param value
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    void prependWithNoReply(final String key, final Object value) throws MemcachedException;

    /**
     * Cas is a check and set operation which means "store this data but only if
     * no one else has updated since I last fetched it."
     *
     * @param key
     * @param exp
     * @param value
     * @param cas
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    boolean cas(final String key, final int exp, final Object value, final long cas) throws MemcachedException;

    boolean cas(final String key, final int exp, final Object value, final Transcoder<Object> transcoder,
            final long timeout, final long cas) throws MemcachedException;

    boolean cas(final String key, final int exp, final Object value, final long timeout, final long cas)
            throws MemcachedException;

    boolean cas(final String key, final int exp, final Object value, final Transcoder<Object> transcoder,
            final long cas) throws MemcachedException;

    /**
     * Cas is a check and set operation which means "store this data but only if
     * no one else has updated since I last fetched it."
     *
     * @param key
     * @param exp
     * @param operation
     *            CASOperation
     * @param transcoder
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    boolean cas(final String key, final int exp, final CASOperation<Object> operation,
            final Transcoder<Object> transcoder) throws MemcachedException;

    /**
     * cas is a check and set operation which means "store this data but only if
     * no one else has updated since I last fetched it."
     *
     * @param key
     * @param exp
     *            data item expire time
     * @param getsReponse
     *            gets method's result
     * @param operation
     *            CASOperation
     * @param transcoder
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    boolean cas(final String key, final int exp, GetsResponse<Object> getsReponse, final CASOperation<Object> operation,
            final Transcoder<Object> transcoder) throws MemcachedException;

    boolean cas(final String key, final int exp, GetsResponse<Object> getsReponse, final CASOperation<Object> operation)
            throws MemcachedException;

    boolean cas(final String key, GetsResponse<Object> getsResponse, final CASOperation<Object> operation)
            throws MemcachedException;

    boolean cas(final String key, final int exp, final CASOperation<Object> operation) throws MemcachedException;

    boolean cas(final String key, final CASOperation<Object> operation) throws MemcachedException;

    /**
     *
     * @param key
     * @param getsResponse
     * @param operation
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    void casWithNoReply(final String key, GetsResponse<Object> getsResponse, final CASOperation<Object> operation)
            throws MemcachedException;

    void casWithNoReply(final String key, final int exp, GetsResponse<Object> getsReponse,
            final CASOperation<Object> operation) throws MemcachedException;

    void casWithNoReply(final String key, final int exp, final CASOperation<Object> operation)
            throws MemcachedException;

    void casWithNoReply(final String key, final CASOperation<Object> operation) throws MemcachedException;

    /**
     * Delete key's data item from memcached.It it is not exists,return
     * false.</br>
     * time is the amount of time in seconds (or Unix time until</br>
     * which) the client wishes the server to refuse "add" and "replace"</br>
     * commands with this key. For this amount of item, the item is put into
     * a</br>
     * delete queue, which means that it won't possible to retrieve it by
     * the</br>
     * "get" command, but "add" and "replace" command with this key will
     * also</br>
     * fail (the "set" command will succeed, however). After the time
     * passes,</br>
     * the item is finally deleted from server memory. </br>
     * <strong>Note: This method is deprecated,because memcached 1.4.0 remove
     * the optional argument "time".You can still use this method on old
     * version,but is not recommended.</strong>
     * 
     * @param key
     * @param opTimeout
     *            Operation timeout
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     * @since 1.3.2
     */
    boolean delete(final String key, long opTimeout) throws MemcachedException;

    /**
     * Get all connected memcached servers's versions.
     * 
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public Map<InetSocketAddress, String> getVersions() throws MemcachedException;

    /**
     * "incr" are used to change data for some item in-place, incrementing it.
     * The data for the item is treated as decimal representation of a 64-bit
     * unsigned integer. If the current data value does not conform to such a
     * representation, the commands behave as if the value were 0. Also, the
     * item must already exist for incr to work; these commands won't pretend
     * that a non-existent key exists with value 0; instead, it will fail.This
     * method doesn't wait for reply.
     * 
     * @return the new value of the item's data, after the increment operation
     *         was carried out.
     * @param key
     * @throws InterruptedException
     * @throws MemcachedException
     */
    long incr(final String key, final long delta) throws MemcachedException;

    public long incr(final String key, final long delta, final long initValue) throws MemcachedException;

    /**
     * "incr" are used to change data for some item in-place, incrementing it.
     * The data for the item is treated as decimal representation of a 64-bit
     * unsigned integer. If the current data value does not conform to such a
     * representation, the commands behave as if the value were 0. Also, the
     * item must already exist for incr to work; these commands won't pretend
     * that a non-existent key exists with value 0; instead, it will fail.This
     * method doesn't wait for reply.
     * 
     * @param key
     *            key increment
     * @param initValue
     *            initValue if the data is not exists.
     * @param timeout
     *            operation timeout
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public long incr(final String key, final long delta, final long initValue, long timeout) throws MemcachedException;

    /**
     * "decr" are used to change data for some item in-place, decrementing it.
     * The data for the item is treated as decimal representation of a 64-bit
     * unsigned integer. If the current data value does not conform to such a
     * representation, the commands behave as if the value were 0. Also, the
     * item must already exist for decr to work; these commands won't pretend
     * that a non-existent key exists with value 0; instead, it will fail.This
     * method doesn't wait for reply.
     * 
     * @return the new value of the item's data, after the decrement operation
     *         was carried out.
     * @param key
     * @throws InterruptedException
     * @throws MemcachedException
     */
    long decr(final String key, final long delta) throws MemcachedException;

    /**
     * @param key
     * @param initValue
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    long decr(final String key, final long delta, long initValue) throws MemcachedException;

    /**
     * "decr" are used to change data for some item in-place, decrementing it.
     * The data for the item is treated as decimal representation of a 64-bit
     * unsigned integer. If the current data value does not conform to such a
     * representation, the commands behave as if the value were 0. Also, the
     * item must already exist for decr to work; these commands won't pretend
     * that a non-existent key exists with value 0; instead, it will fail.This
     * method doesn't wait for reply.
     * 
     * @param key
     *            The key
     * @param initValue
     *            The initial value if the data is not exists.
     * @param timeout
     *            Operation timeout
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    long decr(final String key, final long delta, long initValue, long timeout) throws MemcachedException;

    /**
     * Make All connected memcached's data item invalid
     * 
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    void flushAll() throws MemcachedException;

    void flushAllWithNoReply() throws MemcachedException;

    /**
     * Make All connected memcached's data item invalid
     * 
     * @param timeout
     *            operation timeout
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    void flushAll(long timeout) throws MemcachedException;

    /**
     * Invalidate all existing items immediately
     * 
     * @param address
     *            Target memcached server
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    void flushAll(InetSocketAddress address) throws MemcachedException;

    void flushAllWithNoReply(InetSocketAddress address) throws MemcachedException;

    void flushAll(InetSocketAddress address, long timeout) throws MemcachedException;

    /**
     * This method is deprecated,please use flushAll(InetSocketAddress) instead.
     * 
     * @deprecated
     * 
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */

    Map<String, String> stats(InetSocketAddress address) throws MemcachedException;

    /**
     * 
     * @param address
     * @param timeout
     * @return
     * @throws MemcachedException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    Map<String, String> stats(InetSocketAddress address, long timeout) throws MemcachedException;

    /**
     * Get stats from all memcached servers
     * 
     * @param timeout
     * @return server->item->value map
     * @throws MemcachedException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    Map<InetSocketAddress, Map<String, String>> getStats(long timeout) throws MemcachedException;

    Map<InetSocketAddress, Map<String, String>> getStats() throws MemcachedException;

    /**
     * Get special item stats. "stats items" for example
     *
     * @return
     */
    public Map<InetSocketAddress, Map<String, String>> getStatsByItem(String itemName) throws MemcachedException;

    void shutdown() throws IOException;

    boolean delete(final String key) throws MemcachedException;

    /**
     * return default transcoder,default is SerializingTranscoder
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    Transcoder getTranscoder();

    /**
     * set transcoder
     * 
     * @param transcoder
     */
    @SuppressWarnings("unchecked")
    void setTranscoder(final Transcoder transcoder);

    public Map<InetSocketAddress, Map<String, String>> getStatsByItem(String itemName, long timeout)
            throws MemcachedException;

    /**
     * get operation timeout setting
     * 
     * @return
     */
    public long getOpTimeout();

    /**
     * set operation timeout,default is one second.
     * 
     * @param opTimeout
     */
    public void setOpTimeout(long opTimeout);

    public Map<InetSocketAddress, String> getVersions(long timeout) throws MemcachedException;

    /**
     * get avaliable memcached servers's socket address.
     * 
     * @return
     */
    public Collection<InetSocketAddress> getAvaliableServers();

    /**
     * add a memcached server to MemcachedClient
     * 
     * @param server
     * @param port
     * @param weight
     * @throws IOException
     */
    public void addServer(final String server, final int port, int weight) throws IOException;

    public void addServer(final InetSocketAddress inetSocketAddress, int weight) throws IOException;

    /**
     * Delete key's data item from memcached.This method doesn't wait for reply.
     * This method does not work on memcached 1.3 or later version.See <a href=
     * 'http://code.google.com/p/memcached/issues/detail?id=3&q=delete%20noreply
     * ' > i s s u e 3</a> </br>
     * <strong>Note: This method is deprecated,because memcached 1.4.0 remove
     * the optional argument "time".You can still use this method on old
     * version,but is not recommended.</strong>
     * 
     * @param key
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public void deleteWithNoReply(final String key) throws MemcachedException;

    /**
     * "incr" are used to change data for some item in-place, incrementing it.
     * The data for the item is treated as decimal representation of a 64-bit
     * unsigned integer. If the current data value does not conform to such a
     * representation, the commands behave as if the value were 0. Also, the
     * item must already exist for incr to work; these commands won't pretend
     * that a non-existent key exists with value 0; instead, it will fail.This
     * method doesn't wait for reply.
     * 
     * @param key
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public void incrWithNoReply(final String key, final long delta) throws MemcachedException;

    /**
     * "decr" are used to change data for some item in-place, decrementing it.
     * The data for the item is treated as decimal representation of a 64-bit
     * unsigned integer. If the current data value does not conform to such a
     * representation, the commands behave as if the value were 0. Also, the
     * item must already exist for decr to work; these commands won't pretend
     * that a non-existent key exists with value 0; instead, it will fail.This
     * method doesn't wait for reply.
     * 
     * @param key
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public void decrWithNoReply(final String key, final long delta) throws MemcachedException;

    /**
     * Set the verbosity level of the memcached's logging output.This method
     * will wait for reply.
     * 
     * @param address
     * @param level
     *            logging level
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public void setLoggingLevelVerbosity(InetSocketAddress address, int level) throws MemcachedException;

    /**
     * Set the verbosity level of the memcached's logging output.This method
     * doesn't wait for reply from server
     * 
     * @param address
     *            memcached server address
     * @param level
     *            logging level
     * @throws InterruptedException
     * @throws MemcachedException
     */
    public void setLoggingLevelVerbosityWithNoReply(InetSocketAddress address, int level) throws MemcachedException;

    /**
     * Add a memcached client listener
     * 
     * @param listener
     */
    public void addStateListener(MemcachedClientStateListener listener);

    /**
     * Remove a memcached client listener
     * 
     * @param listener
     */
    public void removeStateListener(MemcachedClientStateListener listener);

    /**
     * Get all current state listeners
     * 
     * @return
     */
    public Collection<MemcachedClientStateListener> getStateListeners();

    public void flushAllWithNoReply(int exptime) throws MemcachedException;

    public void flushAll(int exptime, long timeout) throws MemcachedException;

    public void flushAllWithNoReply(InetSocketAddress address, int exptime) throws MemcachedException;

    public void flushAll(InetSocketAddress address, long timeout, int exptime) throws MemcachedException;

    /**
     * If the memcached dump or network error cause connection closed,xmemcached
     * would try to heal the connection.The interval between reconnections is 2
     * seconds by default. You can change that value by this method.
     * 
     * @param healConnectionInterval
     *            MILLISECONDS
     */
    public void setHealSessionInterval(long healConnectionInterval);

    /**
     * Return the default heal session interval in milliseconds
     * 
     * @return
     */
    public long getHealSessionInterval();

    public Protocol getProtocol();

    /**
     * Store all primitive type as string,defualt is false.
     */
    public void setPrimitiveAsString(boolean primitiveAsString);

    /**
     * In a high concurrent enviroment,you may want to pool memcached
     * clients.But a xmemcached client has to start a reactor thread and some
     * thread pools,if you create too many clients,the cost is very large.
     * Xmemcached supports connection pool instreadof client pool.you can create
     * more connections to one or more memcached servers,and these connections
     * share the same reactor and thread pools,it will reduce the cost of
     * system.
     * 
     * @param poolSize
     *            pool size,default is one,every memcached has only one
     *            connection.
     */
    public void setConnectionPoolSize(int poolSize);

    /**
     * Whether to enable heart beat
     * 
     * @param enableHeartBeat
     *            if true,then enable heartbeat,true by default
     */
    public void setEnableHeartBeat(boolean enableHeartBeat);

    /**
     * Enables/disables sanitizing keys by URLEncoding.
     * 
     * @param sanitizeKey
     *            if true, then URLEncode all keys
     */
    public void setSanitizeKeys(boolean sanitizeKey);

    public boolean isSanitizeKeys();

    /**
     * Get counter for key,and if the key's value is not set,then set it with 0.
     * 
     * @param key
     * @return
     */
    public Counter getCounter(String key);

    /**
     * Get counter for key,and if the key's value is not set,then set it with
     * initial value.
     * 
     * @param key
     * @param initialValue
     * @return
     */
    public Counter getCounter(String key, long initialValue);

    /**
     * Get key iterator for special memcached server.You must known that the
     * iterator is a snapshot for memcached all keys,it is not real-time.The
     * 'stats cachedump" has length limitation,so iterator could not visit all
     * keys if you have many keys.Your application should not be dependent on
     * this feature.
     * 
     * @param address
     * @return
     */
    public KeyIterator getKeyIterator(InetSocketAddress address) throws MemcachedException;

    /**
     * Configure auth info
     * 
     * @param map
     *            Auth info map,key is memcached server address,and value is the
     *            auth info for the key.
     */
    public void setAuthInfoMap(Map<InetSocketAddress, AuthInfo> map);

    /**
     * return current all auth info
     * 
     * @return Auth info map,key is memcached server address,and value is the
     *         auth info for the key.
     */
    public Map<InetSocketAddress, AuthInfo> getAuthInfoMap();

    /**
     * "incr" are used to change data for some item in-place, incrementing it.
     * The data for the item is treated as decimal representation of a 64-bit
     * unsigned integer. If the current data value does not conform to such a
     * representation, the commands behave as if the value were 0. Also, the
     * item must already exist for incr to work; these commands won't pretend
     * that a non-existent key exists with value 0; instead, it will fail.This
     * method doesn't wait for reply.
     * 
     * @param key
     * @param delta
     * @param initValue
     *            the initial value to be added when value is not found
     * @param timeout
     * @param exp
     *            the initial vlaue expire time
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    long decr(String key, long delta, long initValue, long timeout, int exp) throws MemcachedException;

    /**
     * "incr" are used to change data for some item in-place, incrementing it.
     * The data for the item is treated as decimal representation of a 64-bit
     * unsigned integer. If the current data value does not conform to such a
     * representation, the commands behave as if the value were 0. Also, the
     * item must already exist for incr to work; these commands won't pretend
     * that a non-existent key exists with value 0; instead, it will fail.This
     * method doesn't wait for reply.
     * 
     * @param key
     *            key
     * @param delta
     *            increment delta
     * @param initValue
     *            the initial value to be added when value is not found
     * @param timeout
     *            operation timeout
     * @param exp
     *            the initial vlaue expire time
     * @return
     * @throws TimeoutException
     * @throws InterruptedException
     * @throws MemcachedException
     */
    long incr(String key, long delta, long initValue, long timeout, int exp) throws MemcachedException;

    /**
     * Return the cache instance name
     * 
     * @return
     */
    public String getName();

    /**
     * Set cache instance name
     * 
     * @param name
     */
    public void setName(String name);

    /**
     * Returns reconnecting task queue,the result is thread-safe,but maybe you
     * should not modify it at all
     * 
     * @return The reconnecting task queue,if the client has not been
     *         started,returns null.
     */
    public Queue<ReconnectRequest> getReconnectRequestQueue();

    /**
     * Configure wheather to set client in failure mode.If set it to true,that
     * means you want to configure client in failure mode. Failure mode is that
     * when a memcached server is down,it would not taken from the server list
     * but marked as unavailable,and then further requests to this server will
     * be transformed to standby node if configured or throw an exception until
     * it comes back up.
     * 
     * @param failureMode
     *            true is to configure client in failure mode.
     */
    public void setFailureMode(boolean failureMode);

    /**
     * Returns if client is in failure mode.
     * 
     * @return
     */
    public boolean isFailureMode();

}
