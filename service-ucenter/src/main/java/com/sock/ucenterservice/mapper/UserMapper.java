package com.sock.ucenterservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sock.common.entity.YfkUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-10-04
 */
public interface UserMapper extends BaseMapper<YfkUser> {


    YfkUser findUserinfo(@Param("mobile") String mobile);
}
