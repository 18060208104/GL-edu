package com.feng.eduservice.service.impl;

import com.feng.eduservice.entity.EduCourse;
import com.feng.eduservice.entity.EduCourseDescription;
import com.feng.eduservice.entity.vo.CoursePublishVo;
import com.feng.eduservice.entity.vo.CourseinfoVo;
import com.feng.eduservice.mapper.EduCourseMapper;
import com.feng.eduservice.service.EduChapterService;
import com.feng.eduservice.service.EduCourseDescriptionService;
import com.feng.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.eduservice.service.EduVideoService;
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
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;
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

    @Override
    public void updateCourseInfo(CourseinfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new GuliException(20001,"修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);

    }

    @Override
    public CourseinfoVo getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseinfoVo courseInfoVo = new CourseinfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public CoursePublishVo getfinalpublishinfo(String courseId) {
     return  baseMapper.getfinalpublishinfo(courseId);
    }
    //根据课程id删除课程
    @Override
    public boolean deletecourse(String courseId) {
    // 1.课程id删除小节
        videoService.deletevideo(courseId);
    // 2.课程id删除章节
        chapterService.delechapter(courseId);
    // 3.课程id删除描述  由于是1对1可以直接根据courseid删除
        eduCourseDescriptionService.removeById(courseId);
    // 4.删除课程
        int res=baseMapper.deleteById(courseId);
      return res>0;
        }
}
