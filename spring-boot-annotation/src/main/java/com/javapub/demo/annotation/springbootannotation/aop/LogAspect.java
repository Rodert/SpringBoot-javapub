package com.javapub.demo.annotation.springbootannotation.aop;

/**
 * @Author: JavaPub
 * @License: https://github.com/Rodert/ https://gitee.com/rodert/
 * @Contact: https://javapub.blog.csdn.net/
 * @Date: 2022/1/25 15:42
 * @Version: 1.0
 * @Description: 注释式日志切面
 */

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect //@Aspect：声明该类为一个注解类
@Component
public class LogAspect {

    /**
     * @Pointcut：定义一个切点，后面跟随一个表达式，表达式可以定义为切某个注解，也可以切某个 package 下的方法；
     * <p>
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.javapub.demo.annotation.springbootannotation.*.*(..))")'
     */
    @Pointcut("@annotation(com.javapub.demo.annotation.springbootannotation.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * @param joinPoint
     * @return
     * @throws Throwable
     * @Around 环绕，可以在切入点前后织入代码，并且可以自由的控制何时执行切点；
     * @Description: 这里其实应该使用 try{}catch(){}finally{} 做容错，为了代码简洁易懂就不加了
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(joinPoint, time);
        return result;
    }

    void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = signature.getName();
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        System.out.println("**************************");
        System.out.println(method);
        log.info("------------------------接口日志-----------------------" + "\n"
                + "类名称:" + className + "\n"
                + "方法名:" + methodName + "\n"
                + "执行时间:" + time + "毫秒");
        log.info("接口参数" + "\n" + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 在切点之前，织入相关代码；
     *
     * @param joinPoint
     */
    @Before("logPointCut()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
        log.info("进入方法前执行.....");
    }

    /**
     * 在切点返回内容后，织入相关代码，一般用于对返回值做些加工处理的场景；
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) {
        log.info("方法的返回值 : {}", ret);
    }

    /**
     * 用来处理当织入的代码抛出异常后的逻辑处理;
     */
    @AfterThrowing("logPointCut()")
    public void throwss(JoinPoint jp) {
        log.info("方法异常时执行.....");
    }


    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("logPointCut()")
    public void after(JoinPoint jp) {
        log.info("方法最后执行.....");
    }
}
