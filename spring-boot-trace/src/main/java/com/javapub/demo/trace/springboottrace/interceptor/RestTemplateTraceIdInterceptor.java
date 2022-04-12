package com.javapub.demo.trace.springboottrace.interceptor;

import com.javapub.demo.trace.springboottrace.constant.Constants;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateTraceIdInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String traceId = MDC.get(Constants.TRACE_ID);
        if (traceId != null) {
            request.getHeaders().add(Constants.TRACE_ID, traceId);
        }
        return execution.execute(request, body);
    }

}
