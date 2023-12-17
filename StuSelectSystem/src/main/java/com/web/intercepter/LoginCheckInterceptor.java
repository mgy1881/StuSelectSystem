package com.web.intercepter;

import com.alibaba.fastjson2.JSONObject;
import com.web.domain.po.Result;
import com.web.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean ret = true;
        //3.获取请求头中的令牌（token）。
        String jwt = request.getHeader("token");

        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空,返回未登录的信息");
            ret = false;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）。
        if (ret)
            try {
                Claims claims = JwtUtils.parseJWT(jwt);
                System.out.println(claims);
                HttpSession session = request.getSession();
                session.setAttribute("id",claims.get("id"));
                String url = request.getRequestURI();
                log.info("请求路径是:{}", url);
                Integer level = (Integer) claims.get("level");
                if (url.startsWith("/admin") && level != 0)
                    ret = false;
                if (url.startsWith("/jiaowu/student") && level != 2)
                    ret = false;
                if (url.startsWith("/jiaowu/teacher") && level != 1)
                    ret = false;
            } catch (Exception e) {//jwt解析失败
                e.printStackTrace();
                log.info("解析令牌失败, 返回未登录错误信息");
                ret = false;
            }
        if (!ret) {
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        } else {
            //6.放行。
            log.info("令牌合法, 放行");
            return true;
        }

    }
}
