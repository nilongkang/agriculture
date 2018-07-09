package com.nlk.agriculture.controller;

import com.google.gson.Gson;
import com.nlk.agriculture.dao.*;
import com.nlk.agriculture.domain.SysFavor;
import com.nlk.agriculture.domain.SysTopic;
import com.nlk.agriculture.domain.SysTopicFc;
import com.nlk.agriculture.domain.SysTopicSc;
import com.nlk.agriculture.util.GetTime;
import com.nlk.agriculture.util.Reponse;
import com.nlk.agriculture.util.TopicHot;
import com.nlk.agriculture.util.TopicNew;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private SysTopicRepository sysTopicRepository;

    @Autowired
    private SysTopicFcRepository sysTopicFcRepository;


    @Autowired
    private SysFavorRepository sysFavorRepository;

    @Autowired
    private SysTopicScRepository sysTopicScRepository;

    private String qiuniuyun = "http://p8tngjpk7.bkt.clouddn.com";

    @ApiOperation( "用户发表话题(带token值)" )
    @PostMapping("/auth/inserttopic")
    public Reponse inserttopic(@RequestParam(value = "content_word") String content_word,
                               @RequestParam(value = "content_title") String content_title,
                               @RequestParam(value = "file",required = false) MultipartFile file){

        SysTopic sysTopic = new SysTopic( "null","null","null","null","null",0,0 ,0);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户

        sysTopic.setUsername( username );
        sysTopic.setAdd_time( GetTime.gettime() );
        sysTopic.setContent_title( content_title );
        sysTopic.setContent_word( content_word );
        System.out.println( username );
        if (file != null) {
            //System.out.println( "666" );
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("D:\\agri\\topicimg\\"+content_title+".jpg")));//保存图片到目录下
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
                String localFilePath = "D:\\agri\\topicimg\\"+content_title+".jpg";
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
                    sysTopic.setContent_image_url(filename);
                    sysTopicRepository.save(sysTopic);//增加用户
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
            return new Reponse(true,"用户话题发表成功(带图片)");
        } else {
            sysTopicRepository.save( sysTopic );
            return new Reponse(true,"用户话题发表成功(不带图片)");
        }
    }

    @ApiOperation( "获取个人发表过的所有话题(带token值)" )
    @PostMapping("/auth/getpersonaltopic")
    public Reponse getpersonaltopic(){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
        List<SysTopic> sysTopics = sysTopicRepository.findByUsername( username );
        return new Reponse(true,"获取数据成功",sysTopics);
    }

    @ApiOperation( "获取所有话题" )
    @PostMapping("/auth/getalltpoic")
    public Reponse getalltopic(@RequestParam(value = "page") Integer page,
                                      @RequestParam(value = "size") Integer size){
        List<SysTopic> sysTopics = sysTopicRepository.findAll();

        if (sysTopics.size() % size == 0) {
            return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page - 1), size * page ));
        }else {
            int a = sysTopics.size()/size + 1;
            if (page < a) return new Reponse(true,"数据获取成功",sysTopics.subList( size * (page -1), size * page ));
            else return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page -1),  sysTopics.size() ));
        }
    }

    @ApiOperation( "获取最新话题" )
    @PostMapping("/auth/getnewtopic")
    public Reponse getnewtopic(@RequestParam(value = "page") Integer page,
                                      @RequestParam(value = "size") Integer size){
        List<SysTopic> sysTopics = sysTopicRepository.findAll();

        TopicNew topicNew = new TopicNew();
        Collections.sort( sysTopics,topicNew );
        if (sysTopics.size() % size == 0) {
            return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page - 1), size * page ));
        }else {
            int a = sysTopics.size()/size + 1;
            if (page < a) return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page -1), size * page ));
            else return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page -1),  sysTopics.size() ));
        }
    }

    @ApiOperation( "获取最热话题" )
    @PostMapping("/auth/gethottopic")
    public  Reponse gethottopic(@RequestParam(value = "page") Integer page,
                                       @RequestParam(value = "size") Integer size){
        List<SysTopic> sysTopics = sysTopicRepository.findAll();

        TopicHot topicHot = new TopicHot();
        Collections.sort( sysTopics,topicHot );
        if (sysTopics.size() % size == 0) {
            return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page - 1), size * page ));
        }else {
            int a = sysTopics.size()/size + 1;
            if (page < a) return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page -1), size * page ));
            else return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page -1),  sysTopics.size() ));
        }

    }

    @ApiOperation( "获取话题详情页+增加话题点击量" )
    @PostMapping("/auth/gettopicdes/{id}")
    public Reponse gettopicdes(@PathVariable(value = "id") Long id){

        SysTopic sysTopic = sysTopicRepository.findById( id );
        int click_num = sysTopic.getClick_num();
        click_num++;
        sysTopic.setClick_num( click_num );
        sysTopicRepository.save( sysTopic );
        return new Reponse(true,"获取数据成功",sysTopic);
    }

    @ApiOperation( "话题点赞功能" )
    @PostMapping("/auth/agreetopic")
    public Reponse agreetopic(@PathVariable Long id){
        SysTopic sysTopic = sysTopicRepository.findById( id );
        int num = sysTopic.getAgree_num();
        num = num + 1;
        sysTopic.setAgree_num( num );
        sysTopicRepository.save( sysTopic );
        return new Reponse( true,"点赞成功" );
    }
    @ApiOperation( "对话题发表评论(带token值)" )
    @PostMapping(value = "/auth/topicfirstcomment/{topic_id}")
    public Reponse addcomment(@PathVariable Long topic_id,
                               @RequestParam(value = "comment") String comment){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
            SysTopic sysTopic = sysTopicRepository.findById( topic_id );
            SysTopicFc sysTopicFc= new SysTopicFc( "null","null","null","null" );
            sysTopicFc.setUsername( username );
            sysTopicFc.setComment_word( comment );
            sysTopicFc.setAdd_time( GetTime.gettime() );
            List<SysTopicFc> sysTopicFcs = sysTopic.getFcs(); //因为我在关联表时候定义的是L..类型
            sysTopicFcs.add(sysTopicFc);
            sysTopic.setFcs( sysTopicFcs );
            sysTopicRepository.save( sysTopic );
            return new Reponse(true,"评论成功");
        }catch (Exception e){
            return new Reponse(false,e.getMessage());
        }
    }

    @ApiOperation( "对评论进行评论(带token值)" )
    @PostMapping(value = "/auth/topicsecondcomment/{comment_id}")
    public Reponse addsecondcomment(@PathVariable Long comment_id,
                                       @RequestParam(value = "reply_type") long reply_type,
                                       @RequestParam(value = "comment") String comment){
        try {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
        SysTopicSc sysTopicSc = new SysTopicSc( 0,"null","null",0,"null","null","null");

        System.out.println( "comment:"+comment+" "+"reply_type:"+reply_type+"username:"+username);
        sysTopicSc.setReply_name( username );
        sysTopicSc.setComment_id( comment_id );
        sysTopicSc.setReply_type( reply_type );
        sysTopicSc.setComment_word( comment );
        sysTopicSc.setAdd_time( GetTime.gettime() );

        if (reply_type == 1){
            SysTopicFc sysTopicFc = sysTopicFcRepository.findById( comment_id );
            sysTopicSc.setReply_target( sysTopicFc.getUsername() );
            List<SysTopicSc> sysTopicScs = sysTopicFc.getScs();
            sysTopicScs.add( sysTopicSc );
            sysTopicFc.setSysTopicScs( sysTopicScs );
            sysTopicFcRepository.save( sysTopicFc );
        }else {
            SysTopicSc sysTopicSc1 = sysTopicScRepository.findById( comment_id );
            SysTopicFc sysTopicFc = sysTopicFcRepository.findById( sysTopicSc1.getComment_id() );
            sysTopicSc.setReply_target( sysTopicSc1.getReply_name() );
            List<SysTopicSc> sysTopicScs = sysTopicFc.getScs();
            sysTopicScs.add( sysTopicSc );
            sysTopicFc.setSysTopicScs( sysTopicScs );
            sysTopicFcRepository.save( sysTopicFc );

        }
        return new Reponse(true,"评论成功");
        }catch (Exception e){
            return new Reponse(false,e.getMessage());
        }
    }

    @ApiOperation( "搜索框功能" )
    @PostMapping(value = "/auth/topicsearch")
    public Reponse topicsearch(@RequestParam(value = "page") Integer page,
                                      @RequestParam(value = "size") Integer size,
                                      @RequestParam(value = "username") String username){
        List<SysTopic> sysTopics = sysTopicRepository.findByUsername( username );
        if (sysTopics.size() % size == 0) {
            return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page - 1), size * page ));
        }else {
            int a = sysTopics.size()/size + 1;
            if (page < a) return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page -1), size * page ));
            else return new Reponse(true,"获取数据成功",sysTopics.subList( size * (page -1),  sysTopics.size() ));
        }
    }

    @ApiOperation("话题收藏(带token值）")
    @PostMapping(value = "/auth/favor_topic/{id}")
    public Reponse favortopic(@PathVariable Long id){

        List<SysTopic> sysTopics;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
        SysFavor sysFavor;
        sysFavor = sysFavorRepository.findByUsername( username );

        SysTopic sysTopic = sysTopicRepository.findById( id );

        Integer favnum= sysTopic.getFav_num();
        favnum = favnum+1;
        sysTopic.setFav_num(favnum);

        if (sysFavor == null){
            SysFavor sysFavor1 = new SysFavor( "null" );
            sysTopics = new ArrayList<SysTopic>( );
            sysTopics.add( sysTopic );
            sysFavor1.setUsername( username );
            sysFavor1.setTopics( sysTopics );
            sysFavorRepository.save( sysFavor1 );
            return new Reponse(true,"收藏成功");}
        else {
            if (sysFavor.getTopics() != null) {
                sysTopics = sysFavor.getTopics();
                sysTopics.add( sysTopic );
                sysFavor.setTopics( sysTopics );
                sysFavorRepository.save( sysFavor );
                return new Reponse(true,"收藏成功");
            }else {

                sysTopics = new ArrayList<SysTopic>( );
                sysTopics.add( sysTopic );
                sysFavor.setTopics( sysTopics );
                sysFavorRepository.save( sysFavor );
                return new Reponse(true,"收藏成功");
            }
        }

    }

    @ApiOperation( "获取个人所收藏的话题(带token值)" )
    @GetMapping("/auth/getfavortopic")
    public Reponse getfavortopic(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SysFavor sysFavor = sysFavorRepository.findByUsername( username );
        List<SysTopic> sysTopics= sysFavor.getTopics();
        return new Reponse(true,"获取数据成功",sysTopics);
    }
}
