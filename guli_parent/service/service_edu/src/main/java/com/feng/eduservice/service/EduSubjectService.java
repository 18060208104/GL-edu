package com.feng.eduservice.service;

import com.feng.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.eduservice.entity.subject.Onesubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author feng
 * @since 2020-11-18
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<Onesubject> findAllsubject();
}
