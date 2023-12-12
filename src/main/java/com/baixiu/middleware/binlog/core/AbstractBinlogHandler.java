package com.baixiu.middleware.binlog.core;

import com.baixiu.middleware.binlog.model.BinlogTableRowDiffModel;
import java.util.List;
import java.util.Map;

/**
 * @author baixiu
 * @date 创建时间 2023/12/12 11:31 AM
 */
public interface AbstractBinlogHandler {

    /**
     * 需要关心的字段。实现后将仅实现的字段值放置于 fieldValues 中
     * @return 监控字段
     */
    String[] getFields();

    /**
     * 需要关心的变更字段。实现后将仅实现的字段值放置于 changeList 中
     * @return 更新字段
     */
    String[] getUpdateFields();

    /**
     * 新增时触发
     * @param fieldValues 唯一字段，用于确定一条数据
     * @param changeList 字段的值发生变化的
     * @throws Exception 业务exception
     */
    void insert(Map<String, String> fieldValues, List<BinlogTableRowDiffModel> changeList) throws Exception;

    /**
     * 数据修改时触发
     * @param fieldValues 实现了getFields接口里得到的字段里的字段以及字段的值
     * @param changeList  字段的值发生变化的
     * @throws Exception 业务exception
     */
    void update(Map<String, String> fieldValues, List<BinlogTableRowDiffModel> changeList) throws Exception;

    /**
     * 删除时触发
     * @param fieldValues 唯一字段，用于确定一条数据
     * @throws Exception 业务exception
     */
    void delete(Map<String, String> fieldValues) throws Exception;

}
