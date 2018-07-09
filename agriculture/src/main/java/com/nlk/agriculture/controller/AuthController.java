package com.nlk.agriculture.controller;

import com.nlk.agriculture.dao.SysUserRepository;
import com.nlk.agriculture.domain.SysRole;
import com.nlk.agriculture.domain.SysUser;
import com.nlk.agriculture.service.AuthService;
import com.nlk.agriculture.service.Roleservice;
import com.nlk.agriculture.service.Userservice;
import com.nlk.agriculture.util.JwtAuthenticationResponse;
import com.nlk.agriculture.util.Reponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @Autowired
    private Userservice userservice;

    @Autowired
    private Roleservice roleservice;

    @Autowired
    private SysUserRepository sysUserRepository;

    @ApiOperation( "用户注册" )
    @PostMapping(value = "/auth/register")
    public Reponse saveUser(@RequestParam(value = "username") String username,
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "permission") String permission){

        SysUser sysUser = sysUserRepository.findByUsername( username );

        if (sysUser == null) {
            SysRole role = new SysRole();
            role.setPermission( permission );
            SysRole sysRole = roleservice.Add( role );

            List<SysRole> roles = new ArrayList<>(); //因为我在关联表时候定义的是L..类型
            roles.add( sysRole );

            SysUser sysUser1 = new SysUser();
            sysUser1.setUsername( username );
            sysUser1.setPassword( password );
            sysUser1.setLastPasswordResetDate( new Date() );
            sysUser1.setRoles( roles ); //user信息添加完毕

            userservice.save( sysUser1 );
            return new Reponse(true,"用户注册成功");
        }else {
            return new Reponse(false,"用户名已被使用");
        }
    }

    @ApiOperation( "用户登录" )
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public Reponse createAuthenticationToken(@RequestParam(value = "username") String username,
                                                       @RequestParam(value = "password") String password) throws AuthenticationException{
        //  @RequestBody JwtAuthenticationRequest authenticationRequest
        final String token = authService.login(username,password);
        // Return the token
        return new Reponse(true,"登录成功",new JwtAuthenticationResponse(token));
    }

    @ApiOperation( "token值刷新(带token值)" )
    @RequestMapping(value = "/auth/refresh", method = RequestMethod.GET)
    public Reponse refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);

        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return new Reponse(false,"Token值刷新失败",ResponseEntity.badRequest().body(null));
        } else {
            return new Reponse(true,"Token值刷新成功",new JwtAuthenticationResponse(refreshedToken));
        }

    }


}
