//package com.nlk.agriculture.service.impl;
//
//
//import com.nlk.agriculture.dao.SysVideoRepository;
//import com.nlk.agriculture.domain.SysVideo;
//import com.nlk.agriculture.domain.SysVideo2;
//import com.nlk.agriculture.service.Videoservice;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class VideoServiceImpl implements Videoservice {
//    @Autowired
//    SysVideoRepository sysVideoRepository;
//
//    @Override
//    public List<SysVideo2> queryVideo(String video_kind, Integer pageIndex, Integer pageSize) throws Exception {
//
//        Pageable pageable = new PageRequest(pageIndex, pageSize, Sort.Direction.DESC, "id");
//        Page<SysVideo> rulePage = sysVideoRepository.findAll( new Specification<SysVideo>(){
//            @Override
//            public Predicate toPredicate(Root<SysVideo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> list = new ArrayList<Predicate>();
//                if(null!=video_kind&&!"".equals(video_kind)){
//                    list.add(criteriaBuilder.equal(root.get("video_kind").as(String.class), video_kind));
//                }
//
//                Predicate[] p = new Predicate[list.size()];
//                return criteriaBuilder.and(list.toArray(p));
//            }
//        },pageable);
//        List<SysVideo> ruleEntities=rulePage.getContent();
//        List<SysVideo2> ruleDomains=new ArrayList<>();
//        BeanUtils.copyProperties(ruleEntities,ruleDomains);
//        return ruleDomains;
//
//    }
//
//}
