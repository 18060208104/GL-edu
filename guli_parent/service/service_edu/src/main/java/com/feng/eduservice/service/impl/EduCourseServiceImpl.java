package com.feng.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.eduservice.entity.EduCourse;
import com.feng.eduservice.entity.EduCourseDescription;
import com.feng.eduservice.entity.front.CourseFrontVo;
import com.feng.eduservice.entity.front.CourseWebVo;
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
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//1 条件查询带分页查询课程
        @Override
        public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
            //2 根据讲师id查询所讲课程
            QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
            //判断条件值是否为空，不为空拼接
            if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
                wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
            }
            if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
                wrapper.eq("subject_id",courseFrontVo.getSubjectId());
            }
            if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
                wrapper.orderByDesc("buy_count");
            }
            if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
                wrapper.orderByDesc("gmt_create");
            }

            if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
                wrapper.orderByDesc("price");
            }

            baseMapper.selectPage(pageParam,wrapper);

            List<EduCourse> records = pageParam.getRecords();
            long current = pageParam.getCurrent();
            long pages = pageParam.getPages();
            long size = pageParam.getSize();
            long total = pageParam.getTotal();
            boolean hasNext = pageParam.hasNext();//下一页
            boolean hasPrevious = pageParam.hasPrevious();//上一页

            //把分页数据获取出来，放到map集合
            Map<String, Object> map = new HashMap<>();
            map.put("items", records);
            map.put("current", current);
            map.put("pages", pages);
            map.put("size", size);
            map.put("total", total);
            map.put("hasNext", hasNext);
            map.put("hasPrevious", hasPrevious);

            //map返回
            return map;
        }

        //根据课程id，编写sql语句查询课程信息
        @Override
        public CourseWebVo getBaseCourseInfo(String courseId) {
            return baseMapper.getBaseCourseInfo(courseId);
        }

    }
