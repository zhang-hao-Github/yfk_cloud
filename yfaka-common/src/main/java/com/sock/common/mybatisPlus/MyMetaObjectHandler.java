package com.sock.common.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-02 00:48
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
//        this.strictUpdateFill(metaObject, "gmtCreate", () -> LocalDateTime.now(), LocalDateTime.class); // 起始版本 3.3.3(推荐)
        setFieldValByName("gmtCreate", new Date(), metaObject);
        setFieldValByName("gmtModified", new Date(), metaObject);
        log.info("gmtCreate  insertFill -----start---");
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("gmtModified", new Date(), metaObject);
        log.info("gmtCreate  updateFill-----start--- ");

    }
}
