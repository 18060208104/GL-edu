package com.feng.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Onesubject {
    String id;
    String title;

    //要在一级分类中添加二级分类
    List<Twosubject> children = new ArrayList<>();
}
