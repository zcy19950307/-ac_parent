package com.atguigu.security.security;


import com.atguigu.servicebase.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultPasswordEncoder implements PasswordEncoder {


    public  DefaultPasswordEncoder(){
        this(-1);
    }

    public  DefaultPasswordEncoder(int strength){

    }
    // MD5 加密
    @Override
    public String encode(CharSequence charSequence) {

        return MD5.encrypt(charSequence.toString());
    }
    // 密码对比
    @Override
    public boolean matches(CharSequence charSequence, String encodePassword) {
        return encodePassword.equals(MD5.encrypt(charSequence.toString()));
    }
}
