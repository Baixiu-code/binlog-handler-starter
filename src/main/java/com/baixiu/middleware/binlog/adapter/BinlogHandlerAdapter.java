package com.baixiu.middleware.binlog.adapter;

import com.baixiu.middleware.binlog.model.BinlogData;
import com.baixiu.middleware.mq.model.CommonMessage;

/**
 * binlog 适配器接口
 * 适配中间件list:canal,jingwei,drc等。
 * function1:完成不同中间件解析能力
 * function2:完成不同中间件handlerMsg能力
 * @author baixiu
 * @date 2023年12月11日
 */
public interface BinlogHandlerAdapter {


    /**
     * 反序列MQMsg To binlogMsg
     * @param mqMsg mqMsg
     * @return
     */
    BinlogData deserializationMQMsg(CommonMessage mqMsg);

    /**
     * 反序列MQMsg To binlogMsg
     * @param mqMsg mqMsg
     * @return
     */
    void handleBinLogData(BinlogData binLogData) throws Exception;


}
