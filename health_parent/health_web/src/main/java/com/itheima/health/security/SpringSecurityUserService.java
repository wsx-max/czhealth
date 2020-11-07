package com.itheima.health.security;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据登陆用户名称查询用户权限信息
        //t_user -> t_user_role -> t_role -> t_role_permission -> t_permission
        //找出用户所拥有的角色，及角色下所拥有的权限集合
        //User.roles(角色集合).permissions(权限集合)
        User user = userService.findByUsername(username);

        if (user != null){
            // 用户名
            // 密码
            String password = user.getPassword();
            // 权限集合
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            //授权

            //用户所拥有的角色
            SimpleGrantedAuthority sai = null;
            Set<Role> roles = user.getRoles();
            if (roles != null){
                for (Role role : roles) {
                    //角色用关键字，授予角色
                    sai = new SimpleGrantedAuthority(role.getKeyword());
                    authorities.add(sai);
                    //权限，角色下的权限
                    Set<Permission> permissions = role.getPermissions();
                    if (permissions != null){
                        for (Permission permission : permissions) {
                            //授予权限
                            sai = new SimpleGrantedAuthority(permission.getKeyword());
                            authorities.add(sai);
                        }
                    }
                }
            }
            return new org.springframework.security.core.userdetails.User(username,password,authorities);
        }
        return null;
    }
}


















