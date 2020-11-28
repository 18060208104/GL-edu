package com.feng.eduservice.controller;


import com.feng.commonutils.R;
import com.feng.eduservice.client.VodClient;
import com.feng.eduservice.entity.EduVideo;
import com.feng.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author feng
 * @since 2020-11-20
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {
   @Autowired
    EduVideoService videoService;
   @Autowired
    VodClient vodClient;
    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    // TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //首先根据小节id查询出视频id
        EduVideo eduvideo = videoService.getById(id);
        String videosourceId = eduvideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videosourceId)){
        vodClient.removeAlyVideo(videosourceId);
        }
        videoService.removeById(id);
        return R.ok();
    }

    //修改小节 TODO

}

