package com.feng.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    String id;
    String title;
    List <VideoVo> children = new ArrayList<>();
}
