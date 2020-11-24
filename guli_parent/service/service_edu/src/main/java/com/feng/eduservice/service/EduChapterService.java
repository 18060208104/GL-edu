package com.feng.eduservice.service;

import com.feng.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author feng
 * @since 2020-11-20
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getCharpterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);
}
