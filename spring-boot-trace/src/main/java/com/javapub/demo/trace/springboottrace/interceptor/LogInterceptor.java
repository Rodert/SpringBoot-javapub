package com.javapub.demo.trace.springboottrace.interceptor;

import com.javapub.demo.trace.springboottrace.constant.Constants;
import com.javapub.demo.trace.springboottrace.utils.TraceIdUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor implements HandlerInterceptor {

    //controller执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(Constants.TRACE_ID);
        if (traceId == null) {
            traceId = TraceIdUtil.getTraceId();
        }
        MDC.put(Constants.TRACE_ID, traceId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    //controller执行后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //视图渲染前
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(Constants.TRACE_ID);
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
