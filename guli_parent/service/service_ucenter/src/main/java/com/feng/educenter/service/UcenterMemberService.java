package com.feng.educenter.service;

import com.feng.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author feng
 * @since 2020-12-08
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    //注册的方法
    void register(RegisterVo registerVo);
}
