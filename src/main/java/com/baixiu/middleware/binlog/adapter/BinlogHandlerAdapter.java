package com.baixiu.middleware.binlog.adapter;

import com.baixiu.middleware.binlog.model.BinlogData;
import com.baixiu.middleware.mq.model.CommonMessage;

/**
 * binlog �������ӿ�
 * �����м��list:canal,jingwei,drc�ȡ�
 * function1:��ɲ�ͬ�м����������
 * function2:��ɲ�ͬ�м��handlerMsg����
 * @author baixiu
 * @date 2023��12��11��
 */
public interface BinlogHandlerAdapter {


    /**
     * ������MQMsg To binlogMsg
     * @param mqMsg mqMsg
     * @return
     */
    BinlogData deserializationMQMsg(CommonMessage mqMsg);

    /**
     * ������MQMsg To binlogMsg
     * @param mqMsg mqMsg
     * @return
     */
    void handleBinLogData(BinlogData binLogData) throws Exception;


}
