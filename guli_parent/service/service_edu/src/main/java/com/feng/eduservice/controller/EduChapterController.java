package com.feng.eduservice.controller;


import com.feng.commonutils.R;
import com.feng.eduservice.entity.EduChapter;
import com.feng.eduservice.entity.chapter.ChapterVo;
import com.feng.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
   @Autowired
    EduChapterService chapterService;
   //课程大纲列表  根据课程id查询pmp run de
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getCharpterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }
    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.ok();
    }

    //根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除的方法
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }
}

