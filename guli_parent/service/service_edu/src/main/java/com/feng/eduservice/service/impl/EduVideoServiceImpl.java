package com.feng.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feng.eduservice.client.VodClient;
import com.feng.eduservice.entity.EduVideo;
import com.feng.eduservice.mapper.EduVideoMapper;
import com.feng.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author feng
 * @since 2020-11-20
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    VodClient vodClient;
    @Override
    public void deletevideo(String courseId) {
      //根据课程id查询出所有的视频id  转换成List<String> 集合
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        wrapper.select("video_source_id");//只查询这一列的数据
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoid = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoid)){
                videoIds.add(videoid);
            }
        }
        if(videoIds.size()>0){
            vodClient.deleBatch(videoIds);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
