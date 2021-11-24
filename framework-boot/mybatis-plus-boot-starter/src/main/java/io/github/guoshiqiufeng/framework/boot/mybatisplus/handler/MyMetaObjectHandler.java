package io.github.guoshiqiufeng.framework.boot.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis plus 自动填充 配置
 * @author yanghq
 * @version 1.0
 * @since 2021-05-10 15:26
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String LANG_INTEGER = "Integer";

    /**
     * 通用字段：创建时间
     */
    private static final String CREATE_DATE = "createDate";

    /**
     * 通用字段：更新时间
     */
    private static final String UPDATE_DATE = "modifyDate";

    /**
     * 通用字段：已删除
     */
    private static final String DELETED = "isDeleted";

    @Override
    public void insertFill(MetaObject metaObject) {
        // 判断是否有相关字段
        boolean hasCreateDate = metaObject.hasSetter(CREATE_DATE);
        boolean hasUpdateDate = metaObject.hasSetter(UPDATE_DATE);
        boolean hasDeleted = metaObject.hasSetter(DELETED);

        // 有字段则自动填充
        if (hasCreateDate) {
            strictInsertFill(metaObject, CREATE_DATE, LocalDateTime.class, LocalDateTime.now());
        }
        if (hasUpdateDate) {
            strictInsertFill(metaObject, UPDATE_DATE, LocalDateTime.class, LocalDateTime.now());
        }
        if (hasDeleted) {
            String type = metaObject.getGetterType(DELETED).getSimpleName();
            if (StringUtils.isNotBlank(type) && LANG_INTEGER.equals(type)) {
                strictInsertFill(metaObject, DELETED, Integer.class, 0);
            }
            else {
                strictInsertFill(metaObject, DELETED, Boolean.class, false);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object val = getFieldValByName(UPDATE_DATE, metaObject);
        // 没有自定义值时才更新字段
        if (val == null) {
            strictUpdateFill(metaObject, UPDATE_DATE, LocalDateTime.class, LocalDateTime.now());
        }
    }

}
