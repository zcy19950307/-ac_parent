package com.atguigu.security.security;


import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.lettuce.core.codec.CompressionCodec;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenManager {


    private  long tokenEcpiration =  24 * 60*60*1000 ;

    private  String tokenSigKey = "123456";


     // 1.生成有限 token
     public String  createToken(String username){

       return  Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+tokenEcpiration))
                .signWith(SignatureAlgorithm.ES512, tokenSigKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }
    // 2.根据token字符串得到用户信息
    public String getUserInfoFromToken(String token){

        return  Jwts.parser().setSigningKey(tokenSigKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 3. 删除token
    public void removeToken(String token) {




    }


}
