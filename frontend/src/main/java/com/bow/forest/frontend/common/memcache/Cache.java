package com.bow.forest.frontend.common.memcache;

/**
 * 缓存接口,实现类需可以使用各种缓存技术
 *
 */
public interface Cache
{
    /**
     * 初始化缓存
     */
    void init();

    /**
     * 从缓存中获取对象
     * 
     * @param key
     *            要取值的key，可以使用null
     * @return 对于自动失效的缓存，该方法不能返回null, 如果缓存中没有取到,应返回Cachable.NULL_INSTANCE 对于不会自动失效的缓存，则返回缓存的内容，没有则返回null
     */
    Object get(String key);

    /**
     * 把内容放入缓存
     * 
     * @param key
     *            放入的key，可以为null
     * @param value
     *            放入的值，可放入null
     */
    void put(String key, Object value);

    /**
     * 
     * 把内容放入缓存
     * 
     * @param key
     *            放入的key，可以为null
     * @param value
     *            放入的值，可放入null
     * @return
     * 
     */
    boolean putWithResult(String key, Object value);

    /**
     * 删除缓存内容
     * 
     * @author r00138849
     * @param key
     *            要删除的key，可以为null
     */
    void delete(String key);

    /**
     * 删除缓存内容
     * 
     * @author skf74480
     * @param key
     *            要删除的key，可以为null
     * @return
     */
    boolean deleteWithResult(String key);
}
