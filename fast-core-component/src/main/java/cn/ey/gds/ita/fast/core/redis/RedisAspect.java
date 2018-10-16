package cn.ey.gds.ita.fast.core.redis;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
public class RedisAspect {

    @Autowired
    private RedisService redisService;

    @Value("${spring.redis.open:false}")
    private Boolean redisOpen;

    public static String DELIMITER = "^";

    //方法上面加RedisCache注解则走缓存
    @Around("@annotation(cn.ey.gds.ita.fast.core.redis.KalorRedisCache)")
    public Object cache(ProceedingJoinPoint jp) throws Throwable {
        // 得到类名、方法名和参数
        String clazzName = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        // 根据类名，方法名和参数生成key
        String paramKey = genKey(clazzName, methodName, args);
        // 得到被代理的方法
        //Method me = ((MethodSignature) jp.getSignature()).getMethod();
        Method targetMethod = ((MethodSignature) jp.getSignature()).getMethod();
        //得到实际的类方法 上面的代理类是不能取到方法上面的注解的
        Method me = jp.getTarget().getClass().getDeclaredMethod(methodName, targetMethod.getParameterTypes());
        // 得到被代理的方法上的缓存注解
        KalorRedisCache redisCache = me.getAnnotation(KalorRedisCache.class);
        // result是方法的最终返回结果
        Object result = null;
        //如果没加缓存注解或者不开启缓存
        if (!redisOpen || redisCache == null || !redisCache.cacheFlag()) {
            // 调用数据库查询方法
            result = jp.proceed(args);
        } else {
            //从缓存中取出数据，如果存在将缓存数据返回
            String value = redisService.getHashValue(redisCache.redisKey(), paramKey);
//            String value = redisService.getValue(redisCache.redisKey(),String.class);

            if (value != null) {
                // 得到被代理方法的返回值类型
                Class<?> returnType = ((MethodSignature) jp.getSignature()).getReturnType();
                RedisEvict redisEvict = me.getAnnotation(RedisEvict.class);
                Class<?> modelType = null;
                if (redisEvict != null) {
                    //modelType = redisEvict.annotationType();
                    modelType = redisEvict.type();
                }
                // 反序列化从缓存中拿到的json
                result = deserialize(value, returnType, modelType);
            } else {
                result = jp.proceed(args);
//        		value = JSON.toJSONString(result);
                redisService.putHashKey(redisCache.redisKey(), paramKey, result);
//                redisService.putKey(redisCache.redisKey(), value);
            }
        }

        return result;
    }

    /**
     * 根据类名、方法名和参数生成key
     *
     * @param clazzName
     * @param methodName
     * @param args       方法参数
     * @return
     */
    private String genKey(String clazzName, String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder(clazzName);
        sb.append(DELIMITER);
        sb.append(methodName);
        sb.append(DELIMITER);

        for (Object obj : args) {
            sb.append(JSON.toJSONString(obj));
            sb.append(DELIMITER);
        }

        return sb.toString();
    }

    /**
     * 将返回缓存中的结果转换成待输出的结果
     *
     * @param jsonString
     * @param clazz
     * @param modelType
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object deserialize(String jsonString, Class clazz, Class modelType) {
        // 序列化结果应该是List对象
        if (clazz.isAssignableFrom(List.class)) {
            return JSON.parseArray(jsonString, modelType);
        }
        // 序列化结果是普通对象
        return JSON.parseObject(jsonString, clazz);
    }
}
