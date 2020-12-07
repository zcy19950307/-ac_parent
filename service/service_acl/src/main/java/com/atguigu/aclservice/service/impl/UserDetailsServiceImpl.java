package com.atguigu.aclservice.service.impl;

import com.atguigu.aclservice.entity.User;
import com.atguigu.aclservice.service.PermissionService;
import com.atguigu.aclservice.service.UserService;
import com.atguigu.security.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.selectByUsername(username);

        if(user == null )  throw  new UsernameNotFoundException("用户不存在");

        User curUser =  new User();
        BeanUtils.copyProperties(user,curUser);
        // 根据用户查询用户权限列表
        List<String> permissionValueByUser = permissionService.selectPermissionValueByUserId(user.getId());

        SecurityUser securityUser = new SecurityUser();
        securityUser.setPermissionValueList(permissionValueByUser);

        return securityUser;
    }
}
