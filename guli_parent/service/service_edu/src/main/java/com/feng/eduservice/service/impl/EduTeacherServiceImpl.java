package com.feng.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.eduservice.entity.EduTeacher;
import com.feng.eduservice.entity.vo.TeacherQuery;
import com.feng.eduservice.mapper.EduTeacherMapper;
import com.feng.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author feng
 * @since 2020-11-13
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParm, TeacherQuery teacherQuery) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("sort");
        if(teacherQuery==null){
            baseMapper.selectPage(pageParm,queryWrapper);
            return;
        }
        String name=teacherQuery.getName();
        Integer level=teacherQuery.getLevel();
        String begin=teacherQuery.getBegin();
        String end=teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }

        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
        baseMapper.selectPage(pageParm,queryWrapper);
    }

        //1 分页查询讲师的方法
        @Override
        public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam) {
            QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("id");
            //把分页数据封装到pageTeacher对象
            baseMapper.selectPage(pageParam,wrapper);

            List<EduTeacher> records = pageParam.getRecords();
            long current = pageParam.getCurrent();
            long pages = pageParam.getPages();
            long size = pageParam.getSize();
            long total = pageParam.getTotal();
            boolean hasNext = pageParam.hasNext();//下一页
            boolean hasPrevious = pageParam.hasPrevious();//上一页

            //把分页数据获取出来，放到map集合
            Map<String, Object> map = new HashMap<>();
            map.put("items", records);
            map.put("current", current);
            map.put("pages", pages);
            map.put("size", size);
            map.put("total", total);
            map.put("hasNext", hasNext);
            map.put("hasPrevious", hasPrevious);

            //map返回
            return map;
    }
}
