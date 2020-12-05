package com.feng.eduservice.client;

import com.feng.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
//    声明远程调用的模块          执行熔断器加载的类
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    @DeleteMapping("/eduvod/video/removeAlyVideo/{videoId}")
    public R removeAlyVideo(@PathVariable("videoId") String videoId) ;

    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleBatch(@RequestParam("videoIdList") List<String> videoIdList) ;
}
