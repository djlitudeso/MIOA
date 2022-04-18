package pers.jl.mioa.common.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.jl.mioa.common.domain.WebLog;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一日志处理切面
 *
 * @author JL

 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 切点表达式 execution(方法修饰符 返回类型 方法所属的包.类名.方法名称(方法参数))
     * //com.macro.mall.tiny.controller包中所有类的public方法都应用切面里的通知
     * execution(public * com.macro.mall.tiny.controller.*.*(..))
     * //com.macro.mall.tiny.service包及其子包下所有类中的所有方法都应用切面里的通知
     * execution(* com.macro.mall.tiny.service..*.*(..))
     * //com.macro.mall.tiny.service.PmsBrandService类中的所有方法都应用切面里的通知
     * execution(* com.macro.mall.tiny.service.PmsBrandService.*(..))
     */
    @Pointcut("execution(public * pers.jl.mioa.controller.*.*(..))||execution(public * pers.jl.mioa.*.controller.*.*(..))")
    public void webLog() {
    }

    /** @ Before：通知方法会在目标方法调用之前执行 */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    /** @ AfterReturning：通知方法会在目标方法返回后执行 */
    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    /** @ Around：通知方法会将目标方法封装起来 */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        //记录请求信息(通过Logstash传入Elasticsearch)
        WebLog webLog = new WebLog();
        Object result = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            webLog.setDescription(log.value());
        }
        long endTime = System.currentTimeMillis();
        String urlStr = request.getRequestURL().toString();
        webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
        webLog.setUsername(request.getRemoteUser());
        webLog.setIp(request.getRemoteAddr());
        webLog.setMethod(request.getMethod());
        webLog.setParameter(getParameter(method, joinPoint.getArgs()));
        webLog.setResult(result);
        webLog.setSpendTime((int) (endTime - startTime));
        webLog.setStartTime(startTime);
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(request.getRequestURL().toString());
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("url",webLog.getUrl());
        logMap.put("method",webLog.getMethod());
        logMap.put("parameter",webLog.getParameter());
        logMap.put("spendTime",webLog.getSpendTime());
        logMap.put("description",webLog.getDescription());
//        LOGGER.info("{}", JSONUtil.parse(webLog));
        LOGGER.info(Markers.appendEntries(logMap), JSONUtil.parse(webLog).toString());
        return result;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();

/*                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }*/

                if (StringUtils.hasLength(requestParam.value())) {
                    key = requestParam.value();
                }

                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}
