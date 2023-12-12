package com.baixiu.middleware.binlog.core;

import java.util.Map;

/**
 * binlog table handler router
 * 用以加载tableName和具体binlog 业务handler的映射关系。
 * 业务handler通过
 * @author baixiu
 * @date 创建时间 2023/12/12 11:28 AM
 */
public class BinlogTableHandlerRouter {

    public Map<String,AbstractBinlogHandler> ALL_TABLE_HANDLERS;

}
