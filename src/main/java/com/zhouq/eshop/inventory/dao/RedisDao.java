package com.zhouq.eshop.inventory.dao;

/**
 * todo
 *
 * @author zhouq
 * @email zhouqiao@gmail.com
 * @date 2017/12/4 23:34
 */
public interface RedisDao {

    void set(String key,String value);

    String get(String key);

    void delete(String key);
}
