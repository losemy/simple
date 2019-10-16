package com.github.losemy.simple.run.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * Description: 处理通用字段：针对更新跟新增
 * Created by lose on 2019/8/25 9:53
 * @author lose
 */
public class CommonMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建时间
     */
    private static final String createTime = "createTime";
    /**
     * 修改时间
     */
    private static final String updateTime = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        setInsertFieldValByName(createTime, new Date(), metaObject);
        setInsertFieldValByName(updateTime, new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setUpdateFieldValByName(updateTime, new Date(), metaObject);
    }

}
