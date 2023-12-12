package com.baixiu.middleware.binlog.config;

/**
 * @author baixiu
 * @date 创建时间 2023/12/11 8:47 PM
 */
public class BinlogTableMatchHandlerConfig {

    private String tableName;

    private String handlerName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }
}
