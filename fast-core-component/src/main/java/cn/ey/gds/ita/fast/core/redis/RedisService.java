package cn.ey.gds.ita.fast.core.redis;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//配置文件
/*<?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

        <!-- 引入jedis的properties配置文件 -->
        <!--如果你有多个数据源需要通过<context:property-placeholder管理，且不愿意放在一个配置文件里，那么一定要加上ignore-unresolvable=“true"-->
        <context:property-placeholder location="classpath:properties/redis.properties" ignore-unresolvable="true" />

        <!--Jedis连接池的相关配置-->
        <bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
<!--新版是maxTotal，旧版是maxActive-->
<property name="maxTotal">
<value>${redis.pool.maxActive}</value>
</property>
<property name="maxIdle">
<value>${redis.pool.maxIdle}</value>
</property>
<property name="testOnBorrow" value="true"/>
<property name="testOnReturn" value="true"/>
</bean>

<bean id="jedisPool" class="redis.clients.jedis.JedisPool">
<constructor-arg name="poolConfig" ref="jedisPoolConfig" />
<constructor-arg name="host" value="${redis.host}" />
<constructor-arg name="port" value="${redis.port}" type="int" />
<constructor-arg name="timeout" value="${redis.timeout}" type="int" />
<constructor-arg name="password" value="${redis.password}" />
<constructor-arg name="database" value="${redis.database}" type="int" />
</bean>
</beans>*/


/**
 * Created by Chao-C.Yan on 2017/7/16.
 */
@Component
public class RedisService {

    Logger log = Logger.getLogger(RedisService.class);

    @Autowired
    RedisUtils redisUtils;

    /**
     * 将数据插入redis缓存 默认是30分钟过期
     *
     * @param key
     * @param value
     */
    public boolean putKey(String key, Object value) {
        if (key == null || "".equals(key)) {
            return false;
        }
        redisUtils.set(key, JSON.toJSONString(value), 1000 * 30);
        return true;
    }

    /**
     * 以hash形式存储数据默认过期时间是30分钟
     *
     * @param key
     * @param paramKey
     * @param value
     * @return
     */
    public boolean putHashKey(String key, String paramKey, Object value) {
       return  putHashKey(key, paramKey, value,1000 * 30);
    }

    /**
     * 指定过期时间的缓存
     *
     * @param key
     * @param value
     * @param expireTime
     */
    public boolean putKey(String key, Object value, int expireTime) {
        if (key == null || "".equals(key)) {
            return false;
        }
        redisUtils.set(key, JSON.toJSONString(value), expireTime);

        return true;
    }

    /**
     * 以hash形式存储数据
     * @param key
     * @param paramKey
     * @param value
     * @param expireTime
     * @return
     */
    public boolean putHashKey(String key,String paramKey, Object value,int expireTime) {
        if (key == null || "".equals(key)) {
            return false;
        }
        redisUtils.putHashKey(key, paramKey, JSON.toJSONString(value),expireTime);
        return true;
    }

    /**
     * 获取简单形式的缓存数据
     *
     * @param key
     * @param className
     * @return
     */
    public <T> T getValue(String key, Class<T> className) {
        if (redisUtils.get(key) != null) {
            String value = redisUtils.get(key);
            return JSON.parseObject("" + value, className);
        } else {
            return null;
        }

    }
    /**
     * 获取Hash形式的缓存数据
     * @param key
     * @return
     */
    public <T> T getHashValue(String key,String paramKey) {

            if( redisUtils.getHashValue(key,paramKey,1000*30) != null){
                return  redisUtils.getHashValue(key,paramKey,1000*30);
            }else{
                return null;
            }
    }

}
