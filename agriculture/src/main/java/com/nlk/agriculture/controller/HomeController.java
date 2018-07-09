package com.nlk.agriculture.controller;


import com.nlk.agriculture.util.Ranadd;
import com.nlk.agriculture.util.Reponse;
import com.nlk.agriculture.util.Yunpian;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
public class HomeController {

    private int a = Ranadd.random();

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value="/admin/test1")
    @ResponseBody
    public String adminTest1() {
        return "ROLE_USER";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/admin/test2")
    @ResponseBody
    public String adminTest2() {
        return "ROLE_ADMIN";
    }

//    @RequestMapping("/")
//    public Msg index(Model model) {
//        Msg msg = new Msg("这里是你不登录看不到的首页", "下面是你权限的特权", "额外信息，只对管理员显示");
//        model.addAttribute("msg", msg);
//        return msg;
//    }

    /**
    获取短信验证码
     **/

    @ApiOperation( "通过手机号获取验证码" )
    @PostMapping("/auth/getverify")
    public Reponse getverify(@RequestParam(value = "mobile") String mobile) throws IOException {
    //int a = Ranadd.random();
    String result = Yunpian.sendSms(  "217ecd92735fbb86259c24b1e2aa0751","【智慧农业产学研】您的验证码是" + a + "。如非本人操作，请忽略本短信",mobile);

    if ( result != null) result = "true";
    else result = "false";

    return new Reponse(true,"获取数据成功",result);
    }

    /**
    校正验证码
     **/

    @ApiOperation( "检测验证码正确性" )
    @PostMapping("/auth/checkverify")
    public Reponse checkverify(@RequestParam(value = "yanzheng") int yanzheng){
    System.out.println( yanzheng + "," + a);
    if (yanzheng == a) return new Reponse( true,"验证码正确" );
    else  return new Reponse( false,"验证码错误" );
    }

}
