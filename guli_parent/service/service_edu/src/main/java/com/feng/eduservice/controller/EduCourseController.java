package com.feng.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.commonutils.R;
import com.feng.eduservice.entity.EduCourse;
import com.feng.eduservice.entity.EduTeacher;
import com.feng.eduservice.entity.vo.CoursePublishVo;
import com.feng.eduservice.entity.vo.CourseQuery;
import com.feng.eduservice.entity.vo.CourseinfoVo;
import com.feng.eduservice.entity.vo.TeacherQuery;
import com.feng.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author feng
 * @since 2020-11-20
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController {
   @Autowired
    private EduCourseService courseService;
   //添加课程信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseinfoVo courseinfoVo){
        //返回添加之后的课程id  为了后面的编辑
       String id= courseService.saveCourseInfo(courseinfoVo);

        return  R.ok().data("courseId",id);
    }
    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseinfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseinfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo courseInForm = courseService.getfinalpublishinfo(id);
        return R.ok().data("item",courseInForm);
    }

    //课程的最终发布  修改状态即可(其实就是一个修改)
    @PostMapping("publishCourse/{id}")
    public R PublishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal"); //设置状态
        courseService.updateById(eduCourse);
        return R.ok();
    }
   //查询所有课程
    @GetMapping("findAll")
    public R getallcourse(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("iist",list);
    }

    //条件查询带分页的方法
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) CourseQuery courseQuery) {
        //创建page对象
        Page<EduCourse> pageCourse = new Page<>(current,limit);

        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String teacherId = courseQuery.getTeacherId();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)) {
            //构建条件
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.ge("teacher_id",teacherId);
        }
        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        courseService.page(pageCourse,wrapper);

        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }


}

