package cn.tucci.management.core.aspect;

import cn.tucci.management.core.annotation.Limit;
import cn.tucci.management.core.exception.FrequentRequestsException;
import cn.tucci.management.core.response.ResultStatus;
import cn.tucci.management.core.util.WebUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author tucci.lee
 */
@Aspect
@Component
public class LimitAspect {

    private final RedisTemplate<String, Object> redisTemplate;

    private ExpressionParser parser = new SpelExpressionParser();
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    private static final String LIMIT_KEY = "limit:%s:%s";
    private static final String LIMIT_BEGINTIME = "beginTime";
    private static final String LIMIT_EXFREQUENCY = "exFrequency";

    @Autowired
    public LimitAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("@annotation(limit)")
    public void pointcut(Limit limit) {
    }

    @Before(value = "pointcut(limit)")
    public void before(JoinPoint pjp, Limit limit) {
        //获取请求的方法和key
        Method method = getMethod(pjp);
        String methodName = method.toString();
        Object key;
        if(limit.value().isEmpty()){
            key = WebUtil.getIp();
        }else {
            EvaluationContext context = bindParam(method, pjp.getArgs());
            Expression expression = parser.parseExpression(limit.value());
            key = expression.getValue(context);
        }

        //获取方法的访问周期和频率
        long cycle = limit.cycle();
        int frequency = limit.frequency();
        long currentTime = System.currentTimeMillis();

        //获取redis中周期内第一次访问方法的时间和执行的次数
        Long beginTimeLong = (Long) redisTemplate.opsForHash().get(String.format(LIMIT_KEY, methodName, key), LIMIT_BEGINTIME);
        Integer exFrequencyLong = (Integer) redisTemplate.opsForHash().get(String.format(LIMIT_KEY, methodName, key), LIMIT_EXFREQUENCY);

        long beginTime = beginTimeLong == null ? 0L : beginTimeLong;
        int exFrequency = exFrequencyLong == null ? 0 : exFrequencyLong;

        //如果当前时间减去周期内第一次访问方法的时间大于周期时间，则正常访问
        //并将周期内第一次访问方法的时间和执行次数初始化
        if (currentTime - beginTime > cycle * 1000) {
            redisTemplate.opsForHash().put(String.format(LIMIT_KEY, methodName, key), LIMIT_BEGINTIME, currentTime);
            redisTemplate.opsForHash().put(String.format(LIMIT_KEY, methodName, key), LIMIT_EXFREQUENCY, 1);
            redisTemplate.expire(String.format(LIMIT_KEY, methodName, key), limit.expireTime(), TimeUnit.SECONDS);
        } else {
            //如果在周期时间内，执行次数小于频率，则正常访问
            //并将执行次数加一
            if (exFrequency < frequency) {
                redisTemplate.opsForHash().put(String.format(LIMIT_KEY, methodName, key), LIMIT_EXFREQUENCY, exFrequency + 1);
                redisTemplate.expire(String.format(LIMIT_KEY, methodName, key), limit.expireTime(), TimeUnit.SECONDS);
            } else {
                //否则抛出访问频繁异常
                throw new FrequentRequestsException(ResultStatus.FREQUENT_REQUESTS.code(), limit.message());
            }
        }
    }

    /**
     * 获取当前执行的方法
     *
     * @param pjp
     * @return
     */
    private Method getMethod(JoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        return methodSignature.getMethod();
    }

    /**
     * 将方法的参数名和参数值绑定
     *
     * @param method 方法，根据方法获取参数名
     * @param args   方法的参数值
     * @return
     */
    private EvaluationContext bindParam(Method method, Object[] args) {
        EvaluationContext context = new StandardEvaluationContext();
        if(args == null || args.length == 0){
            return context;
        }
        //获取方法的参数名
        String[] params = discoverer.getParameterNames(method);

        //将参数名与参数值对应起来
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        return context;
    }
}
