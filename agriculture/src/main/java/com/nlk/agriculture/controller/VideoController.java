package com.nlk.agriculture.controller;

import com.google.gson.Gson;
import com.nlk.agriculture.dao.SysCommentRepository;
import com.nlk.agriculture.dao.SysFavorRepository;
import com.nlk.agriculture.dao.SysSeCommentRepository;
import com.nlk.agriculture.dao.SysVideoRepository;
import com.nlk.agriculture.domain.*;
import com.nlk.agriculture.util.GetTime;
import com.nlk.agriculture.util.Reponse;
import com.nlk.agriculture.util.VideoHot;
import com.nlk.agriculture.util.VideoNew;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class VideoController {
    @Autowired
    SysVideoRepository sysVideoRepository;

    @Autowired
    SysFavorRepository sysFavorRepository;

    @Autowired
    SysCommentRepository sysCommentRepository;

    @Autowired
    SysSeCommentRepository sysSeCommentRepository;

    private String qiuniuyun = "http://p8tngjpk7.bkt.clouddn.com";


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation( "用户上传视频" )
    @PostMapping(value="/auth/insertvideo")
    public Reponse insertvideo(@RequestParam(value = "username") String username,
                               @RequestParam(value = "video_name") String video_name,
                               @RequestParam(value = "description") String description,
                               @RequestParam(value = "video_kind") String video_kind,
                               @RequestParam(value = "video_url") String video_url,
                               @RequestParam(value = "file") MultipartFile file
                              ) {

        String nowtime = GetTime.gettime();

        SysVideo sysVideo = new SysVideo( "null", "null", "null", "null", 0, 0, "null", "null", "null",0 );
        sysVideo.setUsername( username );
        sysVideo.setVideo_name( video_name );
        sysVideo.setVideo_kind( video_kind );
        sysVideo.setDescription( description );
        sysVideo.setAdd_time( nowtime );
        sysVideo.setVideo_url( video_url );

        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("D:\\agri\\videoimg\\"+video_name+".jpg")));//保存图片到目录下
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
                String localFilePath = "D:\\agri\\videoimg\\"+video_name+".jpg";
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
                    sysVideo.setVideo_img_url(filename);
                    sysVideoRepository.save(sysVideo);//增加用户
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

    @ApiOperation( "获取个人所有视频(带token值)" )
    @GetMapping(value = "/auth/getpersonalvideo")
    public Reponse getpersonalvideo(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Reponse(true,"获取数据成功",sysVideoRepository.findByUsername( username ));
    }

    @ApiOperation( "获取所有用户视频" )
    @PostMapping(value = "/auth/getallvideo")
    public Reponse getallvideo(@RequestParam(value = "page") Integer page,
                                      @RequestParam(value = "size") Integer size) {

        List<SysVideo> sysVideos = new ArrayList<>(  );
        sysVideos = sysVideoRepository.findAll();
        if (sysVideos.size() % size == 0) {
            return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page - 1), size * page ));
        }else {
            int a = sysVideos.size()/size + 1;
            if (page < a) return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page -1), size * page ));
            else return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page -1),  sysVideos.size() ));
        }
    }

    @ApiOperation( "获得最热视频" )
    @PostMapping(value = "/auth/gethotvideo")
    public Reponse gethotvideo(@RequestParam(value = "page") Integer page,
                                      @RequestParam(value = "size") Integer size){
        List<SysVideo> sysVideos = new ArrayList<SysVideo>( );
        sysVideos = sysVideoRepository.findAll();
        VideoHot comparatorHot=new VideoHot();
        Collections.sort(sysVideos, comparatorHot);
        if (sysVideos.size() % size == 0) {
            return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page - 1), size * page ));
        }else {
            int a = sysVideos.size()/size + 1;
            if (page < a) return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page -1), size * page ));
            else return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page -1),  sysVideos.size() ));
        }
    }

    @ApiOperation( "获得最新视频" )
    @PostMapping(value = "/auth/getnewvideo")
    public Reponse getnewvideo(@RequestParam(value = "page") Integer page,
                                      @RequestParam(value = "size") Integer size){
        List<SysVideo> sysVideos = new ArrayList<SysVideo>( );
        sysVideos = sysVideoRepository.findAll();
        VideoNew comparatorNew = new VideoNew();
        Collections.sort( sysVideos, comparatorNew );
        if (sysVideos.size() % size == 0) {
            return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page - 1), size * page ));
        }else {
            int a = sysVideos.size()/size + 1;
            if (page < a) return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page -1), size * page ));
            else return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page -1),  sysVideos.size() ));
        }
    }

    @ApiOperation("视频收藏(带token值）")
    @PostMapping(value = "/auth/favor_video/{id}")
    public Reponse favorvideo(@PathVariable Long id){

        List<SysVideo> sysVideos;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户

        SysFavor sysFavor;

        sysFavor = sysFavorRepository.findByUsername( username );

        SysVideo sysVideo = sysVideoRepository.findById( id );
        int favnum= sysVideo.getFav_num();
        favnum = favnum+1;
        sysVideo.setFav_num(favnum);
        if (sysFavor == null){
            SysFavor sysFavor1 = new SysFavor( "null" );
            sysVideos = new ArrayList<SysVideo>( );
            sysVideos.add( sysVideo );
            sysFavor1.setUsername( username );
            sysFavor1.setVideos( sysVideos );
            sysFavorRepository.save( sysFavor1 );
            return new Reponse(true,"收藏成功");}
        else {
            if (sysFavor.getVideos() != null) {
                sysVideos = sysFavor.getVideos();
                sysVideos.add( sysVideo );
                sysFavor.setVideos( sysVideos );
                sysFavorRepository.save( sysFavor );
                return new Reponse(true,"收藏成功");
            } else {
                sysVideos = new ArrayList<SysVideo>();
                sysVideos.add( sysVideo );
                sysFavor.setVideos( sysVideos );
                sysFavorRepository.save( sysFavor );
                return new Reponse(true,"收藏成功");
            }
        }
    }

    @ApiOperation( "获取个人所收藏的视频(带token值)" )
    @GetMapping("/auth/getfavorvideo")
    public Reponse getfavorvideo(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SysFavor sysFavor = sysFavorRepository.findByUsername( username );
        List<SysVideo> sysVideos = sysFavor.getVideos();
        return new Reponse(true,"获取数据成功",sysVideos);
    }

    @ApiOperation( "视频点赞功能" )
    @PostMapping("/auth/agreevideo/{id}")
    public Reponse agreevideo(@PathVariable Long id){
        SysVideo sysVideo = sysVideoRepository.findById( id );
        int num = sysVideo.getAgree_num();
        num = num + 1;
        sysVideo.setAgree_num( num );
        sysVideoRepository.save( sysVideo );
        return new Reponse(true,"点赞成功");
    }

    @ApiOperation( "对视频发表评论(带token值)" )
    @PostMapping(value = "/auth/firstcomment")
    public Reponse addcomment(@RequestParam(value = "video_id") Long video_id,
                               @RequestParam(value = "comment") String comment){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
            SysVideo sysVideo = sysVideoRepository.findById( video_id );
            SysComment sysComment = new SysComment( "null", "null" );
            sysComment.setUsername( username );
            sysComment.setComment( comment );
            List<SysComment> comments = sysVideo.getComments(); //因为我在关联表时候定义的是L..类型
            comments.add( sysComment );
            sysVideo.setComments( comments );
            sysVideoRepository.save( sysVideo );
            return new Reponse(true,"评论成功");
        }catch (Exception e){
            return new Reponse(false,e.getMessage());
        }
    }

    @ApiOperation( "对评论进行评论(带token值)" )
    @PostMapping(value = "/auth/secondcomment/{comment_id}")
    public Reponse addsecondcomment(@PathVariable Long comment_id,
                                       @RequestParam(value = "reply_type") Integer reply_type,
                                       @RequestParam(value = "comment") String comment){
        try {
            System.out.println( "reply_type:"+reply_type+" "+"comment:"+comment );
            String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
            SysSecondComment sysSecondComment = new SysSecondComment( (long) 0,"null", "null" ,0,"null","null");

            sysSecondComment.setComment_id( comment_id );
            sysSecondComment.setReply_name( username );
            sysSecondComment.setReply_type( reply_type );
            sysSecondComment.setComment( comment );
            sysSecondComment.setAdd_time( GetTime.gettime() );

            if (reply_type == 1){
                SysComment sysComment = sysCommentRepository.findById( comment_id );

                sysSecondComment.setReply_target( sysComment.getUsername() );
                List<SysSecondComment> sysSecondComments = sysComment.getSysSecondComments();
                sysSecondComments.add( sysSecondComment );
                sysComment.setSysSecondComments( sysSecondComments );
                sysCommentRepository.save( sysComment );
            }else {

                SysSecondComment sysSecondComment1 = sysSeCommentRepository.findById( comment_id );
                sysSecondComment.setReply_target( sysSecondComment1.getReply_name() );
                SysComment sysComment = sysCommentRepository.findById( sysSecondComment1.getComment_id() );
                List<SysSecondComment> sysSecondComments = sysComment.getSysSecondComments();
                sysSecondComments.add( sysSecondComment );
                sysComment.setSysSecondComments( sysSecondComments );
                sysCommentRepository.save( sysComment );
            }
            return new Reponse(true,"评论成功");
        }catch (Exception e){
            return new Reponse(false,e.getMessage());
        }
    }

    @ApiOperation( "获取视频详情页+增加视频点击量" )
    @PostMapping(value = "/auth/getvideodes/{id}")
    public Reponse getvideodes(@PathVariable Long id) {
        SysVideo sysVideo = sysVideoRepository.findById( id );
        int clicknum = sysVideo.getClick_num();
        clicknum = clicknum + 1;
        sysVideo.setClick_num( clicknum );
        sysVideoRepository.save( sysVideo );
        return new Reponse(true,"获取数据成功",sysVideo);

    }

    @ApiOperation( "搜索框功能实现" )
    @PostMapping(value = "/auth/search")
    public Reponse search(@RequestParam(value = "page") Integer page,
                                 @RequestParam(value = "size") Integer size,
                                 @RequestParam(value = "video_kind") String video_kind){
        List<SysVideo> sysVideos = sysVideoRepository.findByVideo_kind( video_kind );
        if (sysVideos.size() % size == 0) {
            return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page - 1), size * page ));
        }else {
            int a = sysVideos.size()/size + 1;
            if (page < a) return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page -1), size * page ));
            else return new Reponse(true,"获取数据成功",sysVideos.subList( size * (page -1),  sysVideos.size() ));
        }
    }
}
