package com.feng.eduservice.client;

import com.feng.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Component
@FeignClient("service-vod")
public interface VodClient {
    @DeleteMapping("/eduvod/video/removeAlyVideo/{videoId}")
    public R removeAlyVideo(@PathVariable("videoId") String videoId) ;
}
