package cn.ey.gds.ita.fast.core.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KalorRedisCache {
    //缓存的key
    String redisKey();

    //缓存过期时间默认是60秒
    long expireTime() default 60;

    //开启缓存的开关，默认是开启
    boolean cacheFlag() default true;
}
