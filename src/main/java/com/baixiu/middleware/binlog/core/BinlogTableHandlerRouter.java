package com.baixiu.middleware.binlog.core;

import java.util.Map;

/**
 * binlog table handler router
 * ���Լ���tableName�;���binlog ҵ��handler��ӳ���ϵ��
 * ҵ��handlerͨ��
 * @author baixiu
 * @date ����ʱ�� 2023/12/12 11:28 AM
 */
public class BinlogTableHandlerRouter {

    public Map<String,AbstractBinlogHandler> ALL_TABLE_HANDLERS;

}
