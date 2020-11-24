package com.feng.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.eduservice.entity.EduChapter;
import com.feng.eduservice.entity.EduVideo;
import com.feng.eduservice.entity.chapter.ChapterVo;
import com.feng.eduservice.entity.chapter.VideoVo;
import com.feng.eduservice.mapper.EduChapterMapper;
import com.feng.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.eduservice.service.EduVideoService;
import com.feng.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author feng
 * @since 2020-11-20
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduVideoService videoService;//注入小节的service
    @Override
    public List<ChapterVo> getCharpterVideoByCourseId(String courseId) {

        //1 根据课程id的查询课程里面的所有章节
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(queryWrapper);

        //2 根据课程id查询课程里面所有的小节
        QueryWrapper queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(queryWrapper1);

        //定义结果集
        List<ChapterVo> res = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        for(int i=0;i<eduChapterList.size();i++){
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);  //先是资源  再是目标值
            //创建集合用于封装小节
            List<VideoVo> videoList = new ArrayList<>();
        //4 遍历查询小节list集合进行封装
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo = eduVideoList.get(j);
                if(eduChapter.getId().equals(eduVideo.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }
         chapterVo.setChildren(videoList);
            res.add(chapterVo);
    }
        return res;
    }

    @Override
    //删除章节
    public boolean deleteChapter(String chapterId) {
        //根据章节id查询小节表  如果小节表里面有数据就不能就行删除
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("chapter_id",chapterId);
        int count = videoService.count(queryWrapper2);
        if(count>0){//查询出有小节
            throw new GuliException(20001,"不能删除,章节下面有小节内容");
        }else{
            int result = baseMapper.deleteById(chapterId);
            return  result>0;
        }
      //  return false;
    }
}
