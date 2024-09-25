package com.modules.license;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用拦截器拦截请求，验证证书的可用性
 *
 */
@Slf4j
@Component
public class LicenseCheckInterceptor implements HandlerInterceptor {

    /**
     * 进入controller层之前拦截请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入拦截器，验证证书可使用性");
        LicenseVerify licenseVerify = new LicenseVerify();

        //校验证书是否有效
        boolean verifyResult = licenseVerify.verify();

        if(verifyResult){
            log.info("验证成功，证书可用");
            return true;
        }else{
            log.info("验证失败，证书无效");
            response.setCharacterEncoding("utf-8");
            Map<String,String> result = new HashMap<>(2);
            result.put("code", "10066");
            result.put("result","Your certificate is invalid. Please verify if the server is authorized or reapply for the certificate.");

            response.getWriter().write(JSON.toJSONString(result));

            return false;
        }
    }

    /**
     * 处理请求完成后视图渲染之前的处理操作
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("处理请求完成后视图渲染之前的处理操作");
    }

    /**
     * 视图渲染之后的操作
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("视图渲染之后的操作");
    }

}
