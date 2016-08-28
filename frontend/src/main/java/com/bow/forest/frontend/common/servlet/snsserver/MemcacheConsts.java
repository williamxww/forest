package com.bow.forest.frontend.common.servlet.snsserver;

/**
 * Memcached的常量类
 * 
 * @author KF47558
 * @author s00215221修改
 */
public interface MemcacheConsts
{
    /* ****** *缓存名配置*** start ************* */
    
    /**收到评论信息缓存*/
    String RECEIVE_COMMENT_PREFIX = "RECIVECOMMENT:";
    
    /**发出评论信息缓存*/
    String SEND_COMMENT_PREFIX = "SENDCOMMENT:";
    
    String MOBILE_KEY_MAPPING_PREFIX = "MOBILEKEYMAPPING:";
    
    /**图书粉丝榜缓存*/
    String BOOK_FANRANKING_PREFIX = "BOOKFANRANKING:";
}
