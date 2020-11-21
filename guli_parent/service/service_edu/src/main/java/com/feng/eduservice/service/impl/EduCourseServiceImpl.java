package com.feng.eduservice.service.impl;

import com.feng.eduservice.entity.EduCourse;
import com.feng.eduservice.entity.EduCourseDescription;
import com.feng.eduservice.entity.vo.CourseinfoVo;
import com.feng.eduservice.mapper.EduCourseMapper;
import com.feng.eduservice.service.EduCourseDescriptionService;
import com.feng.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author feng
 * @since 2020-11-20
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
   private EduCourseDescriptionService eduCourseDescriptionService;
    @Override
    public String saveCourseInfo(CourseinfoVo courseinfoVo) {
   //向课程中添加课程基本信息
       //coursrinfovo 转化为educourse对象
        EduCourse eduCourse = new EduCourse();
       BeanUtils.copyProperties(courseinfoVo,eduCourse);
//        eduCourse.setId(courseinfoVo.getId());
//        eduCourse.setTeacherId(courseinfoVo.getTeacherId());
//        eduCourse.setCover(courseinfoVo.getCover());
//        eduCourse.setLessonNum(courseinfoVo.getLessonNum());
//        eduCourse.setPrice(courseinfoVo.getPrice());
//        eduCourse.setSubjectId(courseinfoVo.getSubjectId());
//        eduCourse.setTitle(courseinfoVo.getTitle());
        int insert= baseMapper.insert(eduCourse);
        if(insert==0)
            throw new GuliException(20001,"添加课程失败");
    //向课程简介表添加课程简介
        String cid = eduCourse.getId();
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(cid);
        courseDescription.setDescription(courseinfoVo.getDescription());
        eduCourseDescriptionService.save(courseDescription);
        return cid;
    }
}
