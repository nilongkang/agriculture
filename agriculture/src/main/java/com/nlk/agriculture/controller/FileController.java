package com.nlk.agriculture.controller;

import com.google.gson.Gson;
import com.nlk.agriculture.dao.SysFileRepository;
import com.nlk.agriculture.domain.SysFile;
import com.nlk.agriculture.util.GetTime;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    private SysFileRepository sysFileRepository;

    private String qiuniuyun = "http://p8tngjpk7.bkt.clouddn.com";

    @ApiOperation( "用户上传文件(带token值)" )
    @PostMapping("/auth/fileupload")
    public Reponse fileupload(@RequestParam(value = "file_name") String file_name,
                              @RequestParam(value = "file")MultipartFile file){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
        SysFile sysFile = new SysFile( "null","null","null",0,"null" );
        sysFile.setAdd_time( GetTime.gettime() );
        sysFile.setFile_name( file_name );
        sysFile.setUsername( username );

        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("D:\\agri\\file\\"+file_name)));//保存图片到目录下
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
                String localFilePath = "D:\\agri\\file\\"+file_name;
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
                    sysFile.setFile_url( filename );
                    sysFileRepository.save(sysFile);//增加用户
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
            return new Reponse(true,"用户信息添加成功");
        } else {
            return new Reponse(false,"上传失败，因为文件是空的.");
        }

    }

    @ApiOperation( "获取所有用户上传的文件" )
    @GetMapping("/auth/getallfile")
    public Reponse getallfile(){
        return new Reponse(true,"获取数据成功",sysFileRepository.findAll());
    }

    @ApiOperation( "用户下载文件" )
    @PostMapping("/auth/downloadfile/{id}")
    public Reponse downloadfile(@PathVariable Long id){
        SysFile sysFile = new SysFile( "null","null","null",0,"null" );
        sysFile = sysFileRepository.findById( id );
        int num = sysFile.getDownloadnum();
        num = num+1;
        sysFile.setDownloadnum( num );
        sysFileRepository.save( sysFile );
        return new Reponse(true,"获取数据成功，返回文件url",sysFile.getFile_url());
    }

    @ApiOperation( "用户在线预览" )
    @PostMapping("/auth/preview/{id}")
    public Reponse preview(@PathVariable Long id){
        SysFile sysFile = new SysFile( "null","null","null",0,"null" );
        sysFile = sysFileRepository.findById( id );
        String file_url = sysFile.getFile_url();
        String file_newurl = "http://dcsapi.com/?k=345012355&url="+file_url;
        return new Reponse(true,"获取数据成功，返回文件新的url",file_newurl);
    }

    @ApiOperation( "获取该用户所上传过的所有文件(带token值)" )
    @GetMapping("/auth/getpersonalfile")
    public Reponse getpersonfile(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
        List<SysFile> sysFiles= sysFileRepository.findByUsername( username );
        return new Reponse(true,"获取数据成功",sysFiles);
    }

}
