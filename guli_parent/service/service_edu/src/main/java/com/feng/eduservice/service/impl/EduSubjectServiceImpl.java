package com.feng.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.eduservice.entity.EduSubject;
import com.feng.eduservice.entity.excel.SubjectData;
import com.feng.eduservice.entity.subject.Onesubject;
import com.feng.eduservice.entity.subject.Twosubject;
import com.feng.eduservice.listener.SubjectExcelListener;
import com.feng.eduservice.mapper.EduSubjectMapper;
import com.feng.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author feng
 * @since 2020-11-18
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Onesubject> findAllsubject() {
        List<Onesubject> resultlist = new ArrayList<>();
        //1 首先要查出所有的一级分类   (id==0)
        List<EduSubject> onesubjectslist = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id","0");
        onesubjectslist =   baseMapper.selectList(queryWrapper);

        //2 查出所有的二级分类
        List<EduSubject> twosubjectlist = new ArrayList<>();
        QueryWrapper queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.ne("parent_id","0");  //不等于0
        twosubjectlist=baseMapper.selectList(queryWrapper1);
        //3 封装一级分类  通过循环添加  在一级循环的基础下循环查询出来的二级分类
        // 4 封装二级分类
        for (int i = 0; i <onesubjectslist.size() ; i++) {
            EduSubject edusubject = onesubjectslist.get(i);
            //把edusubject的值取出来放到Onesubject
            Onesubject onesubject = new Onesubject();
           onesubject.setId(edusubject.getId());
           onesubject.setTitle(edusubject.getTitle());

//遍历二级
            List<Twosubject> tworesult = new ArrayList<>();
            for(int m = 0;m<twosubjectlist.size();m++){  //在一级的基础上遍历二级  双层for循环 最后在添加到结果集
            EduSubject twosubject = twosubjectlist.get(m);
            if(twosubject.getParentId().equals(edusubject.getId())) {
                Twosubject tsubject = new Twosubject();
                BeanUtils.copyProperties("twosubject","tsubject");
               tsubject.setId(twosubject.getId());
               tsubject.setTitle(twosubject.getTitle());
                tworesult.add(tsubject);

             }
                    }
             onesubject.setChildren(tworesult);

            resultlist.add(onesubject);
        }
        return resultlist;
    }
}

