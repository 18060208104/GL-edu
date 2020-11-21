package com.feng.eduservice.controller;


import com.feng.commonutils.R;
import com.feng.eduservice.entity.vo.CourseinfoVo;
import com.feng.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

