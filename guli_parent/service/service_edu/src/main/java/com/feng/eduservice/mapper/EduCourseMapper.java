package com.feng.eduservice.mapper;

import com.feng.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author feng
 * @since 2020-11-20
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id查询出所需要显示的最终发布信息
    public CoursePublishVo getfinalpublishinfo(String courseId);
}
