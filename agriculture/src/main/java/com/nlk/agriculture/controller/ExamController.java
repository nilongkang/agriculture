package com.nlk.agriculture.controller;

import com.nlk.agriculture.dao.SysBankRepository;
import com.nlk.agriculture.dao.SysExamRepository;
import com.nlk.agriculture.domain.Result;
import com.nlk.agriculture.domain.SysBank;
import com.nlk.agriculture.domain.SysExam;
import com.nlk.agriculture.util.GetTime;
import com.nlk.agriculture.util.Reponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class ExamController {

    @Autowired
    private SysBankRepository sysBankRepository;

    @Autowired
    private SysExamRepository sysExamRepository;

    @ApiOperation( "向题库插入题目(带token值)" )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/auth/insertquestion")
    public Reponse insertquestion(@RequestParam(value = "question") String question,
                                  @RequestParam(value = "answer") String answer){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
            String add_time = GetTime.gettime();

            SysBank sysBank = new SysBank( "null","null","null","null" );
            sysBank.setUsername( username );
            sysBank.setAdd_time( add_time );
            sysBank.setQuestion( question );
            sysBank.setAnswer( answer );

            sysBankRepository.save( sysBank );
            return new Reponse(true,"题目插入成功");
        }catch (Exception e){
            return new Reponse(false,e.getMessage());
        }
    }

    @ApiOperation( "参加考试,获取题目(带token值)" )
   // @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/auth/test")
    public Reponse test(){

        List<SysBank>sysBanks = new ArrayList<>(  );
        sysBanks = sysBankRepository.findAll();

        Random random = new Random(  );
        int count = 0;//记录有效的随机数个数
        int[] a = new int[]{-1,-1,-1,-1,-1};
        while(count < 5){
            boolean flag = true;//用来标志的变量
            int r = random.nextInt(sysBanks.size());
            for(int i=0; i<a.length; i++){
                if(r == a[i]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                a[count] = r;
                System.out.println(r);
                count++;
            }
        }
        List<SysBank> sysBanks1 = new ArrayList<>(  );
        for ( int i=0; i<a.length; i++){
            sysBanks1.add( sysBanks.get(a[i]) );
        }
       return new Reponse(true,"获取成功，随机返回题目",sysBanks1);
    }

    @ApiOperation( "提交试卷，系统批改并计算得分(带token值)" )
    @PostMapping("/auth/getscore")
    public Reponse getscore(@RequestBody List<Result> result){

        SysExam sysExam = new SysExam( "null","null","null" );
        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前登录用户
        sysExam.setUsername( username );
        sysExam.setExam_time( GetTime.gettime() );

        int count = 0;
        SysBank sysBank = new SysBank( "null","null","null","null" );

        for(Result result1 : result){
            System.out.println( result1.getId()+"-------"+result1.getAnswer() );
            sysBank = sysBankRepository.findById( result1.getId() );
            if (sysBank.getAnswer().compareTo( result1.getAnswer() ) == 0){
                count++;
            }else ;
        }

        String score = String.valueOf( count*20 );
        sysExam.setScore( score );
        sysExamRepository.save( sysExam );
        return new Reponse(true,"考试成功，返回成绩",count*20);
    }
}
