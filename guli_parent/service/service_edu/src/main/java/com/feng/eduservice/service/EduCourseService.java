package com.feng.eduservice.service;

import com.feng.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.eduservice.entity.vo.CourseinfoVo;

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
}
