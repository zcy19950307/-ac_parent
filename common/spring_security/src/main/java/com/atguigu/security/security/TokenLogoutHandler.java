package com.atguigu.security.security;

import com.atguigu.servicebase.utils.R;
import com.atguigu.servicebase.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;


/**
 * 退出处理器
 */


public class TokenLogoutHandler implements LogoutHandler {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager,RedisTemplate redisTemplate){
            this.redisTemplate = redisTemplate ;
            this.tokenManager = tokenManager ;
    }


    @Override
    public void logout(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Authentication authentication) {

        String token = request.getHeader("token");

        if(token != null ){
            // 移除
            tokenManager.removeToken(token);
            // 用户信息
            String username = tokenManager.getUserInfoFromToken(token);

            redisTemplate.delete(username);

        }

        ResponseUtil.out(response, R.ok());

    }
}
