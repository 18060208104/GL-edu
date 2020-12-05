package com.feng.eduservice.client;

import com.feng.commonutils.R;
import com.feng.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
 //如果一旦执行了熔断器  他就会来到这个方法执行此输出
    @Override
    public R removeAlyVideo(String videoId) {
        return R.error().message("time out");
    }

    @Override
    public R deleBatch(List<String> videoIdList) {
        return R.error().message("time out");
    }
}