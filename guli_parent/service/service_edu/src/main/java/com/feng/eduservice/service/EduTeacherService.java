package com.feng.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.eduservice.entity.vo.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author feng
 * @since 2020-11-13
 */
public interface EduTeacherService extends IService<EduTeacher> {
    void pageQuery(Page<EduTeacher> pageParm, TeacherQuery teacherQuery);
}
