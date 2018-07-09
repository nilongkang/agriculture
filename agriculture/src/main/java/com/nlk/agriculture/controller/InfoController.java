package com.nlk.agriculture.controller;

import com.google.gson.Gson;
import com.nlk.agriculture.dao.SysUserInfoRepository;
import com.nlk.agriculture.domain.SysUserInfo;
import com.nlk.agriculture.util.JwtTokenUtil;
import com.nlk.agriculture.util.Reponse;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;



@RestController
public class InfoController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SysUserInfoRepository sysUserInfoRepository;

    @Value("${jwt.header}")
    private String tokenHeader;

    private String qiuniuyun = "http://p8tngjpk7.bkt.clouddn.com";

    /**
     * 保存用户信息到数据库
     * @param address
     * @param birth
     * @param sex
     * @param file
     * @return
     */


    @ApiOperation( "添加个人信息(带token值)" )
    @PostMapping(value = "/auth/insertinfo")
    public Reponse saveinfo(@RequestParam(value = "address") String address,
                            @RequestParam(value = "birth") String birth,
                            @RequestParam(value = "sex") String sex,
                            @RequestParam(value = "file") MultipartFile file
                          ){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
        SysUserInfo sysUserInfo = new SysUserInfo( "null","null","null","null","null" );
        sysUserInfo.setUsername( username );
        sysUserInfo.setSex( sex );
        sysUserInfo.setBirth( birth );
        sysUserInfo.setAddress( address );

        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("D:\\agri\\infoimg\\"+username+".jpg")));//保存图片到目录下
                out.write(file.getBytes());
                out.flush();
                out.close();
                //构造一个带指定Zone对象的配置类
                Configuration cfg = new Configuration( Zone.zone0());
                //...其他参数参考类注释
                UploadManager uploadManager = new UploadManager(cfg);
                //...生成上传凭证，然后准备上传
                String accessKey = "LlhKHbD0avuHz_MeS78bciYXbT_jckoS8hsIXXdX";
                String secretKey = "dbAbCt-_tIgMiTny2r0bGoLddTA4jlJrhHtDr1-5";
                String bucket = "agri";
                //如果是Windows情况下，格式是 D:\\qiniu\\test.png
                String localFilePath = "D:\\agri\\infoimg\\"+username+".jpg";
                //默认不指定key的情况下，以文件内容的hash值作为文件名
                String key = null;
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                try {
                    Response response = uploadManager.put(localFilePath, key, upToken);
                    //解析上传成功的结果
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                    System.out.println(putRet.key);
                    System.out.println(putRet.hash);
                    String filename=qiuniuyun+"/"+putRet.hash;
                    sysUserInfo.setUser_imageurl(filename);
                    sysUserInfoRepository.save(sysUserInfo);//增加用户
                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                    try {
                        System.err.println(r.bodyString());
                    } catch (QiniuException ex2) {
                        //ignore
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new Reponse(false,"上传失败," + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                return new Reponse(false,"上传失败," + e.getMessage());
            }
            //model.addAttribute(sysUserInfo);
            return new Reponse(true,"用户信息添加成功");
        } else {
            return new Reponse(false,"上传失败，因为文件是空的.");
        }
    }


    @ApiOperation( "获取个人信息(带token值)" )
    @GetMapping(value = "/auth/getinfo")
    public Reponse getinfo(){

        SysUserInfo sysUserInfo = new SysUserInfo( "null","null","null","null","null" );
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
        sysUserInfo = sysUserInfoRepository.findByUsername( username );
        return new Reponse(true,"获取数据成功",sysUserInfo);

    }



}
