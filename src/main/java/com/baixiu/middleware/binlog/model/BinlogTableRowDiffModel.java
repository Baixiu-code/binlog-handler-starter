package com.baixiu.middleware.binlog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据变化信息
 * @author baixiu
 * @since 2017/6/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinlogTableRowDiffModel {

    /**
     * db字段
     */
    private String field;

    /**
     * 变化前 值
     */
    private Object before;

    /**
     * 变化后 值
     */
    private Object after;



}
