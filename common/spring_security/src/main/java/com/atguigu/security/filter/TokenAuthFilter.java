package com.atguigu.security.filter;

import com.atguigu.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class TokenAuthFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;


    public TokenAuthFilter(AuthenticationManager authenticationManager,TokenManager tokenManager,RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }




    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
      // 获取当前认证成功权限信息
       UsernamePasswordAuthenticationToken authentication   =  getAuthentication(request);
        // 判断如果有权限信息 放到权限上下文中
        if (authentication !=null){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request,response);


    }

   private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request ){
       String token = request.getHeader("token");

       if(token !=null){
            // 从token中获取用户名
           String username = tokenManager.getUserInfoFromToken(token);
           // 从redis中对应权限列表
           Collection<GrantedAuthority> permissionValueList = ( Collection<GrantedAuthority>) redisTemplate.opsForValue().get(username);

            return new UsernamePasswordAuthenticationToken(username,token,permissionValueList);
       }

        return null ;

   }


}
