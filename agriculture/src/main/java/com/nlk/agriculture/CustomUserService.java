package com.nlk.agriculture;


import com.nlk.agriculture.dao.SysUserRepository;
import com.nlk.agriculture.domain.SysUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/*
* 上面就是这个实现了org.springframework.security.core.userdetails.UserDetailsService接口的实现类，就是通
* 过实现这个类，我们根据登录用户的名称，取出用户信息，在封装到springSecurity要的User对象中，返回给s
*  pringSecurity框架来使用，就可以了。再配置文件中，我们通过springSecurity的配置文件来注入我们的这个
*   实现类。这样就实现了从数据库中，去取用户的登录信息。
* */

public class CustomUserService implements UserDetailsService {
    @Autowired
    SysUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.out.println("s:" + s);
        System.out.println("username:" + user.getUsername() + ";password:" + user.getPassword());
        return user;
    }
}