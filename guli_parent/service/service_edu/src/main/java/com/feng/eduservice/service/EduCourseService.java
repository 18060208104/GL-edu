package com.feng.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.eduservice.entity.front.CourseFrontVo;
import com.feng.eduservice.entity.front.CourseWebVo;
import com.feng.eduservice.entity.vo.CoursePublishVo;
import com.feng.eduservice.entity.vo.CourseinfoVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author feng
 * @since 2020-11-20
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseinfoVo courseinfoVo);

    void updateCourseInfo(CourseinfoVo courseInfoVo);

    CourseinfoVo getCourseInfo(String courseId);

    CoursePublishVo getfinalpublishinfo(String courseId);

    boolean deletecourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
